//Ghost class for Assignment 5, 3/13/24

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ghost extends Sprite
{
    //Private variables for the Ghost class

    static BufferedImage[][][] ghostImages = null;
    private int Animation, Direction, ghostIndex, Count = 0;
    private double speed = 5                                                                                                                                                                                                     ;
    Random rand = new Random();

    //All of the Constructor and Marshalling methods

    public Ghost(int X, int Y)
    {
        super(X, Y, 25, 25);
        ghostIndex = rand.nextInt(1, 5);
        this.loadImage();
    }

    public Json marshal() //Saves the class
    {
        Json ob = Json.newObject();
        ob.add("x-value", x);
        ob.add("y-value", y);
        ob.add("ghostIndex", ghostIndex);
        return ob;
    }

    Ghost(Json ob) //Loads a previously saved class
    {
        super((int)ob.getLong("x-value"), (int)ob.getLong("y-value"), 25, 25);
        ghostIndex = (int)ob.getLong("ghostIndex");

        this.loadImage();
    }

    /*Movement Method */

    private void movement()
    {
        if(Direction == 0) //Left
            x-=speed;
        if(Direction == 1) //Up
            y-=speed;
        if(Direction == 2) //Right
            x+=speed;
        if(Direction == 3) //Down
            y+=speed;
    }   

    /*Default and Misc. Sprite Methods */

    public void loadImage()
    {
        if(ghostImages == null)
        {
            ghostImages = new BufferedImage[5][4][2]; //This format doesn't really apply to the ghost once a fruits been eaten, or when they've been eaten
            String name = " ";

            for(int i = 0; i < 5; i++) //Which One of the Ghost
            {
                name = (i==0) ? "ghost" : name;
                name = (i==1) ? "blinky" : name;
                name = (i==2) ? "inky" : name;
                name = (i==3) ? "pinky" : name;
                name = (i==4) ? "sue" : name;

                for(int j = 0; j < 4; j++) //Depends on the Direction of that Ghost
                {
                    for(int k = 1; k < 3; k++) //Ghost Animation for Moving
                    {
                        ghostImages[i][j][k-1] = View.openImage("images\\spriteImages\\" + name + (j*2 + k) + ".png");
                    }
                }
            }
        }
    }

    public void collision(Sprite s) 
    {
        if(s.isWall())
        {
            int right = x + w - s.getX(); //Diminsions needed for finding the minimum
            int left = s.getX() + s.getW() - x;
            int up = s.getY() + s.getH() - y;
            int down = y + h - s.getY();
            int d = rand.nextInt(0, 10);
           
            int min = (right < left) ? right : left; //Finds the smallest amt of pixels needed to remove itself from the wall
            min = (min < up) ? min : up;
            min = (min < down) ? min : down;
            
            if(min == right)
            {
                x = s.getX() - w - 1; //Removes it from the wall
                Direction = (d % 2 == 0) ? 1 : 3; //This just turns the ghost right or left of its previous direction

            } else if(min == left) 
            {
                x = s.getX() +s.getW() + 1;
                Direction = (d % 2 == 0) ? 1 : 3;

            } else if (min == up)
            {
                y = s.getY() + s.getH() + 1;
                Direction = (d % 2 == 0) ? 0 : 2;

            } else if(min == down) 
            {
                y = s.getY() - h - 1;
                Direction = (d % 2 == 0) ? 0 : 2;
            }
        }
    }

    private void Animation()
    {
        Animation = (Animation + 1) % 2;

        if(Count % 63 == 0)
            Direction = rand.nextInt(0, 4); //This gives the ghost that tweaking look

        if(ghostIndex == 0 && Count % 5 == 0) //Hardcoded death animation
        {
            if(Direction == 1 && Animation == 0)
            {
                Direction = 2;
                Animation = 1;

            }else if(Direction == 0 && Animation == 1)
            {
                Direction = 1;
                Animation = 0;

            } else if(Direction == 1 && Animation == 1)
            {
                Direction = 0;
                Animation = 1;

            } else if(Direction == 0 && Animation == 0) 
            {
                Direction = 1;
                Animation = 1;
            } else 
            {
                y = -5001; //Metaphorical garbage can
                speed = 0;
            }
        }
        Count++;
    }

    public void collided() 
    {
        if(ghostIndex != 0)
        {
            ghostIndex = 0;
            Direction = 0;
            Animation = 0;
        }
    }

    public void draw(Graphics g, int scrPos) 
    {
        g.drawImage(ghostImages[ghostIndex][Direction][Animation], x, y - scrPos, w, h, null);
    }

    public void update() 
    {
        this.Animation();
        this.movement();
    }

    @Override 
    public String toString()
    {
	    return "Ghost (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isGhost()
    {
        return true;
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }
}
