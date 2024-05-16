package clase;

public class Spearman extends AttackUnit  {

	
	// Constructor 1
	public Spearman( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}
	
	// Constructor 2
	public Spearman() {
		this.setArmor(ARMOR_SPEARMAN);
        this.setInitialArmor(ARMOR_SPEARMAN);
        this.setBaseDamage(BASE_DAMAGE_SPEARMAN);
	}




	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_SPEARMAN;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_SPEARMAN;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_SPEARMAN;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return MANA_COST_SPEARMAN;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}
}