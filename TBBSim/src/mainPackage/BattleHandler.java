package mainPackage;

import gameObjects.Entity;

import java.util.ArrayList;

/*
 * backend logic system
 * TODO: back-end infrastructure to handle abilities
 * TEST: A test comment for testing purposes
*/
public class BattleHandler
{
	public Interface gameScreen;
	public ArrayList<Entity> entityList;
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
		this.addEntity(new Entity("Steve",20));
	}
	public void startRound() 
	{
		//entities do start-of-round effects, get actions
	}
	public void endRound() 
	{
		//entities do end-of-round effects, any unused actions are lost
	}
}
