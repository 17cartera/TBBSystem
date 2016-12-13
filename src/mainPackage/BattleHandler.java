package mainPackage;

import java.util.ArrayList;

import gameObjects.Ability;
import gameObjects.Entity;

/*
 * backend logic system
 * TODO: improve things
*/
public class BattleHandler
{
	public Interface gameScreen;
	public ArrayList<Entity> entityList;
	boolean roundInProgress = false;
	public static void main(String[] args)
	{
		//generate interface
		BattleHandler game = new BattleHandler();
		game.entityList = new ArrayList<Entity>();
		game.gameScreen = new Interface(game);
		//run test method
		game.testMethod();
		//wait for user input for actions
	}
	
	//adds an entity, then sends a signal to update the gameScreen
	public void addEntity(Entity entity) 
	{
		entityList.add(entity);
		gameScreen.mainList.updateList(entityList);
	}
	public void testMethod() 
	{
		//test method to load in generic entities
		Entity steve = new Entity(entityList,"Steve",20);
		steve.addAbility(new Ability());
		this.addEntity(steve);
		Entity alex = new Entity(entityList,"Alex",20);
		alex.addAbility(new Ability());
		this.addEntity(alex);
		//ability test code
		gameScreen.mainList.updateList(entityList);
	}
	public void startRound() 
	{
		//entities do start-of-round effects, get actions
		roundInProgress = true;
		for (int n = 0; n < entityList.size(); n++) 
		{
			Entity entity = entityList.get(n);
			entity.startRound();
		}
	}
	public void endRound()
	{
		//entities do end-of-round effects, any unused actions are lost
		roundInProgress = false;
		for (int n = 0; n < entityList.size(); n++) 
		{
			Entity entity = entityList.get(n);
			if (entity.isDead())
			{
				entityList.remove(entity);
				gameScreen.mainList.updateList(entityList);
				System.out.println(entityList.size() + " Entities remaining");
			}
			else
			entity.endRound();
		}
	}
}
