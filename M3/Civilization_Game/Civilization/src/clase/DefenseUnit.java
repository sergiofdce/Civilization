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
	public int setArmor(int armor) {
		return this.armor = armor;
	}
	public int setInitialArmor(int initialArmor) {
		return this.initialArmor = initialArmor;
	}
	public int setBaseDamage(int baseDamage) {
		return this.baseDamage = baseDamage;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public boolean setSanctified(boolean sanctified) {
		return this.sanctified = sanctified;
	}
	
	// Getters
	public int getArmor() {
		return armor;
	}
	public int getInitialArmor() {
		return initialArmor;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
	public void getExperience() {
		return;
	}
	public boolean isSanctified() {
		return sanctified;
	}
	

}




class ArrowTower extends DefenseUnit {

    // Constructor 1
    public ArrowTower(int armor, int baseDamage) {
        super();

        this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
    }

    public int attack() {
    	return this.getBaseDamage();
    }

    @Override
    public void takeDamage(int receivedDamage) {
    	int currentArmor = this.getActualArmor();
        this.setArmor(currentArmor - receivedDamage);
    }

    @Override
    public int getActualArmor() {
    	return this.getArmor(); 
    }

	@Override
	public int getFoodCost() {
		return Variables.FOOD_COST_ARROWTOWER;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_ARROWTOWER;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_ARROWTOWER;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_ARROWTOWER;
	}

	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChanceAttackAgain() {
		
		return 0;
	}

	@Override
	public void resetArmor() {
		int initialArmor = this.getInitialArmor();
	    this.setArmor(initialArmor);
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
	
	// Constructor 1
	public Catapult( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}
	public int attack() {
    	return this.getBaseDamage();
    }

    @Override
    public void takeDamage(int receivedDamage) {
    	int currentArmor = this.getActualArmor();
        this.setArmor(currentArmor - receivedDamage);
    }

    @Override
    public int getActualArmor() {
    	return this.getArmor(); 
    }
    
	@Override
	public int getFoodCost() {
		return Variables.FOOD_COST_CATAPULT;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_CATAPULT;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_CATAPULT;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_CATAPULT;
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
		int initialArmor = this.getInitialArmor();
	    this.setArmor(initialArmor);
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
	
	// Constructor 
	public RocketLauncherTower( int armor, int baseDamage) {
		super();
		
		this.setArmor(armor);
        this.setInitialArmor(armor);
        this.setBaseDamage(baseDamage);
        this.setExperience(0);
        this.setSanctified(false);
			
	}

	public int attack() {
    	return this.getBaseDamage();
    }

    @Override
    public void takeDamage(int receivedDamage) {
    	int currentArmor = this.getActualArmor();
        this.setArmor(currentArmor - receivedDamage);
    }

    @Override
    public int getActualArmor() {
    	return this.getArmor(); 
    }

	@Override
	public int getFoodCost() {
		return Variables.FOOD_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getWoodCost() {
		return Variables.WOOD_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getIronCost() {
		return Variables.IRON_COST_ROCKETLAUNCHERTOWER;
	}

	@Override
	public int getManaCost() {
		return Variables.MANA_COST_ROCKETLAUNCHERTOWER;
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
		int initialArmor = this.getInitialArmor();
	    this.setArmor(initialArmor);
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
