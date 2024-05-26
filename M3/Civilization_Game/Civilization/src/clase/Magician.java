package clase;

public class Magician extends SpecialUnit {

	public Magician( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        
			
	}

	public Magician() {
		super();
		
		this.setArmor(this.getInitialArmor());
      
       
       

	}

	

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return FOOD_COST_MAGICIAN;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return WOOD_COST_MAGICIAN;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return IRON_COST_MAGICIAN;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return MANA_COST_MAGICIAN;
	}
	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return "Magician";
	}

	@Override
	public void setSanti() {
		
		
	}

	@Override
	public boolean isSanctified() {
		// TODO Auto-generated method stub
		return false;
	}

	
}