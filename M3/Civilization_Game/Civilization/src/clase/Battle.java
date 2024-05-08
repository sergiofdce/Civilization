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
	
	private ArrayList[] armies; 
	

	
	private int [] actualNumberUnitsCivilization; 
	private int [] actualNumberUnitsEnemy;
	
	public Battle() {
		
		civilizationArmy = new ArrayList<>();
		enemyArmy = new ArrayList<>();
		this.initializeCivilizationArmy();
		civilizationArmy.get(0).add(new Cannon());
		System.out.println(civilizationArmy.get(0).get(0).getId_civi());
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
			
	
    public int[] initialFleetNumber(ArrayList<MilitaryUnit> army) {
        // Inicializamos el array de números de unidades con todos los elementos en 0
        int[] initialNumbers = new int[9];

        // Iteramos sobre el ejército y contamos el número de unidades de cada tipo
        for (MilitaryUnit unit : army) {
            if (unit instanceof Swordsman) {
                initialNumbers[0]++;
            } else if (unit instanceof Spearman) {
                initialNumbers[1]++;
            } else if (unit instanceof Crossbow) {
                initialNumbers[2]++;
            } else if (unit instanceof Cannon) {
                initialNumbers[3]++;
            } else if (unit instanceof ArrowTower) {
                initialNumbers[4]++;
            } else if (unit instanceof Catapult) {
                initialNumbers[5]++;
            } else if (unit instanceof RocketLauncherTower) {
                initialNumbers[6]++;
            } else if (unit instanceof Magician) {
                initialNumbers[7]++;
            } else if (unit instanceof Priest) {
                initialNumbers[8]++;
            }
        }

        return initialNumbers;
    }

	public void initInitialCostFleet(int[][] initialCostFleet) {
	     this.initialCostFleet = new int[2][3];
	       for (int i = 0; i < 2; i++) {
	         for (int j = 0; j < 3; j++) {
	            this.initialCostFleet[i][j] = initialCostFleet[i][j];
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


