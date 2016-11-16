package mainPackage;
/*
 * backend logic system
*/
public class BattleHandler

{
	public Interface gameScreen;
	public static void main(String[] args)
	{
		//generate interface
		BattleHandler game = new BattleHandler();
		game.gameScreen = new Interface(game);
		//wait for user input for actions
	}

}
