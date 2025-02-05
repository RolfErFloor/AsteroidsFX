package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(GameData gameData, World world) {

        // Schedule the enemy ship creation every 15 seconds
        scheduler.scheduleAtFixedRate(() -> {
            Entity enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
            System.out.println("Enemy created at: " + enemy.getX() + ", " + enemy.getY());
        }, 0, 15, TimeUnit.SECONDS);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/3);
        enemyShip.setRadius(8);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
        scheduler.shutdown();
    }

}
