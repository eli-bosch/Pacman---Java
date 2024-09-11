//Eli Bosch 3/12/24, Class for each wall

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Wall extends Sprite
{
    /*All private variables for the Wall Class */

    static BufferedImage wallImage = null;
    
    /*All the contructor methods for the Wall Class */

    public Wall(int X, int Y)
    {
       super(X, Y, 0, 0);

        wallImage = (wallImage == null ? View.openImage("images\\wallImages\\wall.png") : wallImage);
       
    }

    public Json marshal() //Saves the class
    {
        Json ob = Json.newObject();
        ob.add("x-value", x);
        ob.add("y-value", y);
        ob.add("Width", w);
        ob.add("Height", h);
        return ob;
    }

    Wall(Json ob) //Loads a previously saved class
    {
        super((int)ob.getLong("x-value"), (int)ob.getLong("y-value"), (int)ob.getLong("Width"), (int)ob.getLong("Height"));

        wallImage = (wallImage == null ? View.openImage("images\\wallImages\\wall.png") : wallImage);
    }

    //Get and set methods 

    public void setGridWall(int x, int y, int i, int j)
    {
        int grid = Model.GRID_SIZE;
        x = (x % grid == 0 ? x : x - (x % grid));
        y = (y % grid == 0 ? y : y - (y % grid));
        i = (i % grid == 0 ? i : i - (i % grid));
        j = (j % grid == 0 ? j : j - (j % grid));

        if(x <= i && y <= j) //If click is to the upper left and release is to the bottom right
        {
            this.setX(x);
            this.setY(y);
            i += grid;
            j += grid;
            this.setW(i - x);
            this.setH(j - y);

        } else if (x >= i && y > j) // if click is bottom right and release is to the top left THIS ONE NEEDS WORK FOR SOME REASON &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        {
            this.setX(i);
            this.setY(j);
            x += grid;
            y += grid;
            this.setW(x-i);
            this.setH(y-j);

        } else if (x < i && y >= j) //If click is to the bottom left and release is to the top right
        {
            this.setX(x);
            this.setY(j);
            i += grid;
            y += grid;
            this.setW(i - x);
            this.setH(y - j);
            
        }else if (x > i && y < j) //If click to to the top right and release to the bottom left
        {
            this.setX(i);
            this.setY(y);
            x += grid;
            j += grid;
            this.setW(x - i);
            this.setH(j - y);
        } else 
        {
            this.setX(i);
            this.setY(j);
            x += grid;
            y += grid;
            this.setW(x-i);
            this.setH(y-j);
        }
    }

    // Default Sprite methods and debugging

    public void collision(Sprite s) {   }


    public void draw(Graphics g, int scrPos)
    {
        g.drawImage(wallImage, x, y - scrPos, w, h, null);
    }

    public void collided() {  }

    public void update() {  }

    @Override 
    public String toString()
    {
	    return "Wall (x,y) = (" + x + ", " + y + "), w = " + w + ", h = " + h;
    }

    @Override
    public boolean isWall()
    {
        return true;
    }
}
