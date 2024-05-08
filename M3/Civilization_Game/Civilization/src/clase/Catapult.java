package clase;

import interfaces.Variables;

class Catapult extends DefenseUnit  {
	
	// Constructor 1
		public Catapult( int armor, int baseDamage) {
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
		return Variables.FOOD_COST_CATAPULT;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_CATAPULT;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_CATAPULT;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_CATAPULT;
	}


}
