package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter;

    public CollisionDetector() {
        // Load the Asteroid Splitter service dynamically
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);
        for (IAsteroidSplitter splitter : loader) {
            this.asteroidSplitter = splitter;
            break; // Use the first found implementation
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // Skip self-comparison
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // Check for collision
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Asteroid && asteroidSplitter != null) {
                        asteroidSplitter.createSplitAsteroid(entity1, world);
                    }
                    if (entity2 instanceof Asteroid && asteroidSplitter != null) {
                        asteroidSplitter.createSplitAsteroid(entity2, world);
                    }

                    // Remove both entities after collision
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                }
            }
        }
    }

    private Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
