package gameObjects;

import abilityAttributes.AbilityEffect;
import abilityAttributes.AbilityTiming;
import abilityAttributes.ActionTiming;
import abilityAttributes.DamageEffect;

/*
 * Class containing data on abilities possessed by entities
 */
public class Ability
{
	public String abilityName; //caption of the ability (recommended parameter)
	public Entity parentEntity; //the entity that this ability belongs to (recommended parameter)
	public AbilityTiming timing; //when the ability is useable (mandatory parameter)
	public AbilityEffect effect; //effect of the ability (mandatory parameter)
	//public AbilityTarget target; //target of the ability (optional parameter, set to a default)
	//public AbilityCondition conditions //conditions on how the ability can be used (optional parameter)
	//test method
	public Ability() 
	{
		abilityName = "Generic Ability";
		timing = new ActionTiming();
		effect = new DamageEffect(5);
	}

	//creates a custom ability
	public Ability(AbilityTiming t, AbilityEffect e) 
	{
		abilityName = "Custom Ability";
		timing = t;
		effect = e;
	}
	//creates a custom ability with custom name
	public Ability(String name, AbilityTiming t, AbilityEffect e) 
	{
		abilityName = name;
		timing = t;
		effect = e;
	}
	//test if the entity is a valid target of this ability
	public boolean isValidTarget(Entity target) 
	{
		//will use abilityTarget class
		return (target != parentEntity);
	}
	//activates the ability
	public void activateAbility(Entity target) 
	{
		if (isValidTarget(target))
		effect.activateEffect(target);
		parentEntity.isActive = false;
	}
}
