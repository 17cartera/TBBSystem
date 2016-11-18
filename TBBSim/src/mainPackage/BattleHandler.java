package mainPackage;

import gameObjects.Entity;

import java.util.ArrayList;

/*
 * backend logic system
*/
public class BattleHandler

{
	public Interface gameScreen;
	public ArrayList<Entity> entityList;
	public static void main(String[] args)
	{
		//generate interface
		BattleHandler game = new BattleHandler();
		game.gameScreen = new Interface(game);
		//wait for user input for actions
	}
	public void startRound() 
	{
		
	}
}
