package juego;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import layouts.*;
import interfaces.MilitaryUnit;
import interfaces.Variables;
import clase.*;
public class Main {
	
	
	 private ArrayList<ArrayList<MilitaryUnit>> enemyArmy;

	 //Clase main
	
	 public static void main(String[] args) {

//			Crear Nuestra Civilization
		    Civilization civilization = new Civilization();
	        
//		  	Crear objeto Timer
	        Timer timer = new Timer();

//	      Llamar a la interfaz del juego, le pasamos objeto Civilization
	        VentanaJuego miJuego = new VentanaJuego(civilization, timer);
	        
	       

		
		}
	 	
	 
	 //Metodos clase main.
	 	public void initArrayEjer() {
	 		
	 		 
	 		 ArrayList<ArrayList<MilitaryUnit>> enemyArmy = new ArrayList<>();
	 	        // Inicializar cada uno de los 9 ArrayLists dentro de civilizationArmy
	 	        for (int i = 0; i < 9; i++) {
	 	          
	 	           enemyArmy.add(new ArrayList<>());
	 	        }
	 		
	 		
	 	}
		
		
	

	
	}


