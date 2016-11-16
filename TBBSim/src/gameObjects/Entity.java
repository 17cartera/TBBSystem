package gameObjects;
/*
 * entity objects, with attributes and abilities
 */
public class Entity
{
	public String name;
	public String team = "[N]";
	public int maxHealth;
	public int health;
	public String[] traits;
	//constructs an entity 
	public Entity(String mname, int mhealth)
		{
			name = mname;
			maxHealth = mhealth;
			health = mhealth;
		}
}
