package clase;

public class Crossbow extends AttackUnit  {

	
	// Constructor 1
	public Crossbow( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}
	
	// Constructor 2
	public Crossbow() {
		this.setArmor(ARMOR_CROSSBOW);
        this.setInitialArmor(ARMOR_CROSSBOW);
        this.setBaseDamage(BASE_DAMAGE_CROSSBOW);
	}




	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_CROSSBOW;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_CROSSBOW;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_CROSSBOW;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return "Crossbow";
	}

	@Override
	public void setSanti() {
		this.setSanctified(true);
		this.setInitialArmor(this.getInitialArmor() * PLUS_ARMOR_UNIT_SANCTIFIED );
		this.setBaseDamage(this.attack() * PLUS_ATTACK_UNIT_SANCTIFIED);
		
	}

	
}