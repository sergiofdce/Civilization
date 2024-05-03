package clase;

import interfaces.MilitaryUnit;
import interfaces.Variables;

public abstract class DefenseUnit implements MilitaryUnit, Variables {
	
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


class ArrowTower extends DefenseUnit {

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


class Catapult extends DefenseUnit  {

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


class RocketLauncherTower extends DefenseUnit  {

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