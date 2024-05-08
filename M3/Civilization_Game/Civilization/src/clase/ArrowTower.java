package clase;

import interfaces.Variables;

class ArrowTower extends DefenseUnit {
	

	 public ArrowTower(int armor, int baseDamage) {
	        super();

	        this.setArmor(armor);
	        this.setInitialArmor(armor);
	        this.setBaseDamage(baseDamage);
	        this.setExperience(0);
	        this.setSanctified(false);
	    }
	

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFoodCost() {
		return Variables.FOOD_COST_ARROWTOWER;
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


}
