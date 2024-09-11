// Eli Bosch, 3/12/2024, Model class for Assingment 5

import java.util.ArrayList;
import java.util.Iterator;


public class Model
{
	/*Private Variables for the Model Class */

	public static int GRID_SIZE = 30;
	public ArrayList<Sprite> sprites, spritesBuffer;
	public Pacman pac = new Pacman();
	private boolean editMode, addWall, addPellet, addFruit, addGhost = false;
	
	//Constructor and Json methods
	
	public Model()
	{
		sprites = new ArrayList<Sprite>();
		spritesBuffer = new ArrayList<Sprite>();
		sprites.add(pac);
	}

	Json marshal() //Used for marshalling the wall array into a Json file
	{
		Json tmpPelletList = Json.newList();
		Json tmpWallList = Json.newList();
		Json tmpGhostList = Json.newList();
		Json tmpFruitList = Json.newList();

		Json ob = Json.newObject();

		for(int i = 1; i < sprites.size(); i++) 
		{
			if(sprites.get(i).isWall())
			{
				tmpWallList.add(sprites.get(i).marshal());

			} else if(sprites.get(i).isGhost())
			{
				tmpGhostList.add(sprites.get(i).marshal());
			
			} else if (sprites.get(i).isFruit())
			{
				tmpFruitList.add(sprites.get(i).marshal());
			
			} else if(sprites.get(i).isPellet())
			{
				tmpPelletList.add(sprites.get(i).marshal());
			}
		}

		ob.add("WallList", tmpWallList);
		ob.add("GhostList", tmpGhostList);
		ob.add("FruitList", tmpFruitList);
		ob.add("PelletList", tmpPelletList);

		return ob;
	}

	public void unmarshal(Json ob) //Unmarshals the Json file into the wall array which then calls the Wall umarshalling
	{
		Sprite temp = sprites.get(0);
		sprites.clear();
		sprites.add(temp);

		Json tmpList = ob.get("WallList");;

		for(int i = 0; i < tmpList.size(); i++)
		{
			sprites.add(new Wall(tmpList.get(i)));
		}

		tmpList = ob.get("GhostList");

		for(int i = 0; i < tmpList.size(); i++)
		{
			sprites.add(new Ghost(tmpList.get(i)));
		}

		tmpList = ob.get("FruitList");

		for(int i = 0; i < tmpList.size(); i++)
		{
			sprites.add(new Fruit(tmpList.get(i)));
		}

		tmpList = ob.get("PelletList");

		for(int i = 0; i < tmpList.size(); i++)
		{
			sprites.add(new Pellet(tmpList.get(i)));
		}
			

	}

	// Array List Editors and Information

	public void setSprite(Sprite s) //adds a new wall into the walls array
	{
		spritesBuffer.add(s);
	}

	public void deleteSprite(int x, int y)
	{
		for(int i = sprites.size() - 1; i > 0; i--)
		{	
			Sprite tmp = sprites.get(i);

			if(x >= tmp.getX() && y >= tmp.getY() && x <= (tmp.getW() + tmp.getX()) && y <= (tmp.getH() + tmp.getY())) 
			{
				sprites.remove(i);
				return;
			}
		}
	}

	public void clearWalls() //Clears all walls if edit mode is on
	{
		for(Iterator<Sprite> s = sprites.iterator(); s.hasNext();)
		{
			Sprite tmp = s.next();
			if(tmp.isWall())
				s.remove();
		}

		// Sprite temp = sprites.get(0);
		// sprites.clear();
		// sprites.add(temp);
	}



	// Getters for the Model Class

	public boolean getEdit()
	{
		return editMode;
	}

	public boolean getPellet()
	{
		return addPellet;
	}

	public boolean getAdd()
	{
		return addWall;
	}

	public boolean getFruit()
	{
		return addFruit;
	}

	public boolean getGhost()
	{
		return addGhost;
	}

	public boolean getRemove()
	{
		if(editMode && !(addWall || addPellet || addFruit || addGhost))
			return true;
		
		return false;
	}

	// Setters for the Model Class

	public void toggleEdit() //Starts with all off except for addWall
	{
		editMode = (editMode) ? false : true;

		if(!editMode)
		{
			addPellet = false;
			addFruit = false;
			addGhost = false;
			addWall = false;
		} else
		{
			addPellet = false;
			addFruit = false;
			addGhost = false;
			addWall = true;
		}
	}
	
	public void toggleAdd() //When one of the toggle method is used it turns the rest off
	{
		if(editMode) 
		{
			addWall = (addWall) ? false : true;

			if(addWall)
			{
				addPellet = false;
				addFruit = false;
				addGhost = false;
			}
		}	
	}

	public void togglePellet()
	{
		if(editMode)
		{
			addPellet = (addPellet) ? false : true;

			if(addPellet)
			{
				addWall = false;
				addFruit = false;
				addGhost = false;
			}
		}
	}

	public void toggleGhost()
	{
		if(editMode)
		{
			addGhost = (addGhost) ? false : true;

			if(addGhost)
			{
				addWall = false;
				addFruit = false;
				addPellet = false;
			}
		}
	}

	public void toggleFruit()
	{
		if(editMode)
		{
			addFruit = (addFruit) ? false : true;

			if(addFruit)
			{
				addWall = false;
				addGhost = false;
				addPellet = false;
			}
		}
	}

	public void buffer() //Used to not run into the concurrent modification error with the iterators
	{
		for(int i = 0; i < sprites.size(); i ++)
		{
			if(sprites.get(i).getY() == -5001) //Checks for any sprites on the "Garbage" y-line
				sprites.remove(i);
		}

		sprites.addAll(spritesBuffer);
		spritesBuffer.clear();
	}


	//Collision Methods

	private boolean detectCollision(Sprite s, Sprite s2) //Searches if there is not a collision
	{
		if(s == s2) //Checks if same value
			return false;

		if(s.getX() + s.getW() < s2.getX()) // right side of the wall and the left 
			return false;
		if(s.getX() > s2.getX() + s2.getW()) //left side of the wall and the right side 
			return false;
		if(s.getY() + s.getH() < s2.getY()) //bottom side of the wall and the top 
			return false;
		if(s.getY() > s2.getY() + s2.getH()) //top side of the wall and the bottom
			return false;

		return true;
	}

	private void detectCollision()
	{
		for(Iterator<Sprite> it1 = sprites.iterator(); it1.hasNext();)
		{
			Sprite tmp1 = it1.next();

			for(Iterator<Sprite> it2 = sprites.iterator(); it2.hasNext();)
			{
				Sprite tmp2 = it2.next();

				if(tmp1.isMoving() && tmp1.isCollidable(tmp2) && detectCollision(tmp1, tmp2))
					tmp1.collision(tmp2);
			}

			if(tmp1.isMoving()) //Handles the warping 
			{
				if(tmp1.getX() > Game.FRAME_SIZE)
					tmp1.setX(0 - tmp1.getW() + 1);
				if(tmp1.getX() < 0 - tmp1.getW())
					tmp1.setX(Game.FRAME_SIZE - 1);
			}
		}
	}

	/*Update Method Which Gets Called Repeatedly from the Game Class */

	public void update()
	{	
		this.detectCollision();
		this.buffer();

		for(Iterator<Sprite> s = sprites.iterator(); s.hasNext();)
		{
			s.next().update();
		}
	}
}