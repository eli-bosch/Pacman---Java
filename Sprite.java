// The inherited class for all the other sprites, Assignment 5, 3/12/24

import java.awt.Graphics;

public abstract class Sprite 
{
    protected int x, y, w, h;

    public Sprite(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    // Getter methods

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getW()
    {
        return w;
    }

    public int getH()
    {
        return h;
    }

    public boolean isWall()
    {
        return false;
    }

    public boolean isPacman()
    {
        return false;
    }

    public boolean isGhost()
    {
        return false;
    }

    public boolean isFruit()
    {
        return false;
    }

    public boolean isPellet()
    {
        return false;
    }

    public boolean isMoving()
    {
        return false;
    }

    // Setter methods

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public boolean isCollidable(Sprite s)
    {
        if(this.isPacman())
            return true;
        if(this.isMoving() == true && s.isWall() == true)
            return true;
        
        return false;
    }

    // Abstract classes

    public abstract void collision(Sprite s);

    public abstract void collided();

    public abstract Json marshal();

    public abstract void update();

    public abstract void draw(Graphics g, int scrpos);

    
    

    




}
