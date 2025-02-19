package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author corfixen
 */
public class AsteroidPlugin implements IGamePluginService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r->{
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });
    @Override
    public void start(GameData gameData, World world) {

        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }, 0, 3, TimeUnit.SECONDS);



        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
        scheduler.shutdownNow();
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        return asteroid;
    }
}
