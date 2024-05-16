package layouts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Timer;
import java.io.OutputStream;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import clase.Civilization;
import excepciones.BuildingException;
import excepciones.ResourceException;
import interfaces.Variables;

import java.util.Timer;
import java.util.TimerTask;


public class VentanaJuego extends JFrame {

	 private MenuLabels menuLabels;
	 private CardLayout cardLayout;
	 private MenuImage menuImage;
	 private Game gameFrame;
	 private JPanel centerPanel;
	 private MenuCredits menuCredits;

	
	public static void main(String[] args) {
		
		
//		VentanaJuego miJuego = new VentanaJuego();
		
		
		
	}
	
	
	public VentanaJuego(Civilization civilization, Timer timer) {
        this.setTitle("Civilization");
        this.setSize(1280,720);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // -------------- //
        
        
        // Menu Image
        menuImage = new MenuImage();
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.add(menuImage, "MenuImage");

        // New Game
        menuLabels = new MenuLabels(this, civilization, timer);
        centerPanel.add(menuLabels, "MenuLabels");

        // Credits
        menuCredits = new MenuCredits(this);
        centerPanel.add(menuCredits, "MenuCredits");

        this.add(centerPanel, BorderLayout.CENTER);

        // Main Menu
        this.add(new MenuButtons(this, cardLayout, centerPanel, gameFrame), BorderLayout.WEST);

        // -------------- //
        this.setVisible(true);
        

        
    }
	
	
	class Game extends JFrame implements Variables {
		
		// Menu Bar
	    private JMenuBar menuBar;
	    private JMenu gameMenu, buildMenu, technologyMenu, unitsMenu, helpMenu;
	    private JMenuItem playPauseMenuItem, exitToMainMenuMenuItem, exitGameMenuItem;
	    private JMenuItem newFarm, newCarpentry, newBlacksmith, newMagicTower, newChurch;
	    private JMenuItem upgradeDefense, upgradeAttack;
	    private JMenu offensiveMenu, defensiveMenu, specialMenu;
	    private JMenuItem newSwordsman, newSpearman, newCrossbow, newCannon, newArrowTower, newCatapult, newRocketLauncher, newMagician, newPriest;
	    private JMenuItem showTutorial, aboutMenuItem, creditsMenuItem, contactSupportMenuItem;
	    private JPanel topFrame, leftFrame, rightFrame, bottomFrame, centralGame;
	    
	    // New item JDialog
	    private JDialog dialog;
	    private JPanel dialogImagePanel;
	    private JPanel mainPanel;
	    private GridBagConstraints gbc;
	    private JLabel imageLabel, dialogo, foodLabel, woodLabel, ironLabel, manaLabel, label, exceptionMessage;
	    private JTextField textField;
	    private JButton cancelButton, createButton;
	    private JPanel costPanel, messagePanel, buttonPanel;
	    private ImageIcon foodIcon, woodIcon, ironIcon, manaIcon, icon, scaledIcon;
	    private Image scaledImage;
	    private String newItemTitle, newItemImage, newItemDialogo, newItemLabel, variableName;
	    private String text, sourceButton;
	    
	    // Selector 
	    private JSpinner spinner;
	    private NumberFormatter formatter;
	    
	    
	    // Imagenes
	    private Image backgroundImage;
	    private ImageIcon iconoComida = new ImageIcon("./src/layouts/resources/food.png");
	    private ImageIcon iconoMadera = new ImageIcon("./src/layouts/resources/wood.png");
	    private ImageIcon iconoHierro = new ImageIcon("./src/layouts/resources/iron.png");
	    private ImageIcon iconoMana = new ImageIcon("./src/layouts/resources/mana.png");
	    private Image imagenComida, imagenMadera, imagenHierro, imagenMana;
	    private ImageIcon iconoComidaRedimensionado, iconoMaderaRedimensionado, iconoHierroRedimensionado, iconoManaRedimensionado;
	    
	    // Background image
	    private JPanel backgroundImageJPanel;
	    
	    // JPanel global -> Aquí se añadirán NORTH, WEST, EAST, SOUTH y CENTER
	    private JPanel globalJPanel;
	    
	    // Panel derecho
	    private JLabel labelEdificios, labelUnidades, labelTencologias, labelRecursos;
	    private JPanel infoCivilization, panelRecursos, panelEdificios, panelUnidades, panelTecnologias, contador;
	    private GridBagConstraints gbc_contador, gbc_info, gbc_building, gbc_units, gbc_tech;
	    private JLabel labelTiempo;
	    private JLabel labelComidaUnidades, labelMaderaUnidades, labelHierroUnidades, labelManaUnidades;
	    private JLabel countSwordman, countSpearman, countCrossbow, countCannon, countArrowTower, countCatapult, countRocketLauncher, countMagician, countPriest;

	    
	    // Consola
	    private JTextArea consoleTextArea;
	    private JTextField commandInputField;
	    private JButton sendButton;
	    private ByteArrayOutputStream outputStream;
	    
	    // Timer
	    private Timer timer;
	    private boolean enPausa;


	    public Game(JFrame parentFrame, Civilization civilization, Timer timer) {
	        this.setTitle("Game");
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para que solo cierre este JFrame
	        this.setLocationRelativeTo(null);
	        this.setResizable(true);
	        //---
	        
	        
//	        JPanels

	        // Crear el JMenuBar
	        menuBar = new JMenuBar();
	        buildMenuBar();
	        
	        // Background image, imagen de la ventana
	        backgroundImageJPanel = new JPanel() {
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);

	                // Cargar la imagen de fondo
	                try {
	                    backgroundImage = ImageIO.read(new File("./src/layouts/resources/background_frame.png"));

	                    // Obtener las dimensiones del panel
	                    int panelWidth = this.getWidth();
	                    int panelHeight = this.getHeight();

	                    // Dibujar la imagen de fondo escalada para cubrir todo el panel
	                    g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	        backgroundImageJPanel.setOpaque(false);
	        backgroundImageJPanel.setLayout(new BorderLayout());

	        
	        
	        // JPanel global, tendrá todos los JPanels
	        globalJPanel = new JPanel();
	        globalJPanel.setLayout(new BorderLayout());
	    	globalJPanel.setOpaque(false);
	        
	    	
	    	
	        // Parte central
	    	centralGame = new BackgroundPanel("./src/layouts/resources/background.jpg");
	    	buildCentralGame(centralGame, civilization);
	        
	        
	        // Parte superior
	        topFrame = new JPanel();
	        topFrame.setOpaque(false);
	    	topFrame.setPreferredSize(new Dimension(100, 50));
	    	

	        // Parte izquierda
	        leftFrame = new JPanel();
	        leftFrame.setOpaque(false);
	    	leftFrame.setPreferredSize(new Dimension(50, 0));

	        // Parte derecha -> STATS
	        buildRightFrame(civilization);

	        // Parte inferior
	        bottomFrame = new JPanel();
	        buildBottomFrame(bottomFrame);
	        
	        // Timer
	        iniciarTemporizador(civilization, timer);
	        
	        
	        


	        
	        
	        
	        
	        
	        
	        
	        // Establecer el JMenuBar
	        this.setJMenuBar(menuBar);
	        // Añadir background
	        this.add(backgroundImageJPanel);
	        
	        
	        
	        // Añadir panel global
	        backgroundImageJPanel.add(globalJPanel, BorderLayout.CENTER);
	        
	        
	        // Añadir elementos al panel de fondo
	        globalJPanel.add(topFrame, BorderLayout.NORTH);
	        globalJPanel.add(leftFrame, BorderLayout.WEST);
	        globalJPanel.add(centralGame, BorderLayout.CENTER);
	        globalJPanel.add(rightFrame, BorderLayout.EAST);
	        globalJPanel.add(bottomFrame, BorderLayout.SOUTH);


	        

	        // Configurar el WindowListener
	        this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                parentFrame.setVisible(true); // Mostrar el parentFrame cuando este JFrame se cierre
	            }
	        });
	        
	        
//	         Eventos
	        
	        // Game
	        
		        // Play/Pause
	        playPauseMenuItem.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (enPausa) {
	                    enPausa = false;
	                } else {
	                    enPausa = true;
	                }
	            }
	        });

		        // Exit to Main Menu
	        exitToMainMenuMenuItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres volver al menú principal?", "Guardar y Salir", JOptionPane.YES_NO_OPTION);
	                if (respuesta == JOptionPane.YES_OPTION) {
	                    System.out.println("Volviendo al menú principal...");
	                    dispose();
	                } else {
	                    System.out.println("Permaneciendo en la aplicación...");
	                }
	            }
	        });
	       
	        

	        // Exit Game
	        exitGameMenuItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir del juego?", "Guardar y Salir", JOptionPane.YES_NO_OPTION);
	                if (respuesta == JOptionPane.YES_OPTION) {
	                    System.out.println("Saliendo del juego...");
	                    // Cerrar la aplicación
	                    System.exit(0);
	                } else {
	                    System.out.println("Permaneciendo en el juego...");
	                }
	            }
	        });
	        
//	        Build
	        	// Farm
		        newFarm.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Farm";
		            	newItemImage = "newFarm";
		            	newItemDialogo = "<html>A farm allows you to increase your Civilization's <br> food production by 10%.<br><br>The cost to build a farm is:</html>";
		            	newItemLabel = "How many Farms do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newFarm", civilization);
		            }
		        });

	        	// Carpentry
		        newCarpentry.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Carpentry";
		            	newItemImage = "newCarpentry";
		            	newItemDialogo = "<html>A carpentry allows you to increase your Civilization's <br> wood production by 10%.<br><br>The cost to build a carpentry is:</html>";
		            	newItemLabel = "How many Carpentries do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCarpentry", civilization);
		            }
		        });
	        	// Blacksmith
		        newBlacksmith.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Blacksmith";
		            	newItemImage = "newBlacksmith";
		            	newItemDialogo = "<html>A blacksmith workshop allows you to increase <br> your Civilization's iron production by 10%.<br><br>The cost to build a blacksmith workshop is:</html>";
		            	newItemLabel = "How many Blacksmith's do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newBlacksmith", civilization);
		            }
		        });
	        	// Magic Tower
		        newMagicTower.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Magic Tower";
		            	newItemImage = "newMagicTower";
		            	newItemDialogo = "<html>The magic tower generates +3000 mana each time <br> resources are produced.<br><br>The cost to build a magic tower is:</html>";
		            	newItemLabel = "How many Magic Towers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newMagicTower", civilization);
		            }
		        });
	        	// Church
		        newChurch.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Church";
		            	newItemImage = "newChurch";
		            	newItemDialogo = "<html>Each Church will allow you to generate 1 Priest.</html>";		            	
		            	newItemLabel = "How many Churches do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newChurch", civilization);
		            }
		        });
	        
//	        Upgrade
				 // Attack Technology
		        upgradeAttack.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "Upgrade Attack";
		            	newItemImage = "newTechAttack";
		            	newItemDialogo = "<html>Each upgrade will significantly enhance the offensive <br> capabilities of your team.<br><br>The cost to upgrade it is:</html>";		            	
		            	newItemLabel = "Set the new level of Attack Technology:";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newTechAttack", civilization);
		            }
		        });
		        
		        
				 // Defense Technology
		        upgradeDefense.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "Upgrade Defense";
		            	newItemImage = "newTechDefense";
		            	newItemDialogo = "<html>Each upgrade will significantly enhance the defensive <br> capabilities of your team.<br><br>The cost to upgrade it is:</html>";		            	
		            	newItemLabel = "Set the new level of Defense Technology:";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newTechDefense", civilization);
		            }
		        });
	        
//	        Units
				 // Swordsman
		        newSwordsman.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Swordsman";
		            	newItemImage = "newSwordsman";
		            	newItemDialogo = "<html>The sharp sword of the Swordsman will cut through <br> your enemies with precision and skill!<br><br>The cost to train a new Swordsman is:</html>";		            	
		            	newItemLabel = "How many Swordsmen do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newSwordsman", civilization);
		            }
		        });
				 // Spearman
		        newSpearman.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Spearman";
		            	newItemImage = "newSpearman";
		            	newItemDialogo = "<html>The Spearman is the spear that will pierce through <br> enemy ranks with its sharp tip!<br><br>The cost to train a new Spearman is:\n"
		            			+ "</html>";
		            	newItemLabel = "How many Spearmen do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newSpearman", civilization);
		            }
		        });
		         // Crosswob
		        newCrossbow.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Crossbow";
		            	newItemImage = "newCrossbow";
		            	newItemDialogo = "<html>The Crossbow shoots deadly arrows with lethal <br> accuracy from a distance!<br><br>The cost to train a new Crossbow is:</html>";
		            	newItemLabel = "How many Crossbows do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCrossbow", civilization);
		            }
		        });
				 // Cannon
		        newCannon.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Cannon";
		            	newItemImage = "newCannon";
		            	newItemDialogo = "<html>The Cannon will unleash a rain of destruction <br> upon your enemies with its powerful shot!\n<br><br>The cost to train a new Cannon is:</html>";
		            	newItemLabel = "How many Cannons do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCannon", civilization);
		            }
		        });
				 // Arrow Tower
		        newArrowTower.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Arrow Tower";
		            	newItemImage = "newArrowTower";
		            	newItemDialogo = "<html>The Arrow Tower is the silent guardian that rains <br> arrows on invaders mercilessly!<br><br>The cost to build a new Arrow Tower is:</html>";
		            	newItemLabel = "How many Towers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newArrowTower", civilization);
		            }
		        });
				 // Catapult
		        newCatapult.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Catapult";
		            	newItemImage = "newCatapult";
		            	newItemDialogo = "<html>The Catapult will launch massive projectiles to crush <br> enemy defenses with overwhelming force!<br><br>The cost to build a new Catapult is:</html>";
		            	newItemLabel = "How many Catapults do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCatapult", civilization);
		            }
		        });
				 // Rocket Launcher
		        newRocketLauncher.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Rocket Launcher";
		            	newItemImage = "newRocketLauncher";
		            	newItemDialogo = "<html>The Rocket Launcher will unleash fire and explosions <br> to clear the battlefield with its devastating power!<br><br>The cost to build a new Rocket Launcher is:</html>";
		            	newItemLabel = "How many Launchers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newRocketLauncher", civilization);
		            }
		        });
				 // Magician
		        newMagician.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Magician";
		            	newItemImage = "newMagician";
		            	newItemDialogo = "<html>The Magician will conjure arcane spells to unbalance <br> your enemies with mysterious and powerful magic!<br><br>The cost to educate a new Magician is:</html>";
		            	newItemLabel = "How many Magicians do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newMagician", civilization);
		            }
		        });
				 // Priest
		        newPriest.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Priest";
		            	newItemImage = "newPriest";
		            	newItemDialogo = "<html>The Priest is the source of divine healing and protection <br> that will strengthen your troops and heal their wounds<br>in battle!<br><br>The cost to educate a new Priest is:</html>";		            	
		            	newItemLabel = "How many Priests do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newPriest", civilization);
		            }
		        });
	        
	        // Help
	        
				// Show Tutorial
				// About
				// Credits
				// Contact Support
	        
	        
	        
	        
	        
	        

	        // Hacer visible el JFrame
	        this.setVisible(true);
	        
	        
	        
	        
	    }
	    

	    
	    private void iniciarTemporizador(Civilization civilization, Timer timer) {

	        TimerTask timerTask = new TimerTask() {
	            int tiempoRestante = 180; // 180 = 3 minutos
	            int generarRecursos = 60; // 60 = recursos cada minuto

	            @Override
	            public void run() {
	                if (!enPausa) {
	                    tiempoRestante--;

	                    // Generar recursos cada minuto
	                    if (tiempoRestante % generarRecursos == 0) {
	                        generarRecursos();
	                    }

	                    if (tiempoRestante == 60) {
	                        // Llamar a la función cuando quede 1 minuto
//	                        mostrarAvisoBatalla();
	                    }
	                    if (tiempoRestante >= 0) {
	                        // Actualizar un JLabel con el tiempo restante
	                        labelTiempo.setText(String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60));
	                    } else {
	                        // Comenzar batalla
	                    	iniciarBatalla(civilization, globalJPanel, backgroundImageJPanel);
 	                    	
	                        // Detener el temporizador cuando llegue a cero
	                        timer.cancel();
	                    }
	                }
	            }



				private void iniciarBatalla(Civilization civilization, JPanel globalJPanel, JPanel backgroundImageJPanel) {
					
					// Ocultar JPanels
					globalJPanel.setVisible(false);
//			        backgroundImageJPanel.setOpaque(true);

					
					// Volver a mostrar
					globalJPanel.setVisible(true);
					
					
				}



				// Función para generar recursos
	            private void generarRecursos() {
	            
	            	
	                System.out.println("Generando recursos...");
	                
	                // Calcular recursos adicionales generados por cada edificio
	                int ironFromSmithies = CIVILIZATION_IRON_GENERATED_PER_SMITHY * civilization.getSmithy();
	                int woodFromCarpentries = CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY * civilization.getCarpentry();
	                int foodFromFarms = CIVILIZATION_FOOD_GENERATED_PER_FARM * civilization.getFarm();

	                // Sumar los recursos base y los generados por los edificios
	                int totalIronGenerated = CIVILIZATION_IRON_GENERATED + ironFromSmithies;
	                int totalWoodGenerated = CIVILIZATION_WOOD_GENERATED + woodFromCarpentries;
	                int totalFoodGenerated = CIVILIZATION_FOOD_GENERATED + foodFromFarms;
	                
	                // Añadir recursos
	                civilization.setFood(civilization.getFood() + totalFoodGenerated);
	                civilization.setIron(civilization.getIron() + totalIronGenerated);
	                civilization.setWood(civilization.getWood() + totalWoodGenerated);

	                updateInfoCivilization(civilization);
	                
	                // Imprimir resultados
	                System.out.println("Food Generated: " + CIVILIZATION_FOOD_GENERATED + " base +" + foodFromFarms + " from farms");
	                System.out.println("Wood Generated: " + CIVILIZATION_WOOD_GENERATED + " base +" + woodFromCarpentries + " from carpentries");
	                System.out.println("Iron Generated: " + CIVILIZATION_IRON_GENERATED + " base +" + ironFromSmithies + " from smithies");

	                if (civilization.getMagicTower() > 0) {
	                	int totalManaGenerated =  CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER * civilization.getMagicTower();
		                System.out.println("Mana Generated: " + totalManaGenerated + " from magic towers");

	                }

	                


	                
	                

	                
	                
	                
	                
	            }
	        };

	        // Resto del código...
	    	timer.scheduleAtFixedRate(timerTask, 0, 1000); // Se ejecuta cada 1000 ms = 1s
			
		}



		public class BackgroundPanel extends JPanel {
	        private Image backgroundImage;

	        public BackgroundPanel(String imagePath) {
	            setLayout(null); // Establece el layout a null para permitir el escalado manual
	            setOpaque(false); // Hace que el panel sea transparente
	            backgroundImage = new ImageIcon(imagePath).getImage();
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	            g2d.dispose();
	        }
	    }
	    
	    private void buildCentralGame(JPanel centralGame2, Civilization civilization) {
			
	    	// Parametros layout
	    	centralGame.setLayout(new BorderLayout());
	        centralGame.setOpaque(false);
	        
	        // Crear un JLayeredPane para superponer componentes
	        JLayeredPane layeredPane = new JLayeredPane();
	        centralGame.setLayout(new BorderLayout());
	        centralGame.add(layeredPane, BorderLayout.CENTER);

	        // Agregar la imagen de fondo al JLayeredPane
	        BackgroundPanel backgroundPanel = new BackgroundPanel("ruta/a/tu/imagen.jpg");
	        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

	        // Crear un JPanel para los JLabels
	        JPanel infoPanel = new JPanel();
	        infoPanel.setOpaque(true); // Hacer que el panel sea transparente para que se vea la imagen de fondo
	        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Establecer el layout a BoxLayout con orientación vertical

	        // Establecer el tamaño del infoPanel
	        int width = 300; // Ancho del panel
	        int height = 50; // Altura del panel
	        infoPanel.setBounds( 375, 50, width, height);


	        // Crear los JLabels
	        JLabel buildingLabel = new JLabel("Edificio X");
	        buildingLabel.setFont(buildingLabel.getFont().deriveFont(Font.BOLD)); // Establecer la fuente en negrita
	        buildingLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente


	        JLabel detailLabel = new JLabel("Información detallada");
	        detailLabel.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto horizontalmente
	        detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente


	        // Agregar un margen en la parte superior del buildingLabel
	        infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Agregar un espacio rígido de 10 píxeles de altura

	        // Agregar los JLabels al JPanel
	        infoPanel.add(buildingLabel);
	        infoPanel.add(detailLabel);

	        // Agregar el JPanel al JLayeredPane
	        layeredPane.add(infoPanel, JLayeredPane.PALETTE_LAYER);



	        
	        


	        
		}

		// Nueva Construccion o Unidad
	    private void createNewObject(String newItemTitle, String newItemImage, String newItemDialogo, String newItemLabel, String actionCommand, Civilization civilization) {
	        
	    	int anchuraAltura;
	    	
	    	try {
	            // Crear y configurar el JDialog
	            dialog = new JDialog(this, "Diálogo Modal", true);
	            dialog.setTitle(newItemTitle);
	            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialog.setModal(true); // Bloquea clics fuera del JDialog

	            // Crear un panel para la imagen con márgenes
	            dialogImagePanel = new JPanel();
	            dialogImagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	            icon = new ImageIcon("./src/layouts/resources/"+newItemImage+".png");
	            scaledImage = icon.getImage().getScaledInstance(250, 331, Image.SCALE_SMOOTH);
	            scaledIcon = new ImageIcon(scaledImage);
	            imageLabel = new JLabel(scaledIcon);
	            dialogImagePanel.add(imageLabel);
	            dialog.add(dialogImagePanel, BorderLayout.WEST);

	            // Crear el panel principal con GridBagLayout
	            mainPanel = new JPanel();
	            mainPanel.setLayout(new GridBagLayout());
	            gbc = new GridBagConstraints();

	            // Configurar restricciones para el JLabel y JTextField
	            gbc.gridx = 0;
	            gbc.gridy = 0;
	            gbc.anchor = GridBagConstraints.WEST;
	            gbc.insets = new Insets(10, 10, 10, 10);

	            // Mensaje introductorio
	            dialogo = new JLabel(newItemDialogo);
	            gbc.gridwidth = 2; // Ocupa 2 columnas
	            mainPanel.add(dialogo, gbc);

	         // Iconos recursos
	            gbc.gridy = 1; // Siguiente fila
	            costPanel = new JPanel();
	            costPanel.setLayout(new GridLayout(2, 2, 15, 15)); // 2 filas, 2 columnas, espacio de 10 píxeles entre celdas
	            costPanel.setMinimumSize(new Dimension(100, getMinimumSize().height));


	            // Imágenes de recursos
	            foodIcon = new ImageIcon("./src/layouts/resources/food.png");
	            woodIcon = new ImageIcon("./src/layouts/resources/wood.png");
	            ironIcon = new ImageIcon("./src/layouts/resources/iron.png");
	            manaIcon = new ImageIcon("./src/layouts/resources/mana.png");
	            anchuraAltura = 30;
	            foodIcon = new ImageIcon(foodIcon.getImage().getScaledInstance(anchuraAltura, anchuraAltura, Image.SCALE_SMOOTH));
	            woodIcon = new ImageIcon(woodIcon.getImage().getScaledInstance(anchuraAltura, anchuraAltura, Image.SCALE_SMOOTH));
	            ironIcon = new ImageIcon(ironIcon.getImage().getScaledInstance(anchuraAltura, anchuraAltura, Image.SCALE_SMOOTH));
	            manaIcon = new ImageIcon(manaIcon.getImage().getScaledInstance(anchuraAltura, anchuraAltura, Image.SCALE_SMOOTH));

	            // Coste de producción
	            // Fila 1
	            JPanel panelFood = new JPanel();
	            JLabel foodLabel = new JLabel(foodIcon);
	            foodLabel.setHorizontalAlignment(JLabel.LEFT);
	            panelFood.add(foodLabel);
	            costPanel.add(panelFood);

	            JPanel panelWood = new JPanel();
	            JLabel woodLabel = new JLabel(woodIcon);
	            woodLabel.setHorizontalAlignment(JLabel.LEFT);
	            panelWood.add(woodLabel);
	            costPanel.add(panelWood);

	            // Fila 2
	            JPanel panelIron = new JPanel();
	            JLabel ironLabel = new JLabel(ironIcon);
	            ironLabel.setHorizontalAlignment(JLabel.LEFT);
	            panelIron.add(ironLabel);
	            costPanel.add(panelIron);

	            JPanel panelMana = new JPanel();
	            JLabel manaLabel = new JLabel(manaIcon);
	            manaLabel.setHorizontalAlignment(JLabel.LEFT);
	            panelMana.add(manaLabel);
	            costPanel.add(panelMana);
	            
	            
	            if (actionCommand.equals("newTechAttack")) {
	                int foodCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST, civilization.getTechnologyAttack() + 1));
	                int woodCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST, civilization.getTechnologyAttack() + 1));
	                int ironCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST, civilization.getTechnologyAttack() + 1));

	                foodLabel.setText(String.format(": %10s", foodCost));
	                woodLabel.setText(String.format(": %10s", woodCost));
	                ironLabel.setText(String.format(": %10s", ironCost));
	                manaLabel.setText(String.format(": %10s", 0)); 
	                
	            } else if (actionCommand.equals("newTechDefense")) {
	                int foodCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST, civilization.getTechnologyDefense() + 1));
	                int woodCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST, civilization.getTechnologyDefense() + 1));
	                int ironCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST, civilization.getTechnologyDefense() + 1));

	                foodLabel.setText(String.format(": %10s", foodCost));
	                woodLabel.setText(String.format(": %10s", woodCost));
	                ironLabel.setText(String.format(": %10s", ironCost));
	                manaLabel.setText(String.format(": %10s", 0)); 
	                
	            } else {
		            foodLabel.setText(String.format(": %10s", getFoodCost(actionCommand)));
		            woodLabel.setText(String.format(": %10s", getWoodCost(actionCommand)));
		            ironLabel.setText(String.format(": %10s", getIronCost(actionCommand)));
		            manaLabel.setText(String.format(": %10s", getManaCost(actionCommand)));
	            }

	            // Añadir el panel de recursos al mainPanel
	            gbc.gridwidth = 2; // Ocupa 1 columna
	            gbc.anchor = GridBagConstraints.CENTER; // Centrar horizontalmente
	            mainPanel.add(costPanel, gbc);

//	            Introducir datos
	            gbc.gridx = 0;
	            gbc.gridy = 2; // Siguiente fila
	            gbc.gridwidth = 1; // Ocupa 1 columna
	            label = new JLabel(newItemLabel);
	            mainPanel.add(label, gbc);

	            // Crear el JSpinner
	            if (actionCommand.equals("newTechAttack")) {
    	            spinner = new JSpinner(new SpinnerNumberModel(civilization.getTechnologyAttack() + 1, 1, 30, 1));
    	            
	            } else if (actionCommand.equals("newTechDefense")) {
	                spinner = new JSpinner(new SpinnerNumberModel(civilization.getTechnologyDefense() + 1, 1, 30, 1));
	                
	            } else {
	                spinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
	            }


	            // Obtener el editor predeterminado del JSpinner
	            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();

	            // Deshabilitar la edición de texto en el editor
	            editor.getTextField().setEditable(false);

	            // Agregar el JSpinner al mainPanel
	            gbc.gridx = 1;
	            gbc.gridy = 2;
	            gbc.gridwidth = 1;
	            mainPanel.add(spinner, gbc);

	            // Panel para exception
	            gbc.gridx = 0;
	            gbc.gridy = 4; // Siguiente fila
	            gbc.gridwidth = 2; // Ocupa 2 columnas
	            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
	            
	            exceptionMessage = new JLabel("");

	            if (actionCommand.equals("newTechAttack")) {
                    exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyAttack()+1) +" and 30.");
    	            
	            } else if (actionCommand.equals("newTechDefense")) {
                    exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyDefense()+1) +" and 30.");
	                
	            } else {
		            exceptionMessage.setText("Please enter a number between 1 and 99.");
	            }
	            exceptionMessage.setForeground(Color.RED);
	            messagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	            messagePanel.add(exceptionMessage);
	            mainPanel.add(messagePanel, gbc);

	            // Panel para los botones
	            gbc.gridx = 0;
	            gbc.gridy = 5; // Siguiente fila
	            gbc.gridwidth = 2; // Ocupa 2 columnas
	            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
	            cancelButton = new JButton("Cancel");
	            createButton = new JButton("Create");
	            createButton.setEnabled(true);
	            buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Para alinear los botones
	            buttonPanel.add(cancelButton);
	            buttonPanel.add(createButton);
	            mainPanel.add(buttonPanel, gbc);
	            
	            
	            
//	             Eventos
	            
	            // Valores recursos en tiempo real
	            spinner.addChangeListener(new ChangeListener() {
	                @Override
	                public void stateChanged(ChangeEvent e) {
	                    
	                    int newLevel = (int) spinner.getValue();

	                    if (actionCommand.equals("newTechDefense")) {

	                    	if (newLevel > civilization.getTechnologyDefense()) {
	                            int foodCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST, newLevel));
	                            int woodCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST, newLevel));
	                            int ironCost = (int) (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST, newLevel));
	                            
	        	                foodLabel.setText(String.format(": %10s", foodCost));
	        	                woodLabel.setText(String.format(": %10s", woodCost));
	        	                ironLabel.setText(String.format(": %10s", ironCost));
	        	                manaLabel.setText(String.format(": %10s", 0)); 
	                        } else {
	                            foodLabel.setText(": 0");
	                            woodLabel.setText(": 0");
	                            ironLabel.setText(": 0");
	                            manaLabel.setText(": 0");
	                        }
	                        
	                    } 
	                    else if (actionCommand.equals("newTechAttack")) {

	                    	if (newLevel > civilization.getTechnologyAttack()) {
	                            int foodCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST, newLevel));
	                            int woodCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST, newLevel));
	                            int ironCost = (int) (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * Math.pow(UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST, newLevel));

	        	                foodLabel.setText(String.format(": %10s", foodCost));
	        	                woodLabel.setText(String.format(": %10s", woodCost));
	        	                ironLabel.setText(String.format(": %10s", ironCost));
	        	                manaLabel.setText(String.format(": %10s", 0)); 
	        	                
	                        } else {
	                            foodLabel.setText(": 0");
	                            woodLabel.setText(": 0");
	                            ironLabel.setText(": 0");
	                            manaLabel.setText(": 0");
	                        }
	                        
	                    } else {
	                    	foodLabel.setText(String.format(": %10s", getFoodCost(actionCommand) * newLevel));
	                    	woodLabel.setText(String.format(": %10s", getWoodCost(actionCommand) * newLevel));
	                    	ironLabel.setText(String.format(": %10s", getIronCost(actionCommand) * newLevel));
	                    	manaLabel.setText(String.format(": %10s", getManaCost(actionCommand) * newLevel));

	                    }
	                    
	 
	                }

	            });
	            
	            
	            
	            
	            spinner.addChangeListener(new ChangeListener() {
	                @Override
	                public void stateChanged(ChangeEvent e) {
	                    int spinnerValue = (int) spinner.getValue();
	                    sourceButton = actionCommand;
	                    
	                    if (sourceButton.equals("newTechAttack")) {
	                        if (spinnerValue <= civilization.getTechnologyAttack()) {
	                        	exceptionMessage.setText("Please enter a higher level.");
	            	            createButton.setEnabled(false);

	                        } else {
	                            exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyAttack()+1) + " and 99.");
	            	            createButton.setEnabled(true);

	                        }
	                    }

	                    if (sourceButton.equals("newTechDefense")) {
	                        if (spinnerValue <= civilization.getTechnologyDefense()) {
	                        	exceptionMessage.setText("Please enter a higher level.");
	            	            createButton.setEnabled(false);

	                        } else {
	                            exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyDefense()+1) +" and 99.");
	            	            createButton.setEnabled(true);

	                        }
	                    }

	                        
	                    	
	                    
	                    
	                    
	                    
	                }
	            });

	            

	            
	            
	            

	            cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose(); // Cerrar el diálogo
                    }
                });
	  
	            createButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    sourceButton = actionCommand;
	                    int numeroCreate = (int) spinner.getValue();



	                    if (sourceButton.equals("newFarm")) {
								try {
									civilization.newFarm(numeroCreate);
				                    updateInfoCivilization(civilization);
			                        System.out.println("New Farm was created");


								} catch (ResourceException e1) {
									// TODO Auto-generated catch block
								    System.out.println(e1.getMessage());
								}
		

	                    } else if (sourceButton.equals("newCarpentry")) {
	                        try {
								civilization.newCarpentry(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Carpentry was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newBlacksmith")) {
	                    	try {
								civilization.newSmithy(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Blacksmith was created");

							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newMagicTower")) {
	                    	try {
								civilization.newMagicTower(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Magic Tower was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newChurch")) {
	                    	try {
								civilization.newChurch(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Church button was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newSwordsman")) {
	                    	try {
								civilization.newSwordsman(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Swordsman was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newSpearman")) {
	                    	try {
								civilization.newSpearman(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Spearman was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCrossbow")) {
	                    	try {
								civilization.newCrossbow(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Crossbow was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCannon")) {
	                    	try {
								civilization.newCannon(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Cannon was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newArrowTower")) {
	                    	try {
								civilization.newArrowTower(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Arrow Tower was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCatapult")) {
	                    	try {
								civilization.newCatapult(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Catapult was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newRocketLauncher")) {
	                    	try {
								civilization.newRocketLauncher(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Rocket Launcher was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newMagician")) {
	                    	try {
								civilization.newMagician(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Magician was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							} catch (BuildingException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newPriest")) {
	                    	try {
								civilization.newPriest(numeroCreate);
			                    updateInfoCivilization(civilization);
		                        System.out.println("New Priest was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							} catch (BuildingException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newTechAttack")) {
	                    	try {
								civilization.upgradeTechnologyAttack();
			                    updateInfoCivilization(civilization);
		                        System.out.println("Attack tech was upgraded");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}

	                        
	                    } else if (sourceButton.equals("newTechDefense")) {
	                    	try {
								civilization.upgradeTechnologyDefense();
			                    updateInfoCivilization(civilization);
		                        System.out.println("Defense tech was upgraded");

							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}

	                    	
	                    }
	                    
//	                    System.out.println("Spinner value: " + (int) spinner.getValue() + "\n");
	                    dialog.dispose();

	                }
	            });
	            
	            
	            

	            // Agregar el panel principal al diálogo
	            dialog.add(mainPanel, BorderLayout.CENTER);

	            // Ajustes del JDialog
	            dialog.pack(); 
//	            dialog.setSize(700,700);
                dialog.setLocationRelativeTo(null); 
                dialog.setResizable(false); 
                dialog.setVisible(true);
                
                
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    public void updateInfoCivilization(Civilization civilization) {
	        // Actualizar recursos
	        labelComidaUnidades.setText(String.valueOf(civilization.getFood()));
	        labelMaderaUnidades.setText(String.valueOf(civilization.getWood()));
	        labelHierroUnidades.setText(String.valueOf(civilization.getIron()));
	        labelManaUnidades.setText(String.valueOf(civilization.getMana()));

	        // Actualizar edificios
	        // Farm
	        ((JLabel)panelEdificios.getComponent(1)).setText(String.valueOf(civilization.getFarm()));
	        // Carpentry
	        ((JLabel)panelEdificios.getComponent(3)).setText(String.valueOf(civilization.getCarpentry()));
	        // Blacksmith
	        ((JLabel)panelEdificios.getComponent(5)).setText(String.valueOf(civilization.getSmithy()));
	        // Magic Tower
	        ((JLabel)panelEdificios.getComponent(7)).setText(String.valueOf(civilization.getMagicTower()));
	        // Church
	        ((JLabel)panelEdificios.getComponent(9)).setText(String.valueOf(civilization.getChurch()));

		     // Actualizar unidades
		     // Swordsman
		     countSwordman.setText(String.valueOf(civilization.calculateLengthAtIndex(0)));
		     // Spearman
		     countSpearman.setText(String.valueOf(civilization.calculateLengthAtIndex(1)));
		     // Crossbow
		     countCrossbow.setText(String.valueOf(civilization.calculateLengthAtIndex(2)));
		     // Cannon
		     countCannon.setText(String.valueOf(civilization.calculateLengthAtIndex(3)));
		     // Arrow Tower
		     countArrowTower.setText(String.valueOf(civilization.calculateLengthAtIndex(4)));
		     // Catapult
		     countCatapult.setText(String.valueOf(civilization.calculateLengthAtIndex(5)));
		     // Rocket Launcher
		     countRocketLauncher.setText(String.valueOf(civilization.calculateLengthAtIndex(6)));
		     // Magician
		     countMagician.setText(String.valueOf(civilization.calculateLengthAtIndex(7)));
		     // Priest
		     countPriest.setText(String.valueOf(civilization.calculateLengthAtIndex(8)));


	        // Actualizar tecnologías
	        // Tecnología de ataque
	        ((JLabel)panelTecnologias.getComponent(1)).setText(String.valueOf(civilization.getTechnologyAttack()));
	        // Tecnología de defensa
	        ((JLabel)panelTecnologias.getComponent(3)).setText(String.valueOf(civilization.getTechnologyDefense()));

	        // Repintar el JPanel para reflejar los cambios con los datos actuales de la civilización
	        infoCivilization.repaint();
	        infoCivilization.revalidate();
	    }


	    
	    private int getFoodCost(String actionCommand) {
	        int foodCost = 0;
	        switch (actionCommand) {
	            case "newMagician":
	                foodCost = FOOD_COST_MAGICIAN;
	                break;
	            case "newPriest":
	                foodCost = FOOD_COST_PRIEST;
	                break;
	            case "newRocketLauncher":
	                foodCost = FOOD_COST_ROCKETLAUNCHERTOWER;
	                break;
	            case "newCatapult":
	                foodCost = FOOD_COST_CATAPULT;
	                break;
	            case "newArrowTower":
	                foodCost = FOOD_COST_ARROWTOWER;
	                break;
	            case "newCannon":
	                foodCost = FOOD_COST_CANNON;
	                break;
	            case "newCrossbow":
	                foodCost = FOOD_COST_CROSSBOW;
	                break;
	            case "newSpearman":
	                foodCost = FOOD_COST_SPEARMAN;
	                break;
	            case "newSwordsman":
	                foodCost = FOOD_COST_SWORDSMAN;
	                break;
	            case "newFarm":
	                foodCost = FOOD_COST_FARM;
	                break;
	            case "newCarpentry":
	                foodCost = FOOD_COST_CARPENTRY;
	                break;
	            case "newBlacksmith":
	                foodCost = FOOD_COST_SMITHY;
	                break;
	            case "newMagicTower":
	                foodCost = FOOD_COST_MAGICTOWER;
	                break;
	            case "newChurch":
	                foodCost = FOOD_COST_CHURCH;
	                break;
	            case "newTechAttack":
	                foodCost = FOOD_COST_CHURCH;
	                break;
	            case "newTechDefense":
	                foodCost = FOOD_COST_CHURCH;
	                break;
	            default:
	                // Manejo de caso por defecto si es necesario
	                break;
	        }
	        return foodCost;
	    }
	    private int getWoodCost(String actionCommand) {
	        int woodCost = 0;
	        switch (actionCommand) {
	            case "newMagician":
	                woodCost = WOOD_COST_MAGICIAN;
	                break;
	            case "newPriest":
	                woodCost = WOOD_COST_PRIEST;
	                break;
	            case "newRocketLauncher":
	                woodCost = WOOD_COST_ROCKETLAUNCHERTOWER;
	                break;
	            case "newCatapult":
	                woodCost = WOOD_COST_CATAPULT;
	                break;
	            case "newArrowTower":
	                woodCost = WOOD_COST_ARROWTOWER;
	                break;
	            case "newCannon":
	                woodCost = WOOD_COST_CANNON;
	                break;
	            case "newCrossbow":
	                woodCost = WOOD_COST_CROSSBOW;
	                break;
	            case "newSpearman":
	                woodCost = WOOD_COST_SPEARMAN;
	                break;
	            case "newSwordsman":
	                woodCost = WOOD_COST_SWORDSMAN;
	                break;
	            case "newFarm":
	                woodCost = WOOD_COST_FARM;
	                break;
	            case "newCarpentry":
	                woodCost = WOOD_COST_CARPENTRY;
	                break;
	            case "newBlacksmith":
	                woodCost = WOOD_COST_SMITHY;
	                break;
	            case "newMagicTower":
	                woodCost = WOOD_COST_MAGICTOWER;
	                break;
	            case "newChurch":
	                woodCost = WOOD_COST_CHURCH;
	                break;
	            case "newTechAttack":
	            	woodCost = FOOD_COST_CHURCH;
	                break;
	            case "newTechDefense":
	            	woodCost = FOOD_COST_CHURCH;
	                break;
	            default:
	                // Manejo de caso por defecto si es necesario
	                break;
	        }
	        return woodCost;
	    }
	    private int getIronCost(String actionCommand) {
	        int ironCost = 0;
	        switch (actionCommand) {
	            case "newMagician":
	                ironCost = IRON_COST_MAGICIAN;
	                break;
	            case "newPriest":
	                ironCost = IRON_COST_PRIEST;
	                break;
	            case "newRocketLauncher":
	                ironCost = IRON_COST_ROCKETLAUNCHERTOWER;
	                break;
	            case "newCatapult":
	                ironCost = IRON_COST_CATAPULT;
	                break;
	            case "newArrowTower":
	                ironCost = IRON_COST_ARROWTOWER;
	                break;
	            case "newCannon":
	                ironCost = IRON_COST_CANNON;
	                break;
	            case "newCrossbow":
	                ironCost = IRON_COST_CROSSBOW;
	                break;
	            case "newSpearman":
	                ironCost = IRON_COST_SPEARMAN;
	                break;
	            case "newSwordsman":
	                ironCost = IRON_COST_SWORDSMAN;
	                break;
	            case "newFarm":
	                ironCost = IRON_COST_FARM;
	                break;
	            case "newCarpentry":
	                ironCost = IRON_COST_CARPENTRY;
	                break;
	            case "newBlacksmith":
	                ironCost = IRON_COST_SMITHY;
	                break;
	            case "newMagicTower":
	                ironCost = IRON_COST_MAGICTOWER;
	                break;
	            case "newChurch":
	                ironCost = IRON_COST_CHURCH;
	                break;
	            case "newTechAttack":
	            	ironCost = FOOD_COST_CHURCH;
	                break;
	            case "newTechDefense":
	            	ironCost = FOOD_COST_CHURCH;
	                break;
	            default:
	                break;
	        }
	        return ironCost;
	    }
	    private int getManaCost(String actionCommand) {
	        int manaCost = 0;
	        switch (actionCommand) {
	        
	            case "newMagician":
	                manaCost = MANA_COST_MAGICIAN;
	                break;
	            case "newPriest":
	                manaCost = MANA_COST_PRIEST;
	                break;
	            case "newRocketLauncher":
	                manaCost = MANA_COST_ROCKETLAUNCHERTOWER;
	                break;
	            case "newCatapult":
	                manaCost = MANA_COST_CATAPULT;
	                break;
	            case "newArrowTower":
	                manaCost = MANA_COST_ARROWTOWER;
	                break;
	            case "newCannon":
	                manaCost = MANA_COST_CANNON;
	                break;
	            case "newCrossbow":
	                manaCost = MANA_COST_CROSSBOW;
	                break;
	            case "newSpearman":
	                manaCost = MANA_COST_SPEARMAN;
	                break;
	            case "newSwordsman":
	                manaCost = MANA_COST_SWORDSMAN;
	                break;
	            case "newFarm":
	                manaCost = 0;
	                break;
	            case "newCarpentry":
	                manaCost = 0;
	                break;
	            case "newBlacksmith":
	                manaCost = 0;
	                break;
	            case "newMagicTower":
	                manaCost = 0;
	                break;
	            case "newChurch":
	                manaCost = MANA_COST_CHURCH;
	                break;
	            case "newTechAttack":
	            	manaCost = FOOD_COST_CHURCH;
	                break;
	            case "newTechDefense":
	            	manaCost = FOOD_COST_CHURCH;
	                break;
	            default:
	                break;
	        }
	        return manaCost;
	    }
	    

		// JMenu
	    private void buildMenuBar() {

	    	
	    	// Game
	    	gameMenu = new JMenu("Game");
	    	playPauseMenuItem = new JMenuItem("Play/Pause");
	    	exitToMainMenuMenuItem = new JMenuItem("Exit to Main Menu");
	    	exitGameMenuItem = new JMenuItem("Exit Game");
	    	gameMenu.add(playPauseMenuItem);
	    	gameMenu.add(exitToMainMenuMenuItem);
	    	gameMenu.addSeparator(); // Agregar un separador antes de "Exit Game"
	    	gameMenu.add(exitGameMenuItem);
	    	
	    	
	        // Build
	        buildMenu = new JMenu("Build");
	        newFarm = new JMenuItem("Farm");
	        newCarpentry = new JMenuItem("Carpentry");
	        newBlacksmith = new JMenuItem("Blacksmith");
	        newMagicTower = new JMenuItem("Magic Tower");
	        newChurch = new JMenuItem("Church");
	        buildMenu.add(newFarm);
	        buildMenu.add(newCarpentry);
	        buildMenu.add(newBlacksmith);
	        buildMenu.add(newMagicTower);
	        buildMenu.add(newChurch);

	        // Technology
	        technologyMenu = new JMenu("Upgrade");
	        upgradeDefense = new JMenuItem("Defense Technology");
	        upgradeAttack = new JMenuItem("Attack Technology");
	        technologyMenu.add(upgradeAttack);
	        technologyMenu.add(upgradeDefense);

	        // Units
	        unitsMenu = new JMenu("Units");

	        offensiveMenu = new JMenu("Offensive");
	        newSwordsman = new JMenuItem("Swordsman");
	        newSpearman = new JMenuItem("Spearman");
	        newCrossbow = new JMenuItem("Crossbow");
	        newCannon = new JMenuItem("Cannon");
	        offensiveMenu.add(newSwordsman);
	        offensiveMenu.add(newSpearman);
	        offensiveMenu.add(newCrossbow);
	        offensiveMenu.add(newCannon);

	        defensiveMenu = new JMenu("Defensive");
	        newArrowTower = new JMenuItem("Arrow Tower");
	        newCatapult = new JMenuItem("Catapult");
	        newRocketLauncher = new JMenuItem("Rocket Launcher");
	        defensiveMenu.add(newArrowTower);
	        defensiveMenu.add(newCatapult);
	        defensiveMenu.add(newRocketLauncher);

	        specialMenu = new JMenu("Special");
	        newMagician = new JMenuItem("Magician");
	        newPriest = new JMenuItem("Priest");
	        specialMenu.add(newMagician);
	        specialMenu.add(newPriest);

	        // Agregar los submenús al menú "Units"
	        unitsMenu.add(offensiveMenu);
	        unitsMenu.add(defensiveMenu);
	        unitsMenu.add(specialMenu);

	        // Help
	        helpMenu = new JMenu("Help");
	        showTutorial = new JMenuItem("Show Tutorial");
	        aboutMenuItem = new JMenuItem("About");
	        creditsMenuItem = new JMenuItem("Credits");
	        contactSupportMenuItem = new JMenuItem("Contact Support");
	        helpMenu.add(showTutorial);
	        helpMenu.addSeparator(); // Separador para agrupar opciones relacionadas
	        helpMenu.add(aboutMenuItem);
	        helpMenu.add(creditsMenuItem);
	        helpMenu.add(contactSupportMenuItem);

	        // Agregar menús al JMenuBar
	        menuBar.add(gameMenu);
	        menuBar.add(buildMenu);
	        menuBar.add(technologyMenu);
	        menuBar.add(unitsMenu);
	        menuBar.add(helpMenu);
	       
	        
	    }
	    
        




	    // Parte derecha -> Stats
	    private void buildRightFrame(Civilization civilization) {
	    	
	        rightFrame = new JPanel();
//	    	rightFrame.setOpaque(false);
	    	rightFrame.setPreferredSize(new Dimension(400, 200)); // Establecer un tamaño predeterminado

	    	
	    	// Contador
	    	contador = new JPanel();
	    	contador.setOpaque(false);
	    	contador.setLayout(new GridBagLayout());
	    	gbc_contador = new GridBagConstraints();
	    	labelTiempo = new JLabel();
	    	labelTiempo.setFont(new Font("Arial", Font.PLAIN, 60)); 

	    	// Establecer márgenes
	    	gbc_contador.insets = new Insets(30, 20, 5, 20); // Margen superior, izquierdo, inferior, derecho

	    	gbc_contador.gridx = 0;
	    	gbc_contador.gridy = 0;
	    	contador.add(labelTiempo, gbc_contador);
	    	rightFrame.add(contador);
	    	
	        
	        
	    	// Crear GridBagLayout Contenido
	    	infoCivilization = new JPanel(new GridBagLayout());
	    	infoCivilization.setOpaque(false);
	    	gbc_info = new GridBagConstraints();
	    	rightFrame.add(infoCivilization);

	    	// Configurar propiedades generales 
//	    	gbc_info.fill = GridBagConstraints.BOTH; // Expandirse para llenar la celda
//	    	gbc_info.insets = new Insets(5, 5, 5, 5); // Márgenes alrededor de cada componente
	    	
	    	
	    	  // Establecer restricciones para la tabla de recursos
	    	gbc_info.fill = GridBagConstraints.NONE;
	    	gbc_info.anchor = GridBagConstraints.CENTER;
	    	gbc_info.insets = new Insets(5, 15, 5, 15); // Márgenes de separación

	    	
	    	// Contenido
	    	
//	    	Recursos
	    	labelRecursos = new JLabel("RESOURCES");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 0; 
	    	infoCivilization.add(labelRecursos, gbc_info);
	    	
	    	panelRecursos = new JPanel();
	    	panelRecursos.setOpaque(false);
	    	panelRecursos.setLayout(new GridLayout(2, 2, 10, 10)); // 2 filas, 2 columnas, espacio de 10 píxeles entre celdas

	    	// Añadir Recursos
	    	imagenComida = iconoComida.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	    	iconoComidaRedimensionado = new ImageIcon(imagenComida);

	    	imagenMadera = iconoMadera.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	    	iconoMaderaRedimensionado = new ImageIcon(imagenMadera);

	    	imagenHierro = iconoHierro.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	    	iconoHierroRedimensionado = new ImageIcon(imagenHierro);

	    	imagenMana = iconoMana.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	    	iconoManaRedimensionado = new ImageIcon(imagenMana);

	    	// Fila 1
	    	JPanel panelComida = new JPanel();
	    	panelComida.add(new JLabel(iconoComidaRedimensionado));
	    	labelComidaUnidades = new JLabel(String.valueOf(civilization.getFood()));
	    	panelComida.add(labelComidaUnidades);
	    	panelRecursos.add(panelComida);

	    	JPanel panelMadera = new JPanel();
	    	panelMadera.add(new JLabel(iconoMaderaRedimensionado));
	    	labelMaderaUnidades = new JLabel(String.valueOf(civilization.getWood()));
	    	panelMadera.add(labelMaderaUnidades);
	    	panelRecursos.add(panelMadera);

	    	// Fila 2
	    	JPanel panelHierro = new JPanel();
	    	panelHierro.add(new JLabel(iconoHierroRedimensionado));
	    	labelHierroUnidades = new JLabel(String.valueOf(civilization.getIron()));
	    	panelHierro.add(labelHierroUnidades);
	    	panelRecursos.add(panelHierro);

	    	JPanel panelMana = new JPanel();
	    	panelMana.add(new JLabel(iconoManaRedimensionado));
	    	labelManaUnidades = new JLabel(String.valueOf(civilization.getMana()));
	    	panelMana.add(labelManaUnidades);
	    	panelRecursos.add(panelMana);

	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 1;
	    	infoCivilization.add(panelRecursos, gbc_info);
	    	
	    	
	    	
	    	

	    	
	    	

//	    	Unidades
	    	labelUnidades = new JLabel("ARMY");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 5; 
	    	infoCivilization.add(labelUnidades, gbc_info);
	    	
	    	// Crear tabla de unidades
	        panelUnidades = new JPanel(new GridBagLayout());
	        panelUnidades.setOpaque(false);

	        // Establecer restricciones para la tabla de unidades
	        gbc_units = new GridBagConstraints();
	        gbc_units.fill = GridBagConstraints.HORIZONTAL;
	        gbc_units.anchor = GridBagConstraints.WEST;
	        gbc_units.insets = new Insets(5, 5, 5, 5);


	        // Swordman
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 0;
	        panelUnidades.add(new JLabel("Swordsman"), gbc_units);
	        gbc_units.gridx = 1;
	        countSwordman = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(0)));
	        panelUnidades.add(countSwordman, gbc_units);

	        
	        // Spearman
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 1;
	        panelUnidades.add(new JLabel("Spearman"), gbc_units);
	        gbc_units.gridx = 1;
	        countSpearman = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(1)));
	        panelUnidades.add(countSpearman, gbc_units);
	        
	        // Crossbow
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 2;
	        panelUnidades.add(new JLabel("Crossbow"), gbc_units);
	        gbc_units.gridx = 1;
	        countCrossbow  = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(2)));
	        panelUnidades.add(countCrossbow, gbc_units);
	        
	        // Cannon
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 3;
	        panelUnidades.add(new JLabel("Cannon"), gbc_units);
	        gbc_units.gridx = 1;
	        countCannon = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(3)));
	        panelUnidades.add(countCannon, gbc_units);
	        
	        // Arrow Tower
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 0; 
	        panelUnidades.add(new JLabel("Arrow Tower"), gbc_units);
	        gbc_units.gridx = 3; 
	        countArrowTower = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(4)));
	        panelUnidades.add(countArrowTower, gbc_units);
	        
	        // Catapult
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 1; 
	        panelUnidades.add(new JLabel("Catapult"), gbc_units);
	        gbc_units.gridx = 3; 
	        countCatapult = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(5)));
	        panelUnidades.add(countCatapult, gbc_units);
	        
	        // Rocket Launcher
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 2;
	        panelUnidades.add(new JLabel("Rocket Launcher"), gbc_units);
	        gbc_units.gridx = 3;
	        countRocketLauncher = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(6)));
	        panelUnidades.add(countRocketLauncher, gbc_units);
	        
	        
	        // Magician
	        gbc_units.gridx = 4; // Nueva columna para Magician y Priest
	        gbc_units.gridy = 0;
	        panelUnidades.add(new JLabel("Magician"), gbc_units);
	        gbc_units.gridx = 5;
	        countMagician = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(7)));
	        panelUnidades.add(countMagician, gbc_units);
	        
	        // Priest
	        gbc_units.gridx = 4;
	        gbc_units.gridy = 1;
	        panelUnidades.add(new JLabel("Priest"), gbc_units);
	        gbc_units.gridx = 5;

	        countPriest = new JLabel(String.valueOf(civilization.calculateLengthAtIndex(8)));
	        panelUnidades.add(countPriest, gbc_units);
	        // Añadir la tabla de units al panel principal
	        gbc_info.gridy = 6; 
	        infoCivilization.add(panelUnidades, gbc_info);
	        
	        
	        
//	    	Edificios
	    	labelEdificios = new JLabel("BUILDINGS");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 7; 
	    	infoCivilization.add(labelEdificios, gbc_info);
	    	
	    	// Crear tabla de edificios
	        panelEdificios = new JPanel(new GridBagLayout());
	        panelEdificios.setOpaque(false);

	        // Establecer restricciones para la tabla de edificios
	        gbc_building = new GridBagConstraints();
	        gbc_building.fill = GridBagConstraints.HORIZONTAL;
	        gbc_building.anchor = GridBagConstraints.WEST;
	        gbc_building.insets = new Insets(5, 5, 5, 5);


	        // Farm
	        gbc_building.gridx = 0; 
	        gbc_building.gridy = 0;
	        panelEdificios.add(new JLabel("Farm"), gbc_building);
	        gbc_building.gridx = 1;
	        panelEdificios.add(new JLabel(String.valueOf(civilization.getFarm())), gbc_building);

	        // Carpentry
	        gbc_building.gridx = 0;
	        gbc_building.gridy = 1;
	        panelEdificios.add(new JLabel("Carpentry"), gbc_building);
	        gbc_building.gridx = 1;
	        panelEdificios.add(new JLabel(String.valueOf(civilization.getCarpentry())), gbc_building);

	        // Blacksmith
	        gbc_building.gridx = 0;
	        gbc_building.gridy = 2;
	        panelEdificios.add(new JLabel("Blacksmith"), gbc_building);
	        gbc_building.gridx = 1;
	        panelEdificios.add(new JLabel(String.valueOf(civilization.getSmithy())), gbc_building);

	        // Magic Tower
	        gbc_building.gridx = 2;
	        gbc_building.gridy = 0;
	        panelEdificios.add(new JLabel("Magic Tower"), gbc_building);
	        gbc_building.gridx = 3;
	        panelEdificios.add(new JLabel(String.valueOf(civilization.getMagicTower())), gbc_building);

	        // Church
	        gbc_building.gridx = 2;
	        gbc_building.gridy = 1;
	        panelEdificios.add(new JLabel("Church"), gbc_building);
	        gbc_building.gridx = 3;
	        panelEdificios.add(new JLabel(String.valueOf(civilization.getChurch())), gbc_building);

	        // Añadir la tabla de edificios al panel principal
	        gbc_info.gridy = 8; 
	        infoCivilization.add(panelEdificios, gbc_info);
	    	

	    	
//	    	Tecnologias
	    	labelTencologias = new JLabel("TECHNOLOGY");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 9; 
	    	infoCivilization.add(labelTencologias, gbc_info);
	    	
	    	// Crear tabla de tecnologias
	        panelTecnologias = new JPanel(new GridBagLayout());
	        panelTecnologias.setOpaque(false);
	        
	        // Establecer restricciones para la tabla de unidades
	        gbc_tech = new GridBagConstraints();
	        gbc_tech.fill = GridBagConstraints.HORIZONTAL;
	        gbc_tech.anchor = GridBagConstraints.WEST;
	        gbc_tech.insets = new Insets(5, 5, 5, 5);
	    	
	        // Tecnologías de ataque
	        gbc_tech.gridx = 0; 
	        gbc_tech.gridy = 0;
	        panelTecnologias.add(new JLabel("Attack Level"), gbc_tech);
	        gbc_tech.gridx = 1; // Mantén el mismo valor de gridx para ambos elementos
	        panelTecnologias.add(new JLabel(String.valueOf(civilization.getTechnologyAttack())), gbc_tech);

	        // Tecnologías de defensa
	        gbc_tech.gridx = 2; // Incrementa el valor de gridx para colocarlo en la siguiente "columna"
	        gbc_tech.gridy = 0; // Mantén el mismo valor de gridy para ambos elementos
	        panelTecnologias.add(new JLabel("Defense Level"), gbc_tech);
	        gbc_tech.gridx = 3; // Mantén el mismo valor de gridy para ambos elementos
	        panelTecnologias.add(new JLabel(String.valueOf(civilization.getTechnologyDefense())), gbc_tech);
	        
	        // Añadir la tabla de tecnologias al panel principal
	        gbc_info.gridy = 10; 
	        infoCivilization.add(panelTecnologias, gbc_info);
	    	

//	        Border border = BorderFactory.createLineBorder(Color.RED, 10); 
//	        rightFrame.setBorder(border);
	    }

	    // Parte inferior -> Console output
	    private static void buildBottomFrame(JPanel bottomFrame) {
	        bottomFrame.setOpaque(false);
	        bottomFrame.setPreferredSize(new Dimension(800, 200)); // Establecer un tamaño predeterminado
//
//	        JPanel customConsole = new JPanel();
//	        customConsole.setLayout(new BorderLayout());
//	        customConsole.setOpaque(false);
//	        customConsole.setBorder(new EmptyBorder(10, 100, 10, 100));
//
//	        JTextArea textArea = new JTextArea(10, 50);
//	        textArea.setBackground(Color.BLACK);
//	        textArea.setForeground(Color.WHITE);
//	        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
//	        textArea.setEditable(false);
//
//	        JScrollPane scrollPane = new JScrollPane(textArea);
//	        customConsole.add(scrollPane, BorderLayout.CENTER);
//
//	        JTextField commandField = new JTextField(50);
//	        commandField.setBackground(Color.BLACK);
//	        commandField.setForeground(Color.WHITE);
//	        commandField.setFont(new Font("Consolas", Font.PLAIN, 12));
//	        commandField.addActionListener(new ActionListener() {
//	            @Override
//	            public void actionPerformed(ActionEvent e) {
//	                String command = commandField.getText();
//	                textArea.append("> " + command + "\n");
//	                // Aquí puedes procesar el comando ingresado
//	                commandField.setText("");
//	            }
//	        });
//
//	        customConsole.add(commandField, BorderLayout.SOUTH);
//
//	        OutputStream outputStream = new OutputStream() {
//	            @Override
//	            public void write(int b) {
//	                SwingUtilities.invokeLater(() -> {
//	                    textArea.append(String.valueOf((char) b));
//	                    textArea.setCaretPosition(textArea.getDocument().getLength());
//	                });
//	            }
//
//	            @Override
//	            public void write(byte[] b, int off, int len) {
//	                SwingUtilities.invokeLater(() -> {
//	                    textArea.append(new String(b, off, len));
//	                    textArea.setCaretPosition(textArea.getDocument().getLength());
//	                });
//	            }
//
//	            @Override
//	            public void write(byte[] b) {
//	                write(b, 0, b.length);
//	            }
//	        };
//
//	        PrintStream printStream = new PrintStream(outputStream, true);
//	        System.setOut(printStream);
//	        System.setErr(printStream);
//
//	        bottomFrame.add(customConsole, BorderLayout.CENTER);
	    }


	    

	  
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class MenuImage extends JPanel {

		private BufferedImage backgroundImage;

	    public MenuImage() {
	        // Intentar cargar la imagen
	        try {
	            backgroundImage = ImageIO.read(new File("./src/layouts/resources/home_right_background.jpeg"));
	        } catch (IOException e) {
	            System.err.println("Error al cargar background Menu Right: " + e.getMessage());
	            setBackground(Color.GRAY);
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        if (backgroundImage != null) {
	            int panelWidth = getWidth();
	            int panelHeight = getHeight();

	            int imageWidth = backgroundImage.getWidth();
	            int imageHeight = backgroundImage.getHeight();

	            // Escala para ajustar la imagen a la altura del panel, manteniendo la proporción
	            double scale = (double) panelHeight / imageHeight;

	            int newImageWidth = (int) (imageWidth * scale);
	            int newImageHeight = (int) (imageHeight * scale);

	            // Centrar la imagen horizontalmente
	            int x = (panelWidth - newImageWidth) / 2; // Centrado horizontal
	            int y = 0; // Dibuja desde la parte superior

	            // Dibuja la imagen ajustada con la nueva altura y centrada horizontalmente
	            g.drawImage(backgroundImage, x, y, newImageWidth, newImageHeight, this);
	        }
	    }
	}
	
	class MenuButtons extends JPanel {
		
		private JButton resume, newGame, credits, quit;
		private JLabel logoLabel;
		private BufferedImage backgroundImage;
		private ImageIcon originalIcon, scaledIcon;
		private Image originalImage, scaledImage;

        
	    public MenuButtons(VentanaJuego frame, CardLayout cardLayout, JPanel centerPanel, Game gameFrame) {
	    	
	    	
	    	// Cargar la imagen
	        try {
	            backgroundImage = ImageIO.read(new File("./src/layouts/resources/home_buttons_background.webp"));
	        } catch (IOException e) {
	            System.err.println("Error al cargar background Menu Left: " + e.getMessage());
	            setBackground(Color.RED);
	        }
	        
	        
	        // Configurar el BoxLayout
	        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	        this.setPreferredSize(new Dimension(600, 100));

	        // Cargar la imagen y redimensionarla
	        originalIcon = new ImageIcon("./src/layouts/resources/logo.png"); 
	        originalImage = originalIcon.getImage();

	        // Define las dimensiones deseadas para la imagen
	        int newWidth = 200; // Ancho deseado
	        int newHeight = 200; // Alto deseado

	        // Redimensionar la imagen
	        scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	        scaledIcon = new ImageIcon(scaledImage);

	        // Crear un JLabel con la imagen redimensionada
	        logoLabel = new JLabel(scaledIcon);

	        // Centrar la imagen en el eje X
	        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	        // Agregar un margen en la parte superior
	        this.add(Box.createVerticalStrut(100)); // Espacio en la parte superior

	        // Agregar la imagen al panel
	        this.add(logoLabel);

	        // Instanciar los botones
	        resume = new JButton("Resume");
	        newGame = new JButton("New Game");
	        credits = new JButton("Credits");
	        quit = new JButton("Quit");

	        // Centrar los botones en el eje X
	        resume.setAlignmentX(Component.CENTER_ALIGNMENT);
	        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
	        credits.setAlignmentX(Component.CENTER_ALIGNMENT);
	        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

	        // Agregar los botones al panel
	        this.add(Box.createVerticalStrut(20)); // Espacio entre elementos
	        this.add(resume);
	        this.add(Box.createVerticalStrut(10)); // Espacio entre botones
	        this.add(newGame);
	        this.add(Box.createVerticalStrut(10)); // Espacio entre botones
	        this.add(credits);
	        this.add(Box.createVerticalStrut(10)); // Espacio entre botones
	        this.add(quit);

//	         Eventos
	        
	        // Quit
	        quit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	frame.dispose();
	            }
	        });

	        // New Game
	        newGame.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                cardLayout.show(centerPanel, "MenuLabels");
	            }
	        });

	        // Credits
	        credits.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                cardLayout.show(centerPanel, "MenuCredits");
	            }
	        });
	        
	        
	        // Resume game
	        resume.setVisible(true);
	        resume.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	
	            	// Crear y mostrar el nuevo frame
                    
	            	
	            }
	        });
	        
	    }
		
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        if (backgroundImage != null) {
	            int panelWidth = getWidth();
	            int panelHeight = getHeight();

	            int imageWidth = backgroundImage.getWidth();
	            int imageHeight = backgroundImage.getHeight();

	            // Escala para ajustar la imagen a la altura del panel, manteniendo la proporción
	            double scale = (double) panelHeight / imageHeight;

	            int newImageWidth = (int) (imageWidth * scale);
	            int newImageHeight = (int) (imageHeight * scale);

	            // Centrar la imagen horizontalmente
	            int x = (panelWidth - newImageWidth) / 2; // Centrado horizontal
	            int y = 0; // Dibuja desde la parte superior

	            // Dibuja la imagen ajustada con la nueva altura y centrada horizontalmente
	            g.drawImage(backgroundImage, x, y, newImageWidth, newImageHeight, this);
	        }
	    }
		
		
		
		
	}
	
	class MenuCredits extends JPanel {
		
		private JLabel welcome, emptySpace, civilizationName, era, exceptionMessage;
		private JTextField enterCivilizationName;
		private JComboBox<String> eraComboBox;
		
		private boolean trueName;
		private JButton createGame, goBack;

		private GridBagConstraints gbc;
		
		
		public MenuCredits(JFrame parentFrame) {
			

		    this.setLayout(new GridBagLayout());
		    this.setBackground(Color.CYAN);

		    gbc = new GridBagConstraints();
		    gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes
	        gbc.fill = GridBagConstraints.HORIZONTAL; // Expande horizontalmente solo si es necesario

		    // Etiqueta de bienvenida
		    welcome = new JLabel("<html><h1>Créditos del Proyecto Civilization</h1>"
		            + "<p>Este proyecto de Civilization en Java fue creado por estudiantes de 1º de DAW.</p>"
		            + "<ul>"
		            + "<li>Alumno 1: Sergio Fernández</li>"
		            + "<li>Alumno 2: Jorge Pérez</li>"
		            + "<li>Alumno 3: Unax Fernández</li>"
		            + "</ul>"
		            + "<p>Gracias por jugar y esperamos que disfrutes el juego.</p>"
		            + "<p>Creado como parte del curso de Desarrollo de Aplicaciones Web.</p>"
		            + "</html>");
		    
		    gbc.gridx = 0; // Primera columna
	        gbc.gridy = 0; // Primera fila
	        gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa todas las columnas disponibles
	        gbc.anchor = GridBagConstraints.NORTHWEST; // Componente se alinea en la esquina superior izquierda
	        gbc.ipady = 100; // Altura del componente
	        this.add(welcome, gbc); // Agregar etiqueta

	        // Espacio vacío para separar la etiqueta de bienvenida de otros componentes
	        emptySpace = new JLabel();
	        gbc.gridx = 0; // Primera columna
	        gbc.gridy = 1; // Segunda fila
	        gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa todas las columnas disponibles
	        gbc.ipady = 20; // Altura del componente
	        this.add(emptySpace, gbc); // Agregar espacio vacío
//	        emptySpace.setOpaque(true); 
//	        emptySpace.setBackground(Color.LIGHT_GRAY); 

	     // Botón Go Back
	        goBack = new JButton("Back");
	        gbc.gridx = 0; // Primera columna
	        gbc.gridy = 9; // Décima fila
	        gbc.gridwidth = 1; // Componente ocupa solo una columna
	        gbc.fill = GridBagConstraints.NONE; // No expandirse horizontalmente
	        gbc.anchor = GridBagConstraints.NORTHWEST; // Anclar en la esquina superior izquierda
	        gbc.insets = new Insets(200, 10, 10, 10); // Más margen arriba para mayor separación
	        this.add(goBack, gbc);
  
		    
		    
		    
		    
		    
		    // Eventos
    
	        // Cerrar creación partida
	        goBack.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                cardLayout.show(centerPanel, "MenuImage"); // Mostrar el panel principal al presionar "GoBack"
	            }
	        });
	        
		}
		
		
		
	}
	
	class MenuLabels extends JPanel {

		private JLabel welcome, emptySpace, civilizationName, era, exceptionMessage;
		private JTextField enterCivilizationName;
		private JComboBox<String> eraComboBox;
		
		private boolean trueName;
		private JButton createGame, goBack;

		private GridBagConstraints gbc;
		
		private Game gameFrame;
		
		
		
		public MenuLabels(JFrame parentFrame, Civilization civilization, Timer timer) {
			
//			this.setVisible(false);
			
			trueName = true;
			
		    this.setLayout(new GridBagLayout());
		    this.setBackground(Color.CYAN);

		    gbc = new GridBagConstraints();
		    gbc.insets = new Insets(10, 10, 10, 10); // Márgenes entre componentes
		    gbc.fill = GridBagConstraints.HORIZONTAL; // Para que los JTextField se expandan horizontalmente

		    // Etiqueta de bienvenida
		    welcome = new JLabel("<html><h1>Welcome to Civilization!</h1>"
		            + "<p>You're about to start a journey through history, leading your civilization to greatness.</p>"
		            + "<p>Please enter the following information to begin:</p>"
		            + "<ul>"
		            + "<li>Your Civilization's Name</li>"
		            + "<li>Your Coat of Arms</li>"
		            + "</ul>"
		            + "<p>Good luck, Leader!</p></html>");
		    
		    
		    gbc.gridx = 0; // Primera columna
		    gbc.gridy = 0; // Primera fila
		    gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa todas las columnas disponibles
		    gbc.anchor = GridBagConstraints.NORTHWEST; // Componente se alinea en la esquina superior izquierda
		    gbc.ipady = 100; // Altura del componente
		    this.add(welcome, gbc); // Agregar etiqueta

		    // Espacio vacío para separar la etiqueta de bienvenida de los campos de texto
		    emptySpace = new JLabel();
		    gbc.gridx = 0; // Primera columna
		    gbc.gridy = 1; // Segunda fila
		    gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa todas las columnas disponibles
		    gbc.ipady = 20; // Altura del componente
		   
		    this.add(emptySpace, gbc); // Agregar espacio vacío

		    // Etiqueta para Civilization Name
		    civilizationName = new JLabel("Civilization Name:");
		    gbc.gridx = 0; // Primera columna
		    gbc.gridy = 2; // Tercera fila
		    gbc.gridwidth = 1; // Componente ocupa una sola columna
		    this.add(civilizationName, gbc); // Agregar etiqueta

		    // JTextField para Civilization Name
		    enterCivilizationName = new JTextField("Civilization",15);
		    enterCivilizationName.setHorizontalAlignment(JTextField.CENTER); // Alinear el texto en el centro
		    gbc.gridx = 1; // Segunda columna
		    gbc.gridy = 2; // Tercera fila
		    gbc.gridwidth = 2; // Componente ocupa dos columnas
		    this.add(enterCivilizationName, gbc); // Agregar JTextField
		    
		    
		    
		    // Etiqueta para mensajes de excepción
		    exceptionMessage = new JLabel(" ");
		    exceptionMessage.setForeground(Color.RED); // Establecer el color del texto en rojo
		    gbc.gridx = 2; // Primera columna
		    gbc.gridy = 4; // Quinta fila
		    gbc.gridwidth = GridBagConstraints.REMAINDER; // Ocupa todas las columnas disponibles
		    this.add(exceptionMessage, gbc); // Agregar etiqueta de mensaje de excepción

		    
		    // Botón Go back
		    goBack = new JButton("Back");
		    gbc.gridx = 0; // Primera columna
		    gbc.gridy = 5; // Sexta fila
		    gbc.gridwidth = 1; // Componente ocupa dos columnas
		    this.add(goBack, gbc);
		    
		    
		    // Botón "Create Game"
		    createGame = new JButton("Create Game");
		    gbc.gridx = 1; // Segunda columna
		    gbc.gridy = 5; // Sexta fila
		    gbc.gridwidth = 2; // Componente ocupa dos columnas
		    this.add(createGame, gbc);
		    createGame.setEnabled(true);
		    
		    
		    
		    
		    // Eventos
		    
		    // Evento para aceptar solo letras en el campo de Civilization Name
		    enterCivilizationName.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyReleased(KeyEvent e) {
	                String text = enterCivilizationName.getText();
	                
	                // Check if text contains only letters
	                if (text.isEmpty()) {
	                    exceptionMessage.setText("Civilization Name cannot be empty.");
	                    trueName = false;
	                } else if (!text.chars().allMatch(Character::isLetter)) { // Verifies all characters are letters
	                    exceptionMessage.setText("Only letters are allowed in the Civilization Name!");
	                    trueName = false;
	                } else {
	                    exceptionMessage.setText("");
	                    trueName = true;
	                }

	                updateCreateGameState();
	            }
	        });

		    
		    
	        
	        // Iniciar juego
	        createGame.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Ocultar el frame principal
	                setVisible(false);
	                
	                // Usar SwingUtilities.invokeLater para abrir el nuevo frame después
	                SwingUtilities.invokeLater(new Runnable() {
	                    @Override
	                    public void run() {
	                        // Crear y mostrar el nuevo frame
	                        gameFrame = new Game(parentFrame, civilization, timer);
	                        
	                        // Ocultar el frame principal
	                        parentFrame.setVisible(false);
	                    }
	                });
	            }
	        });
	        
	        // Cerrar creación partida
	        goBack.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                cardLayout.show(centerPanel, "MenuImage"); // Mostrar el panel principal al presionar "GoBack"
	            }
	        });
	        
		    
		}
		
		
		
		private void updateCreateGameState( ) {
			if (trueName) {
		        createGame.setEnabled(true);
		        exceptionMessage.setText("");
		    } else {
		        createGame.setEnabled(false);
		        
		        if (!trueName) {
		            String text = enterCivilizationName.getText();
		            if (text.isEmpty()) {
		                exceptionMessage.setText("Civilization Name cannot be empty.");
		            } else if (!isValidCivilizationName(text)) {
		                exceptionMessage.setText("Only letters are allowed in the Civilization Name!");
		            } else {
		                exceptionMessage.setText("");
		            }
		        }
		    }
	    }
		
		private boolean isValidCivilizationName(String name) {
		    for (int i = 0; i < name.length(); i++) {
		        if (!Character.isLetter(name.charAt(i))) {
		            return false;
		        }
		    }
		    return true;
		}
		
		
		
	
	
	
	}
	
	
	

	

}


