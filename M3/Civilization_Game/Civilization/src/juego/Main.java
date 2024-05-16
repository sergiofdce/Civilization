package juego;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.plaf.metal.MetalInternalFrameTitlePane;

import clase.Cannon;
import clase.Civilization;
import clase.Crossbow;
import clase.Spearman;
import clase.Swordsman;
import excepciones.BuildingException;
import excepciones.ResourceException;
import interfaces.MilitaryUnit;
import interfaces.Variables;
import layouts.VentanaJuego;



public class Main {
    ArrayList<ArrayList<MilitaryUnit>> enemyArmy = new ArrayList<>();
    
    
	public static void main(String[] args) {

//		 Crear Nuestra Civilization
	    Civilization civilization = new Civilization();
        
//	  	Crear objeto Timer
        Timer timer = new Timer();

//         Llamar a la interfaz del juego, le pasamos objeto Civilization
        VentanaJuego miJuego = new VentanaJuego(civilization, timer);
        


	
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
	
	//    viewThreat();
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