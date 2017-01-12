package gameObjects;

import abilityAttributes.AbilityEffect;
import abilityAttributes.DamageEffect;

/*
 * Class containing data on abilities possessed by entities
 * TODO: Implement ability subclasses for effects
 * TODO: Improve referencing, potentially integrate into Entity class
 */
public class Ability
{
	//ability timing (when ability is activated)
	//ability conditions (conditions that can modify the ability or its effects)
	//ability effect (what effect the ability has when activated)
	public enum ABILITY_TIMING {ACTION,REACTION,OTHER}
	public String abilityName;
	public ABILITY_TIMING timing;
	public AbilityEffect effect;
	//test method
	public Ability() 
	{
		abilityName = "Generic Ability";
		timing = ABILITY_TIMING.ACTION;
		effect = new DamageEffect(5);
	}
	//creates a custom ability
	public Ability(ABILITY_TIMING t, AbilityEffect e) 
	{
		abilityName = "Custom Ability";
		timing = t;
		effect = e;
	}
	public void activateAbility(Entity target) 
	{
		effect.activateEffect(target);
	}
}
