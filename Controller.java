// Eli Bosch, 3/12/2024, Controller for Assignment 5

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements MouseListener, KeyListener
{
	/*All Private variables and class references contained within the Controller Class */

	private View view;
	private Model model;
	private int clx, cly;
	private boolean keyUp, keyDown, mouseClFS, keyRight, keyLeft; //mouseCLFS is to make sure that if mouse is pressed and then edit mode is turned on if that mouse is then released that a wall wont be placed

	/*Class methods contaioned within Controller */

	public Controller(Model m, View v) // For Controller to reference the model class
	{
    	model = m; 
		view = v;
	}

	/*All the Mouse Listner Methods */

	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {	}

	public void mousePressed(MouseEvent e) //Sends the pixel cordinates of the mouse click to the model set destination 
	{
		
		if(model.getAdd()) //Sets the click cordinates into the wall class if edit and add wall mode are on
		{
			clx = e.getX();
			cly = e.getY() + view.getFrame();
			mouseClFS = true;

		} else {mouseClFS = false;}

		if(model.getPellet()) //Sets a pellet on a grid near where you clicked
		{
			Pellet temp = new Pellet(e.getX(),e.getY() + view.getFrame());
			temp.setGridPellet(); //This is just so that the pellets look nicer
			model.setSprite(temp);
		}

		if(model.getFruit()) //Sets a fruit where you clicked, however, it has random movement
		{
			Fruit temp = new Fruit(e.getX(),e.getY() + view.getFrame());
			model.setSprite(temp);
		}

		if(model.getGhost()) //Sets a ghost where you, also, with random movement
		{
			Ghost temp = new Ghost(e.getX(), e.getY() + view.getFrame());
			model.setSprite(temp);
		}


		if(model.getRemove()) //Turns on when all other modes are on except for editMode ofc
		{
			model.deleteSprite(e.getX(), e.getY() + view.getFrame());
		} 
	}

	public void mouseReleased(MouseEvent e) //This completes the positioning of the wall
	{   
		if(model.getAdd() && mouseClFS) 
		{	
			Wall temp = new Wall(0,0);
			temp.setGridWall(clx, cly, e.getX(), e.getY() + view.getFrame()); //Also utilizes a grid pattern because that how I wanted it
			model.setSprite(temp);
		}
	}

	/*All of the  KeyListner Methods */

	public void keyTyped(KeyEvent e){	}

	public void keyPressed(KeyEvent e) // checks which keys are being clicked and sets them to true
	{ 
		switch(e.getKeyCode())
		{
			
			case KeyEvent.VK_Q: System.exit(0); break;
			case KeyEvent.VK_ESCAPE: System.exit(0); break;

			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
		}
	}

	public void keyReleased(KeyEvent e) // once the keys are released it sets them to false
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_C: model.clearWalls(); break; //Clears walls if edit mode is on
			case KeyEvent.VK_E: model.toggleEdit(); break;
			case KeyEvent.VK_A: model.toggleAdd(); break;
			case KeyEvent.VK_P: model.togglePellet(); break;
			case KeyEvent.VK_G: model.toggleGhost(); break;
			case KeyEvent.VK_F: model.toggleFruit(); break;

			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;

			case KeyEvent.VK_S: //Saves 
			{
				Json saveOb = model.marshal();
				saveOb.save("map.json");
				break;
			}

			case KeyEvent.VK_L: //Loads previous saves
			{
				Json tmpList = Json.newObject();

				try
				{
					tmpList = Json.load("map.json");
				} catch(Exception ex) {
					ex.printStackTrace(System.err);
    				System.exit(1);
				}
			
				model.unmarshal(tmpList);
			}
		}
	}

	/*Constantly updated Controller Class which controls the movement of Pacman */

	public void update() 
	{
		if(keyUp){
			model.pac.moveUp();; //Controlls the up movement of pacman
		}else if(keyDown) {	
			model.pac.moveDown(); //COntrols the down movement of pacman
		}else if(keyRight) {
			model.pac.moveRight(); //Controls the right movement of pacman
		} else if(keyLeft) {
			model.pac.moveLeft(); //Controls the left movement of pacman
		} else {
			model.pac.stationary(); //Turns off the chomper when pacman is not moving
		}
	}	
}
