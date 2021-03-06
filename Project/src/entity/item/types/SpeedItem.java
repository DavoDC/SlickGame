package entity.item.types;

import components.modules.Player;
import base.Globals;

/**
 * Models an item that changes the player's speed
 *
 * @author David
 */
public class SpeedItem extends InstantItem {

    // The value of the speed modification
    float speedMod;

    /**
     * Create a speed item
     *
     * @param name
     * @param desc
     * @param soundName
     * @param col
     * @param row
     * @param speedMod
     */
    public SpeedItem(String name, String desc, String soundName,
            int col, int row, float speedMod) {

        // Call entity constructor
        super(name, new String[]{desc},
                soundName, col, row);

        // Save speed mod value
        this.speedMod = speedMod;

    }

    /**
     * Change the player's speed by the speed mod value
     *
     * @param player
     */
    @Override
    public void applyEffect(Player player) {
        Globals.player.changeMovSpeed(speedMod);
    }
}
