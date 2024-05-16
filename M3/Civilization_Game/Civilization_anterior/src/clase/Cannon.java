package clase;



class Cannon extends AttackUnit {

	
	// Constructor 1
	public Cannon( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}
	
	// Constructor 2
	public Cannon() {
		this.setArmor(ARMOR_CANNON);
        this.setInitialArmor(ARMOR_CANNON);
        this.setBaseDamage(BASE_DAMAGE_CANNON);
	}

	

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_CANNON;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_CANNON;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_CANNON;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

}