//Eli Bosch, 3/12/2023, Pacman class for Assignment 5

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pacman extends Sprite
{
    /*All the private variables for the Pacman Class */

    private int Animation, Direction = 0;
    private double Speed = 7;
    private boolean moving = false;
    static BufferedImage[][] pacmanImages = null;

    /*Contructor for the Pacman Class */

    public Pacman()
    {
        super(100, 175, 25, 25);

        this.loadImage();

    }

    Pacman(Json ob)
    {
        super((int)ob.getLong("x-value"), (int)ob.getLong("y-value"), (int)ob.getLong("Width"), (int)ob.getLong("Height"));

        this.loadImage();
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x-value", x);
        ob.add("y-value", y);
        ob.add("Width", w);
        ob.add("Height", h);
        return ob;
    }

    //All Movement methods for Pacman

    public void moveRight()
    {
        x+=Speed;
        Direction = 3;
        moving = true;
    }

    public void moveLeft()
    {
        x-=Speed;
        Direction = 1;
        moving = true;
    }

    public void moveUp()
    {
        y-=Speed;
        Direction = 2;
        moving = true;
    }

    public void moveDown()
    {
        y+=Speed;
        Direction = 0;
        moving = true;
    }

    public void stationary()
    {
        moving = false;
    }

    public void animation()
    {
        Animation = (moving) ? (Animation + 1) % 3: 2;
    }


    // All Sprite Class Required Methods and some Misc.

    public void draw(Graphics g, int scrPos)
    {
        g.drawImage(pacmanImages[Direction][Animation], x, y - scrPos, 25, 25, null);
    }

    public void update()
    {
        this.animation();
    }

    public void collision(Sprite s)
    {
        if(s.isWall())
        {
            int right = x + w - s.getX();
            int left = s.getX() + s.getW() - x;
            int up = s.getY() + s.getH() - y;
            int down = y + h - s.getY();
           
            int min = (right < left) ? right : left;
            min = (min < up) ? min : up;
            min = (min < down) ? min : down;
            
            if(min == right)
            {
                x = s.getX() - w - 1;

            } else if(min == left) 
            {
                x = s.getX() +s.getW() + 1;

            } else if (min == up)
            {
                y = s.getY() + s.getH() + 1;

            } else if(min == down) 
            {
                y = s.getY() - h - 1;
            }
        } else
        {
            s.collided();
        }
    }

    public void collided() {  }

    public void loadImage()
    {
        if(pacmanImages == null) //Loads Pacman images
        {   
            pacmanImages = new BufferedImage[4][3];

			for(int i = 0; i < 4; i++)
			{
				for(int j = 1; j < 4; j++)
				{
					int temp = (i*3) + j;
					pacmanImages[i][j-1] =  View.openImage("images\\pacmanImages\\pacman" + temp + ".png");
				}
            }
        }
    }

    @Override 
    public String toString() //Used to debug
    {
	    return "Pac (x,y) = (" + x + ", " + y + "), w = " + 25 + ", h = " + 25;
    }

    @Override
    public boolean isPacman()
    {
        return true;
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }

}
