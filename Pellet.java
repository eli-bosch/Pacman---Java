    //Eli Bosch 3/12/24, Class for each wall

import java.awt.Graphics;
import java.awt.Color;

public class Pellet extends Sprite
{
    /*All the contructor methods for the Pellet Class */

    public Pellet(int X, int Y)
    {
       super(X, Y, 10, 10);
    }

    public Json marshal() //Saves the class
    {
        Json ob = Json.newObject();
        ob.add("x-value", x);
        ob.add("y-value", y);
        return ob;
    }

    Pellet(Json ob) //Loads a previously saved class
    {
        super((int)ob.getLong("x-value"), (int)ob.getLong("y-value"), 10, 10);
    }

    //Setter methods

    public void setGridPellet()
    {
        int grid = Model.GRID_SIZE;
        x = (x % grid == 0) ? x + (grid/2) - (w/2) : x - (x % grid) + (grid/2) - (w/2);
        y = (y % grid == 0) ? y + (grid/2) - (h/2) : y - (y % grid) + (grid/2) - (h/2);
    }

    //Default Sprite methods

    public void collision(Sprite s) {   }

    public void draw(Graphics g, int scrPos)
    {
        g.setColor(new Color(255, 255, 255));
        g.fillOval(x, y - scrPos, w, h);
        
    }

    public void update() {  }

    public void collided() 
    {
        y = -5001;
    }

    @Override 
    public String toString()
    {
	    return "Pellet (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isPellet()
    {
        return true;
    }
}