package clase;

import java.util.ArrayList;
import java.util.Random;

import interfaces.MilitaryUnit;

public class Battle {
	
	  private ArrayList<ArrayList<MilitaryUnit>> civilizationArmy;
	  private ArrayList<ArrayList<MilitaryUnit>> enemyArmy;
	
	private String []  battleDevelopment;
	
	private int[][]  initialCostFleet ;
	
	private int initialNumberUnitsEnemy ;
	
	private int initialNumberUnitsCivilization;
	
	private int [] wasteWoodIron; 
	private int enemyDrops; 
	
	private int civilizationDrops;
	
	private int [][] resourcesLooses; 
	
	private int [][] initialArmi;
	private int [][] initialArmies;
	
	private ArrayList[] armies; 
	

	private int [] actualNumberUnitsCivilization; 
	private int [] actualNumberUnitsEnemy;
	
public Battle() {
		
		civilizationArmy = new ArrayList<>();
		enemyArmy = new ArrayList<>();
		this.initializeCivilizationArmy();
		civilizationArmy.get(0).add(new Cannon());
		System.out.println(civilizationArmy.get(0).get(0).getId_civi());
		actualNumberUnitsCivilization = new int[9];
        actualNumberUnitsEnemy = new int[9];
        initialArmies = new int[2][9];
        initialNumberUnitsCivilization = 0;
        initialNumberUnitsEnemy = 0;
        

		}


	
	public void initialFleetNumber() {
        // Calcular el número inicial de unidades para el ejército de la civilización
        for (int i = 0; i < 9; i++) {
            initialNumberUnitsCivilization += initialArmies[0][i];
        }

        // Calcular el número inicial de unidades para el ejército enemigo
        for (int i = 0; i < 9; i++) {
            initialNumberUnitsEnemy += initialArmies[1][i];
        }
    }
	
	public int remainderPercentageFleet() {
	    // Calcular los porcentajes de unidades restantes para la civilización
	    int percentageCivilization = 0;
	    for (int i = 0; i < actualNumberUnitsCivilization.length; i++) {
	        percentageCivilization += actualNumberUnitsCivilization[i];
	    }
	    percentageCivilization = (percentageCivilization * 100) / initialNumberUnitsCivilization;

	    // Calcular los porcentajes de unidades restantes para el enemigo
	    int percentageEnemy = 0;
	    for (int i = 0; i < actualNumberUnitsEnemy.length; i++) {
	        percentageEnemy += actualNumberUnitsEnemy[i];
	    }
	    percentageEnemy = (percentageEnemy * 100) / initialNumberUnitsEnemy;

	    
	    if (percentageCivilization <= 20) {
	        return -1; 
	    } else if (percentageEnemy <= 20) {
	        return -2; 
	    } else {
	        return 0; 
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
	
	
	// Métodos para acceder y modificar los arreglos de unidades
	
	public int[][] getInitialArmies() {
        return initialArmies;
    }

    public int getInitialNumberUnitsCivilization() {
        return initialNumberUnitsCivilization;
    }

    public int getInitialNumberUnitsEnemy() {
        return initialNumberUnitsEnemy;
    }
    public int[] getActualNumberUnitsCivilization() {
        return actualNumberUnitsCivilization;
    }

    public void setActualNumberUnitsCivilization(int[] actualNumberUnitsCivilization) {
        this.actualNumberUnitsCivilization = actualNumberUnitsCivilization;
    }

    public int[] getActualNumberUnitsEnemy() {
        return actualNumberUnitsEnemy;
    }

    public void setActualNumberUnitsEnemy(int[] actualNumberUnitsEnemy) {
        this.actualNumberUnitsEnemy = actualNumberUnitsEnemy;
    }

    public void initializeCivilizationArmy() {
        civilizationArmy = new ArrayList<>();

	    for (int i = 1; i <= 9; i++) {
	        civilizationArmy.add((ArrayList<MilitaryUnit>) new ArrayList<MilitaryUnit>());
	    }
	}
	
	public void initializeCivilizationEnemArmy() {
        enemyArmy = new ArrayList<>();

	    for (int i = 1; i <= 9; i++) {
	    	enemyArmy.add((ArrayList<MilitaryUnit>) new ArrayList<MilitaryUnit>());
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
	
	
     
	public MilitaryUnit getCivilizationGroupAttacker() {
		
		int aleatorioGrupo =    (int) (Math.random() * (100 - 1 + 1)) + 1;
		
		int posGrupo = 0;
		
		MilitaryUnit grupoSelect;
		
		
		if(aleatorioGrupo <1 && aleatorioGrupo <=37) {
			
			posGrupo = 3;
			
		}else if(aleatorioGrupo > 37 && aleatorioGrupo<=41) {
			
			posGrupo = 0;
		}else if(aleatorioGrupo > 41 && aleatorioGrupo<=50) {
			
			posGrupo = 1;
		}else if(aleatorioGrupo > 50 && aleatorioGrupo<=63) {
			
			posGrupo = 2;
		}else if(aleatorioGrupo > 63 && aleatorioGrupo<=67) {
			
			posGrupo = 4;
		}else if(aleatorioGrupo > 67 && aleatorioGrupo<=76) {
			
			posGrupo = 5;
		}else if(aleatorioGrupo > 76 && aleatorioGrupo<=90) {
			
			posGrupo = 6;
		}else if(aleatorioGrupo > 91 && aleatorioGrupo<=100) {
			
			posGrupo = 7;
		}
		
		Random random = new Random();
		int lenGrupo = ((ArrayList<MilitaryUnit>) armies[0].get(posGrupo)).size();
		
		grupoSelect = ((ArrayList<MilitaryUnit>) armies[0].get(posGrupo)).get(random.nextInt(lenGrupo)-1);
		
		
		return grupoSelect;}
	
	public MilitaryUnit getEnemyGroupAttacker() {
		int aleatorioGrupo =    (int) (Math.random() * (100 - 1 + 1)) + 1;
		
		int posGrupo = 0;
		
		MilitaryUnit grupoSelect;
		
		
		if(aleatorioGrupo <1 && aleatorioGrupo <=10) {
			
			posGrupo = 0;
			
		}else if(aleatorioGrupo > 10 && aleatorioGrupo<=30) {
			
			posGrupo = 1;
		}else if(aleatorioGrupo > 30 && aleatorioGrupo<=60) {
			
			posGrupo = 2;
		}else if(aleatorioGrupo > 60 && aleatorioGrupo<=100) {
			
			posGrupo = 3;
		}

		Random random = new Random();
		int lenGrupo = ((ArrayList<MilitaryUnit>) armies[1].get(posGrupo)).size();
		
		grupoSelect = ((ArrayList<MilitaryUnit>) armies[1].get(posGrupo)).get(random.nextInt(lenGrupo)-1);
		
		return grupoSelect;
	
		}

	
	public void resetArmyArmor() {
		
		for(int i = 0; i< armies[0].size(); i++) {
			
			if(armies[0].get(i) != null) {
				
				((MilitaryUnit) armies[0].get(i)).resetArmor();
				
			}
		}
	}
			
	
	public int initInitialNumberUnits(int initialNumberUnitsCivilization, int initialNumberUnitsEnemy) {
	    this.initialNumberUnitsCivilization = initialNumberUnitsCivilization;
	    this.initialNumberUnitsEnemy = initialNumberUnitsEnemy;

	    // Verificar si la batalla ha terminado
	    double civilizationPercentage = (double) initialNumberUnitsCivilization / initialNumberUnitsCivilization;
	    double enemyPercentage = (double) initialNumberUnitsEnemy / initialNumberUnitsEnemy;

	    if (civilizationPercentage <= 0.2 || enemyPercentage <= 0.2) {
	        return 1; // Indicar que la batalla ha terminado
	    } else {
	        return 0; // Indicar que la batalla continúa
	    }
	}



	

	

		
}


