package clase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import interfaces.MilitaryUnit;
import interfaces.Variables;

public class Battle implements Variables {
	
	  private ArrayList<ArrayList<MilitaryUnit>> civilizationArmy;
	  private ArrayList<ArrayList<MilitaryUnit>> enemyArmy;
	
	
	
	private int[][] initialCostFleet ;
	
	private int initialNumberUnitsEnemy ;
	
	private int initialNumberUnitsCivilization;
	
	private int [] wasteWoodIron; 
	
	
	private int [] enemyDrops; 
	
	private int [] civilizationDrops;
	
	private int [][] resourcesLooses; 
	
	private int [][] initialArmies; 
	
	private ArrayList[] armies; 
	
	private int [][] recursosGenerados;
	
	private MilitaryUnit  atacanteCivi;
	
	private MilitaryUnit  atacanteEne;
	private MilitaryUnit defenCivi;
	private MilitaryUnit defenEne;
	
	private int  grupoAtacanteCivi;
	
	private int grupoAtacanteEne;
	private int  grupoDefenCivi;
	private int  grupoDefenEne;
	
	private Double[] actualNumberUnitsCivilization; 
	private Double[] actualNumberUnitsEnemy;
	private String battleDevelopment;
	private int actualTotalCivi = 0;
	private int actualTotalEne = 0;
	
	private int turno;
	
	public Battle(ArrayList<ArrayList<MilitaryUnit>> civilization,ArrayList<ArrayList<MilitaryUnit>> enemy ) {
		civilizationArmy = civilization;
		this.enemyArmy = enemy;
			armies = new ArrayList[2];
		 armies[0] = this.civilizationArmy;
		 armies[1] = this.enemyArmy;
		initAll(civilizationArmy, enemyArmy);
		}
	
	


public void initAll(ArrayList<ArrayList<MilitaryUnit>> civilization1,ArrayList<ArrayList<MilitaryUnit>> enemy1 ) {
	recursosGenerados = new int[2][2];
	battleDevelopment = "";
	initDropsArrays();
	initialFleetNumber();
	initinitialCostFleet();
	fleetResourceCost();
	ataqueInicial();
	initResourcesLooses();
	remainderPercentageFleet();
}
	

	//Inicializamos los arrays donde guardaremos la cantidad de perdidas por unidad.
	
	public void initDropsArrays() {
		
		
		civilizationDrops = new int[9];
		
		enemyDrops = new int[9];
		
		
	}
	

		
	
	
	
	//Calculamos el total de unidades por cada grupo y el total de unidades iniciales.
	
		public void initialFleetNumber() {
			
			initialArmies = new int[2][9];
			
			
		    for (int i = 0; i < armies.length; i++) {
		    	 int suma = 0;
		        for (int j = 0; j < armies[i].size(); j++) {

		        		
		        	initialArmies[i][j] =  ((ArrayList<ArrayList<MilitaryUnit>>) armies[i].get(j)).size();
		        	
		        	suma +=  ((ArrayList<ArrayList<MilitaryUnit>>) armies[i].get(j)).size();
		        			
		        }
		        
		        
		        if(i == 0) {
		        	initialNumberUnitsCivilization = suma;
		        	
		        }else {
		        	initialNumberUnitsEnemy = suma;
		        	
		        }
		    }
		}
	
		//CInicializamos el array donde meteremos los costes iniciales de los ejercitos.
			
		public void  initinitialCostFleet() {
			
			initialCostFleet = new int[2][3];
			
		
			
		};
		
		//Calculamos el total de recursos que nos ha costado cada ejercito.

	public void	fleetResourceCost() {
		
		    
		    for (int i = 0; i < armies.length; i++) {
		        for (int j = 0; j < armies[i].size(); j++) {
		            for (int x = 0; x < ((ArrayList<MilitaryUnit>) armies[i].get(j)).size(); x++) {
		                MilitaryUnit unit = ((ArrayList<MilitaryUnit>) armies[i].get(j)).get(x);
		                initialCostFleet[i][0] += unit.getFoodCost();
		              
		                initialCostFleet[i][1] += unit.getWoodCost();
		                initialCostFleet[i][2] += unit.getIronCost(); 
		            }
		        }
		    }
		
	}
		
	
	
	//Inicializamos el array donde guardaremos los recursos perdidos aleliminar una unidad.
	
	public void  initResourcesLooses() {
		
		resourcesLooses  = new int [2][4];
		
		
		
	};
	
	
	//Calculamos los recursos perdidos y los vamos usmando.
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
	
	//Calculamos las unidades totales actuales y la cantidad por cada uno de los grupos de los ejercitos, 
	//tambien comprobamos si hay un 20 disponible de ambos ejercitos, en caso contrario finaliza la partida.
	public int remainderPercentageFleet() {
		
		actualNumberUnitsCivilization = new Double[9];
		actualNumberUnitsEnemy= new Double[9];
		actualTotalCivi = 0;
		actualTotalEne = 0;
	  
	        for (int j = 0; j < civilizationArmy.size(); j++) {

	        	actualNumberUnitsCivilization[j] =  (double) civilizationArmy.get(j).size();
	        
	        	actualTotalCivi += civilizationArmy.get(j).size();
	       
	        }
	        
	        for (int j = 0; j < enemyArmy.size(); j++) {

	        	actualNumberUnitsEnemy[j] =  (double) enemyArmy.get(j).size();
	        	actualTotalEne +=  enemyArmy.get(j).size();	
	        }
	        
	        
	       int percentageCivilization = (actualTotalCivi * 100) / initialNumberUnitsCivilization;
	        
	       int percentageEnemy = (actualTotalEne * 100) / initialNumberUnitsEnemy;
	        
	       
	        if (percentageCivilization <= 20) {
	        	
	        	insertLargeReport("\nYou've lost more than 20% of the units! The game is over. \n");
	        	
	        	if(resourcesLooses[0][3] >resourcesLooses[1][3]) {
	        		
	        		insertLargeReport("\nYour opponent has lost fewer resources than you, they are the winner! \n");
	        	}else {
	        		
	        		insertLargeReport("\nYour opponent has lost more resources than you, you are the winner! \n");
	        		
	        	}
	            return -1; 
	            
	            
	        	
	        } else if (percentageEnemy <= 20) {
	        	insertLargeReport("\nThe enemy has lost more than 20% of the units! The game is over. \n");
	        	
	        	if(resourcesLooses[0][3] >resourcesLooses[1][3]) {
	        		
	        		insertLargeReport("\nYour opponent has lost fewer resources than you, they are the winner! \n");
	        	}else {
	        		
	        		insertLargeReport("\nYour opponent has lost more resources than you, you are the winner! \n");
	        		
	        	}
	            return -2; 
	        } else {
	            return 0; 
	        }
		
	    }
		
	

	//Extraemos el grupo y la unidad que atacara en nuestra civilizacion.
	
	public MilitaryUnit getCivilizationGroupAttacker() {
	
		int posGrupo = 0;
	
		boolean comprobarGrupo = false;
		
		
		
		while(!comprobarGrupo) {	
			int aleatorioGrupo = (int) (Math.random() * 100) + 1;
		int acumulado = 0;
		for (int i = 0; i < CHANCE_ATTACK_CIVILIZATION_UNITS.length; i++) {
		    acumulado += CHANCE_ATTACK_CIVILIZATION_UNITS[i];
		    if (aleatorioGrupo <= acumulado) {
		        posGrupo = i;
		        
		        if(civilizationArmy.get(i).isEmpty()) {
		        	comprobarGrupo = false;
		        	break;
		        	
		        }else {
		        	
		        	comprobarGrupo = true;
			        break;
		        	
		        }
		        
		    
		    }
		}
		}
		
	
		Random random = new Random();
		int lenGrupo = ((ArrayList<MilitaryUnit>) armies[0].get(posGrupo)).size();
		

		
		int  posicion;
		System.out.println(lenGrupo);
	
		posicion = random.nextInt(lenGrupo);
		System.out.println(posicion);
		insertLargeReport("\nYour new attacker will be: " + civilizationArmy.get(posGrupo).get(posicion).getSimpleName() + ". \n");
		return civilizationArmy.get(posGrupo).get(posicion);
	}
	
	//Extraemos el grupo y la unidad que atacara en el enemigo.
	public MilitaryUnit getEnemyGroupAttacker() {
		
		boolean comprobarGrupo = false;
	
		int posGrupo = 0;
		MilitaryUnit grupoSelect;

		while(!comprobarGrupo) {	
			
			int aleatorioGrupo = (int) (Math.random() * 100) + 1;
			int acumulado = 0;
			for (int i = 0; i < CHANCE_ATTACK_ENEMY_UNITS.length; i++) {
			    acumulado += CHANCE_ATTACK_ENEMY_UNITS[i];
			    if (aleatorioGrupo <= acumulado) {
			        posGrupo = i;
			        
			        if(((ArrayList<ArrayList<MilitaryUnit>>) armies[1].get(i)).isEmpty()) {
			        	
			        	comprobarGrupo = false;
			        	break;
			        	
			        }else {
			        	
			        	comprobarGrupo = true;
				        break;
			        	
			        }
			        
			        
			    }
			}		
			
		}
		Random random = new Random();
		int lenGrupo = ((ArrayList<MilitaryUnit>) armies[1].get(posGrupo)).size();
		
		int  posicion;
		
		grupoAtacanteEne = posGrupo;
		posicion = random.nextInt(lenGrupo);
		
		insertLargeReport("\nThe enemy's new attacker will be: " + enemyArmy.get(posGrupo).get(posicion).getSimpleName() + ". \n");
		
		return enemyArmy.get(posGrupo).get(posicion);
	
	
		}
	
	//Extraemos la unidad que nod defenderá.
	
	public MilitaryUnit getGroupDefenderCivi() {
		
		double [] posibilidades = new double[9];
		int elegido ;
		int grupo = -1;
		
		for(int i = 0; i < actualNumberUnitsCivilization.length; i++) {
		    
	    	
		  
		    
			if(actualNumberUnitsCivilization[i] == 0) {
				posibilidades[i] = 0;	
				
			}else {posibilidades[i] = 100*(actualNumberUnitsCivilization[i]) / actualTotalCivi;}
			
		}
		
		double aleatorio = (int) (Math.random() * 99.9) + 1;
		
		
		 double suma = 0;
		 
		
		    // Selección del grupo
		    for (int j = 0; j < posibilidades.length; j++) {
		    
		    	
		        suma += posibilidades[j];
		    
		        if (suma >= aleatorio) {
		            
		        	grupo = j;
		        	
		        	break;
		        }
		    }
		    		
		   
		    	    int lenGrupo = ((ArrayList<MilitaryUnit>) armies[0].get(grupo)).size();

		    Random random = new Random();
		    
		    grupoDefenCivi = grupo;
			
			elegido = random.nextInt(lenGrupo);
			insertLargeReport("Tu nuevo defensor será: " + civilizationArmy.get(grupo).get(elegido).getSimpleName() + ". \n");
			return  civilizationArmy.get(grupo).get(elegido);
			
			
		}
		


	//Extraemos la defensa del enemigo.
	
	public MilitaryUnit getGroupDefenderEne() {
		
		double [] posibilidades = new double[9];
		int elegido;
		int grupo = -1;
		
		for(int i = 0; i < actualNumberUnitsEnemy.length; i++) {
			
			if(actualNumberUnitsEnemy[i] == 0) {
				posibilidades[i] = 0;	
				
			}else {posibilidades[i] = 100*(actualNumberUnitsEnemy[i]) / (actualTotalEne);
			
			
			
		}}

	
		double aleatorio = (int) (Math.random() * 99.9) + 1;
		
		
		 double suma = 0;
	
		 
		  
	
	
		    
		    // Selección del grupo
		    for (int j = 0; j < posibilidades.length; j++) {
		    
		    	
		        suma += posibilidades[j];
		    
		        if (suma >= aleatorio) {
		            
		        	grupo = j;
		        	
		        	break;
		        }
		    }
		    		
		    	
		    

		    int lenGrupo = ((ArrayList<MilitaryUnit>) armies[1].get(grupo)).size();
		   
		    Random random = new Random();
		    
		    grupoDefenEne = grupo;
		    
			elegido = random.nextInt(lenGrupo);
			insertLargeReport("\nThe new defender of the enemy will be: " + enemyArmy.get(grupo).get(elegido).getSimpleName() + ".\n");
			
			return enemyArmy.get(grupo).get(elegido);
			
		
	} 

	//Resetealas armaduras de los ejercitos.
	public void resetArmyArmor() {
		
		for(int i = 0; i<civilizationArmy.size(); i++) {
			
			for(MilitaryUnit x :civilizationArmy.get(i) ) {
				
				x.resetArmor();
				
				
			}
			
			for(MilitaryUnit x :enemyArmy.get(i) ) {
				
				x.resetArmor();
				
				
			}
		}
	}
			
	
 
//Generamos los recursos que se extraen al morir una unidad.

	public void generarRecursosBatalla(MilitaryUnit x) {
	    int aleatorio = 90;
	    int[] wateGene = new int[2];

	    if (x instanceof Swordsman && aleatorio >= CHANCE_GENERATNG_WASTE_SWORDSMAN) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_SWORDSMAN) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_SWORDSMAN) / 100;
	    } else if (x instanceof Spearman && aleatorio >= CHANCE_GENERATNG_WASTE_SPEARMAN) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_SPEARMAN) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_SPEARMAN) / 100;
	    } else if (x instanceof Crossbow && aleatorio >= CHANCE_GENERATNG_WASTE_CROSSBOW) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_CROSSBOW) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_CROSSBOW) / 100;
	    } else if (x instanceof Cannon && aleatorio >= CHANCE_GENERATNG_WASTE_CANNON) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_CANNON) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_CANNON) / 100;
	    } else if (x instanceof ArrowTower && aleatorio >= CHANCE_GENERATNG_WASTE_ARROWTOWER) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_ARROWTOWER) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_ARROWTOWER) / 100;
	    } else if (x instanceof Catapult && aleatorio >= CHANCE_GENERATNG_WASTE_CATAPULT) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_CATAPULT) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_CATAPULT) / 100;
	    } else if (x instanceof RocketLauncherTower && aleatorio >= CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_ROCKETLAUNCHERTOWER) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_ROCKETLAUNCHERTOWER) / 100;
	    } else if (x instanceof Magician && aleatorio >= CHANCE_ATTACK_AGAIN_MAGICIAN) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_MAGICIAN) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_MAGICIAN) / 100;
	    } else if (x instanceof Priest && aleatorio >= CHANCE_ATTACK_AGAIN_PRIEST) {
	        wateGene[0] = (PERCENTATGE_WASTE * WOOD_COST_PRIEST) / 100;
	        wateGene[1] = (PERCENTATGE_WASTE * IRON_COST_PRIEST) / 100;
	    }

	    

	    if (x.getId_civi() == 2) {
	    	insertLargeReport("\nBy defeating your enemy, you have gained " + wateGene[0] + " wood and " + wateGene[1] + " iron. \n");
	        recursosGenerados[0][0] +=  wateGene[0];
	        recursosGenerados[0][1] +=  wateGene[1];
	        
	    } else if (x.getId_civi() == 1) {
	    	insertLargeReport("\nBy defeating you, your enemy has gained " + wateGene[0] + " wood and " + wateGene[1] + " iron. \n");
	        recursosGenerados[1][0] +=  wateGene[0];
	        recursosGenerados[1][1] +=  wateGene[1];
	    }

	   
	}
	
	//Funcion que devuelve los recursos totales generados con las muertes.

	public int [][] getRecursosGanados() {
		
		return recursosGenerados;
		
	}

	//Miramos si se repite el ataque, si el guerrero puede volver a atacar.
	
	public int attackAgain(MilitaryUnit x) {
		
		int aleatorio = (int) (Math.random() * 100) + 1;
		int [] wateGene = new int [2];
		
		 if (x instanceof Swordsman) {
			 
			 if(aleatorio <=  CHANCE_ATTACK_AGAIN_SWORDSMAN ) {
				
				 
				 return 1;
				 
			 }else {
				 
				 return 0;
			 }
			 

         } else if (x instanceof Spearman) {
        	
        		 if(aleatorio <=  CHANCE_ATTACK_AGAIN_SPEARMAN ) {
     				
    				 
    				 return 1;
    				 
    			 }else {
    				 
    				 return 0;
    			 }
         } else if (x instanceof Crossbow) {
        	 if(aleatorio <=  CHANCE_ATTACK_AGAIN_CROSSBOW ) {
  				
				 
 				 return 1;
 				 
 			 }else {
 				 
 				 return 0;
 			 }
         } else if (x instanceof Cannon) {
        	 if(aleatorio <=  CHANCE_ATTACK_AGAIN_CANNON ) {
   				
				 
  				 return 1;
  				 
  			 }else {
  				 
  				 return 0;
  			 }
         } else if (x instanceof ArrowTower) {
        	 if(aleatorio <= CHANCE_ATTACK_AGAIN_ARROWTOWER ) {
  				
				 
 				 return 1;
 				 
 			 }else {
 				 
 				 return 0;
 			 }
         } else if (x instanceof Catapult) {
        	 if(aleatorio <= CHANCE_ATTACK_AGAIN_CATAPULT ) {
  				
				 
 				 return 1;
 				 
 			 }else {
 				 
 				 return 0;
 			 }
        } else if (x instanceof RocketLauncherTower) {
        	if(aleatorio <=  CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER ) {
  				
				 
 				 return 1;
 				 
 			 }else {
 				 
 				 return 0;
 			 }
         } else if (x instanceof Magician) {
        	 if(aleatorio <=  CHANCE_ATTACK_AGAIN_MAGICIAN ) {
				 
        		 
 				 return 1;
 				 
 			 }else {
 				 
 				 return 0;
 			 }
				 
				 
			 }
         else {
			 
			 return 0;
		 }
		
		
	}



//Determinamos quien será el primer ejercito en atacar.	

public void ataqueInicial() {
	
	turno = (int) ((Math.random() * 2) + 1);	
	
}

private int comprobacionPorciento;

public void comprobarVida(MilitaryUnit x) {
	
	MilitaryUnit actual = x;
	
	if (x.getActualArmor() <=0) {

		
		
		
		updateResourcesLooses(actual);
	
		
		if(x.getId_civi() == 1) {
			insertLargeReport("\nYour defender has died! Resources will be generated. \n");
			civilizationDrops[grupoDefenCivi] +=1;
			generarRecursosBatalla(actual);
			if(actual instanceof Priest) {
				
				if(civilizationArmy.get(8).size() == 1){
					
					desantificar();
					
				}
				
				
			}
			civilizationArmy.get(grupoDefenCivi).remove(actual);
			comprobacionPorciento = remainderPercentageFleet();
			
			if(comprobacionPorciento == 0) {
				defenCivi = getGroupDefenderCivi();
				
				
			}
			
		}else if(x.getId_civi() == 2) {
			insertLargeReport("\nThe enemy defender has died! Resources will be generated. \n");
			generarRecursosBatalla(actual);
			enemyDrops [grupoDefenEne] +=1;
			enemyArmy.get(grupoDefenEne).remove(actual);
			comprobacionPorciento = remainderPercentageFleet();
			if(comprobacionPorciento == 0) {
				
				defenEne = getGroupDefenderEne();
				
			}
			
		}

	}
	
}


//Función que realiza los ataques dependiendo del turno.

public void ataque() {
	
	
	if(turno == 1) {
		
	
		defenEne.takeDamage(atacanteCivi.attack());
		insertLargeReport(atacanteCivi.getSimpleName() + " attacks the unit " + defenEne.getSimpleName() + " of your enemy. \n");
		insertLargeReport(defenEne.getSimpleName() + " lost " +  atacanteCivi.attack() + " armor and now has " + defenEne.getActualArmor() + " life left. \n");

		comprobarVida(defenEne);
		
		
		
		
	}else if(turno == 2) {
		
		defenCivi.takeDamage(atacanteEne.attack());
		insertLargeReport("Your enemy's " +  atacanteEne.getSimpleName() + " has attacked the following unit of your army: " + defenCivi.getSimpleName() + "\n");
		insertLargeReport(defenCivi.getSimpleName() + " lost " +  atacanteEne.attack() + " armor and now has " + defenCivi.getActualArmor() + " life left. \n");

		
		comprobarVida(defenCivi);
		
	}
	
	

}

//Funcion que realiza el cambio de turno.

private void cambioTurno() {
	
	if(turno == 1) {
		
		insertLargeReport("\n +++ Turn change, it's the enemy's turn! +++ \n\n");
		
		turno = 2;
		
	}else {
		
		turno = 1;
		insertLargeReport("\n +++ Turn change, show them what you can do! +++ \n\n");
		
	}
	
	
}

//Funcion que inserta los reportes del reporte detallado.

public void insertLargeReport(String x) {
	
	battleDevelopment += x;
	
}

//Devolvemos la string del reporte detallado.

public String getLargeReport() {
	

	return battleDevelopment;
	
	
	
}


public void SetNewExperience(){
	
	for (ArrayList<MilitaryUnit> array : enemyArmy) {
        for (MilitaryUnit unit : array) {
            
        	unit.setExperience(1);
        }
    }
	
	for (ArrayList<MilitaryUnit> array : civilizationArmy) {
        for (MilitaryUnit unit : array) {
            
        	unit.setExperience(1);
        }
    }
	
	
}


public void desantificar() {
	
	 for (ArrayList<MilitaryUnit> array : enemyArmy) {
	       for (MilitaryUnit unit : array) {
	           if (unit instanceof Swordsman) {
	           	
	           	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
	           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
	               
	           } else if (unit instanceof Spearman) {
	        	   
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	           } else if (unit instanceof Crossbow) {
	        	   
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	           } else if (unit instanceof Cannon) {
	        	   
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	              	
	           }else if (unit instanceof ArrowTower) {
	        	   
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	              	
	           } else if (unit instanceof Catapult) {
	        	   
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	              	
	           }else if (unit instanceof RocketLauncherTower) {
	           	
	        	 	unit.setArmor(unit.getActualArmor() / PLUS_ARMOR_UNIT_SANCTIFIED );
		           	unit.setBaseDamage(unit.attack() / PLUS_ATTACK_UNIT_SANCTIFIED);
		               
	              	
	           }
	       }
	   }

	
	
	
}





//Imprimimos el reporte resumen;

public void printReport() {
    System.out.println("BATTLE STATISTICS\n");
    
    System.out.printf("%-20s %-6s %-6s %-20s %-6s %-6s\n", "Army planet", "Units", "Drops", "Initial Army Enemy", "Units", "Drops");
    
    
        System.out.printf("%-20s %-6d %-6d %-20s %-6d %-6d\n", 
           "Swordman", initialArmies[0][0], civilizationDrops[0], 
           "Swordman", initialArmies[1][0], enemyDrops[0]);
        
        System.out.printf("%-20s %-6d %-6d %-20s %-6d %-6d\n", 
                "Spearman", initialArmies[0][1], civilizationDrops[1], 
                "Spearman", initialArmies[1][1], enemyDrops[1]);
        
        System.out.printf("%-20s %-6d %-6d %-20s %-6d %-6d\n", 
                "Crossbow", initialArmies[0][2], civilizationDrops[2], 
                "Crossbow", initialArmies[1][2], enemyDrops[2]);
        
        System.out.printf("%-20s %-6d %-6d %-20s %-6d %-6d\n", 
                "Cannon", initialArmies[0][3], civilizationDrops[3], 
                "Cannon", initialArmies[1][3], enemyDrops[3]);
        
        System.out.printf("%-20s %-6d %-6d\n", 
                "Arrow Tower", initialArmies[0][4], civilizationDrops[4]);
        
        System.out.printf("%-20s %-6d %-6d\n", 
                "Catapult", initialArmies[0][5], civilizationDrops[5]);
        
        System.out.printf("%-20s %-6d %-6d\n", 
                "Rocket Launcher Tower", initialArmies[0][6], civilizationDrops[6]);
        
        System.out.printf("%-20s %-6d %-6d\n", 
                "Magician", initialArmies[0][7], civilizationDrops[7]);
        
        System.out.printf("%-20s %-6d %-6d\n", 
                "Priest", initialArmies[0][8], civilizationDrops[8]);
    
    System.out.println("\n**************************************************");
  
    System.out.println("Cost Army Civilization");
    
    System.out.printf("%-20s %s\n","Food: ", initialCostFleet[0][0] );
    System.out.printf("%-20s %s\n","Wood: ", initialCostFleet[0][1] );
    System.out.printf("%-20s %s\n","Iron: ", initialCostFleet[0][2] );

    System.out.println("\nCost Army Enemy");
    System.out.printf("%-20s %s\n","Food: ", initialCostFleet[1][0] );
    System.out.printf("%-20s %s\n","Wood: ", initialCostFleet[1][1] );
    System.out.printf("%-20s %s\n","Iron: ", initialCostFleet[1][2] );
    
    

    
    System.out.println("\n************************************************************************");
    System.out.println("Losses Army Civilization");
    System.out.printf("%-20s %s\n","Food: ", resourcesLooses[0][0] );
    System.out.printf("%-20s %s\n","Wood: ", resourcesLooses[0][1] );
    System.out.printf("%-20s %s\n","Iron: ", resourcesLooses[0][2] );
    System.out.println("Losses Army Enemy");
    System.out.printf("%-20s %s\n","Food: ", resourcesLooses[1][0] );
    System.out.printf("%-20s %s\n","Wood: ", resourcesLooses[1][1] );
    System.out.printf("%-20s %s\n","Iron: ", resourcesLooses[1][2] );
    
    
	if(resourcesLooses[0][3] < resourcesLooses[1][3]) {
		
		 System.out.printf("\nHas perdido la batalla!\n");
		
		
	}else {
		
		
		 System.out.printf("\nHas ganado la batalla!\n\n");
	}
    
    System.out.println("\n************************************************************************");}

//Devolvemos el string del reporte resumen;

public String generalReportToString() {
  StringBuilder battleStatistics = new StringBuilder();

  battleStatistics.append("<html><pre>BATTLE STATISTICS<br><br>");
  battleStatistics.append(String.format("%-23s %-6s %-6s %-20s %-6s %-6s<br>", "Army planet", "Units", "Drops", "Initial Army Enemy", "Units", "Drops"));

  battleStatistics.append(String.format("%-23s %-6d %-6d %-20s %-6d %-6d<br>", 
         "Swordman", initialArmies[0][0], civilizationDrops[0], 
         "Swordman", initialArmies[1][0], enemyDrops[0]));

  battleStatistics.append(String.format("%-23s %-6d %-6d %-20s %-6d %-6d<br>", 
          "Spearman", initialArmies[0][1], civilizationDrops[1], 
          "Spearman", initialArmies[1][1], enemyDrops[1]));
  battleStatistics.append(String.format("%-23s %-6d %-6d %-20s %-6d %-6d<br>", 
          "Crossbow", initialArmies[0][2], civilizationDrops[2], 
          "Crossbow", initialArmies[1][2], enemyDrops[2]));

  battleStatistics.append(String.format("%-23s %-6d %-6d %-20s %-6d %-6d<br>", 
          "Cannon", initialArmies[0][3], civilizationDrops[3], 
          "Cannon", initialArmies[1][3], enemyDrops[3]));

  battleStatistics.append(String.format("%-23s %-6d %-6d<br>", 
          "Arrow Tower", initialArmies[0][4], civilizationDrops[4]));

  battleStatistics.append(String.format("%-23s %-6d %-6d<br>", 
          "Catapult", initialArmies[0][5], civilizationDrops[5]));

  battleStatistics.append(String.format("%-23s %-6d %-6d<br>", 
          "Rocket Launcher Tower", initialArmies[0][6], civilizationDrops[6]));

  battleStatistics.append(String.format("%-23s %-6d %-6d<br>", 
          "Magician", initialArmies[0][7], civilizationDrops[7]));

  battleStatistics.append(String.format("%-23s %-6d %-6d<br>", 
          "Priest", initialArmies[0][8], civilizationDrops[8]));

  // Continúa con el resto de las construcciones

  battleStatistics.append("<br>**************************************************<br>");
  battleStatistics.append("Cost Army Civilization<br>");

  battleStatistics.append(String.format("%-23s %s<br>","Food: ", initialCostFleet[0][0]));
  battleStatistics.append(String.format("%-23s %s<br>","Wood: ", initialCostFleet[0][1]));
  battleStatistics.append(String.format("%-23s %s<br>","Iron: ", initialCostFleet[0][2]));
  
  battleStatistics.append("<br>**************************************************<br>");

  battleStatistics.append("Cost Army Enemy<br>");
  battleStatistics.append(String.format("%-23s %s<br>","Food: ", initialCostFleet[1][0]));
  battleStatistics.append(String.format("%-23s %s<br>","Wood: ", initialCostFleet[1][1]));
  battleStatistics.append(String.format("%-23s %s<br>","Iron: ", initialCostFleet[1][2]));
  
  battleStatistics.append("<br>**************************************************<br>");
  battleStatistics.append("Losses Army Civilization<br>");

  battleStatistics.append(String.format("%-23s %s<br>","Food: ", resourcesLooses[0][0]));
  battleStatistics.append(String.format("%-23s %s<br>","Wood: ", resourcesLooses[0][1]));
  battleStatistics.append(String.format("%-23s %s<br>","Iron: ", resourcesLooses[0][2]));

  battleStatistics.append("<br>Losses Army Enemy<br>");
  battleStatistics.append(String.format("%-23s %s<br>","Food: ", resourcesLooses[1][0]));
  battleStatistics.append(String.format("%-23s %s<br>","Wood: ", resourcesLooses[1][1]));
  battleStatistics.append(String.format("%-23s %s<br>","Iron: ", resourcesLooses[1][2]));
  
  battleStatistics.append("</pre></html>");

  String battleStatisticsString = battleStatistics.toString();
  return battleStatisticsString;
}




private int newAtack;
	
public void  battleGame() {
	insertLargeReport("The battle begins!\n\n");
		remainderPercentageFleet();
		atacanteCivi = getCivilizationGroupAttacker() ;
				
		
		atacanteEne =  getEnemyGroupAttacker();
		defenCivi = getGroupDefenderCivi();
		defenEne = getGroupDefenderEne();
		ataqueInicial();
		insertLargeReport("Your attacker will be " + atacanteCivi.getSimpleName() + " and your defender will be " + defenCivi.getSimpleName() + ".\n");
		insertLargeReport("The opponent's attacker will be " + atacanteEne.getSimpleName() + " and their defender will be " + defenEne.getSimpleName() + ".\n");

		while(true) {
			
			ataque();
			
			if(comprobacionPorciento != 0) {
				
				break;
			}
			
			if(turno == 1) {
			newAtack = attackAgain(atacanteCivi);
			
			
			}else if(turno == 2) {
				newAtack = attackAgain(atacanteEne);
				
				
				}
			
			if(newAtack == 1) {
				
				if(turno == 1) {
					
					insertLargeReport("Your warrior attacks again! \n");
					
				}else {
					
					insertLargeReport("Your enemy attacks again! \n");
					
				}
				continue;
				
			}else {
				
					cambioTurno();
				}
		
				
			}
		resetArmyArmor();
		SetNewExperience();
	
		printReport();
		
		
		}

		}
		






