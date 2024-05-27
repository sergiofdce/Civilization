package clase;

import interfaces.Variables;

public class RocketLauncherTower extends DefenseUnit  {
	public RocketLauncherTower( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}

	

	@Override
	public int getFoodCost() {
		return Variables.FOOD_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_ROCKETLAUNCHERTOWER;
	}
	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return "RocketLauncherTower";
	}

	@Override
	public void setSanti() {
		this.setSanctified(true);
		this.setInitialArmor(this.getActualArmor() * PLUS_ARMOR_UNIT_SANCTIFIED );
		this.setBaseDamage(this.attack() * PLUS_ATTACK_UNIT_SANCTIFIED);
		
	}
	
}
