package clase;
class Priest extends SpecialUnit {

	public Priest( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
     
			
	}

	public Priest() {
		super();
		
	}



	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_PRIEST;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_PRIEST;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_PRIEST;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return MANA_COST_PRIEST;
	}}