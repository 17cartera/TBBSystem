package gameObjects;

import java.util.ArrayList;

import abilityAttributes.AbilityEffect;
import abilityAttributes.AbilityTiming;

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
	boolean isActive = false; //whether or not the entity can take actions (starts as false)
	boolean isDead = false; //whether or not the entity is dead
	ArrayList<Ability> abilities = new ArrayList<Ability>();; //list of abilities possessed by this creature
	ArrayList<Entity> entityList; //list of entities that methods can reference (may not be needed)
	//constructs an entity 
	public Entity(ArrayList<Entity> refs, String mname, int mhealth)
	{
		entityList = refs;
		name = mname;
		maxHealth = mhealth;
		health = mhealth;
	}
	//adds an ability to the entity	(multiple methods to match possible constructors)
	public void addAbility(AbilityTiming t, AbilityEffect e) 
	{
		Ability a = new Ability(t,e);
		a.parentEntity = this;
		abilities.add(a);
	}
	public void addAbility(String name, AbilityTiming t, AbilityEffect e) 
	{
		Ability a = new Ability(name,t,e);
		a.parentEntity = this;
		abilities.add(a);
	}
	public void addAbility(Ability a) 
	{
		a.parentEntity = this;
		abilities.add(a);
	}
	//returns abilities list
	public ArrayList<Ability> getAbilities() 
	{
		return abilities;
	}
	//causes the entity to take damage
	public void takeDamage(int damageVal) 
	{
		health -= damageVal;
		checkDeath();
	}
	//checks if entity has died, and handles death if the entity is dead
	public void checkDeath() 
	{
		if (health <= 0) 
		{
			System.out.println(name + " has died");
			isDead = true;
			//dead entities are not removed immediately due to abilities relating to dead entities
		}
	}
	//signals the entity that the round started or ended
	public void startRound() 
	{
		isActive = true;
	}
	public void endRound() 
	{
		isActive = false;
	}
	
	//misc. getter methods
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
	public boolean isDead() 
	{
		return isDead;
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
