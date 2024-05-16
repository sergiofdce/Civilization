package clase;

 public class Swordsman extends AttackUnit {

	// Constructor 1
	public Swordsman( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}
	
	// Constructor 2
	public Swordsman() {
		
        this.setInitialArmor(ARMOR_SWORDSMAN);
        this.setBaseDamage(BASE_DAMAGE_SWORDSMAN);
	}



	


	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_SWORDSMAN;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_SWORDSMAN;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_SWORDSMAN;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return MANA_COST_SWORDSMAN;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	
}


	



