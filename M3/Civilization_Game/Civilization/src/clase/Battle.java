package clase;

import java.util.ArrayList;

import interfaces.MilitaryUnit;

public class Battle {
	
	private ArrayList<MilitaryUnit> civilizationArmy;

	private ArrayList<MilitaryUnit> enemyArmy;
	
	
	private String []  battleDevelopment;
	
	private int []  initialCostFleet ;
	
	private int initialNumberUnitsEnemy ;
	
	private int initialNumberUnitsCivilization;
	
	private int [] wasteWoodIron; 
	private int enemyDrops; 
	
	private int civilizationDrops;
	
	private int [][] resourcesLooses; 
	
	private int [][] initialArmi; 
	
	private ArrayList[] armies; 
	

	
	private int [] actualNumberUnitsCivilization; 
	private int [] actualNumberUnitsEnemy;
	
	public Battle() {
		
		
		
		
	
	}
	
	
	public void initializeCivilizationArmy() {
	    for (int i = 1; i <= 9; i++) {
	        civilizationArmy.add((MilitaryUnit) new ArrayList<MilitaryUnit>());
	    }
	}
	
	public void initializeCivilizationEnemArmy() {
	    for (int i = 1; i <= 9; i++) {
	    	enemyArmy.add((MilitaryUnit) new ArrayList<MilitaryUnit>());
	    }
	}
	
	public void initializeArmies() {
		
		
		armies = new ArrayList [2];
		
	    for (int i = 0; i < armies.length; i++) {
	        armies[i] = new ArrayList<>();
	        for (int j = 1; j <= 9; j++) {
	            
	            armies[i].add(new ArrayList<MilitaryUnit>());
	        }
	    }
	}
	
	
	public String getBattleReport(int battles) {
		return "";
		
		
	}
	
	public String getBattleDevelopment() {
		return null;}
	
	public void initInitialArmies() {
		
		int total = 0;
		
		initialArmi = new int[2][9];
		
		for (int i = 0; i < armies.length; i++) {
		    for (int j = 0; j < armies[i].size(); j++) {
		        initialArmi[i][j] = ((ArrayList<MilitaryUnit>) armies[i].get(j)).size();
		    }
		}
		
		
	}
	
	public void  initResourcesLooses() {
		
		resourcesLooses  = new int [2][4];
		
		resourcesLooses[0][0] = 0;
		resourcesLooses[0][1] = 0;
		resourcesLooses[0][2] = 0;
		resourcesLooses[0][3] = 0;
		
		resourcesLooses[1][0] = 0;
		resourcesLooses[1][1] =0;
		resourcesLooses[1][2] = 0;
		resourcesLooses[1][3] = 0;
		
	};
	
	
	
	public void updateResourcesLooses(MilitaryUnit x) {
		
		
		if(x.getId_civi() == 1) {
			
			resourcesLooses[0][0] += x.getFoodCost();
			resourcesLooses[0][1] += x.getWoodCost();
			resourcesLooses[0][2] += x.getIronCost();
			resourcesLooses[0][3] += x.getIronCost() + 5*x.getWoodCost()+ 10*x.getFoodCost();
			
		}else if(x.getId_civi() == 2) {
			
			resourcesLooses[1][0] += x.getFoodCost();
			resourcesLooses[1][1] += x.getWoodCost();
			resourcesLooses[1][2] += x.getIronCost();
			resourcesLooses[1][3] += x.getIronCost() + 5*x.getWoodCost()+ 10*x.getFoodCost();
	
		}
		
		
	
	}
	
	public void fleetResourceCost(ArrayList<MilitaryUnit> army) {}
	
	public int remainderPercentageFleet(ArrayList<MilitaryUnit> army) {
		return civilizationDrops;}
	
	public int getGroupDefender(ArrayList<MilitaryUnit> army) {
		return civilizationDrops;}
	
	
	
	public int getCivilizationGroupAttacker() {
		return civilizationDrops;}
	
	public int getEnemyGroupAttacker() {
		return civilizationDrops;} 
	
	
	public void resetArmyArmor() {
		
		for(int i = 0; i< armies[0].size(); i++) {
			
			if(armies[0].get(i) != null) {
				
				((MilitaryUnit) armies[0].get(i)).resetArmor();
				
			}
			
			
			
			
			
		}
		
		
	}
	
	
	
	
}


