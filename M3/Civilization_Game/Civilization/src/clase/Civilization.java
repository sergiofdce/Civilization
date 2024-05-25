package clase;

import java.util.ArrayList;

import BBDD.BBDD;
import excepciones.ResourceException;
import excepciones.BuildingException;
import interfaces.MilitaryUnit;
import interfaces.Variables;


public class Civilization implements Variables {
	
	private String name;
	private int technologyDefense, technologyAttack;
	private int wood, iron, food, mana;
	private int enemyWood, enemyIron, enemyFood;
	private int magicTower, church, farm, smithy, carpentry;
	private int battles;
	private ArrayList<ArrayList<MilitaryUnit>> enemyArmy;
	public int getEnemyWood() {
		return enemyWood;
	}

	public void setEnemyWood(int enemyWood) {
		this.enemyWood = enemyWood;
	}

	public int getEnemyIron() {
		return enemyIron;
	}

	public void setEnemyIron(int enemyIron) {
		this.enemyIron = enemyIron;
	}

	public int getEnemyFood() {
		return enemyFood;
	}

	public void setEnemyFood(int enemyFood) {
		this.enemyFood = enemyFood;
	}

	public ArrayList<ArrayList<MilitaryUnit>> getEnemyArmy() {
		return enemyArmy;
	}

	public void setEnemyArmy(ArrayList<ArrayList<MilitaryUnit>> enemyArmy) {
		this.enemyArmy = enemyArmy;
	}

	private ArrayList<ArrayList<MilitaryUnit>> army;
	private ArrayList[] batallasGuardadas = new ArrayList[2];
	
	public void initEnemyArmy() {
		
		
		

	        // Inicializar cada uno de los 3 ArrayLists dentro de civilizationArmy
	    enemyArmy = new ArrayList<>();
	        for (int i = 0; i < 9; i++) {
	        	enemyArmy.add(new ArrayList<MilitaryUnit>());
//	           System.out.println(enemyArmy);
	        }
	}
	// Constructor 
	public Civilization() {
		
		// Inicializar ArrayList
		initArrayEjer();
        
        // Tecnologias
        this.technologyDefense = 0;
        this.technologyAttack = 0;
        // Materiales
        this.wood = 10000000;
        this.iron = 10000000;
        this.food = 10000000;
        this.mana = 10000000;
        enemyWood =	WOOD_BASE_ENEMY_ARMY;
        enemyIron = IRON_BASE_ENEMY_ARMY;
        enemyFood = FOOD_BASE_ENEMY_ARMY ;
        // Edificios
        this.magicTower = 0;
        this.church = 0;
        this.farm = 0;
        this.smithy = 0;
        this.carpentry = 0;
        // Batallas
        this.battles = 0;
        batallasGuardadas[0] = new ArrayList<>();
        batallasGuardadas[1] = new ArrayList<>();
        initEnemyArmy();
        
       
    }
	
	public ArrayList[] getReportes() {
		
		return batallasGuardadas;
	}
	
	public String returnSavedLargeReport(int i) {
		return  (String) batallasGuardadas[1].get(i);
		
		
	}
	
	public String returnSavedGeneralReport(int i) {
		return  (String) batallasGuardadas[0].get(i);
		
		
	}
	
	public void initArrayEjer() {
 		
		army = new ArrayList<>();
	
	        // Inicializar cada uno de los 9 ArrayLists dentro de civilizationArmy
	        for (int i = 0; i < 9; i++) {
	            army.add(new ArrayList<>());
	        
	        }
		
		
	}
	
public int getBattles(){
	
	return battles;
	
}
public void setBattles(int x){
	
	this.battles = x;
	
}
public void setName (String name) {
	
	this.name = name;
	
}

public String getName () {
	
	return this.name;
	
}
    public void insertBattle(String general, String large) {


        if ( batallasGuardadas[0].size() == 5) {


            batallasGuardadas[0].remove(4);
            batallasGuardadas[1].remove(4);

            batallasGuardadas[0].add(0,general);
            batallasGuardadas[1].add(0,large);
        }else {

            batallasGuardadas[0].add(0,general);
            batallasGuardadas[1].add(0,large);

        }

        BBDD carga = new BBDD();
        carga.guardarJuego(this);
    }
	
	
	// Getters y Setters
	
	
//	Materiales
	
	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public ArrayList<ArrayList<MilitaryUnit>> getArmy() {
		return army;
	}
	
	
//	Edificios
	


	public int getMagicTower() {
		return magicTower;
	}

	public void setMagicTower(int magicTower) {
		this.magicTower = magicTower;
	}

	public int getChurch() {
		return church;
	}

	public void setChurch(int church) {
		this.church = church;
	}

	public int getFarm() {
		return farm;
	}

	public void setFarm(int farm) {
		this.farm = farm;
	}

	public int getSmithy() {
		return smithy;
	}

	public void setSmithy(int smithy) {
		this.smithy = smithy;
	}

	public int getCarpentry() {
		return carpentry;
	}

	public void setCarpentry(int carpentry) {
		this.carpentry = carpentry;
	}
	
	
//	Tecnologia
	
	public int getTechnologyDefense() {
		return technologyDefense;
	}


	public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}


	public int getTechnologyAttack() {
		return technologyAttack;
	}


	public void setTechnologyAttack(int technologyAttack) {
		this.technologyAttack = technologyAttack;
	}
	
	

	
	
	

	// Metodos
	
//	Calcular numero de unidades pasando index del ArrayList army
	public int calculateLengthAtIndex(int index) {
	    if (index >= 0 && index < army.size()) {
	        return army.get(index).size();
	    } else {
	        return -1;
	    }
	}
	
//	Edificios

	public void newChurch(int n) throws ResourceException {
		// Requisitos: 10.000 Food		20.000 Wood 		24.000 Iron		10.000 Mana
		if (this.getFood() >= n * FOOD_COST_CHURCH && 
				this.getWood() >= n * WOOD_COST_CHURCH && 
				this.getIron() >= n * IRON_COST_CHURCH && 
				this.getMana() >= n * MANA_COST_CHURCH) {
            this.setFood(this.getFood() - n * FOOD_COST_CHURCH);
            this.setWood(this.getWood() - n * WOOD_COST_CHURCH);
            this.setIron(this.getIron() - n * IRON_COST_CHURCH);
            this.setMana(this.getMana() - n * MANA_COST_CHURCH);
            
            // Crea nuevo edificio
            this.setChurch(this.getChurch() + n);

        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva iglesia.");
        }
		BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}


	public void newMagicTower(int n) throws ResourceException {
		// Requisitos: 10.000 Food		20.000 Wood 		24.000 Iron
		if (this.getFood() >= n * FOOD_COST_MAGICTOWER && 
				this.getWood() >= n * WOOD_COST_MAGICTOWER && 
				this.getIron() >= n * IRON_COST_MAGICTOWER) {
			this.setFood(this.getFood() - n * FOOD_COST_MAGICTOWER);
            this.setWood(this.getWood() - n * WOOD_COST_MAGICTOWER);
            this.setIron(this.getIron() - n * IRON_COST_MAGICTOWER);
            
            // Crea nuevo edificio
            this.setMagicTower(this.getMagicTower() + n);
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Torre Mágica.");
        }
		BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}
	
	public void newFarm(int n) throws ResourceException {
		
		
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= n * FOOD_COST_FARM && 
				this.getWood() >= n * WOOD_COST_FARM && 
				this.getIron() >= n * IRON_COST_FARM) {
			this.setFood(this.getFood() - n * FOOD_COST_FARM);
            this.setWood(this.getWood() - n * WOOD_COST_FARM);
            this.setIron(this.getIron() - n * IRON_COST_FARM);
            
            // Crea nuevo edificio
            this.setFarm(this.getFarm() + n);
            
            // +10% producción de comida
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Granja.");
        }
		BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}
	
	public void newCarpentry(int n) throws ResourceException {
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= n * FOOD_COST_CARPENTRY && 
				this.getWood() >= n * WOOD_COST_CARPENTRY && 
				this.getIron() >= n * IRON_COST_CARPENTRY) {
			this.setFood(this.getFood() - n * FOOD_COST_CARPENTRY);
            this.setWood(this.getWood() - n * WOOD_COST_CARPENTRY);
            this.setIron(this.getIron() - n * IRON_COST_CARPENTRY);
            
            // Crea nuevo edificio
            this.setCarpentry(this.getCarpentry() + n);
            
            // +10% producción de madera
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Carpintería.");
        }
		
	}
	
	public void newSmithy(int n) throws ResourceException {
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= n * FOOD_COST_SMITHY && 
				this.getWood() >= n * WOOD_COST_SMITHY && 
				this.getIron() >= n * IRON_COST_SMITHY) {
			this.setFood(this.getFood() - n * FOOD_COST_SMITHY);
            this.setWood(this.getWood() - n * WOOD_COST_SMITHY);
            this.setIron(this.getIron() - n * IRON_COST_SMITHY);
            
            // Crea nuevo edificio
            this.setSmithy(this.getSmithy() + n);
            
            // +10% producción de hierro
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Herrería.");
        }
		BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}
	
	
	
	
	
	
//	Tecnologias
	
	public void upgradeTechnologyDefense() throws ResourceException  {

		// Calcular el costo actualizado basado en el nivel de tecnología actual
		int food_cost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST, technologyDefense));
		int wood_cost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST  * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST, technologyDefense));
		int iron_cost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST, technologyDefense));

		    // Verificar si se pueden pagar los costos
		    if (getFood() >= food_cost && 
		    getWood() >= wood_cost && 
		    getIron() >= iron_cost) {
		        // Decrementar los recursos
		    this.setFood(this.getFood() - food_cost);
		    this.setWood(this.getWood() - wood_cost);
		    this.setIron(this.getIron() - iron_cost);
		    
		    
	        // Actualizar los niveles de tecnología
		    this.setTechnologyDefense(this.getTechnologyDefense() + 1);

		    for (ArrayList<MilitaryUnit> array : army) {
		    	
		        for (MilitaryUnit unit : array) {
		        	
		            if (unit instanceof Swordsman) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Spearman) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Crossbow) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY));

		            } else if (unit instanceof Cannon) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_CANNON_BY_TECHNOLOGY));

		            }else if (unit instanceof ArrowTower) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Catapult) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY));

		            } else if (unit instanceof RocketLauncherTower) {
		            	unit.setArmor(unit.getActualArmor() + (this.getTechnologyDefense() * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY));

		            }
		            
		        }BBDD carga = new BBDD();
		        carga.guardarJuego(this);
		    }
		    
		    

		        System.out.println("Attack technology has been improved!");
		    } else {
		        throw new ResourceException("You do not have enough resources to improve attack technology.");
		    }
	}


		public void upgradeTechnologyAttack() throws ResourceException {
		// Calcular el costo actualizado basado en el nivel de tecnología actual
		 int food_cost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST, technologyAttack));
		 int wood_cost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST,technologyAttack));
		 int iron_cost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST, technologyAttack));

		    // Verificar si se pueden pagar los costos
		    if (getFood()>= food_cost && 
		    getWood() >= wood_cost && 
		    getIron() >= iron_cost) {
		        // Decrementar los recursos
		    this.setFood(this.getFood() - food_cost);
		    this.setWood(this.getWood() - wood_cost);
		    this.setIron(this.getIron() - iron_cost);
		    
		    // Actualizar los niveles de tecnología
		    this.setTechnologyAttack(this.getTechnologyAttack() + 1);
		    
		    for (ArrayList<MilitaryUnit> array : army) {
		    	
		        for (MilitaryUnit unit : array) {
		        	
		            if (unit instanceof Swordsman) {
		            	unit.setBaseDamage(unit.getActualArmor() + (this.getTechnologyAttack() * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Spearman) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Crossbow) {
		            	unit.setBaseDamage(unit.attack() +(this.getTechnologyAttack() * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY));

		            } else if (unit instanceof Cannon) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_CANNON_BY_TECHNOLOGY));

		            }else if (unit instanceof ArrowTower) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY));
		            	
		            } else if (unit instanceof Catapult) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY));

		            } else if (unit instanceof RocketLauncherTower) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY));

		            } else if (unit instanceof Magician) {
		            	unit.setBaseDamage(unit.attack() + (this.getTechnologyAttack() * PLUS_ATTACK_MAGICIAN_BY_TECHNOLOGY));

		            } 
		            
		        }BBDD carga = new BBDD();
		        carga.guardarJuego(this);
		    }
		
		    
		    
		    
		        System.out.println("Attack technology has been improved!");
		    } else {
		        throw new ResourceException("You do not have enough resources to improve attack technology.");
		    }
		}
	
	
	
	
	
	
	
//	Unidades
	
	public void newSwordsman(int n) throws ResourceException {
		// Requisitos: 8.000 Food		3.000 Wood 		50 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / FOOD_COST_SWORDSMAN, 
													this.getWood() / WOOD_COST_SWORDSMAN), 
													this.getIron() / IRON_COST_SWORDSMAN);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * FOOD_COST_SWORDSMAN);
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_SWORDSMAN);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_SWORDSMAN);
	        
	        
	        // Crear nueva unidad
	        int armor = ARMOR_SWORDSMAN;
	        int baseDamage = BASE_DAMAGE_SWORDSMAN;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_SWORDSMAN + (this.getTechnologyDefense() * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  BASE_DAMAGE_SWORDSMAN + (this.getTechnologyAttack() * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(0).add(new Swordsman(armor, baseDamage));
	        }	   
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Espadachines.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Espadachines.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Espadachín.");
	    }
	    BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}
	   
	public void newSpearman(int n) throws ResourceException {
		// Requisitos: 5.000 Food		6.500 Wood 		50 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / FOOD_COST_SPEARMAN, 
													this.getWood() / WOOD_COST_SPEARMAN), 
													this.getIron() / IRON_COST_SPEARMAN);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * FOOD_COST_SPEARMAN);
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_SPEARMAN);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_SPEARMAN);
	        
	        
	        // Crear nueva unidad
	        int armor = ARMOR_SPEARMAN;
	        int baseDamage = BASE_DAMAGE_SPEARMAN;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_SPEARMAN + (this.getTechnologyDefense() * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  BASE_DAMAGE_SPEARMAN + (this.getTechnologyAttack() * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(1).add(new Spearman(armor, baseDamage));
	        }	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Lanceros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Lanceros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Lancero.");
	    }	BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}
	
	public void newCrossbow(int n) throws ResourceException {
	    // Requisitos: 45.000 Wood y 7.000 Iron
	    
	    // Comprobar número máximo de unidades que podemos crear
	    int availableUnit = Math.min(this.getWood() / WOOD_COST_CROSSBOW, this.getIron() / IRON_COST_CROSSBOW);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_CROSSBOW);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_CROSSBOW);
	        
	        // Crear nueva unidad
	        int armor = ARMOR_CROSSBOW;
	        int baseDamage = BASE_DAMAGE_CROSSBOW;
	        
	        // Comprobar estadísticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_CROSSBOW + (this.getTechnologyDefense() * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	            baseDamage =  BASE_DAMAGE_CROSSBOW + (this.getTechnologyAttack() * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(2).add(new Crossbow(armor, baseDamage));
	        }
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Ballesteros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Ballesteros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Ballestero.");
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}


	public void newCannon(int n) throws ResourceException {
	    // Requisitos: 30.000 Wood y 15.000 Iron
	    
	    // Comprobar número máximo de unidades que podemos crear
	    int availableUnit = Math.min(this.getWood() / WOOD_COST_CANNON, this.getIron() / IRON_COST_CANNON);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_CANNON);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_CANNON);
	        
	        // Crear nueva unidad
	        int armor = ARMOR_CANNON;
	        int baseDamage = BASE_DAMAGE_CANNON;
	        
	        // Comprobar estadísticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_CANNON + (this.getTechnologyDefense() * PLUS_ARMOR_CANNON_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	            baseDamage =  BASE_DAMAGE_CANNON + (this.getTechnologyAttack() * PLUS_ATTACK_CANNON_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(3).add(new Cannon(armor, baseDamage));
	        }
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Artilleros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Artilleros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Artillero.");
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}

	
	public void newArrowTower(int n) throws ResourceException {
	    // Requisitos: 2.000 Wood
	    
	    // Comprobar número máximo de unidades que podemos crear
	    int availableUnit = this.getWood() / WOOD_COST_ARROWTOWER;
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_ARROWTOWER);
	        
	        // Crear nueva unidad
	        int armor = ARMOR_ARROWTOWER;
	        int baseDamage = BASE_DAMAGE_ARROWTOWER;
	        
	        // Comprobar estadísticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_ARROWTOWER + (this.getTechnologyDefense() * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	            baseDamage =  BASE_DAMAGE_ARROWTOWER + (this.getTechnologyAttack() * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(4).add(new ArrowTower(armor, baseDamage));
	        }
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " ArrowTower.");
	        } else {
	            System.out.println("Have been added " + unitsToAdd + " Artilleros.");
	        }
	        
	    } else {
	        throw new ResourceException("Insufficient resources to train a new ArrowTower.");
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}


	public void newCatapult(int n) throws ResourceException {
	    // Requisitos: 4.000 Wood y 500 Iron
	    
	    // Comprobar número máximo de unidades que podemos crear
	    int availableUnit = Math.min(this.getWood() / WOOD_COST_CATAPULT, this.getIron() / IRON_COST_CATAPULT);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_CATAPULT);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_CATAPULT);
	        
	        // Crear nueva unidad
	        int armor = ARMOR_CATAPULT;
	        int baseDamage = BASE_DAMAGE_CATAPULT;
	        
	        // Comprobar estadísticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_CATAPULT + (this.getTechnologyDefense() * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	            baseDamage =  BASE_DAMAGE_CATAPULT + (this.getTechnologyAttack() * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(5).add(new Catapult(armor, baseDamage));
	        }
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " Catapult.");
	        } else {
	            System.out.println("Have been added " + unitsToAdd + " Artilleros.");
	        }
	        
	    } else {
	        throw new ResourceException("Insufficient resources to train a new Catapult.");
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}

	public void newRocketLauncher(int n) throws ResourceException {
	    // Requisitos: 50.000 Wood y 5.000 Iron
	    
	    // Comprobar número máximo de unidades que podemos crear
	    int availableUnit = Math.min(this.getWood() / WOOD_COST_ROCKETLAUNCHERTOWER, this.getIron() / IRON_COST_ROCKETLAUNCHERTOWER);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setWood(this.getWood() - unitsToAdd * WOOD_COST_ROCKETLAUNCHERTOWER);
	        this.setIron(this.getIron() - unitsToAdd * IRON_COST_ROCKETLAUNCHERTOWER);
	        
	        // Crear nueva unidad
	        int armor = ARMOR_ROCKETLAUNCHERTOWER;
	        int baseDamage = BASE_DAMAGE_ROCKETLAUNCHERTOWER;
	        
	        // Comprobar estadísticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  ARMOR_ROCKETLAUNCHERTOWER + (this.getTechnologyDefense() * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	            baseDamage =  BASE_DAMAGE_ROCKETLAUNCHERTOWER + (this.getTechnologyAttack() * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY) * 1000 / 100;
	        }
	        
	        // Añadir al ArrayList
	        for (int i = 0; i < unitsToAdd; i++) {
	            army.get(6).add(new RocketLauncherTower(armor, baseDamage));
	        }
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " RocketLauncher.");
	        } else {
	            System.out.println("Have been added " + unitsToAdd + " RocketLauncher.");
	        }
	        
	    } else {
	        throw new ResourceException("Insufficient resources to train a new RocketLauncher.");
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}

	public void newMagician(int n) throws ResourceException, BuildingException {
	    if (magicTower == 0) {
	        throw new BuildingException("You haven't any Magic Tower.");
	    } else {
	        // Comprobar número máximo de unidades que podemos crear
	        int availableUnit = Math.min(
	                Math.min(this.getFood() / FOOD_COST_MAGICIAN, this.getWood() / WOOD_COST_MAGICIAN),
	                this.getMana() / MANA_COST_MAGICIAN);

	        if (availableUnit > 0) {
	            int unitsToAdd = Math.min(n, availableUnit);

	            this.setFood(this.getFood() - unitsToAdd * FOOD_COST_MAGICIAN);
	            this.setWood(this.getWood() - unitsToAdd * WOOD_COST_MAGICIAN);
	            this.setMana(this.getMana() - unitsToAdd * MANA_COST_MAGICIAN);

	            // Crear nueva unidad
	            int armor = 0;
	            int baseDamage = BASE_DAMAGE_MAGICIAN;

	            // Añadir al ArrayList
	            for (int i = 0; i < unitsToAdd; i++) {
	                army.get(7).add(new Magician(armor, baseDamage));
	            }

	            // Excepciones
	            if (unitsToAdd < n) {
	                throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " Magician.");
	            } else {
	                System.out.println("Have been added " + unitsToAdd + " Magician.");
	            }

	        } else {
	            throw new ResourceException("Insufficient resources to train a new Magician.");
	        }
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}

	
	public void newPriest(int n) throws ResourceException, BuildingException {
	    // Obtener la cantidad total de sacerdotes ya creados
	    int totalPriests = army.get(8).size();

	    // Obtener la cantidad de iglesias disponibles
	    int availableChurches = this.getChurch();

	    // Verificar si hay suficientes iglesias para crear más sacerdotes
	    if (availableChurches == 0) {
	        throw new BuildingException("At least 1 Church is required to train a new Priest.");
	    } else if (totalPriests >= availableChurches) {
	        throw new BuildingException("You don't have enough Churches to train a new Priest.");
	    } else {
	        // Calcular el número de sacerdotes que se pueden crear (limitado por la cantidad de iglesias)
	        int unitsToAdd = Math.min(n, availableChurches - totalPriests);

	        // Verificar si hay suficientes recursos para crear los sacerdotes
	        int availableFood = this.getFood() / FOOD_COST_PRIEST;
	        int availableMana = this.getMana() / MANA_COST_PRIEST;
	        int unitsPossible = Math.min(availableFood, availableMana);

	        // Calcular el número máximo de sacerdotes que se pueden crear con los recursos disponibles
	        int maxUnitsPossible = Math.min(unitsToAdd, unitsPossible);

	        if (maxUnitsPossible > 0) {
	            // Deducción de recursos
	            this.setFood(this.getFood() - maxUnitsPossible * FOOD_COST_PRIEST);
	            this.setMana(this.getMana() - maxUnitsPossible * MANA_COST_PRIEST);

	            // Crear nuevas unidades y agregarlas al ArrayList
	            int armor = 0;
	            int baseDamage = 0;
	            for (int i = 0; i < maxUnitsPossible; i++) {
	                army.get(8).add(new Priest(armor, baseDamage));
	            }
	            
	            

	            // Excepción si no se pueden crear todas las unidades solicitadas
	            if (maxUnitsPossible < n) {
	                throw new ResourceException("Insufficient resources, could only be added " + maxUnitsPossible + " Priest.");
	            } else {
	                System.out.println("Have been added " + maxUnitsPossible + " Priest.");
	            }
	        } else {
	            throw new ResourceException("Insufficient resources to train a new Priest.");
	        }
	    }BBDD carga = new BBDD();
        carga.guardarJuego(this);
	}


public void setSant() {
	
	 // Recorrer Army
    for (ArrayList<MilitaryUnit> array : army) {
        for (MilitaryUnit unit : array) {
           
        	unit.setSanti();
        }
    }

    BBDD carga = new BBDD();
    carga.guardarJuego(this);
}

public ArrayList[] devolverListaReportes() {
	
	return batallasGuardadas;
	
}
	
//	Mostrar Estadísticas
	
	public void printStats() {	
        System.out.println("***************************CIVILIZATION STATS***************************");

        // Tecnología
        System.out.println("--------------------------------------------------TECHNOLOGY----------------------------------------");
        System.out.println("Atack\tDefense");
        System.out.println(this.technologyAttack + "\t" + this.technologyDefense);

        // Edificios
        System.out.println("---------------------------------------------------BUILDINGS----------------------------------------");
        System.out.println("Farm\tSmithy\tCarpentry\tMagic Tower\tChurch");
        System.out.println(this.farm + "\t" + this.smithy + "\t" + this.carpentry + "\t\t" + this.magicTower + "\t\t" + this.church);

        // Defensas
        System.out.println("----------------------------------------------------DEFENSES----------------------------------------");
        System.out.println("Arrow Tower\tCatapult\tRocket Launcher");
        // Asumiendo que "defenses" es un ArrayList que contiene las cantidades de cada tipo de defensa
        System.out.println(army.get(4).size() + "\t\t" + army.get(5).size() + "\t\t" + army.get(6).size());

        // Unidades de ataque
        System.out.println("-----------------------------------------------ATTACK UNITS----------------------------------------");
        System.out.println("Swordsman\tSpearman\tCrossbow\tCannon");
        // Asumiendo que "attackUnits" es un ArrayList que contiene las cantidades de cada tipo de unidad de ataque
        System.out.println(army.get(0).size() + "\t\t" + army.get(1).size() + "\t\t" + army.get(2).size() + "\t\t" + army.get(3).size() + "\t\t" + army.get(4).size());

      // Unidades especiales
       System.out.println("---------------------------------------------ESPECIAL UNITS----------------------------------------"); 
       System.out.println("Mague\tPriest");
       System.out.println("0\t" + army.get(7).size() + army.get(8).size());

        // Recursos
        System.out.println("---------------------------------------------------RESOURCES----------------------------------------");
        System.out.println("Food\tWood\tIron\tMana");
        System.out.println(this.food + "\t" + this.wood + "\t" + this.iron + "\t" + this.mana);

        // Generación de recursos
        System.out.println("----------------------------------------GENERATION RESOURCES----------------------------------------");
        System.out.println("Food\tWood\tIron\tMana");
        System.out.println(CIVILIZATION_FOOD_GENERATED + "\t" + CIVILIZATION_WOOD_GENERATED + "\t" + CIVILIZATION_IRON_GENERATED);

	}
}




		



