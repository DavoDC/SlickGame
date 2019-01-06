
package code.GameState;

import java.awt.Graphics2D;

import code.Manager.Content;
import code.Manager.GameStateManager;
import code.Manager.JukeBox;
import code.Manager.Keys;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * The pause GameState can only be activated by calling GameStateManager.setPaused(true).
 * @author David Charkey
 */
public class PauseState extends GameState {
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
	}
	
        @Override
	public void init() {}
	
        @Override
	public void update() {
		handleInput();
	}
	
    /**
     * Draw pause menu
     * @param g Graphics
     */
    @Override
	public void draw(Graphics2D g) {

                Content.drawString(g, "paused", 40, 30);
		
		Content.drawString(g, "arrow", 12, 76);
		Content.drawString(g, "keys", 16, 84);
		Content.drawString(g, ": move", 52, 80);
		
		Content.drawString(g, "space", 12, 96);
		Content.drawString(g, ": action", 52, 96);
		
		Content.drawString(g, "F1:", 36, 112);
		Content.drawString(g, "return", 68, 108);
		Content.drawString(g, "to menu", 68, 116);
		
	}
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(false);
			JukeBox.resumeLoop("music1");
		}
		if(Keys.isPressed(Keys.F1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENU);
		}
	}
	
}