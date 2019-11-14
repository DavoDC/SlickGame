package states;

import main.Globals;
import challenge.Challenge;
import components.helpers.FontServer;
import components.structures.Camera;
import components.structures.HUD;
import components.structures.Map;
import components.structures.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Shows a loading screen while loading the game's resources
 *
 * @author David
 */
public class Loading extends BasicGameState
{

    // Background image
    private Image introLogo;

    // Text font
    private TrueTypeFont font;

    // Time variables
    private final long startRef = Globals.agc.getTime(); // Starting time
    private final long introTime = 3669; // Duration of loading

    // State loading status
    private boolean statesLoaded;

    /**
     * Used to identify this state
     *
     * @return
     */
    @Override
    public int getID()
    {
        return 0;
    }

    /**
     * Loads the game's resources
     *
     * @param gc
     * @param sbg
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        try
        {
            // Set state loading status
            statesLoaded = false;

            // Initialise and adjust background
            introLogo = new Image("res/misc/intro.png");
            introLogo = introLogo.getScaledCopy(Globals.screenW, Globals.screenH);

            // Initialise font
            font = FontServer.getFont("gamefont-plain-75");

            // Special initialisations
            Globals.SBG = sbg;
            Globals.agc.setDefaultFont(FontServer.getFont("Segoe UI-Plain-16"));

            // Create structures
            Globals.map = new Map(Globals.mapRes);
            Globals.alien = new Player();
            Globals.cam = new Camera(gc, Globals.map);
            Globals.cam.centerOn(Globals.alien.getX(), Globals.alien.getX());
            Globals.hud = new HUD(Globals.cam, Globals.alien);

            // Initialise and start music
            Globals.agc.setMusicOn(true);
            Globals.agc.setSoundOn(true);
            Globals.agc.setMusicVolume(0.69f);
            Globals.ambientMusic = new Music(Globals.ambMusRes);
            Globals.ambientMusic.loop();

        }
        catch (SlickException ex)
        {
            System.err.println("Error in 'init' of Loading");
        }

    }

    /**
     * Add all states. ID numbers are determined automatically. Loading state is
     * not included as it is already added as state 0.
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        // If states have not been loaded yet
        if (!statesLoaded)
        {
            // Add states to directory and game
            Globals.STATES.put("ABOUT", Globals.STATES.size() + 1);
            sbg.addState(new About());

            Globals.STATES.put("CONTROLS", Globals.STATES.size() + 1);
            sbg.addState(new Controls());

            Globals.STATES.put("CREDITS", Globals.STATES.size() + 1);
            sbg.addState(new Credits());

            Globals.STATES.put("EXIT", Globals.STATES.size() + 1);
            sbg.addState(new Exit());

            Globals.STATES.put("GAMEOVER", Globals.STATES.size() + 1);
            sbg.addState(new GameOver());

            Globals.STATES.put("MAINMENU", Globals.STATES.size() + 1);
            sbg.addState(new MainMenu());

            Globals.STATES.put("PLAY", Globals.STATES.size() + 1);
            sbg.addState(new ActualGame());

            Globals.STATES.put("SETTINGS", Globals.STATES.size() + 1);
            sbg.addState(new Settings());

            Globals.STATES.put("CHALLENGE", Globals.STATES.size() + 1);
            sbg.addState(new Challenge());

            Globals.STATES.put("INVENTORY", Globals.STATES.size() + 1);
            sbg.addState(new Inventory());

            // Update status
            statesLoaded = true;
        }

        // Initialize SBG
        sbg.init(gc);

        // Wait a bit, to allow loading screen to be shown
        if (Globals.agc.getTime() > startRef + introTime)
        {
            // Enter main menu
            sbg.enterState(Globals.STATES.get("MAINMENU"),
                    new FadeOutTransition(Color.black, 2000), // Leave
                    new FadeInTransition(Color.black, 10) // Enter
            );
        }

    }

    /**
     * Draws background and loading text
     *
     * @param gc
     * @param game
     * @param g
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g)
    {
        // Draw intro/logo background
        g.drawImage(introLogo, 0, 0);

        // Write text = 'LOADING'
        int Xpos = (Globals.screenW / 2) - 150;
        int Ypos = Globals.screenH - 100;
        font.drawString(Xpos, Ypos, "LOADING");
    }

}