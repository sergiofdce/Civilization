package juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import clase.Cannon;
import clase.Civilization;
import clase.Crossbow;
import clase.Spearman;
import clase.Swordsman;
import excepciones.BuildingException;
import excepciones.ResourceException;
import interfaces.MilitaryUnit;
import interfaces.Variables;

public class Main {
    ArrayList<ArrayList<MilitaryUnit>> enemyArmy = new ArrayList<>();
	public static void main(String[] args) {
		Main main = new Main();
	    Timer timer = new Timer();
	    Civilization civilization = new Civilization(); // Creamos una instancia de Civilization
	    Scanner scanner = new Scanner(System.in);
        int opcion;
        
        
        
	    TimerTask createEnemyArmyTask = new TimerTask() {
            @Override
            public void run() {
            	Main main = new Main();
                main.createEnemyArmy();
                main.battle();
            }
        };

	    // Creamos una tarea para generar comida
	    TimerTask generateFoodTask = new TimerTask() {
	        @Override
	        public void run() {
	            // Generar comida aquí
	            civilization.generateFood(Variables.CIVILIZATION_FOOD_GENERATED);
//	            civilization.printStats();
	        }
	    };

	    // Creamos una tarea para generar madera
	    TimerTask generateWoodTask = new TimerTask() {
	        @Override
	        public void run() {
	            // Generar madera aquí
	            civilization.generateWood(Variables.CIVILIZATION_WOOD_GENERATED);
//	            civilization.printStats();
	        }
	    };

	    // Creamos una tarea para generar hierro
	    TimerTask generateIronTask = new TimerTask() {
	        @Override
	        public void run() {
	            // Generar hierro aquí
	            civilization.generateIron(Variables.CIVILIZATION_IRON_GENERATED);
	            civilization.printStats();
	        }
	    };
	    
	    

	    // Programamos las tareas para que se ejecuten cada cierto tiempo
	    // Generar comida cada 5 segundos, iniciando en 10 segundos
	    timer.schedule(generateFoodTask, 10000, 5000);
	    // Generar madera cada 7 segundos, iniciando en 15 segundos
	    timer.schedule(generateWoodTask, 15000, 7000);
	    // Generar hierro cada 10 segundos, iniciando en 20 segundos
	    timer.schedule(generateIronTask, 20000, 10000);
	 // Tarea para crear ejército enemigo	
	    timer.schedule(createEnemyArmyTask, 10000, 10000);
	
	
	do {
        System.out.println("Bienvenido al juego de civilización");
        System.out.println("1. Crear unidades");
        System.out.println("2. Construir edificios");
        System.out.println("3. Mejorar tecnologías");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
        opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
            	main.createUnitsMenu(civilization, scanner);
                break;
            case 2:
            	main.buildBuildingsMenu(civilization, scanner);
                break;
            case 3:
            	main.upgradeTechnologiesMenu(civilization, scanner);
                break;
            case 4:
                System.out.println("¡Hasta luego!");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
        }
    } while (opcion != 4);

    scanner.close();
}

	public void createUnitsMenu(Civilization civilization, Scanner scanner) {
	    int option;
	    do {
	        System.out.println("Menu de Creación de Unidades:");
	        System.out.println("1. Espadachín");
	        System.out.println("2. Lancero");
	        System.out.println("3. Ballestero");
	        System.out.println("4. Artillero");
	        System.out.println("5. Torre de Flechas");
	        System.out.println("6. Catapulta");
	        System.out.println("7. Lanzacohetes");
	        System.out.println("8. Mago");
	        System.out.println("9. Sacerdote");
	        System.out.println("10. Volver al menú principal");
	        System.out.print("Seleccione una opción: ");
	        option = scanner.nextInt();

	        switch (option) {
	            case 1:
	                createSwordsman(civilization, scanner);
	                break;
	            case 2:
	                createSpearman(civilization, scanner);
	                break;
	            case 3:
	                createCrossbow(civilization, scanner);
	                break;
	            case 4:
	                createCannon(civilization, scanner);
	                break;
	            case 5:
	                createArrowTower(civilization, scanner);
	                break;
	            case 6:
	                createCatapult(civilization, scanner);
	                break;
	            case 7:
	                createRocketLauncher(civilization, scanner);
	                break;
	            case 8:
	                createMagician(civilization, scanner);
	                break;
	            case 9:
	                createPriest(civilization, scanner);
	                break;
	            case 10:
	                System.out.println("Volviendo al menú principal...");
	                break;
	            default:
	                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
	        }
	    } while (option != 10);
	}

	public void createSwordsman(Civilization civilization, Scanner scanner) {
	    System.out.print("Ingrese la cantidad de Espadachines que desea crear: ");
	    int quantity = scanner.nextInt();
	    try {
	        civilization.newSwordsman(quantity);
	        
	    } catch (ResourceException e) {
	        System.out.println(e.getMessage());
	    }
	}


	public void createSpearman(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Lanceros que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newSpearman(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public void createCrossbow(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Ballesteros que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newCrossbow(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public void createCannon(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Artilleros que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newCannon(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public void createArrowTower(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Torres de Flechas que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newArrowTower(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public  void createCatapult(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Catapultas que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newCatapult(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public void createRocketLauncher(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Lanzacohetes que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newRocketLauncher(quantity);
        } catch (ResourceException e) {
            System.out.println(e.getMessage());
        }
    }

	public  void createMagician(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Magos que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newMagician(quantity);
        } catch (ResourceException | BuildingException e) {
            System.out.println(e.getMessage());
        }
    }

	public  void createPriest(Civilization civilization, Scanner scanner) {
        System.out.print("Ingrese la cantidad de Sacerdotes que desea crear: ");
        int quantity = scanner.nextInt();
        try {
            civilization.newPriest(quantity);
        } catch (ResourceException | BuildingException e) {
            System.out.println(e.getMessage());
        }
    }

	public void buildBuildingsMenu(Civilization civilization, Scanner scanner) {
    int option;
    do {
        System.out.println("Menu de Construcción de Edificios:");
        System.out.println("1. Iglesia");
        System.out.println("2. Torre Mágica");
        System.out.println("3. Granja");
        System.out.println("4. Carpintería");
        System.out.println("5. Herrería");
        System.out.println("6. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                buildChurch(civilization);
                break;
            case 2:
                buildMagicTower(civilization);
                break;
            case 3:
                buildFarm(civilization);
                break;
            case 4:
                buildCarpentry(civilization);
                break;
            case 5:
                buildSmithy(civilization);
                break;
            case 6:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
        }
    } while (option != 6);
}

public void buildChurch(Civilization civilization) {
    try {
        civilization.newChurch();
        System.out.println("¡Se ha construido una nueva Iglesia!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void buildMagicTower(Civilization civilization) {
    try {
        civilization.newMagicTower();
        System.out.println("¡Se ha construido una nueva Torre Mágica!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void buildFarm(Civilization civilization) {
    try {
        civilization.newFarm();
        System.out.println("¡Se ha construido una nueva Granja!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void buildCarpentry(Civilization civilization) {
    try {
        civilization.newCarpentry();
        System.out.println("¡Se ha construido una nueva Carpintería!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void buildSmithy(Civilization civilization) {
    try {
        civilization.newSmithy();
        System.out.println("¡Se ha construido una nueva Herrería!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void upgradeTechnologiesMenu(Civilization civilization, Scanner scanner) {
    int option;
    do {
        System.out.println("Menu de Mejora de Tecnologías:");
        System.out.println("1. Mejorar Tecnología de Defensa");
        System.out.println("2. Mejorar Tecnología de Ataque");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                upgradeDefenseTechnology(civilization);
                break;
            case 2:
                upgradeAttackTechnology(civilization);
                break;
            case 3:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
        }
    } while (option != 3);
}



public  void upgradeDefenseTechnology(Civilization civilization) {
    try {
        civilization.upgradeTechnologyDefense();
        System.out.println("¡Se ha mejorado la tecnología de defensa!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}

public void upgradeAttackTechnology(Civilization civilization) {
    try {
        civilization.upgradeTechnologyAttack();
        System.out.println("¡Se ha mejorado la tecnología de ataque!");
    } catch (ResourceException e) {
        System.out.println(e.getMessage());
    }
}


public void createEnemyArmy() {
    Random random = new Random();

    int food = Variables.IRON_BASE_ENEMY_ARMY;
    int wood = Variables.IRON_BASE_ENEMY_ARMY;
    int iron = Variables.IRON_BASE_ENEMY_ARMY;
    
 // Inicializar la lista enemyArmy si está vacía
    if (enemyArmy.isEmpty()) {
        enemyArmy.add(new ArrayList<>());
    }

    while (food >= Variables.FOOD_COST_SWORDSMAN && wood >= Variables.WOOD_COST_SWORDSMAN && iron >= Variables.IRON_COST_SWORDSMAN) {
        // Generar un número aleatorio entre 0 y 99
        int randomNumber = random.nextInt(100);

        if (randomNumber < 35 && food >= Variables.FOOD_COST_SWORDSMAN && wood >= Variables.WOOD_COST_SWORDSMAN && iron >= Variables.IRON_COST_SWORDSMAN) {
            // Crear Swordsman
            Swordsman swordsman = new Swordsman();
            enemyArmy.get(0).add(swordsman);

            // Deduct resources
            food -= Variables.FOOD_COST_SWORDSMAN;
            wood -= Variables.WOOD_COST_SWORDSMAN;
            iron -= Variables.IRON_COST_SWORDSMAN;
        } else if (randomNumber < 60 && food >= Variables.FOOD_COST_SPEARMAN && wood >= Variables.WOOD_COST_SPEARMAN && iron >= Variables.IRON_COST_SPEARMAN) {
            // Crear Spearman
            Spearman spearman = new Spearman();
            enemyArmy.get(0).add(spearman);

            // Deduct resources
            food -= Variables.FOOD_COST_SPEARMAN;
            wood -= Variables.WOOD_COST_SPEARMAN;
            iron -= Variables.IRON_COST_SPEARMAN;
        } else if (randomNumber < 80 && food >= Variables.FOOD_COST_CROSSBOW && wood >= Variables.WOOD_COST_CROSSBOW && iron >= Variables.IRON_COST_CROSSBOW) {
            // Crear Crossbow
            Crossbow crossbow = new Crossbow();
            enemyArmy.get(0).add(crossbow);

            // Deduct resources
            food -= Variables.FOOD_COST_CROSSBOW;
            wood -= Variables.WOOD_COST_CROSSBOW;
            iron -= Variables.IRON_COST_CROSSBOW;
        } else if (randomNumber < 100 && food >= Variables.FOOD_COST_CANNON && wood >= Variables.WOOD_COST_CANNON && iron >= Variables.IRON_COST_CANNON) {
            // Crear Cannon
            Cannon cannon = new Cannon();
            enemyArmy.get(0).add(cannon);

            // Deduct resources
            food -= Variables.FOOD_COST_CANNON;
            wood -= Variables.WOOD_COST_CANNON;
            iron -= Variables.IRON_COST_CANNON;
        }
    }

    viewThreat();
}
    
public void viewThreat() {
    System.out.println("NEW THREAT COMING");

    int swordsmanCount = 0;
    int spearmanCount = 0;
    int crossbowCount = 0;
    int cannonCount = 0;

    // Contar la cantidad de cada tipo de unidad enemiga
    for (ArrayList<MilitaryUnit> unitList : enemyArmy) {
        for (MilitaryUnit unit : unitList) {
            if (unit instanceof Swordsman) {
                swordsmanCount++;
            } else if (unit instanceof Spearman) {
                spearmanCount++;
            } else if (unit instanceof Crossbow) {
                crossbowCount++;
            } else if (unit instanceof Cannon) {
                cannonCount++;
            }
        }
    }

    // Imprimir la cantidad de cada tipo de unidad enemiga
    System.out.println("Swordsman: " + swordsmanCount);
    System.out.println("Spearman: " + spearmanCount);
    System.out.println("Crossbow: " + crossbowCount);
    System.out.println("Cannon: " + cannonCount);
}

public void battle() {
        // Implementa la lógica de la batalla
    }
    
    
}
