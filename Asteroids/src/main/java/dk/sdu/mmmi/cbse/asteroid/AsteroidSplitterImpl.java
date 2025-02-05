package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    private static final int MIN_ASTEROID_SIZE = 5; // Minimum size before asteroids stop splitting

    @Override
    public void createSplitAsteroid(Entity asteroid, World world) {
        float originalRadius = asteroid.getRadius();

        // Only split if the asteroid is large enough
        if (originalRadius <= MIN_ASTEROID_SIZE) {
            return;
        }

        // Create two smaller asteroids
        for (int i = 0; i < 2; i++) {
            Entity smallerAsteroid = new Asteroid();
            smallerAsteroid.setRadius(originalRadius / 2);// Reduce size
            int size = (int) smallerAsteroid.getRadius();
            smallerAsteroid.setX(asteroid.getX() + (i == 0 ? -10 : 10)); // Offset new asteroids
            smallerAsteroid.setY(asteroid.getY() + (i == 0 ? -10 : 10));
            smallerAsteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

            // Apply a slight random rotation for variety
            Random rand = new Random();
            smallerAsteroid.setRotation(rand.nextDouble() * 360);

            world.addEntity(smallerAsteroid);
        }
    }
}
