package clase;

import interfaces.MilitaryUnit;
import interfaces.Variables;

public abstract class DefenseUnit implements MilitaryUnit, Variables {
	
	private int armor;
	private int initialArmor;
	private int baseDamage; 
	private int experience;
	private boolean sanctified;
	private int id_civi = 1;
	

	

	public int getId_civi() {
		return id_civi;
	}

	public void setId_civi(int id_civi) {
		this.id_civi = id_civi;
	}

	
	// Setters
	@Override
	public int attack() {
		// TODO Auto-generated method stub
		return baseDamage ;
	}
	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return armor;
	}

	@Override
	public
	abstract int getFoodCost();

	@Override
	abstract public int getWoodCost();

	@Override
	abstract  public int getIronCost();

	@Override
	abstract  public int getManaCost();



	@Override
	public void resetArmor() {
		
		int armor = initialArmor;
		
		if(experience != 0) {
			
			armor += this.getExperience() * PLUS_ARMOR_UNIT_PER_EXPERIENCE_POINT;
		}
		
		if(isSanctified() == true) {
			
			
			armor += this.initialArmor * PLUS_ARMOR_UNIT_SANCTIFIED ;
			
			
			
			
		}
		
		this.setArmor(armor);
	}

	@Override
	public void setExperience(int n) {
		this.experience += n;
		
		this.setArmor(this.initialArmor + (this.getExperience() * PLUS_ARMOR_UNIT_PER_EXPERIENCE_POINT));
		this.setBaseDamage(this.baseDamage + (this.getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT));
		
	}

	@Override
	public int getExperience() {
        return experience;
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

	public int getBaseDamage() {
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
	
	@Override
	public void takeDamage(int receivedDamage) {
		this.setArmor(this.getActualArmor() - receivedDamage);
		
	}
	

}