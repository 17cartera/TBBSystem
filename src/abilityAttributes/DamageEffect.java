package abilityAttributes;

import gameObjects.Entity;

public class DamageEffect extends AbilityEffect
{
	int damage;
	public DamageEffect(int damageVal) 
	{
		damage = damageVal;
	}
	public void activateEffect(Entity target) 
	{
		target.takeDamage(damage);
	}
}
