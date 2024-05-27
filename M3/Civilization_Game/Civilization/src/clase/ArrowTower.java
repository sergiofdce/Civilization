package clase;

import interfaces.Variables;

public class ArrowTower extends DefenseUnit implements Variables {
	

	 public ArrowTower(int armor, int baseDamage) {
	        super();

	        this.setArmor(armor);
	        this.setInitialArmor(armor);
	        this.setBaseDamage(baseDamage);
	        this.setExperience(0);
	        this.setSanctified(false);
	    }
	



	@Override
	public int getFoodCost() {
		return FOOD_COST_ARROWTOWER;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_ARROWTOWER;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_ARROWTOWER;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_ARROWTOWER;
	}


	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return "ArrowTower";
	}




	@Override
	public void setSanti() {
		this.setSanctified(true);
		this.setInitialArmor(this.getInitialArmor() * PLUS_ARMOR_UNIT_SANCTIFIED );
		this.setBaseDamage(this.attack() * PLUS_ATTACK_UNIT_SANCTIFIED);
		
	}


}
