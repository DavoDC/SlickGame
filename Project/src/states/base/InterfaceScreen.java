package states.base;

import java.util.ArrayList;
import java.util.Random;

import base.Globals;
import components.buttons.ButtonGrid;

import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Models a game state which is a user interface screen
 *
 * @author David
 */
public abstract class InterfaceScreen extends AutoState {

    // Background
    private BigImage bg;

    // Handles button group
    private ButtonGrid buttonGrid;

    // Back control line
    private String backContLine;
    private TrueTypeFont backInstFont;

    /**
     * Access underlying ButtonGrid
     *
     * @return
     */
    public ButtonGrid getButtonGrid() {
        return buttonGrid;
    }

    /**
     * Initialize state
     *
     * @param gc
     * @param sbg
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        // If backgrounds have not been loaded, load them
        if (Globals.backgrounds.size() != Globals.BG_COUNT) {

            // Add every background to the array        
            for (int i = 1; i <= Globals.BG_COUNT; i++) {

                String imgPath = Globals.getFP("bg" + i + ".png");
                BigImage curBG = new BigImage(imgPath);
                curBG = (BigImage) curBG.getScaledCopy(
                        Globals.screenW, Globals.screenH);
                Globals.backgrounds.add(curBG);
            }
        }

        // Get random background
        Random rng = new Random();
        int ranIndex = rng.nextInt(Globals.BG_COUNT);
        bg = Globals.backgrounds.get(ranIndex);

        // Initialise and adjust cursor image if not done already
        if (Globals.cursor == null) {
            Globals.cursor = new Image(Globals.getFP("cursor.png"));
            Globals.cursor = Globals.cursor.getScaledCopy(0.75f);
        }

        // Apply cursor image
        gc.setMouseCursor(Globals.cursor, 0, 0);

        // Initialise font
        backInstFont = Globals.fontServer.getFont("Segoe UI-Italic-18");

        // Initialise buttons
        buttonGrid = new ButtonGrid(getButtonFeatures(), getButtonLabels());

        // Get back control line
        backContLine = Globals.conServer.getContInfo().get("Back");

        // Custom initialization
        customInit();
    }

    /**
     * Setup buttons here
     *
     * @return
     */
    public abstract ArrayList<Object> getButtonFeatures();

    /**
     * Setup button parameters
     *
     * @return
     */
    public abstract ArrayList<String> getButtonLabels();

    /**
     * Add additional post initialization tasks here
     */
    public void customInit() {
        // For overriding
    }

    /**
     * Update internal variables
     *
     * @param gc
     * @param sbg
     * @param delta
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {

        // Do custom pre update
        customUpdate();

        // Handle input
        Globals.conServer.handleInput(
                new String[]{"Back", "Screenshot"});
    }

    /**
     * Do custom pre updating here
     */
    public void customUpdate() {
        // For overriding
    }

    /**
     * Get the name of the state to go back to
     *
     * @return
     */
    public String getBackState() {
        return "Menu";
    }

    /**
     * Handle graphics rendering
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {

        // Draw background (darkened or not)
        if (isDarkened()) {
            g.drawImage(bg, 0, 0, Color.darkGray);
        } else {
            g.drawImage(bg, 0, 0);
        }

        // Draw buttons
        buttonGrid.drawButtons(g);

        // If not in main menu, write instructions for going back
        if (!Globals.isGameInState("Menu")) {
            g.setColor(Color.white);
            backInstFont.drawString(10, Globals.screenH - 40,
                    "To go back, press: " + backContLine);
        }

        // Do custom post rendering
        customRender(g);
    }

    /**
     * Set whether the background will be darkened
     *
     * @return
     */
    public abstract boolean isDarkened();

    /**
     * Do custom post rendering here
     *
     * @param g
     */
    public void customRender(Graphics g) {
        // For overriding
    }

}
