package gameObjects;

import java.util.ArrayList;

/*
 * entity objects, with attributes and abilities
 */
public class Entity
{
	String name; //name of entity
	String team = "[N]"; //team of entity
	int maxHealth; //maximum health of entity
	int health; //current health of entity
	String[] traits; //list of traits
	Boolean isActive; //whether or not the entity can take actions
	ArrayList<Ability> abilities; //list of abilities possessed by this creature
	//constructs an entity 
	public Entity(String mname, int mhealth)
		{
			name = mname;
			maxHealth = mhealth;
			health = mhealth;
			isActive = false; //entities have a delay on actions after being summoned, may change
		}
	//getter methods
	public String getName() 
	{
		return name;
	}
	public String getTeam() 
	{
		return team;
	}
	public int getMaximumHealth() 
	{
		return maxHealth;
	}
	public int getHealth() 
	{
		return health;
	}
	public String[] getTraits() 
	{
		return traits;
	}
	public boolean canAct() 
	{
		return isActive;
	}
	//setter methods
	public void setName(String newName) 
	{
		name = newName;
	}
	public void setTeam(String newTeam) 
	{
		team = newTeam;
	}
	public void setMaximumHealth(int newMaxHealth) 
	{
		maxHealth = newMaxHealth;
	}
	public void setHealth(int newHealth) 
	{
		health = newHealth;
	}
	public void setActive(boolean canAct) 
	{
		isActive = canAct;
	}
}
