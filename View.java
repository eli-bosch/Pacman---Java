// Eli Bosch, 3/12/24, Controls what is viewed

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.util.Iterator;


public class View extends JPanel
{
	/*Private variables in the View Class */

	private Model model;
	private int frame = 0;
	static JLabel l;

	/*Constructor for View Class */

	public View(Model m)
	{
		model = m;
	}

	/*Getter and Setters for the View Class */

	public void changeFrame(int direction) //Used to simulate a scrolling camera
	{
		frame+=510*direction;
	}

	public int getFrame()//Returns what positions the camera is in
	{
		return frame;
	}

	/*Imaging Loading Static Method*/

	public static BufferedImage openImage(String file)
	{
		try{
			
			return ImageIO.read(new File(file)); 

		} catch (Exception e)
		{
			e.printStackTrace(System.err);
    		System.exit(1);
		}

		return null;
	}

	/*Jframe Painting Method */

	public void paintComponent(Graphics g) // creates the cyan color and prints the constantly moving turtle
	{	
		//Changes the background color depending on what edit mode is on

		g.setColor(new Color(0, 0, 0));
		g.fillRect(0,0 , this.getWidth(), this.getHeight());
		
		if(model.getEdit()) //Used to get the grid pattern once editMode is on (Hurts your eyes I know)
		{
			if(model.getAdd()) 
			{
				g.setColor(new Color(255, 0, 255));
			}
			else if(model.getPellet())
			{
				g.setColor(new Color(255, 255, 255));
			}			
			else if(model.getFruit())
			{
				g.setColor(new Color(0, 255, 0));
			}
			else if(model.getGhost())
			{
				g.setColor(new Color(128, 128, 128));
			}
			else
			{
				g.setColor(new Color(128, 0, 0));
			}

			for(int i = 0; i < this.getWidth(); i+=Model.GRID_SIZE)
			{
				g.fillRect(0, i - 1, this.getWidth(), 3);
				g.fillRect(i-1, 0, 3, this.getHeight());
			}
		}

		for(Iterator<Sprite> s = model.sprites.iterator(); s.hasNext();) //Prints out all of the sprites no matter what oOo
		{
			s.next().draw(g, frame);
		}

		//Changes the frame based on Pacman's position

		if(model.pac.getY() <= getFrame() - model.pac.getH())
			changeFrame(-1);

		if (model.pac.getY() >= this.getHeight() + frame)
			changeFrame(1);
		
			
	}
}
