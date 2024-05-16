package clase;

import java.util.ArrayList;
import excepciones.ResourceException;
import excepciones.BuildingException;
import interfaces.MilitaryUnit;
import interfaces.Variables;


public class Civilization {
	
	private int technologyDefense, technologyAttack;
	private int wood, iron, food, mana;
	private int magicTower, church, farm, smithy, carpentry;
	private int battles;
	private ArrayList<ArrayList<MilitaryUnit>> army;
	

	// Constructor 
	public Civilization() {
		
		// Inicializar ArrayList
        army = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            army.add(new ArrayList<>());
        }
        
        // Tecnologias
        this.technologyDefense = 0;
        this.technologyAttack = 0;
        // Materiales
        this.wood = 0;
        this.iron = 0;
        this.food = 0;
        this.mana = 0;
        // Edificios
        this.magicTower = 0;
        this.church = 0;
        this.farm = 0;
        this.smithy = 0;
        this.carpentry = 0;
        // Batallas
        this.battles = 0;
        
        
        
       
    }
    // Método para generar comida
    public void generateFood(int amount) {
        this.food += amount;
    }

    // Método para generar madera
    public void generateWood(int amount) {
        this.wood += amount;
    }

    // Método para generar hierro
    public void generateIron(int amount) {
        this.iron += amount;
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
	
//	Edificios

	public void newChurch() throws ResourceException {
		// Requisitos: 10.000 Food		20.000 Wood 		24.000 Iron		10.000 Mana
		if (this.getFood() >= Variables.FOOD_COST_CHURCH && 
				this.getWood() >= Variables.WOOD_COST_CHURCH && 
				this.getIron() >= Variables.IRON_COST_CHURCH && 
				this.getMana() >= Variables.MANA_COST_CHURCH) {
            this.setFood(this.getFood() - Variables.FOOD_COST_CHURCH);
            this.setWood(this.getWood() - Variables.WOOD_COST_CHURCH);
            this.setIron(this.getIron() - Variables.IRON_COST_CHURCH);
            this.setMana(this.getMana() - Variables.MANA_COST_CHURCH);
            
            // Crea nuevo edificio
            this.setChurch(this.getChurch() + 1);

        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva iglesia.");
        }
			
	}


	public void newMagicTower() throws ResourceException {
		// Requisitos: 10.000 Food		20.000 Wood 		24.000 Iron
		if (this.getFood() >= Variables.FOOD_COST_MAGICTOWER && 
				this.getWood() >= Variables.WOOD_COST_MAGICTOWER && 
				this.getIron() >= Variables.IRON_COST_MAGICTOWER) {
			this.setFood(this.getFood() - Variables.FOOD_COST_MAGICTOWER);
            this.setWood(this.getWood() - Variables.WOOD_COST_MAGICTOWER);
            this.setIron(this.getIron() - Variables.IRON_COST_MAGICTOWER);
            
            // Crea nuevo edificio
            this.setMagicTower(this.getMagicTower() + 1);
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Torre Mágica.");
        }
			
	}
	
	public void newFarm() throws ResourceException {
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= Variables.FOOD_COST_FARM && 
				this.getWood() >= Variables.WOOD_COST_FARM && 
				this.getIron() >= Variables.IRON_COST_FARM) {
			this.setFood(this.getFood() - Variables.FOOD_COST_FARM);
            this.setWood(this.getWood() - Variables.WOOD_COST_FARM);
            this.setIron(this.getIron() - Variables.IRON_COST_FARM);
            
            // Crea nuevo edificio
            this.setFarm(this.getFarm() + 1);
            
            // +10% producción de comida
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Granja.");
        }
		
	}
	
	public void newCarpentry() throws ResourceException {
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= Variables.FOOD_COST_CARPENTRY && 
				this.getWood() >= Variables.WOOD_COST_CARPENTRY && 
				this.getIron() >= Variables.IRON_COST_CARPENTRY) {
			this.setFood(this.getFood() - Variables.FOOD_COST_CARPENTRY);
            this.setWood(this.getWood() - Variables.WOOD_COST_CARPENTRY);
            this.setIron(this.getIron() - Variables.IRON_COST_CARPENTRY);
            
            // Crea nuevo edificio
            this.setCarpentry(this.getCarpentry() + 1);
            
            // +10% producción de madera
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Carpintería.");
        }
		
	}
	
	public void newSmithy() throws ResourceException {
		// Requisitos: 5.000 Food		10.000 Wood 		12.000 Iron
		if (this.getFood() >= Variables.FOOD_COST_SMITHY && 
				this.getWood() >= Variables.WOOD_COST_SMITHY && 
				this.getIron() >= Variables.IRON_COST_SMITHY) {
			this.setFood(this.getFood() - Variables.FOOD_COST_SMITHY);
            this.setWood(this.getWood() - Variables.WOOD_COST_SMITHY);
            this.setIron(this.getIron() - Variables.IRON_COST_SMITHY);
            
            // Crea nuevo edificio
            this.setSmithy(this.getSmithy() + 1);
            
            // +10% producción de hierro
            
        } else {
            throw new ResourceException("Recursos insuficientes para construir una nueva Herrería.");
        }

	}
	
	
	
	
	
	
//	Tecnologias
	
	public void upgradeTechnologyDefense() throws ResourceException  {
				
		// Calcular el costo actualizado basado en el nivel de tecnología actual
		int UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST = (int) (Variables.UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST * Math.pow(1.1, technologyDefense));
		int UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST = (int) (Variables.UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST  * Math.pow(1.15, technologyDefense));
		int UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST = (int) (Variables.UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * Math.pow(1.2, technologyDefense));

	    // Verificar si se pueden pagar los costos
	    if (getFood() >= Variables.UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST && 
    		getWood() >= Variables.UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST && 
    		getIron() >= Variables.UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST) {
		        // Decrementar los recursos
		    	this.setFood(this.getFood() - UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST);
		    	this.setWood(this.getWood() - UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST);
		    	this.setIron(this.getIron() - UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST);
		        // Actualizar los niveles de tecnología
		    	this.setTechnologyDefense(this.getTechnologyDefense() + 1);
		        System.out.println("¡Se ha mejorado la tecnología de defensa!");
	    } else {
	        throw new ResourceException("No tienes suficientes recursos para mejorar la tecnología de defensa.");
	    }
	}
	
	public void upgradeTechnologyAttack() throws ResourceException {
		// Calcular el costo actualizado basado en el nivel de tecnología actual
		 int UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST = (int) (Variables.UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST * Math.pow(1.1, technologyAttack));
		 int UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST = (int) (Variables.UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST * Math.pow(1.15,technologyAttack));
		 int UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST = (int) (Variables.UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * Math.pow(1.2, technologyAttack));
	
	    // Verificar si se pueden pagar los costos
	    if (getFood()>= Variables.UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST && 
    		getWood() >= Variables.UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST && 
    		getIron() >= Variables.UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST) {
		        // Decrementar los recursos
		    	this.setFood(this.getFood() - UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST);
		    	this.setWood(this.getWood() - UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST);
		    	this.setIron(this.getIron() - UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST);
		        // Actualizar los niveles de tecnología
		    	this.setTechnologyAttack(this.getTechnologyAttack() + 1);
		        System.out.println("¡Se ha mejorado la tecnología de ataque!");
	    } else {
	        throw new ResourceException("No tienes suficientes recursos para mejorar la tecnología de ataque.");
			    }
	}
	
	
	
	
	
	
	
//	Unidades
	
	public void newSwordsman(int n) throws ResourceException {
		// Requisitos: 8.000 Food		3.000 Wood 		50 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_SWORDSMAN, 
													this.getWood() / Variables.WOOD_COST_SWORDSMAN), 
													this.getIron() / Variables.IRON_COST_SWORDSMAN);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_SWORDSMAN);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_SWORDSMAN);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_SWORDSMAN);
	        
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_SWORDSMAN;
	        int baseDamage = Variables.BASE_DAMAGE_SWORDSMAN;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_SWORDSMAN + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_SWORDSMAN + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(0).add(new Swordsman(armor, baseDamage)); 
	   
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Espadachines.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Espadachines.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Espadachín.");
	    }
	    
	}
	   
	public void newSpearman(int n) throws ResourceException {
		// Requisitos: 5.000 Food		6.500 Wood 		50 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_SPEARMAN, 
													this.getWood() / Variables.WOOD_COST_SPEARMAN), 
													this.getIron() / Variables.IRON_COST_SPEARMAN);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_SPEARMAN);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_SPEARMAN);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_SPEARMAN);
	        
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_SPEARMAN;
	        int baseDamage = Variables.BASE_DAMAGE_SPEARMAN;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_SPEARMAN + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_SPEARMAN + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(1).add(new Spearman(armor, baseDamage)); 
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Lanceros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Lanceros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Lancero.");
	    }	
	}
	
	public void newCrossbow(int n) throws ResourceException {
		// Requisitos: 0 Food		45.000 Wood 		7.000 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_CROSSBOW, 
													this.getWood() / Variables.WOOD_COST_CROSSBOW), 
													this.getIron() / Variables.IRON_COST_CROSSBOW);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_CROSSBOW);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_CROSSBOW);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_CROSSBOW);
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_CROSSBOW;
	        int baseDamage = Variables.BASE_DAMAGE_CROSSBOW;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_CROSSBOW + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_CROSSBOW + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(2).add(new Crossbow(armor, baseDamage)); 
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Ballesteros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Ballesteros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Ballestero.");
	    }
	}
	
	public void newCannon(int n) throws ResourceException {
		// Requisitos: 0 Food		30.000 Wood 		15.000 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_CANNON, 
													this.getWood() / Variables.WOOD_COST_CANNON), 
													this.getIron() / Variables.IRON_COST_CANNON);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_CANNON);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_CANNON);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_CANNON);
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_CANNON;
	        int baseDamage = Variables.BASE_DAMAGE_CANNON;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_CANNON + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_CANNON_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_CANNON + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_CANNON_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(3).add(new Cannon(armor, baseDamage)); 
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + " Artilleros.");
	        } else {
	            System.out.println("Se han añadido " + unitsToAdd + " Artilleros.");
	        }
	        
	    } else {
	        throw new ResourceException("Recursos insuficientes para instruir un nuevo Artillero.");
	    }
	}
	
public void newArrowTower(int n) throws ResourceException {
		
		// Requisitos: 0 Food		2.000 Wood 		0 Iron
		
				// Comprobar numero maximo de unidades que podemos crear
				int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_ARROWTOWER, 
															this.getWood() / Variables.WOOD_COST_ARROWTOWER), 
															this.getIron() / Variables.IRON_COST_ARROWTOWER);
			    
			    if (availableUnit > 0) {
			        int unitsToAdd = Math.min(n, availableUnit);
			        
			        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_ARROWTOWER);
			        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_ARROWTOWER);
			        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_ARROWTOWER);
			        
			        // Crear nueva unidad
			        int armor = Variables.ARMOR_ARROWTOWER;
			        int baseDamage = Variables.BASE_DAMAGE_ARROWTOWER;
			        
			        // Comprobar estadisticas
			        if (this.getTechnologyDefense() > 0) {
			            armor =  Variables.ARMOR_ARROWTOWER + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY)*1000/100;
			        }
			        if (this.getTechnologyAttack() > 0) {
			        	baseDamage =  Variables.BASE_DAMAGE_ARROWTOWER + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY)*1000/100;
			        }
			        
			        // Añadir al ArrayList
			        army.get(4).add(new ArrowTower(armor, baseDamage)); 
			        
			        // Excepciones
			        if (unitsToAdd < n) {
			            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " ArrowTower.");
			        } else {
			            System.out.println("Have been added " + unitsToAdd + " Artilleros.");
			        }
			        
			    } else {
			        throw new ResourceException("Insufficient resources to train a new ArrowTower.");
			    }
			}
	
	public void newCatapult(int n) throws ResourceException {
		
		// Requisitos: 0 Food		4.000 Wood 		500 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_CATAPULT, 
													this.getWood() / Variables.WOOD_COST_CATAPULT), 
													this.getIron() / Variables.IRON_COST_CATAPULT);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_CATAPULT);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_CATAPULT);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_CATAPULT);
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_CATAPULT;
	        int baseDamage = Variables.BASE_DAMAGE_CATAPULT;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_CATAPULT + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_CATAPULT + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(5).add(new Catapult(armor, baseDamage)); 
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " Catapult.");
	        } else {
	            System.out.println("Have been added " + unitsToAdd + " Artilleros.");
	        }
	        
	    } else {
	        throw new ResourceException("Insufficient resources to train a new Catapult.");
	    }
	}
	
	public void newRocketLauncher(int n) throws ResourceException {
		
		// Requisitos: 0 Food		50.000 Wood 		5000 Iron
		
		// Comprobar numero maximo de unidades que podemos crear
		int availableUnit = Math.min(Math.min(this.getFood() / Variables.FOOD_COST_ROCKETLAUNCHERTOWER, 
													this.getWood() / Variables.WOOD_COST_ROCKETLAUNCHERTOWER), 
													this.getIron() / Variables.IRON_COST_ROCKETLAUNCHERTOWER);
	    
	    if (availableUnit > 0) {
	        int unitsToAdd = Math.min(n, availableUnit);
	        
	        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_ROCKETLAUNCHERTOWER);
	        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_ROCKETLAUNCHERTOWER);
	        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_ROCKETLAUNCHERTOWER);
	        
	        // Crear nueva unidad
	        int armor = Variables.ARMOR_ROCKETLAUNCHERTOWER;
	        int baseDamage = Variables.BASE_DAMAGE_ROCKETLAUNCHERTOWER;
	        
	        // Comprobar estadisticas
	        if (this.getTechnologyDefense() > 0) {
	            armor =  Variables.ARMOR_ROCKETLAUNCHERTOWER + (this.getTechnologyDefense() * Variables.PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY)*1000/100;
	        }
	        if (this.getTechnologyAttack() > 0) {
	        	baseDamage =  Variables.BASE_DAMAGE_ROCKETLAUNCHERTOWER + (this.getTechnologyAttack() * Variables.PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY)*1000/100;
	        }
	        
	        // Añadir al ArrayList
	        army.get(6).add(new RocketLauncherTower(armor, baseDamage)); 
	        
	        // Excepciones
	        if (unitsToAdd < n) {
	            throw new ResourceException("Insufficient resources, could only be added " + unitsToAdd + " RocketLauncher.");
	        } else {
	            System.out.println("Have been added " + unitsToAdd + " RocketLauncher.");
	        }
	        
	    } else {
	        throw new ResourceException("Insufficient resources to train a new RocketLauncher.");
	    }
	}
	
	public void newMagician(int n) throws ResourceException, BuildingException {
		
		
		
		if(magicTower == 0) {
			
			throw new BuildingException("You haven't any Magic Tower.");
			
		}else {
			
			// Comprobar numero maximo de unidades que podemos crear
			int availableUnit = Math.min(
				    Math.min(
				        Math.min(this.getFood() / Variables.FOOD_COST_MAGICIAN, 
				                 this.getWood() / Variables.WOOD_COST_MAGICIAN), 
				        this.getIron() / Variables.IRON_COST_MAGICIAN),
				    this.getMana() / Variables.MANA_COST_MAGICIAN);

		    
		    if (availableUnit > 0) {
		        int unitsToAdd = Math.min(n, availableUnit);
		        
		        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_MAGICIAN);
		        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_MAGICIAN);
		        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_MAGICIAN);
		        this.setMana(this.getMana() - unitsToAdd * Variables.MANA_COST_MAGICIAN);
		        // Crear nueva unidad
		        int armor = 0;
		        int baseDamage = 0;
		        
		      
		        // Añadir al ArrayList
		        army.get(7).add(new Magician(armor, baseDamage)); 
		        
		        // Excepciones
		        if (unitsToAdd < n) {
		            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + "Magician.");
		        } else {
		            System.out.println("Se han añadido " + unitsToAdd + " Mago.");
		        }
		        
		    } else {
		        throw new ResourceException("Recursos insuficientes para instruir un nuevo Mago.");
		    }
			
		}
		
		
	}
	
	public void newPriest(int n) throws ResourceException, BuildingException {
		
	if(magicTower == 0) {
			
			throw new BuildingException("You haven't any Magic Tower.");
			
		}else {
			
			// Comprobar numero maximo de unidades que podemos crear
			int availableUnit = Math.min(
				    Math.min(
				        Math.min(this.getFood() / Variables.FOOD_COST_PRIEST, 
				                 this.getWood() / Variables.WOOD_COST_PRIEST), 
				        this.getIron() / Variables.IRON_COST_PRIEST),
				    this.getMana() / Variables.MANA_COST_PRIEST);

		    
		    if (availableUnit > 0) {
		        int unitsToAdd = Math.min(n, availableUnit);
		        
		        this.setFood(this.getFood() - unitsToAdd * Variables.FOOD_COST_PRIEST);
		        this.setWood(this.getWood() - unitsToAdd * Variables.WOOD_COST_PRIEST);
		        this.setIron(this.getIron() - unitsToAdd * Variables.IRON_COST_PRIEST);
		        this.setMana(this.getMana() - unitsToAdd * Variables.MANA_COST_PRIEST);
		        // Crear nueva unidad
		        int armor = 0;
		        int baseDamage = 0;
		        
		      
		        // Añadir al ArrayList
		        army.get(8).add(new Magician(armor, baseDamage)); 
		        
		        // Excepciones
		        if (unitsToAdd < n) {
		            throw new ResourceException("Recursos insuficientes, solo se pudieron añadir " + unitsToAdd + "Magician.");
		        } else {
		            System.out.println("Se han añadido " + unitsToAdd + " Priest.");
		        }
		        
		    } else {
		        throw new ResourceException("Recursos insuficientes para instruir un nuevo Mago.");
		    }
			
		}
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
        System.out.println(Variables.CIVILIZATION_FOOD_GENERATED + "\t" + Variables.CIVILIZATION_WOOD_GENERATED + "\t" + Variables.CIVILIZATION_IRON_GENERATED);

	}
}
