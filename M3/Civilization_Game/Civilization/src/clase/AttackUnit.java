package clase;

import interfaces.MilitaryUnit;
import interfaces.Variables;

public abstract class AttackUnit implements MilitaryUnit, Variables {
	
	private int armor;
	private int initialArmor;
	private int baseDamage; 
	private int experience;
	private boolean sanctified;
	private int id_civi;
	
	

	

	public int getId_civi() {
		return id_civi;
	}

	public void setId_civi(int id_civi) {
		this.id_civi = id_civi;
	}

	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return armor;
	}

	@Override
	public abstract int getFoodCost();

	@Override
	abstract public int getWoodCost();

	@Override
	abstract  public int getIronCost();

	@Override
	abstract  public int getManaCost();

	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChanceAttackAgain() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetArmor() {
		this.setArmor(initialArmor);
	}

	@Override
	public void setExperience(int n) {
		this.experience += n;
		
	}

	@Override
	public int getExperience() {
		return experience;
		// TODO Auto-generated method stub
		
	}


	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getInitialArmor() {
		return initialArmor;
	}

	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}

	public int attack() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public boolean isSanctified() {
		return sanctified;
	}

	public void setSanctified(boolean sanctified) {
		this.sanctified = sanctified;
	}
	
	
	
	

}





	





