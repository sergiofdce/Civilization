package layouts;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;

import BBDD.BBDD;
import clase.*;

import excepciones.BuildingException;
import excepciones.ResourceException;
import interfaces.MilitaryUnit;
import interfaces.Variables;



class VentanaBattle extends JFrame {

    private JPanel estadoBattle;
    private JPanel resumenUnidades;
    private JPanel pasoPasoBattle;
    private JPanel countdownPanel;
    private JPanel botonSouth;
    private JLabel victoryDefeat, countdownLabel;
    private JButton nextButton, mostrarDetalles;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Timer timer;
    private TimerTask timerTask;
    
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    
    

   
    public VentanaBattle(Civilization civilization, Battle batallaActual, JButton closeButton) {
        setTitle("Summary of the Battle");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setResizable(false);
        
        
        
        
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // No action needed when window gains focus
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                toFront(); // Bring the window to the front when focus is lost
            }
        });

        // Guardar registros
        civilization.insertBattle(batallaActual.generalReportToString(), batallaActual.getLargeReport());
        
        
       
        
        // Titulo
        estadoBattle = new JPanel();
        victoryDefeat = new JLabel();
        estadoBattle.setPreferredSize(new Dimension(200, 100));
        if (batallaActual.getLargeReport().contains("Tu enemigo ha perdido menos recursos que tu, has perdido!")) {
        	victoryDefeat.setText("DEFEAT");
        } else if (batallaActual.getLargeReport().contains("Tu enemigo ha perdido más recursos que tu, eres el ganador!")) {
        	victoryDefeat.setText("VICTORY");
        }
        victoryDefeat.setFont(new Font("Arial", Font.BOLD, 70));

        estadoBattle.add(victoryDefeat);
        

        
        
        
        
        // Mostrar reporte de unidades
        resumenUnidades = new JPanel();
        resumenUnidades.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        resumenUnidades.setPreferredSize(new Dimension(600, 650));
        resumenUnidades.add(new JLabel("Summary of Units"));        
        JLabel registroUnidadesJLabel = new JLabel();
        registroUnidadesJLabel.setText(batallaActual.generalReportToString());
        registroUnidadesJLabel.setVerticalAlignment(SwingConstants.TOP); // Alinear el texto arriba
        resumenUnidades.add(registroUnidadesJLabel);
        
        



       

        // Reporte detallado
        pasoPasoBattle = new JPanel();
        pasoPasoBattle.setPreferredSize(new Dimension(400, 600));
        pasoPasoBattle.setLayout(new BorderLayout());
        pasoPasoBattle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        String reportePasoPasoString = batallaActual.getLargeReport();
        JTextArea reportTextArea = new JTextArea(reportePasoPasoString);
        reportTextArea.setEditable(false);
        reportTextArea.setLineWrap(true);
        reportTextArea.setWrapStyleWord(true);
        // Establecer márgenes al JTextArea
        reportTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(reportTextArea);
        pasoPasoBattle.add(scrollPane, BorderLayout.CENTER);

        

        // Boton Cerrar
        botonSouth = new JPanel();
        botonSouth.setLayout(new BorderLayout());
        botonSouth.add(closeButton, BorderLayout.EAST);

        
        

        
        
        
        
        
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        // Crear panel de cuenta regresiva
        countdownPanel = new JPanel();
        countdownPanel.setBackground(Color.BLACK);
        countdownPanel.setLayout(new BorderLayout());

        countdownLabel = new JLabel("3", SwingConstants.CENTER);
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 100));
        countdownPanel.add(countdownLabel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
//        nextButton.setFont(new Font("Arial", Font.PLAIN, 20));
        nextButton.setVisible(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "mainContent");
                timer.cancel(); // Cancel the timer if it hasn't finished yet
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(nextButton);
        countdownPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Añadir paneles al mainPanel
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.add(estadoBattle, BorderLayout.NORTH);
        mainContent.add(resumenUnidades, BorderLayout.WEST);
        mainContent.add(pasoPasoBattle, BorderLayout.EAST);
        mainContent.add(botonSouth, BorderLayout.SOUTH);
        mainPanel.add(countdownPanel, "countdown");
        mainPanel.add(mainContent, "mainContent");

        add(mainPanel, BorderLayout.CENTER);

        // Iniciar la cuenta regresiva
        startCountdown();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startCountdown() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int countdown = 3;

            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (countdown > 0) {
                            countdownLabel.setText(String.valueOf(countdown));
                            countdown--;
                        } else {
                            countdownLabel.setFont(new Font("Arial", Font.BOLD, 70));
                            countdownLabel.setText("See Battle Report");
                            
                            nextButton.setVisible(true);
                            timer.cancel(); // Cancel the timer after countdown finishes
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }


}





public class VentanaJuego extends JFrame {

	 private MenuLabels menuLabels;
	 private CardLayout cardLayout;
	 private MenuImage menuImage;
	 private Game gameFrame;
	 private JPanel centerPanel;
	 private MenuCredits menuCredits;
	 
	
	 
	 
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
	    private ImageIcon iconoComida = new ImageIcon("src/layouts/resources/food.png");
	    private ImageIcon iconoMadera = new ImageIcon("src/layouts/resources/wood.png");
	    private ImageIcon iconoHierro = new ImageIcon("src/layouts/resources/iron.png");
	    private ImageIcon iconoMana = new ImageIcon("src/layouts/resources/mana.png");
	    private Image imagenComida, imagenMadera, imagenHierro, imagenMana;
	    private ImageIcon iconoComidaRedimensionado, iconoMaderaRedimensionado, iconoHierroRedimensionado, iconoManaRedimensionado;
	    
	    // Background image
	    private JPanel backgroundImageJPanel;
	    
	    // JPanel global -> Aquí se añadirán NORTH, WEST, EAST, SOUTH y CENTER
	    private JPanel globalJPanel;
	    
	    // Panel derecho
	    private JLabel labelEdificios, labelUnidades, labelTencologias, labelRecursos;
	    private JPanel EnemyUnitPanel, contentPanelEnemyArmy, enemyarmyInfo, armyInfo, techInfo,battleReportesPanel, battleInfo, enemyInfo, marginLeftJPanel, marginRightJPanel, contenedorRightJPanel, infoCivilization, panelRecursos, panelEdificios, panelUnidades, panelTecnologias, contador;
	    private GridBagConstraints gbc_contador, gbc_info, gbc_building, gbc_units, gbc_tech;
	    private JLabel labelTimer, labelTiempo;
	    private JLabel labelComidaUnidades, labelMaderaUnidades, labelHierroUnidades, labelManaUnidades;
	    private JLabel mensajeBattleJLabel, mensajeEnemyArmyJLabel, countSwordman, countSpearman, countCrossbow, countCannon, countArrowTower, countCatapult, countRocketLauncher, countMagician, countPriest;
	    private JTabbedPane tabbedPaneRight;
	    private JButton btnPlayPause, botonMas30s;
	    
	    // Consola
	    private JTextArea consoleTextArea;
	    private JTextField commandInputField;
	    private JButton sendButton;
	    private ByteArrayOutputStream outputStream;
	    
	    // Timer
	    private Timer timer;
	    private boolean enPausa, mostrarEnemyArmy, armyVacio;
	    
	    // Batalla
	    private ArrayList<ArrayList<MilitaryUnit>> enemyArmy;
		private int EnemyWood, EnemyIron, EnemyFood;
		
		// SO
		String sSistemaOperativo;




	    public Game(JFrame parentFrame, Civilization civilization, Timer timer) {
	        this.setTitle("Game");
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para que solo cierre este JFrame
	        this.setLocationRelativeTo(null);
	        this.setResizable(false);
	       
	     
 	        

// 	        Comprobar SO
// 	       sSistemaOperativo = System.getProperty("os.name");
// 	       System.out.println(sSistemaOperativo);
	        
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
	    	            backgroundImage = ImageIO.read(new File("src/layouts/resources/background_frame.png"));
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
	    	
	    	centralGame = new BackgroundPanel("Civilization/src/layouts/resources/background.jpg");
	    	buildCentralGame(civilization);
	        
	        
	        // Parte superior
	        topFrame = new JPanel();
	        topFrame.setOpaque(false);
	    	topFrame.setPreferredSize(new Dimension(100, 50));
	    	

	        // Parte izquierda
	        leftFrame = new JPanel();
	        leftFrame.setOpaque(false);
	    	leftFrame.setPreferredSize(new Dimension(25, 0));

	        // Parte derecha -> STATS
	        buildRightFrame(civilization);

	        // Parte inferior
	        bottomFrame = new JPanel();
	        bottomFrame.setOpaque(false);
	        bottomFrame.setPreferredSize(new Dimension(-1, 100));
//	        buildBottomFrame(bottomFrame);
	        
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
	        globalJPanel.add(contenedorRightJPanel, BorderLayout.EAST);
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
	    	            labelTiempo.setForeground(Color.BLACK); // Color rojo si enPausa es false

	                } else {
	                    enPausa = true;
	    	            labelTiempo.setForeground(Color.RED); // Color verde si enPausa es true

	                }
	            }
	        });


		        // Exit to Main Menu
	        exitToMainMenuMenuItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres volver al menú principal?", "Guardar y Salir", JOptionPane.YES_NO_OPTION);
	                if (respuesta == JOptionPane.YES_OPTION) {
	                    System.out.println("Volviendo al menú principal...");
	                    BBDD carga = new BBDD();
	                    carga.guardarJuego(civilization);
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
	                    BBDD carga = new BBDD();
	                    carga.guardarJuego(civilization);
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

	    	
	    	
            // TimerTask
	        TimerTask timerTask = new TimerTask() {

                int tiempoRestante = 180; // 180 = 3 minutos
                boolean enemyArmyCreated = false;

                
                

	            @Override
	            public void run() {
	            	
	            	botonMas30s.addActionListener(new ActionListener() {
	            	    @Override
	            	    public void actionPerformed(ActionEvent e) {
	            	    	tiempoRestante = 62;

//            	            int minutos = tiempoRestante / 60;
//            	            int segundos = tiempoRestante % 60;
	            	    }
	            	});

	            	
	            	// Avanzar Temporizador
	                if (!enPausa) {
	                    int tiempoAnterior = tiempoRestante;
	                    tiempoRestante--;
	                    	                    
	                    // Chequear si se ha pasado por un múltiplo de un minuto
	                    int minutosPasados = (tiempoAnterior / 60) - (tiempoRestante / 60);
	                    if (minutosPasados > 0) {
	                        for (int i = 0; i < minutosPasados; i++) {
	                        	// Generar recursos
	                            generarRecursos();
	                            
	                        }
	                    }
	                    
	                    labelTiempo.setText(String.format("%02d:%02d", tiempoRestante / 60, tiempoRestante % 60));
	                }
	                
	                if (tiempoRestante == 61 && !enemyArmyCreated) {
	                	// Mostrar tab Army
                        mostrarEnemyArmy = true;
                        mensajeEnemyArmyJLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        mensajeEnemyArmyJLabel.setText("<html>Here you can see the statistics of each unit, click on <br>the 'Show Info' button to display its information.</html>");
                        mensajeEnemyArmyJLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Añadir margen superior e inferior
                        
  

                    	// Crear ejercito enemigo cuando falte 1 minuto
                        generarRecursosEnemigo();
                        if (!enemyArmyCreated) {
                        	createEnemyArmy(civilization);
                        	System.out.println("El ejercito enemigo ha sido creado:");
                        	System.out.println(civilization.getEnemyArmy() + "\n");
                        	contentPanelEnemyArmy.setVisible(true);
                        }
                         
	                }
	                    
	                // Eventos temporizador
	                if (tiempoRestante == 60 && !enemyArmyCreated) {
	                	
	                    tiempoRestante--;
	                    
	                    // Mostrar mensaje de creación del ejército enemigo
	                    JDialog dialog = new JDialog();
	                    JPanel panel = new JPanel(new BorderLayout());
	                    JLabel label = new JLabel("Puedes ver el ejercito enemigo");
	                    label.setHorizontalAlignment(SwingConstants.CENTER);
	                    
	                    JButton okButton = new JButton("Close");
	                    okButton.addActionListener(new ActionListener() {
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                            tiempoRestante++;
	                            dialog.dispose();
	                            enPausa = false;
	                            labelTiempo.setForeground(Color.BLACK);
	                        }
	                    });
	                    dialog.addWindowListener(new WindowAdapter() {
	                        @Override
	                        public void windowClosing(WindowEvent e) {
	                        	tiempoRestante++;
	                            dialog.dispose();
	                            enPausa = false;
	                            labelTiempo.setForeground(Color.BLACK);
	                        }
	                    });
	                    
	                    panel.add(label, BorderLayout.CENTER);
	                    panel.add(okButton, BorderLayout.SOUTH);
	                    
	                    dialog.setContentPane(panel);
	                    dialog.setSize(300, 150);
	                    dialog.setTitle("El enemigo se acerca");
	                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	                    dialog.setLocationRelativeTo(null);
	                    dialog.setResizable(false);
	                    dialog.setVisible(true);
	                   
	                    
	                    enPausa = true; 
	                    labelTiempo.setForeground(Color.RED);
	                }
                    
                    if (tiempoRestante == 0) {
                    	
                    	// Ocultar tab army enemigo 
                        mostrarEnemyArmy = false;
                    	contentPanelEnemyArmy.setVisible(false);
                        mensajeEnemyArmyJLabel.setText("<html>Enemy forces are regrouping. Come back later.</html>");


                    	
            	    	int sumaUnidades = 0;

            	    	
                    	for (ArrayList<MilitaryUnit> array : civilization.getArmy()) {
                    		sumaUnidades += array.size();
                    	}
                    	
                   
                    	// Comprobar si army tiene unidades, si no, reiniciar timer
                    	// Crear ejercito enemigo cuando falte 1 minuto
                    	if (sumaUnidades==0) {
                    		

                    		JDialog dialog = new JDialog();
                            JLabel label = new JLabel("Battle postponed");
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            
                            JButton closeButton = new JButton("Close");
                            closeButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                	timer.cancel(); // Cancelar el timer actual
         			                Timer newTimer = new Timer(); // Crear un nuevo timer
         			                iniciarTemporizador(civilization, newTimer); // Reiniciar el temporizador con el nuevo timer
                                    dialog.dispose(); // Cierra el diálogo al hacer clic en el botón
                                }
                            });

                            dialog.setLayout(new BorderLayout());
                            dialog.add(label, BorderLayout.CENTER);
                            dialog.add(closeButton, BorderLayout.SOUTH);
                            dialog.setSize(300, 150);
                            dialog.setTitle("Battle postponed");
                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog.setLocationRelativeTo(null);
                            dialog.setResizable(false);
                            dialog.setVisible(true);

                            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                    System.out.println("Dialog closed!"); // Evento al cerrar el diálogo
                                    timer.cancel(); // Cancelar el timer actual
        			                Timer newTimer = new Timer(); // Crear un nuevo timer
        			                iniciarTemporizador(civilization, newTimer); // Reiniciar el temporizador con el nuevo timer
        			                
                                }
                            });


                            	
                    	} else {
                    		
 	                    	// Iniciar batalla
 	                    	Battle batallaActual= new Battle(civilization.getArmy(), civilization.getEnemyArmy());
 	                    	batallaActual.battleGame();
 	                    	civilization.insertBattle(batallaActual.generalReportToString(), batallaActual.getLargeReport());
 	                    	BBDD carga = new BBDD();
 	                    	carga.guardarJuego(civilization);
 	                    	try {
								carga.saveReport(civilization.getReportes());
							} catch (ResourceException | BuildingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
 	                        // Comenzar batalla
 	                    	mostrarEnemyArmy = false;
 	                        iniciarBatalla(civilization, globalJPanel, backgroundImageJPanel, batallaActual, timer);
 	                        enemyArmyCreated = false;

                    	}
                    	
                    	// Parar timer
                    	timer.cancel();
                    }
	            }


				private void iniciarBatalla(Civilization civilization, JPanel globalJPanel, JPanel backgroundImageJPanel, Battle batallaActual, Timer timer) {
					
	                JButton closeButton = new JButton("Close");

					// Inciar JFrame
				    VentanaBattle ventanaBatalla = new VentanaBattle(civilization, batallaActual, closeButton);
				    
				    closeButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			            	updateInfoCivilization(civilization);
			                timer.cancel(); // Cancelar el timer actual
			                Timer newTimer = new Timer(); // Crear un nuevo timer
			                iniciarTemporizador(civilization, newTimer); // Reiniciar el temporizador con el nuevo timer
			                ventanaBatalla.dispose(); // Cerrar la ventana de batalla
			            }
			        });

				    
				}

	            
				public void createEnemyArmy(Civilization civilization) {
	            	
	            	
	            	
	            	
	            	
	    	 	    
	    	 	    while (civilization.getEnemyFood() >= FOOD_COST_SPEARMAN && civilization.getEnemyWood() >= WOOD_COST_SWORDSMAN && civilization.getEnemyIron() >= IRON_COST_SWORDSMAN) {
	    	 		   
	    	 	    	Random random = new Random();
		    	 	    
		    	 	    // Generar un número aleatorio entre 0 y 99
		    	 	    int randomNumber = random.nextInt(100);
		    	 	    
						  if (randomNumber < 35 && checkResourceAvailability(FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN, IRON_COST_SWORDSMAN)) {
							  	// Crear Swordsman
								Swordsman swordsman = new Swordsman();
								swordsman.setId_civi(2);
								civilization.getEnemyArmy().get(0).add(swordsman);
							
								// Deduct resources
								civilization.setEnemyFood(civilization.getEnemyFood() -FOOD_COST_SWORDSMAN) ;
								civilization.setEnemyWood(civilization.getEnemyWood()  -WOOD_COST_SWORDSMAN);
								 civilization.setEnemyIron(civilization.getEnemyIron()  -IRON_COST_SWORDSMAN);
						    
						  } else if (randomNumber < 60 && checkResourceAvailability(FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN, IRON_COST_SPEARMAN)) {
								Spearman spearman = new Spearman();
							    spearman.setId_civi(2);
							    civilization.getEnemyArmy().get(1).add(spearman);
							
							    // Deduct resources
							    civilization.setEnemyFood(civilization.getEnemyFood() -FOOD_COST_SPEARMAN) ;
							    civilization.setEnemyWood(civilization.getEnemyWood()  -WOOD_COST_SPEARMAN);
							    civilization.setEnemyIron(civilization.getEnemyIron()  -IRON_COST_SPEARMAN);
							
						  } else if (randomNumber < 80 && checkResourceAvailability(FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW, IRON_COST_CROSSBOW)) {
								 // Crear Crossbow
								Crossbow crossbow = new Crossbow();
								crossbow.setId_civi(2);
								civilization.getEnemyArmy().get(2).add(crossbow);
								
								// Deduct resources
								 civilization.setEnemyFood(civilization.getEnemyFood() -FOOD_COST_CROSSBOW) ;
								 civilization.setEnemyWood(civilization.getEnemyWood()  -WOOD_COST_CROSSBOW);
							   
								 civilization.setEnemyIron(civilization.getEnemyIron()  -IRON_COST_CROSSBOW);
						    
						  } else if (randomNumber < 100 && checkResourceAvailability(FOOD_COST_CANNON, WOOD_COST_CANNON, IRON_COST_CANNON)) {
								// Crear Cannon
								Cannon cannon = new Cannon();
								cannon.setId_civi(2);
								civilization.getEnemyArmy().get(3).add(cannon);
								
								// Deduct resources
								 civilization.setEnemyFood(civilization.getEnemyFood() -FOOD_COST_CANNON) ;
								 civilization.setEnemyWood(civilization.getEnemyWood()  -WOOD_COST_CANNON);
								 civilization.setEnemyIron(civilization.getEnemyIron()  -IRON_COST_CANNON);
								
						  }
		    	 		   
	    	 	   }
	    	 	    
	    	 	   
	    	 	}

	    	 	private boolean checkResourceAvailability(int foodCost, int woodCost, int ironCost) {
	    	 	    return civilization.getEnemyFood() >= foodCost && civilization.getEnemyWood() >= woodCost && civilization.getEnemyIron() >= ironCost;
	    	 	}
	    		
	    		public void viewThreat() {
	    		    System.out.println("NEW THREAT COMING");
	    		
	    		    int swordsmanCount = 0;
	    		    int spearmanCount = 0;
	    		    int crossbowCount = 0;
	    		    int cannonCount = 0;
	    		
	    		    // Recorrer civiArmy
	    		    for (ArrayList<MilitaryUnit> array : civilization.getEnemyArmy()) {
	    		        for (MilitaryUnit unit : array) {
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
	                BBDD carga = new BBDD();
	                carga.guardarJuego(civilization);
	            }
	            
	            
	            private void generarRecursosEnemigo() {
	            	
	                System.out.println("El enemigo ha generado recursos...");
	                civilization.setEnemyFood((int) (civilization.getEnemyFood() + FOOD_BASE_ENEMY_ARMY + (FOOD_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE))); 
	                civilization.setEnemyWood((int) (civilization.getEnemyWood() + WOOD_BASE_ENEMY_ARMY + (WOOD_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE)));
	                civilization.setEnemyIron((int) (civilization.getEnemyIron() + IRON_BASE_ENEMY_ARMY + (IRON_BASE_ENEMY_ARMY * ENEMY_FLEET_INCREASE)));
	             
				    
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
	    
	    private void buildCentralGame(Civilization civilization) {
			
	    	// Parametros layout
	    	centralGame.setLayout(new BorderLayout());
	        centralGame.setOpaque(false);
	        
	        // Crear un JLayeredPane para superponer componentes
	        JLayeredPane layeredPane = new JLayeredPane();
	        centralGame.add(layeredPane, BorderLayout.CENTER);

	        // Crear un JPanel para los JLabels
	        JPanel infoPanel = new JPanel();
	        infoPanel.setOpaque(true);
	        infoPanel.setVisible(false);
	        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Establecer el layout a BoxLayout con orientación vertical

	        // Lugar y Tamaño del panel
	        infoPanel.setBounds( 375, 50, 300, 50);


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
	        
//	        Edificios
	        
	        // Carpinteria
	        try {
	            // Intenta cargar la imagen
	            ImageIcon originalIcon = new ImageIcon("Civilization/src/layouts/resources/carpinteria.png");
	            
	            // Escalar la imagen
	            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
	            ImageIcon scaledIcon = new ImageIcon(scaledImage);

	            // Crear el JLabel con la imagen escalada
	            JLabel imageLabel = new JLabel(scaledIcon);
		        // Lugar y Tamaño del panel
	            imageLabel.setBounds(570, 390, 300, 150);
	            
	            imageLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseEntered(MouseEvent e) {
	        	        infoPanel.setVisible(true);
	                    buildingLabel.setText("Carpentry");
	                    detailLabel.setText("Level: "+civilization.getCarpentry());
	                }

	                @Override
	                public void mouseExited(MouseEvent e) {
	        	        infoPanel.setVisible(false);

	                }
	            });
	            
	            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
	        } catch (Exception e) {
	            // En caso de error, muestra un mensaje de error
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error de carga de imagen", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        // Farm
	        try {
	            // Intenta cargar la imagen
	            ImageIcon originalIcon = new ImageIcon("Civilization/src/layouts/resources/farm.png");
	            
	            // Escalar la imagen
	            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	            ImageIcon scaledIcon = new ImageIcon(scaledImage);

	            // Crear el JLabel con la imagen escalada
	            JLabel imageLabel = new JLabel(scaledIcon);
		        // Lugar y Tamaño del panel
	            imageLabel.setBounds(750, 300, 300, 200);
	            
	            
	            
	            imageLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseEntered(MouseEvent e) {
	        	        infoPanel.setVisible(true);
	                    buildingLabel.setText("Farm");
	                    detailLabel.setText("Level: "+civilization.getFarm());
	                }

	                @Override
	                public void mouseExited(MouseEvent e) {
	        	        infoPanel.setVisible(false);

	                }
	            });

	            
	            
	            
	            
	            
	            
	            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
	        } catch (Exception e) {
	            // En caso de error, muestra un mensaje de error
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error de carga de imagen", JOptionPane.ERROR_MESSAGE);
	        }
	        


	        
	        // Herreria
	        try {
	            // Intenta cargar la imagen
	            ImageIcon originalIcon = new ImageIcon("Civilization/src/layouts/resources/herreria.png");
	            
	            // Escalar la imagen
	            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 190, Image.SCALE_SMOOTH);
	            ImageIcon scaledIcon = new ImageIcon(scaledImage);

	            // Crear el JLabel con la imagen escalada
	            JLabel imageLabel = new JLabel(scaledIcon);
		        // Lugar y Tamaño del panel
	            imageLabel.setBounds(170, 360, 300, 190);
	            
	            
	            imageLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseEntered(MouseEvent e) {
	        	        infoPanel.setVisible(true);
	                    buildingLabel.setText("Blacksmith");
	                    detailLabel.setText("Level: "+civilization.getSmithy());
	                }

	                @Override
	                public void mouseExited(MouseEvent e) {
	        	        infoPanel.setVisible(false);

	                }
	            });
	            
	            
	            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
	        } catch (Exception e) {
	            // En caso de error, muestra un mensaje de error
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error de carga de imagen", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        // Torre de magos
	        try {
	            // Intenta cargar la imagen
	            ImageIcon originalIcon = new ImageIcon("Civilization/src/layouts/resources/torre.png");
	            
	            // Escalar la imagen
	            Image scaledImage = originalIcon.getImage().getScaledInstance(240, 370, Image.SCALE_SMOOTH);
	            ImageIcon scaledIcon = new ImageIcon(scaledImage);

	            // Crear el JLabel con la imagen escalada
	            JLabel imageLabel = new JLabel(scaledIcon);
		        // Lugar y Tamaño del panel
	            imageLabel.setBounds(0, 140, 240, 370);
	            
	            imageLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseEntered(MouseEvent e) {
	        	        infoPanel.setVisible(true);
	                    buildingLabel.setText("Magic Tower");
	                    detailLabel.setText("Level: "+civilization.getMagicTower());
	                }

	                @Override
	                public void mouseExited(MouseEvent e) {
	        	        infoPanel.setVisible(false);

	                }
	            });
	            
	            
	            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
	        } catch (Exception e) {
	            // En caso de error, muestra un mensaje de error
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error de carga de imagen", JOptionPane.ERROR_MESSAGE);
	        }
	        // Iglesia
	        try {
	            // Intenta cargar la imagen
	            ImageIcon originalIcon = new ImageIcon("Civilization/src/layouts/resources/iglesia.png");
	            
	            // Escalar la imagen
	            Image scaledImage = originalIcon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH);
	            ImageIcon scaledIcon = new ImageIcon(scaledImage);

	            // Crear el JLabel con la imagen escalada
	            JLabel imageLabel = new JLabel(scaledIcon);
		        // Lugar y Tamaño del panel
	            imageLabel.setBounds(350, 250, 350, 250);
	            
	            imageLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseEntered(MouseEvent e) {
	        	        infoPanel.setVisible(true);
	                    buildingLabel.setText("Church");
	                    detailLabel.setText("Level: "+civilization.getChurch());
	                }

	                @Override
	                public void mouseExited(MouseEvent e) {
	        	        infoPanel.setVisible(false);

	                }
	            });
	            
	            
	            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
	        } catch (Exception e) {
	            // En caso de error, muestra un mensaje de error
	            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error de carga de imagen", JOptionPane.ERROR_MESSAGE);
	        }






	        
	        


	        
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
	            icon = new ImageIcon("Civilization/src/layouts/resources/"+newItemImage+".png");
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
	            foodIcon = new ImageIcon("Civilization/src/layouts/resources/food.png");
	            woodIcon = new ImageIcon("Civilization/src/layouts/resources/wood.png");
	            ironIcon = new ImageIcon("Civilization/src/layouts/resources/iron.png");
	            manaIcon = new ImageIcon("Civilization/src/layouts/resources/mana.png");
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
	                
	            } else if (actionCommand.equals("newTechDefense")) {
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

	                        } else  {
	                            exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyAttack()+1) + " and 30.");
	            	            createButton.setEnabled(true);

	                        }
	                    }

	                    if (sourceButton.equals("newTechDefense")) {
	                        if (spinnerValue <= civilization.getTechnologyDefense()) {
	                        	exceptionMessage.setText("Please enter a higher level.");
	            	            createButton.setEnabled(false);

	                        } else {
	                            exceptionMessage.setText("Please enter a number between "+ (civilization.getTechnologyDefense()+1) +" and 30.");
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
//				                    updateInfoCivilization(civilization);
			                        System.out.println("New Farm was created");


								} catch (ResourceException e1) {
									// TODO Auto-generated catch block
								    System.out.println(e1.getMessage());
								}
		

	                    } else if (sourceButton.equals("newCarpentry")) {
	                        try {
								civilization.newCarpentry(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Carpentry was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newBlacksmith")) {
	                    	try {
								civilization.newSmithy(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Blacksmith was created");

							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newMagicTower")) {
	                    	try {
								civilization.newMagicTower(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Magic Tower was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newChurch")) {
	                    	try {
								civilization.newChurch(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Church button was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newSwordsman")) {
	                    	try {
								civilization.newSwordsman(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Swordsman was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newSpearman")) {
	                    	try {
								civilization.newSpearman(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Spearman was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCrossbow")) {
	                    	try {
								civilization.newCrossbow(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Crossbow was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCannon")) {
	                    	try {
								civilization.newCannon(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Cannon was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newArrowTower")) {
	                    	try {
								civilization.newArrowTower(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Arrow Tower was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newCatapult")) {
	                    	try {
								civilization.newCatapult(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Catapult was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newRocketLauncher")) {
	                    	try {
								civilization.newRocketLauncher(numeroCreate);
//			                    updateInfoCivilization(civilization);
		                        System.out.println("New Rocket Launcher was created");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}
	                        
	                    } else if (sourceButton.equals("newMagician")) {
	                    	try {
								civilization.newMagician(numeroCreate);
//			                    updateInfoCivilization(civilization);
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
//			                    updateInfoCivilization(civilization);
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
//			                    updateInfoCivilization(civilization);
		                        System.out.println("Attack tech was upgraded");


							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}

	                        
	                    } else if (sourceButton.equals("newTechDefense")) {
	                    	try {
								civilization.upgradeTechnologyDefense();
//			                    updateInfoCivilization(civilization);
		                        System.out.println("Defense tech was upgraded");

							} catch (ResourceException e1) {
								// TODO Auto-generated catch block
							    System.out.println(e1.getMessage());
							}

	                    	
	                    }
	                    
	                    updateInfoCivilization(civilization);

	                    System.out.println(civilization.getArmy());
	                    
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

	        // JPanel Main
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
	    
        

	    // Funciones rigthFrame
	    private void showUnitInfo(String unitName, Civilization civilization) {
	    	// JPanel
	        JPanel unitInfoPanel = new JPanel(new BorderLayout());
	        JPanel showInfoJPanel =  new JPanel();
	        
	        
	        // Body
	        switch(unitName) {
	        	case "Swordsman":
	        		
	        		JLabel unitInfoLabel = new JLabel("Swordsmen Info");
	    	        unitInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior
	    	        unitInfoLabel.setFont(unitInfoLabel.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente

	    	        
	        		ArrayList<MilitaryUnit> swordsmanArmy = civilization.getArmy().get(0);

	        		// No hay unidades
	        		if (swordsmanArmy.isEmpty()) {
	        			JLabel emptyLabel = new JLabel("You don't have any swordsman right now.");
	        		    showInfoJPanel.add(emptyLabel);
	        		} else {
	        		    // Mostrar unidades
	        			String[] columnas = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	        	        Object[][] datos = new Object[swordsmanArmy.size()][columnas.length];

	        	        // Llenar los datos de la tabla con la información de los Swordsmen
	        	        for (int i = 0; i < swordsmanArmy.size(); i++) {
	        	            Swordsman swordman = (Swordsman) swordsmanArmy.get(i);
	        	            datos[i][0] = i+1;
	        	            datos[i][1] = swordman.getInitialArmor();
	        	            datos[i][2] = swordman.attack();
	        	            datos[i][3] = swordman.getExperience();
	        	            datos[i][4] = swordman.isSanctified();
	        	        }

	        	        // Tabla
	        	        JTable tabla = new JTable(datos, columnas) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false; // Hace que todas las celdas no sean editables
	        	            }
	        	            
	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false; // Hace que las celdas no sean seleccionables
	        	            }
	        	        };
	        	      
	        	        
	        	        JScrollPane scrollPane = new JScrollPane(tabla);
	        	        scrollPane.setPreferredSize(new Dimension(370, 500));



	        	        // Agregar la tabla al panel de información
	        	        showInfoJPanel.add(scrollPane);
	        			
	        			
	        		}
	        		
	        		 // Añadir contenido
	    	        unitInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	        unitInfoPanel.add(unitInfoLabel, BorderLayout.NORTH);
	    	        
	        		break;


	        	case "Spearman":
	        	    JLabel unitInfoLabelSpearman = new JLabel("Spearman Info");
	        	    unitInfoLabelSpearman.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelSpearman.setFont(unitInfoLabelSpearman.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> spearmanArmy = civilization.getArmy().get(1); 

	        	    if (spearmanArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any spearman right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasSpearman = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	        	        Object[][] datosSpearman = new Object[spearmanArmy.size()][columnasSpearman.length];

	        	        for (int i = 0; i < spearmanArmy.size(); i++) {
	        	            Spearman spearman = (Spearman) spearmanArmy.get(i);
	        	            datosSpearman[i][0] = i + 1;
	        	            datosSpearman[i][1] = spearman.getInitialArmor();
	        	            datosSpearman[i][2] = spearman.attack();
	        	            datosSpearman[i][3] = spearman.getExperience();
	        	            datosSpearman[i][4] = spearman.isSanctified();
	        	        }

	        	        JTable tablaSpearman = new JTable(datosSpearman, columnasSpearman) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneSpearman = new JScrollPane(tablaSpearman);
	        	        scrollPaneSpearman.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneSpearman);
	        	    }

	        	    unitInfoLabelSpearman.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelSpearman, BorderLayout.NORTH);

	        	    break;
	        	    
	        	    
	        	case "Crossbow":
	        	    JLabel unitInfoLabelCrossbow = new JLabel("Crossbow Info");
	        	    unitInfoLabelCrossbow.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelCrossbow.setFont(unitInfoLabelCrossbow.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> crossbowArmy = civilization.getArmy().get(2); 

	        	    if (crossbowArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any crossbow right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasCrossbow = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	        	        Object[][] datosCrossbow = new Object[crossbowArmy.size()][columnasCrossbow.length];

	        	        for (int i = 0; i < crossbowArmy.size(); i++) {
	        	            Crossbow crossbow = (Crossbow) crossbowArmy.get(i);
	        	            datosCrossbow[i][0] = i + 1;
	        	            datosCrossbow[i][1] = crossbow.getInitialArmor();
	        	            datosCrossbow[i][2] = crossbow.attack();
	        	            datosCrossbow[i][3] = crossbow.getExperience();
	        	            datosCrossbow[i][4] = crossbow.isSanctified();
	        	        }

	        	        JTable tablaCrossbow = new JTable(datosCrossbow, columnasCrossbow) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneCrossbow = new JScrollPane(tablaCrossbow);
	        	        scrollPaneCrossbow.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneCrossbow);
	        	    }

	        	    unitInfoLabelCrossbow.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelCrossbow, BorderLayout.NORTH);

	        	    break;
	        	    
	        	    
	        	case "Cannon":
	        	    JLabel unitInfoLabelCannon = new JLabel("Cannon Info");
	        	    unitInfoLabelCannon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelCannon.setFont(unitInfoLabelCannon.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> cannonArmy = civilization.getArmy().get(3); 

	        	    if (cannonArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any cannon right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasCannon = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	        	        Object[][] datosCannon = new Object[cannonArmy.size()][columnasCannon.length];

	        	        for (int i = 0; i < cannonArmy.size(); i++) {
	        	            Cannon cannon = (Cannon) cannonArmy.get(i);
	        	            datosCannon[i][0] = i + 1;
	        	            datosCannon[i][1] = cannon.getInitialArmor();
	        	            datosCannon[i][2] = cannon.attack();
	        	            datosCannon[i][3] = cannon.getExperience();
	        	            datosCannon[i][4] = cannon.isSanctified();
	        	        }

	        	        JTable tablaCannon = new JTable(datosCannon, columnasCannon) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneCannon = new JScrollPane(tablaCannon);
	        	        scrollPaneCannon.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneCannon);
	        	    }

	        	    unitInfoLabelCannon.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelCannon, BorderLayout.NORTH);

	        	    break;
	        	    
	        	    
	        	case "Arrow Tower":
	        		JLabel unitInfoLabelArrowTower = new JLabel("Arrow Tower Info");
	        		unitInfoLabelArrowTower.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        		unitInfoLabelArrowTower.setFont(unitInfoLabelArrowTower.getFont().deriveFont(Font.BOLD, 16));

	        		ArrayList<MilitaryUnit> arrowTowerArmy = civilization.getArmy().get(4); 

	        		if (arrowTowerArmy.isEmpty()) {
	        		    JLabel emptyLabel = new JLabel("You don't have any arrow towers right now.");
	        		    showInfoJPanel.add(emptyLabel);
	        		} else {
	        		    String[] columnasArrowTower = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	        		    Object[][] datosArrowTower = new Object[arrowTowerArmy.size()][columnasArrowTower.length];

	        		    for (int i = 0; i < arrowTowerArmy.size(); i++) {
	        		        ArrowTower arrowTower = (ArrowTower) arrowTowerArmy.get(i);
	        		        datosArrowTower[i][0] = i + 1;
	        		        datosArrowTower[i][1] = arrowTower.getInitialArmor();
	        		        datosArrowTower[i][2] = arrowTower.attack();
	        		        datosArrowTower[i][3] = arrowTower.getExperience();
	        		        datosArrowTower[i][4] = arrowTower.isSanctified();
	        		    }

	        		    JTable tablaArrowTower = new JTable(datosArrowTower, columnasArrowTower) {
	        		        @Override
	        		        public boolean isCellEditable(int row, int column) {
	        		            return false;
	        		        }

	        		        @Override
	        		        public boolean isCellSelected(int row, int column) {
	        		            return false;
	        		        }
	        		    };

	        		    JScrollPane scrollPaneArrowTower = new JScrollPane(tablaArrowTower);
	        		    scrollPaneArrowTower.setPreferredSize(new Dimension(370, 500));

	        		    showInfoJPanel.add(scrollPaneArrowTower);
	        		}

	        		unitInfoLabelArrowTower.setHorizontalAlignment(SwingConstants.CENTER);
	        		unitInfoPanel.add(unitInfoLabelArrowTower, BorderLayout.NORTH);

	        		break;
	        	    
	        	    
	            case "Catapult":
	            	JLabel unitInfoLabelCatapult = new JLabel("Catapult Info");
	            	unitInfoLabelCatapult.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	            	unitInfoLabelCatapult.setFont(unitInfoLabelCatapult.getFont().deriveFont(Font.BOLD, 16));

	            	ArrayList<MilitaryUnit> catapultArmy = civilization.getArmy().get(5); 

	            	if (catapultArmy.isEmpty()) {
	            	    JLabel emptyLabel = new JLabel("You don't have any catapults right now.");
	            	    showInfoJPanel.add(emptyLabel);
	            	} else {
	            	    String[] columnasCatapult = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
	            	    Object[][] datosCatapult = new Object[catapultArmy.size()][columnasCatapult.length];

	            	    for (int i = 0; i < catapultArmy.size(); i++) {
	            	        Catapult catapult = (Catapult) catapultArmy.get(i);
	            	        datosCatapult[i][0] = i + 1;
	            	        datosCatapult[i][1] = catapult.getInitialArmor();
	            	        datosCatapult[i][2] = catapult.attack();
	            	        datosCatapult[i][3] = catapult.getExperience();
	            	        datosCatapult[i][4] = catapult.isSanctified();
	            	    }

	            	    JTable tablaCatapult = new JTable(datosCatapult, columnasCatapult) {
	            	        @Override
	            	        public boolean isCellEditable(int row, int column) {
	            	            return false;
	            	        }

	            	        @Override
	            	        public boolean isCellSelected(int row, int column) {
	            	            return false;
	            	        }
	            	    };

	            	    JScrollPane scrollPaneCatapult = new JScrollPane(tablaCatapult);
	            	    scrollPaneCatapult.setPreferredSize(new Dimension(370, 500));

	            	    showInfoJPanel.add(scrollPaneCatapult);
	            	}

	            	unitInfoLabelCatapult.setHorizontalAlignment(SwingConstants.CENTER);
	            	unitInfoPanel.add(unitInfoLabelCatapult, BorderLayout.NORTH);

	            	break;
	            	
	            	
		            case "Rocket Launcher":
		            	JLabel unitInfoLabelRocketLauncher = new JLabel("Rocket Launcher Info");
		            	unitInfoLabelRocketLauncher.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		            	unitInfoLabelRocketLauncher.setFont(unitInfoLabelRocketLauncher.getFont().deriveFont(Font.BOLD, 16));
	
		            	ArrayList<MilitaryUnit> rocketLauncherArmy = civilization.getArmy().get(6); 
	
		            	if (rocketLauncherArmy.isEmpty()) {
		            	    JLabel emptyLabel = new JLabel("You don't have any rocket launchers right now.");
		            	    showInfoJPanel.add(emptyLabel);
		            	} else {
		            	    String[] columnasRocketLauncher = {"ID", "Armor", "Damage", "Experience", "Sanctified"};
		            	    Object[][] datosRocketLauncher = new Object[rocketLauncherArmy.size()][columnasRocketLauncher.length];
	
		            	    for (int i = 0; i < rocketLauncherArmy.size(); i++) {
		            	        RocketLauncherTower rocketLauncher = (RocketLauncherTower) rocketLauncherArmy.get(i);
		            	        datosRocketLauncher[i][0] = i + 1;
		            	        datosRocketLauncher[i][1] = rocketLauncher.getInitialArmor();
		            	        datosRocketLauncher[i][2] = rocketLauncher.attack();
		            	        datosRocketLauncher[i][3] = rocketLauncher.getExperience();
		            	        datosRocketLauncher[i][4] = rocketLauncher.isSanctified();
		            	    }
	
		            	    JTable tablaRocketLauncher = new JTable(datosRocketLauncher, columnasRocketLauncher) {
		            	        @Override
		            	        public boolean isCellEditable(int row, int column) {
		            	            return false;
		            	        }
	
		            	        @Override
		            	        public boolean isCellSelected(int row, int column) {
		            	            return false;
		            	        }
		            	    };
	
		            	    JScrollPane scrollPaneRocketLauncher = new JScrollPane(tablaRocketLauncher);
		            	    scrollPaneRocketLauncher.setPreferredSize(new Dimension(370, 500));
	
		            	    showInfoJPanel.add(scrollPaneRocketLauncher);
		            	}
	
		            	unitInfoLabelRocketLauncher.setHorizontalAlignment(SwingConstants.CENTER);
		            	unitInfoPanel.add(unitInfoLabelRocketLauncher, BorderLayout.NORTH);
	
		            	break;
	            	
	            	
		            case "Magician":
		                JLabel unitInfoLabelMagician = new JLabel("Magician Info");
		                unitInfoLabelMagician.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		                unitInfoLabelMagician.setFont(unitInfoLabelMagician.getFont().deriveFont(Font.BOLD, 16));

		                ArrayList<MilitaryUnit> magicianArmy = civilization.getArmy().get(7);

		                if (magicianArmy.isEmpty()) {
		                    JLabel emptyLabel = new JLabel("You don't have any magicians right now.");
		                    showInfoJPanel.add(emptyLabel);
		                } else {
		                    String[] columnasMagician = {"ID", "Armor", "Damage", "Experience"};
		                    Object[][] datosMagician = new Object[magicianArmy.size()][columnasMagician.length];

		                    for (int i = 0; i < magicianArmy.size(); i++) {
		                        Magician magician = (Magician) magicianArmy.get(i);
		                        datosMagician[i][0] = i + 1;
		                        datosMagician[i][1] = magician.getInitialArmor();
		                        datosMagician[i][2] = magician.attack();
		                        datosMagician[i][3] = magician.getExperience();
		                    }

		                    JTable tablaMagician = new JTable(datosMagician, columnasMagician) {
		                        @Override
		                        public boolean isCellEditable(int row, int column) {
		                            return false;
		                        }

		                        @Override
		                        public boolean isCellSelected(int row, int column) {
		                            return false;
		                        }
		                    };

		                    JScrollPane scrollPaneMagician = new JScrollPane(tablaMagician);
		                    scrollPaneMagician.setPreferredSize(new Dimension(370, 500));

		                    showInfoJPanel.add(scrollPaneMagician);
		                }

		                unitInfoLabelMagician.setHorizontalAlignment(SwingConstants.CENTER);
		                unitInfoPanel.add(unitInfoLabelMagician, BorderLayout.NORTH);

		                break;
		                
		                
		            case "Priest":
		                JLabel unitInfoLabelPriest = new JLabel("Priest Info");
		                unitInfoLabelPriest.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		                unitInfoLabelPriest.setFont(unitInfoLabelPriest.getFont().deriveFont(Font.BOLD, 16));

		                ArrayList<MilitaryUnit> priestArmy = civilization.getArmy().get(8);

		                if (priestArmy.isEmpty()) {
		                    JLabel emptyLabel = new JLabel("You don't have any priests right now.");
		                    showInfoJPanel.add(emptyLabel);
		                } else {
		                    String[] columnasPriest = {"ID", "Armor", "Damage", "Experience"};
		                    Object[][] datosPriest = new Object[priestArmy.size()][columnasPriest.length];

		                    for (int i = 0; i < priestArmy.size(); i++) {
		                        Priest priest = (Priest) priestArmy.get(i);
		                        datosPriest[i][0] = i + 1;
		                        datosPriest[i][1] = priest.getInitialArmor();
		                        datosPriest[i][2] = priest.attack();
		                        datosPriest[i][3] = priest.getExperience();
		                    }

		                    JTable tablaPriest = new JTable(datosPriest, columnasPriest) {
		                        @Override
		                        public boolean isCellEditable(int row, int column) {
		                            return false;
		                        }

		                        @Override
		                        public boolean isCellSelected(int row, int column) {
		                            return false;
		                        }
		                    };

		                    JScrollPane scrollPanePriest = new JScrollPane(tablaPriest);
		                    scrollPanePriest.setPreferredSize(new Dimension(370, 500));

		                    showInfoJPanel.add(scrollPanePriest);
		                }

		                unitInfoLabelPriest.setHorizontalAlignment(SwingConstants.CENTER);
		                unitInfoPanel.add(unitInfoLabelPriest, BorderLayout.NORTH);

		                break;
	                
	                
	            default:
	                break;
	        }
	        
	       
	        unitInfoPanel.add(showInfoJPanel, BorderLayout.CENTER);


	        // Cambiar el contenido del tabbedPaneRight a unitInfoPanel
	        tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(armyInfo), unitInfoPanel);
	        tabbedPaneRight.revalidate();  // Forzar actualización
	        tabbedPaneRight.repaint();     // Forzar repintado

	        
	        // Go Back
	        JButton backButton = new JButton("Go back");
	        backButton.addActionListener(backEvent -> {
	            // Volver a mostrar el JPanel armyInfo en la pestaña "Army"
	            tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(unitInfoPanel), armyInfo);
	            tabbedPaneRight.revalidate();  // Forzar actualización
	            tabbedPaneRight.repaint();     // Forzar repintado
	        });
	        unitInfoPanel.add(backButton, BorderLayout.SOUTH);

	    }

	    
	    private void showEnemyUnitInfo(String unitName, Civilization civilization) {
	    	// JPanel
	        JPanel unitInfoPanel = new JPanel(new BorderLayout());
	        JPanel showInfoJPanel =  new JPanel();
	        
	        
	        // Body
	        switch(unitName) {
	        	case "Swordsman":
	        		
	        		JLabel unitInfoLabel = new JLabel("Swordsmen Info");
	    	        unitInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior
	    	        unitInfoLabel.setFont(unitInfoLabel.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente


	        		ArrayList<MilitaryUnit> swordsmanArmy = civilization.getEnemyArmy().get(0);

	        		// No hay unidades
	        		if (swordsmanArmy.isEmpty()) {
	        			JLabel emptyLabel = new JLabel("You don't have any swordsman right now.");
	        		    showInfoJPanel.add(emptyLabel);
	        		} else {
	        		    // Mostrar unidades
	        			String[] columnas = {"ID", "Armor", "Damage", "Experience"};
	        	        Object[][] datos = new Object[swordsmanArmy.size()][columnas.length];

	        	        // Llenar los datos de la tabla con la información de los Swordsmen
	        	        for (int i = 0; i < swordsmanArmy.size(); i++) {
	        	            Swordsman swordman = (Swordsman) swordsmanArmy.get(i);
	        	            datos[i][0] = i+1;
	        	            datos[i][1] = swordman.getInitialArmor();
	        	            datos[i][2] = swordman.attack();
	        	            datos[i][3] = swordman.getExperience();
	        	        }

	        	        // Tabla
	        	        JTable tabla = new JTable(datos, columnas) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false; // Hace que todas las celdas no sean editables
	        	            }
	        	            
	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false; // Hace que las celdas no sean seleccionables
	        	            }
	        	        };
	        	      
	        	        
	        	        JScrollPane scrollPane = new JScrollPane(tabla);
	        	        scrollPane.setPreferredSize(new Dimension(370, 500));



	        	        // Agregar la tabla al panel de información
	        	        showInfoJPanel.add(scrollPane);
	        			
	        			
	        		}
	        		
	        		 // Añadir contenido
	    	        unitInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	        unitInfoPanel.add(unitInfoLabel, BorderLayout.NORTH);
	    	        
	        		break;


	        	case "Spearman":
	        	    JLabel unitInfoLabelSpearman = new JLabel("Spearman Info");
	        	    unitInfoLabelSpearman.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelSpearman.setFont(unitInfoLabelSpearman.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> spearmanArmy = civilization.getEnemyArmy().get(1); 

	        	    if (spearmanArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any spearman right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasSpearman = {"ID", "Armor", "Damage", "Experience"};
	        	        Object[][] datosSpearman = new Object[spearmanArmy.size()][columnasSpearman.length];

	        	        for (int i = 0; i < spearmanArmy.size(); i++) {
	        	            Spearman spearman = (Spearman) spearmanArmy.get(i);
	        	            datosSpearman[i][0] = i + 1;
	        	            datosSpearman[i][1] = spearman.getInitialArmor();
	        	            datosSpearman[i][2] = spearman.attack();
	        	            datosSpearman[i][3] = spearman.getExperience();
	        	        }

	        	        JTable tablaSpearman = new JTable(datosSpearman, columnasSpearman) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneSpearman = new JScrollPane(tablaSpearman);
	        	        scrollPaneSpearman.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneSpearman);
	        	    }

	        	    unitInfoLabelSpearman.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelSpearman, BorderLayout.NORTH);

	        	    break;
	        	    
	        	    
	        	case "Crossbow":
	        	    JLabel unitInfoLabelCrossbow = new JLabel("Crossbow Info");
	        	    unitInfoLabelCrossbow.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelCrossbow.setFont(unitInfoLabelCrossbow.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> crossbowArmy = civilization.getEnemyArmy().get(2); 

	        	    if (crossbowArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any crossbow right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasCrossbow = {"ID", "Armor", "Damage", "Experience"};
	        	        Object[][] datosCrossbow = new Object[crossbowArmy.size()][columnasCrossbow.length];

	        	        for (int i = 0; i < crossbowArmy.size(); i++) {
	        	            Crossbow crossbow = (Crossbow) crossbowArmy.get(i);
	        	            datosCrossbow[i][0] = i + 1;
	        	            datosCrossbow[i][1] = crossbow.getInitialArmor();
	        	            datosCrossbow[i][2] = crossbow.attack();
	        	            datosCrossbow[i][3] = crossbow.getExperience();
	        	        }

	        	        JTable tablaCrossbow = new JTable(datosCrossbow, columnasCrossbow) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneCrossbow = new JScrollPane(tablaCrossbow);
	        	        scrollPaneCrossbow.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneCrossbow);
	        	    }

	        	    unitInfoLabelCrossbow.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelCrossbow, BorderLayout.NORTH);

	        	    break;
	        	    
	        	    
	        	case "Cannon":
	        	    JLabel unitInfoLabelCannon = new JLabel("Cannon Info");
	        	    unitInfoLabelCannon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	        	    unitInfoLabelCannon.setFont(unitInfoLabelCannon.getFont().deriveFont(Font.BOLD, 16));

	        	    ArrayList<MilitaryUnit> cannonArmy =civilization.getEnemyArmy().get(3); 

	        	    if (cannonArmy.isEmpty()) {
	        	        JLabel emptyLabel = new JLabel("You don't have any cannon right now.");
	        	        showInfoJPanel.add(emptyLabel);
	        	    } else {
	        	        String[] columnasCannon = {"ID", "Armor", "Damage", "Experience"};
	        	        Object[][] datosCannon = new Object[cannonArmy.size()][columnasCannon.length];

	        	        for (int i = 0; i < cannonArmy.size(); i++) {
	        	            Cannon cannon = (Cannon) cannonArmy.get(i);
	        	            datosCannon[i][0] = i + 1;
	        	            datosCannon[i][1] = cannon.getInitialArmor();
	        	            datosCannon[i][2] = cannon.attack();
	        	            datosCannon[i][3] = cannon.getExperience();
	        	        }

	        	        JTable tablaCannon = new JTable(datosCannon, columnasCannon) {
	        	            @Override
	        	            public boolean isCellEditable(int row, int column) {
	        	                return false;
	        	            }

	        	            @Override
	        	            public boolean isCellSelected(int row, int column) {
	        	                return false;
	        	            }
	        	        };

	        	        JScrollPane scrollPaneCannon = new JScrollPane(tablaCannon);
	        	        scrollPaneCannon.setPreferredSize(new Dimension(370, 500));

	        	        showInfoJPanel.add(scrollPaneCannon);
	        	    }

	        	    unitInfoLabelCannon.setHorizontalAlignment(SwingConstants.CENTER);
	        	    unitInfoPanel.add(unitInfoLabelCannon, BorderLayout.NORTH);

	        	    break;

	                
	            default:
	                break;
	        }
	        
	       
	        unitInfoPanel.add(showInfoJPanel, BorderLayout.CENTER);


	        // Cambiar el contenido del tabbedPaneRight a unitInfoPanel
	        tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(enemyInfo), unitInfoPanel);
	        tabbedPaneRight.revalidate();  // Forzar actualización
	        tabbedPaneRight.repaint();     // Forzar repintado

	        
	        // Go Back
	        JButton backButton = new JButton("Go back");
	        backButton.addActionListener(backEvent -> {
	            // Volver a mostrar el JPanel armyInfo en la pestaña "Army"
	            tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(unitInfoPanel), enemyInfo);
	            tabbedPaneRight.revalidate();  // Forzar actualización
	            tabbedPaneRight.repaint();     // Forzar repintado
	        });
	        unitInfoPanel.add(backButton, BorderLayout.SOUTH);

	    }
	    


	    
	    // Parte derecha -> Stats
	    private void buildRightFrame(Civilization civilization) {
	    	
	    	
	    	tabbedPaneRight = new JTabbedPane();
	        

	    	contenedorRightJPanel = new JPanel();
	    	contenedorRightJPanel.setLayout(new BorderLayout());
	    	contenedorRightJPanel.setOpaque(false);
	    	
	    	
	        rightFrame = new JPanel();
//	    	rightFrame.setOpaque(false);
	    	rightFrame.setPreferredSize(new Dimension(400, 400)); // Establecer un tamaño predeterminado

	    	
	    	// Contador
	    	contador = new JPanel();
	    	contador.setOpaque(false);
	    	contador.setLayout(new GridBagLayout());
	    	gbc_contador = new GridBagConstraints();
	    	labelTimer = new JLabel("TIME UNTIL BATTLE");
	    	labelTimer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior

	    	labelTiempo = new JLabel();
	    	labelTiempo.setFont(new Font("Arial", Font.BOLD, 60)); 

	    	// Agregar un MouseListener al labelTiempo
	    	labelTiempo.addMouseListener(new MouseAdapter() {
	    	    @Override
	    	    public void mouseClicked(MouseEvent e) {
	    	        // Realizar un switch del booleano enPausa
	    	        enPausa = !enPausa; // Cambia el estado de pausa
	    	        
	    	        // Cambiar el color del texto según el nuevo estado de enPausa
	    	        if (enPausa) {
	    	            labelTiempo.setForeground(Color.RED); // Color verde si enPausa es true
	    	        } else {
	    	            labelTiempo.setForeground(Color.BLACK); // Color rojo si enPausa es false
	    	        }
	    	        
	    	        labelTiempo.repaint();
	    	        labelTiempo.revalidate();
	    	    }
	    	});

	    	// Creación del botón "Pausa"
	    	JButton botonPausa = new JButton("Pausa");
	    	botonPausa.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent e) {
	    	        enPausa = !enPausa; // Cambiar el estado de pausa al hacer clic en el botón
	    	        if (enPausa) {
	    	            labelTiempo.setForeground(Color.RED); // Cambiar color a rojo si está en pausa
	    	        } else {
	    	            labelTiempo.setForeground(Color.BLACK); // Cambiar color a negro si no está en pausa
	    	        }
	    	        labelTiempo.repaint();
	    	        labelTiempo.revalidate();
	    	    }
	    	});

	    	// Creación del botón ">30s"
	    	botonMas30s = new JButton(">30s");
	    	

	    	// Establecer márgenes
	    	gbc_contador.insets = new Insets(0, 0, 0, 0); // Margen superior, izquierdo, inferior, derecho

	    	// Establecer restricciones para labelTimer
	    	gbc_contador.gridx = 0; 
	    	gbc_contador.gridy = 0;
	    	gbc_contador.gridwidth = 4; // Ocupar 4 columnas para centrar el labelTimer
	    	gbc_contador.weightx = 1.0; // Establecer weightx a 1.0 para que labelTimer se centre
	    	gbc_contador.anchor = GridBagConstraints.CENTER; // Centrar el label horizontalmente
	    	contador.add(labelTimer, gbc_contador);

	    	// Establecer restricciones para labelTiempo
	    	gbc_contador.gridx = 0; 
	    	gbc_contador.gridy = 1;
	    	gbc_contador.gridwidth = 4; // Ocupar 4 columnas para centrar el labelTiempo
	    	gbc_contador.weightx = 1.0; // Establecer weightx a 1.0 para que labelTiempo se centre
	    	gbc_contador.anchor = GridBagConstraints.CENTER; // Centrar el label horizontalmente
	    	contador.add(labelTiempo, gbc_contador);

	    	// Establecer márgenes y restricciones para los botones
	    	gbc_contador.gridx = 0;
	    	gbc_contador.gridy = 2;
	    	gbc_contador.gridwidth = 2; // Ocupar 2 columnas para cada botón
	    	gbc_contador.weightx = 1.0; // Establecer weightx a 1.0 para centrar los botones
	    	gbc_contador.anchor = GridBagConstraints.CENTER; // Centrar horizontalmente los botones

	    	// Agregar los botones al panel contador
	    	contador.add(botonPausa, gbc_contador);
	    	gbc_contador.gridx = 2;
	    	contador.add(botonMas30s, gbc_contador);

	    	rightFrame.add(contador);


	    	
	        
	        
	    	
	    	
	    	
	    	
	    	
	    	// Crear GridBagLayout Contenido
	    	infoCivilization = new JPanel(new GridBagLayout());
	    	infoCivilization.setOpaque(false);
	    	gbc_info = new GridBagConstraints();
	    	rightFrame.add(infoCivilization);

	    	
	    	marginLeftJPanel = new JPanel();
	    	marginLeftJPanel.setOpaque(false);
	    	marginLeftJPanel.setPreferredSize(new Dimension(50, 0));
	    	
	    	marginRightJPanel = new JPanel();
	    	marginRightJPanel.setOpaque(false);
	    	marginRightJPanel.setPreferredSize(new Dimension(20, 0));

	    	
	    	
//	    	 Army
	    	
	    	// Creación del JPanel armyInfo
	    	armyInfo = new JPanel(new BorderLayout());
	    	
	    	JPanel contentPanelArmy = new JPanel();
	    	contentPanelArmy.setLayout(new BoxLayout(contentPanelArmy, BoxLayout.Y_AXIS)); // Usar BoxLayout para apilamiento vertical

	    	JLabel mensajeArmy = new JLabel("Army");
	    	mensajeArmy.setFont(mensajeArmy.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	mensajeArmy.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Añadir margen superior e inferior
	    	mensajeArmy.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	mensajeArmy.setHorizontalAlignment(SwingConstants.CENTER);
	    	contentPanelArmy.add(mensajeArmy);

	    	JLabel armyInfoText = new JLabel("<html>Here you can see the statistics of each unit, click on <br>the 'Show Info' button to display its information.</html>");
	    	armyInfoText.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0)); // Añadir margen superior e inferior
	    	armyInfoText.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	armyInfoText.setHorizontalAlignment(SwingConstants.CENTER);
	    	contentPanelArmy.add(armyInfoText);
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	

	    	// Nombres de las imágenes y etiquetas
	    	String[] unitNames = {
	    	    "Swordsman", "Spearman", "Crossbow",
	    	    "Cannon", "Arrow Tower", "Catapult",
	    	    "Rocket Launcher", "Magician", "Priest"
	    	};

	    	for (String unitName : unitNames) {
	    	    JPanel unitPanel = new JPanel(new BorderLayout());
	    	    
	    	    // Crear la etiqueta con el nombre de la unidad
	    	    JLabel unitLabel = new JLabel(unitName);
	    	    unitPanel.add(unitLabel, BorderLayout.CENTER);
	    	    
	    	    // Crear el botón "Show Info"
	    	    JButton showInfoButton = new JButton("Show Info");
	    	    
	    	    showInfoButton.addActionListener(e -> showUnitInfo(unitName, civilization));

	
	    	    unitPanel.add(showInfoButton, BorderLayout.EAST);
	    	    contentPanelArmy.add(unitPanel);
	    	}

	    	// Agregar el panel de contenido al JPanel armyInfo
	    	armyInfo.add(contentPanelArmy, BorderLayout.NORTH);
	    	// Agregar el panel de contenido al JPanel armyInfo
	    	armyInfo.add(contentPanelArmy, BorderLayout.CENTER);


	    	// Agregar márgenes laterales
	    	armyInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));
	    	
	    	



	    	
	    	
	    	

	    	
	    	
	    	
	    	
	    	
	    	
	    	


	    	// Tech
	    	techInfo = new JPanel();
	    	techInfo.setLayout(new BorderLayout());
	    	
	    	// Titulo
	    	JPanel techtitle = new JPanel();
	    	techtitle.setLayout(new BoxLayout(techtitle, BoxLayout.Y_AXIS)); // Usar BoxLayout para apilamiento vertical

	    	JLabel mensajeTech = new JLabel("Technologies");
	    	mensajeTech.setFont(mensajeTech.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	mensajeTech.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Añadir margen superior e inferior
	    	mensajeTech.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	mensajeTech.setHorizontalAlignment(SwingConstants.CENTER);
	    	techtitle.add(mensajeTech);

	    	JLabel techInfoText = new JLabel("<html>Here you can see the statistics of each technology, click on <br>the button to display its information.</html>");
	    	techInfoText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Añadir margen superior e inferior
	    	techInfoText.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	techInfoText.setHorizontalAlignment(SwingConstants.CENTER);
	    	techtitle.add(techInfoText);


	    	
	    	// Botones
	    	JPanel panelBotones = new JPanel();
	    	panelBotones.setLayout(new GridBagLayout()); // Usar GridBagLayout para centrado vertical y horizontal
	    	GridBagConstraints gbc = new GridBagConstraints();
	    	gbc.gridx = 0;
	    	gbc.gridy = GridBagConstraints.RELATIVE;
	    	gbc.insets = new Insets(10, 10, 10, 10); // Márgenes exteriores

	    	JButton buttonTechAttack = new JButton("Attack Technology");
	    	JButton buttonTechDefense = new JButton("Defense Technology");
	    	// Añadir botones al panel con GridBagConstraints
	    	panelBotones.add(buttonTechAttack, gbc);
	    	panelBotones.add(buttonTechDefense, gbc);


	    	
	    	// Ataque
	    	JPanel techAttackPanel = new JPanel(new BorderLayout());
	    	JLabel attackDetails = new JLabel("Attack Technology Information");
	    	attackDetails.setFont(attackDetails.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	attackDetails.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior
	    	attackDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	attackDetails.setHorizontalAlignment(SwingConstants.CENTER);

	    	JLabel secondLabel = new JLabel("<html>This table shows the increase in statistics that we have<br> in our army thanks to attack technology.</html>");
	    	secondLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Añadir margen superior e inferior
	    	secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	secondLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    	
	    	
    	    JLabel attacklevelLabel = new JLabel("Attack Technology level: " + civilization.getTechnologyAttack());
    	    attacklevelLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0)); // Añadir margen superior e inferior
	    	attacklevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	attacklevelLabel.setHorizontalAlignment(SwingConstants.CENTER);



	    	// Crear un contenedor para albergar ambos JLabels en la región norte
	    	JPanel labelsPanel = new JPanel(new GridLayout(3, 1)); // GridLayout con 2 filas y 1 columna
	    	labelsPanel.add(attackDetails);
	    	labelsPanel.add(secondLabel);
	    	labelsPanel.add(attacklevelLabel);


	    	techAttackPanel.add(labelsPanel, BorderLayout.NORTH);

	    	// Contenido de Ataque
	    	JPanel techAttackinfoJPanel = new JPanel();
	    	techAttackinfoJPanel.setLayout(new GridBagLayout());
	    	
	    	// Crear las constraints para cada componente
	    	gbc = new GridBagConstraints();
	    	gbc.insets = new Insets(5, 10, 5, 10); // Ajustar el espacio entre filas a 5 píxeles
	    	gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
	    	
	    	// Fila 1: Encabezados
	    	gbc.gridy = 0;
	    	gbc.gridx = 0;
	    	JLabel encabezado1 = new JLabel("<html><b>Unit</b></html>");
	    	JLabel encabezado2 = new JLabel("<html><b>Base Damage</b></html>");
	    	JLabel encabezado3 = new JLabel("<html><b>Increment</b></html>");
	    	JLabel encabezado4 = new JLabel("<html><b>Total</b></html>");

	    	// Add the JLabels to the techAttackinfoJPanel as before
	    	techAttackinfoJPanel.add(encabezado1, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(encabezado2, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(encabezado3, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(encabezado4, gbc);

	    	// Fila 2: Espadachín
	    	gbc.gridy = 1;
	    	gbc.gridx = 0;
	    	JLabel ATechSwordman = new JLabel("Swordsman");
	    	JLabel ATechSwordmanBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_SWORDSMAN));
	    	JLabel ATechSwordmanIncrement = new JLabel("+");
	    	JLabel ATechSwordmanTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechSwordman, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechSwordmanBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechSwordmanIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechSwordmanTotal, gbc);

	    	// Fila 3: Lancero
	    	gbc.gridy = 2;
	    	gbc.gridx = 0;
	    	JLabel ATechLancer = new JLabel("Lancer");
	    	JLabel ATechLancerBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_SPEARMAN));
	    	JLabel ATechLancerIncrement = new JLabel("+");
	    	JLabel ATechLancerTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechLancer, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechLancerBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechLancerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechLancerTotal, gbc);

	    	// Fila 4: Ballesta
	    	gbc.gridy = 3;
	    	gbc.gridx = 0;
	    	JLabel ATechCrossbowman = new JLabel("Crossbowman");
	    	JLabel ATechCrossbowmanBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_CROSSBOW));
	    	JLabel ATechCrossbowmanIncrement = new JLabel("+");
	    	JLabel ATechCrossbowmanTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechCrossbowman, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechCrossbowmanBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechCrossbowmanIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechCrossbowmanTotal, gbc);

	    	// Fila 5: Cañón
	    	gbc.gridy = 4;
	    	gbc.gridx = 0;
	    	JLabel ATechCannon = new JLabel("Cannon");
	    	JLabel ATechCannonBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_CANNON));
	    	JLabel ATechCannonIncrement = new JLabel("+");
	    	JLabel ATechCannonTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechCannon, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechCannonBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechCannonIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechCannonTotal, gbc);

	    	// Fila 6: Torre de Flechas
	    	gbc.gridy = 5;
	    	gbc.gridx = 0;
	    	JLabel ATechArcherTower = new JLabel("Archer Tower");
	    	JLabel ATechArcherTowerBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_ARROWTOWER));
	    	JLabel ATechArcherTowerIncrement = new JLabel("+");
	    	JLabel ATechArcherTowerTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechArcherTower, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechArcherTowerBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechArcherTowerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechArcherTowerTotal, gbc);

	    	// Fila 7: Catapulta
	    	gbc.gridy = 6;
	    	gbc.gridx = 0;
	    	JLabel ATechCatapult = new JLabel("Catapult");
	    	JLabel ATechCatapultBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_CATAPULT));
	    	JLabel ATechCatapultIncrement = new JLabel("+");
	    	JLabel ATechCatapultTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechCatapult, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechCatapultBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechCatapultIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechCatapultTotal, gbc);

	    	// Fila 8: Torre de Lanzacohetes
	    	gbc.gridy = 7;
	    	gbc.gridx = 0;
	    	JLabel ATechRocketTower = new JLabel("Rocket Tower");
	    	JLabel ATechRocketTowerBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_ROCKETLAUNCHERTOWER));
	    	JLabel ATechRocketTowerIncrement = new JLabel("+");
	    	JLabel ATechRocketTowerTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechRocketTower, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechRocketTowerBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechRocketTowerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechRocketTowerTotal, gbc);

	    	// Fila 9: Mago
	    	gbc.gridy = 8;
	    	gbc.gridx = 0;
	    	JLabel ATechMage = new JLabel("Mage");
	    	JLabel ATechMageBaseDamage = new JLabel(Integer.toString(BASE_DAMAGE_MAGICIAN));
	    	JLabel ATechMageIncrement = new JLabel("+");
	    	JLabel ATechMageTotal = new JLabel("");
	    	techAttackinfoJPanel.add(ATechMage, gbc);
	    	gbc.gridx = 1;
	    	techAttackinfoJPanel.add(ATechMageBaseDamage, gbc);
	    	gbc.gridx = 2;
	    	techAttackinfoJPanel.add(ATechMageIncrement, gbc);
	    	gbc.gridx = 3;
	    	techAttackinfoJPanel.add(ATechMageTotal, gbc);



	    	// Añadir la tabla al panel techAttackPanel
	    	techAttackPanel.add(techAttackinfoJPanel, BorderLayout.CENTER);
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Defensa
	    	JPanel techDefensePanel = new JPanel(new BorderLayout());
	    	JLabel defenseDetails = new JLabel("Defense Technology Information");
	    	defenseDetails.setFont(defenseDetails.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	defenseDetails.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior
	    	defenseDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	defenseDetails.setHorizontalAlignment(SwingConstants.CENTER);
	    	
	    	JLabel mensajeDefensa = new JLabel("<html>This table shows the increase in statistics that we have<br> in our army thanks to defense technology.</html>");
	    	mensajeDefensa.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Añadir margen superior e inferior
	    	mensajeDefensa.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	mensajeDefensa.setHorizontalAlignment(SwingConstants.CENTER);
	    	
    	    JLabel defenselevelLabel = new JLabel("Defense Technology level: " + civilization.getTechnologyDefense());
    	    defenselevelLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0)); // Añadir margen superior e inferior
    	    defenselevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	    defenselevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	    
	    	// Crear un contenedor para albergar ambos JLabels en la región norte
	    	JPanel labelsPanelDefensa = new JPanel(new GridLayout(3, 1)); // GridLayout con 2 filas y 1 columna
	    	labelsPanelDefensa.add(defenseDetails);
	    	labelsPanelDefensa.add(mensajeDefensa);
	    	labelsPanelDefensa.add(defenselevelLabel);

	    	techDefensePanel.add(labelsPanelDefensa, BorderLayout.NORTH);
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Contenido Defensa
	    	JPanel techDefenseinfoJPanel = new JPanel();
	    	techDefenseinfoJPanel.setLayout(new GridBagLayout());

	    	// Crear las constraints para cada componente
	    	gbc = new GridBagConstraints();
	    	gbc.insets = new Insets(5, 10, 5, 10); // Ajustar el espacio entre filas a 5 píxeles
	    	gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
	    	
	    	// Fila 1: Encabezados
	    	gbc.gridy = 0;
	    	gbc.gridx = 0;
	    	JLabel encabezado1Defensa = new JLabel("<html><b>Unit</b></html>");
	    	JLabel encabezado2Defensa = new JLabel("<html><b>Base Armor</b></html>");
	    	JLabel encabezado3Defensa = new JLabel("<html><b>Increment</b></html>");
	    	JLabel encabezado4Defensa = new JLabel("<html><b>Total</b></html>");

	    	// Add the JLabels to the techAttackinfoJPanel as before
	    	techDefenseinfoJPanel.add(encabezado1Defensa, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(encabezado2Defensa, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(encabezado3Defensa, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(encabezado4Defensa, gbc);

	    	// Fila 2: Espadachín
	    	gbc.gridy = 1;
	    	gbc.gridx = 0;
	    	JLabel DTechSwordman = new JLabel("Swordsman");
	    	JLabel DTechSwordmanBaseArmor = new JLabel(Integer.toString(ARMOR_SWORDSMAN));
	    	JLabel DTechSwordmanIncrement = new JLabel("+");
	    	JLabel DTechSwordmanTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechSwordman, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechSwordmanBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechSwordmanIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechSwordmanTotal, gbc);

	    	// Fila 3: Lancero
	    	gbc.gridy = 2;
	    	gbc.gridx = 0;
	    	JLabel DTechLancer = new JLabel("Lancer");
	    	JLabel DTechLancerBaseArmor = new JLabel(Integer.toString(ARMOR_SPEARMAN));
	    	JLabel DTechLancerIncrement = new JLabel("+");
	    	JLabel DTechLancerTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechLancer, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechLancerBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechLancerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechLancerTotal, gbc);

	    	// Fila 4: Ballesta
	    	gbc.gridy = 3;
	    	gbc.gridx = 0;
	    	JLabel DTechCrossbowman = new JLabel("Crossbowman");
	    	JLabel DTechCrossbowmanBaseArmor = new JLabel(Integer.toString(ARMOR_CROSSBOW));
	    	JLabel DTechCrossbowmanIncrement = new JLabel("+");
	    	JLabel DTechCrossbowmanTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechCrossbowman, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechCrossbowmanBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechCrossbowmanIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechCrossbowmanTotal, gbc);

	    	// Fila 5: Cañón
	    	gbc.gridy = 4;
	    	gbc.gridx = 0;
	    	JLabel DTechCannon = new JLabel("Cannon");
	    	JLabel DTechCannonBaseArmor = new JLabel(Integer.toString(ARMOR_CANNON));
	    	JLabel DTechCannonIncrement = new JLabel("+");
	    	JLabel DTechCannonTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechCannon, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechCannonBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechCannonIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechCannonTotal, gbc);

	    	// Fila 6: Torre de Flechas
	    	gbc.gridy = 5;
	    	gbc.gridx = 0;
	    	JLabel DTechArcherTower = new JLabel("Archer Tower");
	    	JLabel DTechArcherTowerBaseArmor = new JLabel(Integer.toString(ARMOR_ARROWTOWER));
	    	JLabel DTechArcherTowerIncrement = new JLabel("+50");
	    	JLabel DTechArcherTowerTotal = new JLabel("1050");
	    	techDefenseinfoJPanel.add(DTechArcherTower, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechArcherTowerBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechArcherTowerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechArcherTowerTotal, gbc);

	    	// Fila 7: Catapulta
	    	gbc.gridy = 6;
	    	gbc.gridx = 0;
	    	JLabel DTechCatapult = new JLabel("Catapult");
	    	JLabel DTechCatapultBaseArmor = new JLabel(Integer.toString(ARMOR_CATAPULT));
	    	JLabel DTechCatapultIncrement = new JLabel("+");
	    	JLabel DTechCatapultTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechCatapult, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechCatapultBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechCatapultIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechCatapultTotal, gbc);

	    	// Fila 8: Torre de Lanzacohetes
	    	gbc.gridy = 7;
	    	gbc.gridx = 0;
	    	JLabel DTechRocketTower = new JLabel("Rocket Tower");
	    	JLabel DTechRocketTowerBaseArmor = new JLabel(Integer.toString(ARMOR_ROCKETLAUNCHERTOWER));
	    	JLabel DTechRocketTowerIncrement = new JLabel("+");
	    	JLabel DTechRocketTowerTotal = new JLabel("");
	    	techDefenseinfoJPanel.add(DTechRocketTower, gbc);
	    	gbc.gridx = 1;
	    	techDefenseinfoJPanel.add(DTechRocketTowerBaseArmor, gbc);
	    	gbc.gridx = 2;
	    	techDefenseinfoJPanel.add(DTechRocketTowerIncrement, gbc);
	    	gbc.gridx = 3;
	    	techDefenseinfoJPanel.add(DTechRocketTowerTotal, gbc);



	    	// Añadir la tabla al panel techAttackPanel
	    	techDefensePanel.add(techDefenseinfoJPanel, BorderLayout.CENTER);
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Crear botones de "Go back" y sus ActionListeners para cada panel
	    	JButton techAttackBackButton = new JButton("Go back");
	    	techAttackBackButton.addActionListener(backEvent -> {
	    	    // Volver a mostrar el JPanel techInfo en la pestaña "Tech"
	    	    tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(techAttackPanel), techInfo);
	    	    tabbedPaneRight.revalidate();  // Forzar actualización
	    	    tabbedPaneRight.repaint();     // Forzar repintado
	    	});
	    	techAttackPanel.add(techAttackBackButton, BorderLayout.SOUTH);

	    	JButton techDefenseBackButton = new JButton("Go back");
	    	techDefenseBackButton.addActionListener(backEvent -> {
	    	    // Volver a mostrar el JPanel techInfo en la pestaña "Tech"
	    	    tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(techDefensePanel), techInfo);
	    	    tabbedPaneRight.revalidate();  // Forzar actualización
	    	    tabbedPaneRight.repaint();     // Forzar repintado
	    	});
	    	techDefensePanel.add(techDefenseBackButton, BorderLayout.SOUTH);

	    	// Añadir ActionListeners a los botones
	    	buttonTechAttack.addActionListener(e -> {
	    	    // Cambiar el contenido del tabbedPaneRight a techAttackPanel
	    	    tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(techInfo), techAttackPanel);
	    	    tabbedPaneRight.revalidate();  // Forzar actualización
	    	    tabbedPaneRight.repaint();     // Forzar repintado
	    	});

	    	buttonTechDefense.addActionListener(e -> {
	    	    // Cambiar el contenido del tabbedPaneRight a techDefensePanel
	    	    tabbedPaneRight.setComponentAt(tabbedPaneRight.indexOfComponent(techInfo), techDefensePanel);
	    	    tabbedPaneRight.revalidate();  // Forzar actualización
	    	    tabbedPaneRight.repaint();     // Forzar repintado
	    	});

	    	// Establecer el tamaño preferido de los botones como cuadrados
	    	Dimension buttonSize = new Dimension(200, 200); // Cuadrado 200x200
	    	buttonTechAttack.setPreferredSize(buttonSize);
	    	buttonTechDefense.setPreferredSize(buttonSize);


	    	// Añadir los componentes al panel techInfo
	    	techInfo.add(techtitle, BorderLayout.NORTH);
	    	techInfo.add(panelBotones, BorderLayout.CENTER);


	    	// Añadir el panel techInfo al tabbedPaneRight
	    	tabbedPaneRight.addTab("Tech", techInfo);





	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	




	    	// Enemy Army
	    	enemyInfo = new JPanel();
	    	enemyInfo.setLayout(new BoxLayout(enemyInfo, BoxLayout.Y_AXIS)); // Establecer un BoxLayout en el eje Y

	    	JLabel mensajeEnemyArmy = new JLabel("Enemy Army");
	    	mensajeEnemyArmy.setFont(mensajeEnemyArmy.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	mensajeEnemyArmy.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Añadir margen superior e inferior
	    	mensajeEnemyArmy.setAlignmentX(Component.CENTER_ALIGNMENT);

	    	enemyInfo.add(mensajeEnemyArmy);

	    	// Mensaje
	    	mensajeEnemyArmyJLabel = new JLabel("Enemy forces are regrouping. Come back later.");
	    	mensajeEnemyArmyJLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior
    		mensajeEnemyArmyJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	    enemyInfo.add(mensajeEnemyArmyJLabel);
    	    
    	    // Contenido	    	

	    	// Crear un panel adicional para contener el contenido centrado
	    	contentPanelEnemyArmy = new JPanel(new GridLayout(0, 1));

	    	// Nombres de las imágenes y etiquetas
	    	String[] EnemyUnitNames = {
	    	    "Swordsman", "Spearman", "Crossbow",
	    	    "Cannon"
	    	};

	    	for (String unitName : EnemyUnitNames) {
	    	    EnemyUnitPanel = new JPanel(new BorderLayout());
	    	    
	    	    // Crear la etiqueta con el nombre de la unidad
	    	    JLabel EnemyUnitLabel = new JLabel(unitName);
	    	    EnemyUnitPanel.add(EnemyUnitLabel, BorderLayout.CENTER);
	    	    
	    	    // Crear el botón "Show Info"
	    	    JButton showEnemyInfoButton = new JButton("Show Info");
	    	    
	    	    showEnemyInfoButton.addActionListener(e -> showEnemyUnitInfo(unitName, civilization));

	
	    	    EnemyUnitPanel.add(showEnemyInfoButton, BorderLayout.EAST);
	    	    contentPanelEnemyArmy.add(EnemyUnitPanel);
	    	}


	    	contentPanelEnemyArmy.setVisible(false);
	    	
	    	enemyInfo.add(contentPanelEnemyArmy);
	    	// Agregar márgenes laterales
	    	enemyInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));


	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
//	    	 Battle Info
	    	battleInfo = new JPanel();
	    	battleInfo.setLayout(new BoxLayout(battleInfo, BoxLayout.Y_AXIS)); // Establecer un BoxLayout en el eje Y

	    	JLabel mensajeBattleInfo = new JLabel("Battle Reports");
	    	mensajeBattleInfo.setFont(mensajeBattleInfo.getFont().deriveFont(Font.BOLD, 16)); // Aumentar el tamaño de la fuente
	    	mensajeBattleInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Añadir margen superior e inferior
	    	mensajeBattleInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

	    	battleInfo.add(mensajeBattleInfo);

	    	// Mensaje
	    	mensajeBattleJLabel = new JLabel("<html> From here you can view the details of your latest battles. <br>Only the last 5 battles will be visible.</html>");
	    	mensajeBattleJLabel.setHorizontalAlignment(JLabel.CENTER); // Centrar horizontalmente
	    	mensajeBattleJLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Añadir margen superior e inferior

	    	mensajeBattleJLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar verticalmente
	    	battleInfo.add(mensajeBattleJLabel);
    	    
	    	// Contenido
	    	ArrayList<String> reportes = new ArrayList<>();
	    	// Add some elements to the ArrayList
	    	reportes.add("Reporte 1");
	    	reportes.add("Reporte 2");
	    	reportes.add("Reporte 3");
	    	reportes.add("Reporte 4");
	    	reportes.add("Reporte 5");

	    	int battles = 23; // Cambiar el número de batallas a 0 para simular el escenario sin batallas

	    	battleReportesPanel = new JPanel();
	    	battleReportesPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Establecer un GridLayout con espacio entre los paneles

	    	if (battles == 0) {
	    	    JLabel noBatallasLabel = new JLabel("<html>Don't be impatient, you just need to create<br> your first unit and wait 3 minutes.</html>");
	    	    noBatallasLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente
	    	    noBatallasLabel.setVerticalAlignment(SwingConstants.CENTER); // Centrar verticalmente
	    	    
	    	    battleReportesPanel.add(noBatallasLabel);
	    	    mensajeBattleJLabel.setVisible(false);
	    	    
	    	    
	    	} else {
	    	    mensajeBattleJLabel.setVisible(true);

	    	    JLabel numeroBatallasJLabel = new JLabel("Amount of Battles: " + battles);
	    	    numeroBatallasJLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar horizontalmente
	    	    numeroBatallasJLabel.setVerticalAlignment(SwingConstants.CENTER); // Centrar verticalmente
	    	    battleReportesPanel.add(numeroBatallasJLabel);

	    	    // Obtener las últimas 5 batallas
	    	    int startIndex = Math.max(0, battles - 5);

	    	    for (int i = battles - 1; i >= Math.max(0, battles - 5); i--) {
	    	        int index = i; // Captura final del índice actual
	    	        JPanel reportePanel = new JPanel(new BorderLayout());

	    	        JLabel reporteLabel = new JLabel("Battle: " + (index + 1));
	    	        reportePanel.add(reporteLabel, BorderLayout.CENTER);

	    	        JButton showReportButton = new JButton("Show Report");
	    	        showReportButton.addActionListener(e -> showReportInfo(reportes.get(battles > 4 ? index - (battles - 5) : index))); // Calcular el índice correcto en el ArrayList

	    	        reportePanel.add(showReportButton, BorderLayout.EAST);

	    	        battleReportesPanel.add(reportePanel);
	    	    }

	    	}


	        
	        // Añadir panels en blanco
	        while (battleReportesPanel.getComponentCount() < 9) {
	            JPanel emptyPanel = new JPanel();
	            battleReportesPanel.add(emptyPanel);
	        }

	        battleInfo.add(battleReportesPanel);
	        
	    	// Agregar márgenes laterales
	        battleInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 50, 20));
	        
	        
	        

	        

	        
	
	  
	    	
	    	
	    	


	
	    	
	    	



	    	
 	    	contenedorRightJPanel.add(tabbedPaneRight, BorderLayout.CENTER);
// 	    	contenedorRightJPanel.add(marginLeftJPanel, BorderLayout.WEST);
	    	contenedorRightJPanel.add(marginRightJPanel, BorderLayout.EAST);
	    	
	    	
	        tabbedPaneRight.addTab("Main", rightFrame);
	        tabbedPaneRight.addTab("Army", armyInfo);
	        tabbedPaneRight.addTab("Technology", techInfo);
	        tabbedPaneRight.addTab("Enemy", enemyInfo);
	        tabbedPaneRight.addTab("Battles", battleInfo);


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
	    








	    // Método para mostrar la información del reporte en un JDialog
	    private void showReportInfo(String reporte) {
	        JDialog reportDialog = new JDialog(this, "Report Information", true);
	        reportDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        reportDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);


	        JTextArea reportTextArea = new JTextArea(reporte);
	        reportTextArea.setEditable(false);
	        reportTextArea.setLineWrap(true);
	        reportTextArea.setWrapStyleWord(true);

	        JScrollPane scrollPane = new JScrollPane(reportTextArea);
	        reportDialog.add(scrollPane, BorderLayout.CENTER);
	        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir márgenes de 10px alrededor del JTextArea


	        JButton closeButton = new JButton("Close");
	        closeButton.addActionListener(e -> reportDialog.dispose());
	        reportDialog.add(closeButton, BorderLayout.SOUTH);

	        reportDialog.setSize(400, 300);
	        reportDialog.setLocationRelativeTo(this);
	        reportDialog.setVisible(true);
	    }






		// Parte inferior -> Console output
	    private static void buildBottomFrame(JPanel bottomFrame) {
	        bottomFrame.setOpaque(false);
	        bottomFrame.setPreferredSize(new Dimension(100, 20)); // Establecer un tamaño predeterminado
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
	            backgroundImage = ImageIO.read(new File("Civilization/src/layouts/resources/home_right_background.jpeg"));
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
	            backgroundImage = ImageIO.read(new File("Civilization/src/layouts/resources/home_buttons_background.webp"));
	        } catch (IOException e) {
	            System.err.println("Error al cargar background Menu Left: " + e.getMessage());
	            setBackground(Color.RED);
	        }
	        
	        
	        // Configurar el BoxLayout
	        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	        this.setPreferredSize(new Dimension(600, 100));

	        // Cargar la imagen y redimensionarla
	        originalIcon = new ImageIcon("Civilization/src/layouts/resources/logo.png"); 
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
	            	
	            	// Setter nombre civilizacion
	            	
	            	// Llamar a ventana Juego
                    System.out.println("cargar partida");
                    Civilization civilization = new Civilization();
                    Timer timer = new Timer();
                   
                    BBDD descargadatos = new BBDD();
                    
                    descargadatos.InsertarJuego(civilization);
                    Game resumeGame = new Game(frame, civilization, timer);
                    // Ocultar menu
                    frame.setVisible(false);


                    

	            	
	            	
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
	

	
	class MenuLabels extends JPanel {

		private JLabel welcome, emptySpace, civilizationName, exceptionMessage;
		private JTextField enterCivilizationName;
		
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

	

}


