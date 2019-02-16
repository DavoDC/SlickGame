package Components.Popups;

import Main.Globals;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * Helps to display information in-game
 *
 * @author David Charkey
 */
public class PopupDisplayer
{

    // Time
    private int timeElapsed;
    
    // Popup list
    private ArrayList<Popup> popupList;

    /**
     * Create a popup displayer
     */
    public PopupDisplayer()
    {
        // Initialise time
        timeElapsed = 0;

        // Initialise popuplist
        popupList = new ArrayList<>();
        popupList.add(getIntroPopup());
        popupList.add(getHintPopup());

    }
    
    /**
     * Returns the intro popup
     */
    private Popup getIntroPopup()
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(8);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(20); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        
        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("You: Where am I?");
        textLines.add("You: I was supposed to awake from cryosleep on my home planet!");
        textLines.add("You: Something must have happened to Xadu ... ");
        textLines.add("Xadu: Kaxo, I crashed when a solar flare "
                + "damaged my electrospacemotor circuits");
        textLines.add("Kaxo: Xadu! You surprised me! I am so glad you are still alive!");
        textLines.add("Xadu: I do not dissipate that easily, my friend");
        textLines.add("Kaxo: You must be quite hurt though. "
                + "We need some conductive material to repair you");
        textLines.add("Xadu: Indeed. This rich planet is "
                + "bound to have some. Have a look around");
        textLines.add("Xadu: Come back to me if you find something");
        
        // Return
        return new Popup(feats, textLines);
    }
    
        /**
     * Returns the intro popup
     */
    private Popup getHintPopup()
    {
        // Features
        ArrayList<Object> feats = new ArrayList<>();
        feats.add(4);  // Tile grid row
        feats.add(2);  // Tile grid column 
        feats.add(17); // Width as number of tiles 
        feats.add(2);  // Height as number of tiles 
        feats.add(30); // Interval for delay writer
        feats.add("default"); // FontS or "default"
        
        // Text
        ArrayList<String> textLines = new ArrayList<>();
        textLines.add("Explore as much as you can. You'll find something to grab");
        
        // Return
        return new Popup(feats, textLines);
    }
    
    /**
     * Manages popups Handles input
     *
     * @param delta
     */
    public void updatePD(int delta)
    {
        // Increase time
        timeElapsed += delta;
        
        // Enable popups based off time
        if (atTime(100))
        {
            Globals.inputIgnored = true;
            popupList.get(0).setVisible(true);
        }
        
        // Enable popups based off conditions
        if (Globals.newHintAdded)
        {
            Globals.inputIgnored = true;
            //update hint popup
            popupList.get(1).setVisible(true);
        }
    }
    
    /**
     * Helps interpreting time data
     */
    private boolean atTime(int time)
    {
        boolean before = (time - 40) < timeElapsed;
        boolean after = (time + 40) > timeElapsed;
        
        return (before && after);
    }

    /**
     * Renders popups
     * @param g
     */
    public void renderPopups(Graphics g)
    {
        // Call render method of all popups
        for (Popup curP : popupList)
        {
            curP.show(g);
        }
    }

}