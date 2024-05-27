package clase;

import interfaces.Variables;

public class Catapult extends DefenseUnit  {
	
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
	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return "Catapult";
	}

	@Override
	public void setSanti() {
		this.setSanctified(true);
		this.setInitialArmor(this.getInitialArmor() * PLUS_ARMOR_UNIT_SANCTIFIED );
		this.setBaseDamage(this.attack() * PLUS_ATTACK_UNIT_SANCTIFIED);
		
	}

}
