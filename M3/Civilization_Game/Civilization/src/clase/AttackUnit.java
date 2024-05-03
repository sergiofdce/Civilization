package clase;

import interfaces.MilitaryUnit;
import interfaces.Variables;

public abstract class AttackUnit implements MilitaryUnit, Variables {
	
	private int armor;
	private int initialArmor;
	private int baseDamage; 
	private int experience;
	private boolean sanctified;
	
	
	// Setters
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public void setSanctified(boolean sanctified) {
		this.sanctified = sanctified;
	}
	
	
	
	

}


class Swordsman extends AttackUnit {

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
		this.setArmor(ARMOR_SWORDSMAN);
        this.setInitialArmor(ARMOR_SWORDSMAN);
        this.setBaseDamage(BASE_DAMAGE_SWORDSMAN);
	}

	
	// Metodos
	@Override
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExperience(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getExperience() {
		// TODO Auto-generated method stub
		
	}
}



class Spearman extends AttackUnit  {

	
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
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExperience(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getExperience() {
		// TODO Auto-generated method stub
		
	}

}



class Crossbow extends AttackUnit  {

	
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
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExperience(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getExperience() {
		// TODO Auto-generated method stub
		
	}

}


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
	public int attack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeDamage(int receivedDamage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActualArmor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWoodCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIronCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getManaCost() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExperience(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getExperience() {
		// TODO Auto-generated method stub
		
	}

}

