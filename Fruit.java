//Fruit class, 3/13/24, for Assignment 5

import java.awt.Graphics;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Fruit extends Sprite
{
    //Private variables for the Fruit class

    private double speedX, speedY = 0;
    private int fruit;
    private BufferedImage fruitImages[] = null;
    Random rand = new Random();

    // Constructor and JSON methods for the Fruit class

    public Fruit(int X, int Y)
    {
        super(X, Y, 15, 15);
        speedX = rand.nextInt(-9, 9);
        speedY = rand.nextInt(-9, 9);
      
       

        fruit = rand.nextInt(1, 7);

        this.loadImage();
    }

    public Json marshal() //Saves the class
    {
        Json ob = Json.newObject();
        ob.add("x-value", x);
        ob.add("y-value", y);
        ob.add("x-speed", speedX);
        ob.add("y-speed", speedY);
        ob.add("fruit", fruit);
        return ob;
    }

    Fruit(Json ob) //Loads a previously saved class
    {
        super((int)ob.getLong("x-value"), (int)ob.getLong("y-value"), 15, 15);
        speedX = (int)ob.getDouble("x-speed");
        speedY = (int)ob.getDouble("y-speed");
        fruit = (int)ob.getLong("fruit");

        this.loadImage();
    }

    //Movement methods for the Fruit class

    private void movementX()
    {
        x+=speedX;
    }

    private void movementY()
    {
        y+=speedY;
    }

    // Default Sprite class methods 

    public void collision(Sprite s) 
    {
        if(s.isWall())
        { 
           int right = x + w - s.getX(); //Collision detection
           int left = s.getX() + s.getW() - x;
           int up = s.getY() + s.getH() - y;
           int down = y + h - s.getY();
           
           int min = (right < left) ? right : left;
           min = (min < up) ? min : up;
           min = (min < down) ? min : down;
            
           if(min == right)
           {
                x = s.getX() - w - 1;
                speedX = -speedX;

           } else if(min == left) 
           {
                x = s.getX() +s.getW() + 1;
                speedX = -speedX;

           } else if (min == up)
           {
                y = s.getY() + s.getH() + 1;
                speedY = -speedY;

           } else if(min == down) //Doesn't work yet
           {
                y = s.getY() - h - 1;
                speedY = -speedY;
           }
        }
    }

    public void collided() 
    {
       y = -5001;
       speedY = 0;
    }

    public void draw(Graphics g, int scrPos)
    {
        g.drawImage(fruitImages[fruit], x, y - scrPos, w, h, null);
    }

    public void update() 
    {  
        this.movementX();
        this.movementY();
    }

    public void loadImage()
    {
        if(fruitImages == null)
        {
            fruitImages = new BufferedImage[7];

            for(int i = 0; i < 7; i++)
            {
                fruitImages[i] = View.openImage("images\\spriteImages\\fruit" + (i+1) + ".png");
            }
        }
    }

    @Override 
    public String toString()
    {
	    return "Fruit (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isFruit()
    {
        return true;
    }

    @Override
    public boolean isMoving()
    {
        return true;
    }
}