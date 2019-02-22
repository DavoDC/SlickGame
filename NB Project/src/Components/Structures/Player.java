package Components.Structures;

import Entity.Item.Item;
import Main.Globals;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Models the player
 *
 * @author David
 */
public class Player
{

    // Player location
    private int xPos;
    private int yPos;

    // Starting location
    private final int START_X = 300;
    private final int START_Y = 300;

    // Dimensions of player
    private double scale; // Size factor
    private int width;
    private int height;

    // Movement speed of player
    private float movSpeed;

    // Spritesheet holding all frames of player
    private SpriteSheet sprites;

    // Animation of player moving
    private int animSpeed; 
    private Animation anim; 
    
    // Amount of animSpeed per movSpeed
    private final int speedRatio = 2000; 

    // Inventory
    private ArrayList<Item> inv;

    // Glitch status
    private boolean glitched;

    /**
     * Create a player with preset values
     */
    public Player()
    {
        // Initialise fields
        xPos = START_X;
        yPos = START_Y;

        scale = 1.5;
        width = 33;
        height = 48;

        movSpeed = 0.20f; // Default = 0.20

        try
        {
            sprites = new SpriteSheet(Globals.playerSprRes, width, height);
        }
        catch (SlickException e)
        {
        }

        harmonizeSpeeds();
        anim = new Animation(sprites, animSpeed);

        inv = new ArrayList<>();

        glitched = false;

        // Adjust animation
        anim.stop();  // Prevents animation from starting on its own
        anim.setCurrentFrame(1); // Puts animation on "standing still" frame

    }

    private void harmonizeSpeeds()
    {
        animSpeed = (int) ((1/movSpeed) * speedRatio);
    }
    
    public int getX()
    {
        return xPos;
    }

    public int getY()
    {
        return yPos;
    }

    public void adjustX(int change)
    {
        // Adjust x
        xPos += change;

        // Prevent negative values
        if (xPos < 0)
        {
            xPos -= change;
        }

        // Account for glitch
        xPos += getGlitchValue();

    }

    public void adjustY(int change)
    {
        // Adjust x
        yPos += change;

        // Prevent negative values
        if (yPos < 0)
        {
            yPos -= change;
        }

        // Account for glitch
        yPos += getGlitchValue();
    }

    private int getGlitchValue()
    {
        if (glitched)
        {
            int randomVal = 0;

            // Add random value
            randomVal += (int) (Math.random() * 10);

            // Apply random sign
            boolean flip = Math.random() >= 0.5;
            randomVal = randomVal * (flip ? -1 : 1);

            return randomVal;
        }

        return 0;
    }

    public void resetLocation()
    {
        xPos = START_X;
        yPos = START_Y;
    }

    public float getMovSpeed()
    {
        return movSpeed;
    }

    public void changeMovSpeed(float change)
    {
        movSpeed += change;
        harmonizeSpeeds();
    }

    public void updateAnimation(int delta)
    {
        anim.update(delta);
    }

    /**
     * Starts the walking animation in a given direction
     *
     * @param dir Direction of motion
     */
    public void startAnim(String dir)
    {
        anim.start();

        String frameConfig = "n0";

        switch (dir)
        {
            case "up":
                frameConfig = "n9n10n11"; // back
                break;
            case "down":
                frameConfig = "n0n1n2"; //front 
                break;
            case "left":
                frameConfig = "n3n4n5"; //left
                break;
            case "right":
                frameConfig = "n6n7n8"; //right
                break;
        }

        configureFrames(frameConfig);
    }

    /**
     * Adjusts an animation to only use the referenced frames Frame numbers are
     * prefixed by "n" Example: "n0n1n2" (for first three frames)
     *
     * @param configS
     */
    public void configureFrames(String configS)
    {
        // Get frame count 
        int frameCount = anim.getFrameCount();

        //Turn string into ArrayList
        ArrayList<Integer> config = new ArrayList<>();
        for (String curNo : configS.split("n"))
        {
            if (curNo.length() != 0)
            {
                config.add(Integer.parseInt(curNo));
            }
        }

        // Process config
        for (int i = 0; i < frameCount; i++)
        {

            //If inputted, activate frame
            if (config.contains(i))
            {
                anim.setDuration(i, animSpeed);
            }
            else //If not, deactivate
            {
                anim.setDuration(i, 0);
            }
        }
    }

    /**
     * Stop the player's animation
     */
    public void stopAnim()
    {
        anim.stop();
    }

    /**
     * Draw the player at the coordinates Enlarges the player sprite beforehand
     *
     * @param pX
     * @param pY
     */
    public void drawPlayer(int pX, int pY)
    {
        int largerW = (int) (width * scale);
        int largerH = (int) (height * scale);

        anim.draw((int) pX, (int) pY, largerW, largerH);
    }

    /**
     * Add to player's inventory
     *
     * @param item
     */
    public void addItem(Item item)
    {
        inv.add(item);
    }

    /**
     * Check if the player has a given item Uses "contains"
     *
     * @param itemName
     * @return
     */
    public boolean hasItem(String itemName)
    {
        // Check for item name
        for (Item curItem : inv)
        {
            // Get current item's name
            String curItemS = curItem.getName();

            // Return true and stop searching, if matches input
            if (curItemS.contains(itemName))
            {
                return true;
            }
        }

        // Default
        return false;
    }

    /**
     * Get all items
     *
     * @return
     */
    public ArrayList<Item> getInv()
    {
        return inv;
    }

    /**
     * Activate movement glitch
     */
    public void activateGlitch()
    {
        glitched = true;
    }

    /**
     * Turn off movement glitch
     */
    public void disableGlitch()
    {
        glitched = false;
    }

}
