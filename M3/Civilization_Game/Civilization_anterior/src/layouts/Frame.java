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
import javax.swing.*;
import javax.swing.border.Border;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.io.OutputStream;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class Frame extends JFrame {

	 private MenuLabels menuLabels;
	 private CardLayout cardLayout;
	 private MenuImage menuImage;
	 private Game gameFrame;

	 private JPanel centerPanel;
	 private MenuCredits menuCredits;
	
	public static void main(String[] args) {
		Frame miFrame = new Frame();
	}
	
	
	public Frame() {
        this.setTitle("Civilization");
        this.setSize(1280,720);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // -------------- //
        
        
        
//        // test game
        gameFrame = new Game(this);
        
    
        
        
        
//        // Menu Image
//        menuImage = new MenuImage();
//        cardLayout = new CardLayout();
//        centerPanel = new JPanel(cardLayout);
//        centerPanel.add(menuImage, "MenuImage");
//
//        // New Game
//        menuLabels = new MenuLabels(this);
//        centerPanel.add(menuLabels, "MenuLabels");
//
//        // Credits
//        menuCredits = new MenuCredits(this);
//        centerPanel.add(menuCredits, "MenuCredits");
//
//        this.add(centerPanel, BorderLayout.CENTER);
//
//        // Main Menu
//        this.add(new MenuButtons(this, cardLayout, centerPanel, gameFrame), BorderLayout.WEST);

        // -------------- //
        this.setVisible(true);
        

        
    }
	
	
	class Game extends JFrame {
		
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
	    private String newItemTitle, newItemImage, newItemDialogo, newItemLabel;
	    private String sourceButton;
	    
	    
	    
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
	    private JLabel labelTiempo, labelComida, labelMadera, labelHierro, labelMana;
	    private JLabel labelComidaUnidades, labelMaderaUnidades, labelHierroUnidades, labelManaUnidades;

	    
	    // Consola
	    private JTextArea consoleTextArea;
	    private JTextField commandInputField;
	    private JButton sendButton;
	    private ByteArrayOutputStream outputStream;
	    


	    public Game(JFrame parentFrame) {
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
	        centralGame = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);

	                // Cargar la imagen de fondo
	                try {
	                    backgroundImage = ImageIO.read(new File("./src/layouts/resources/background.jpg"));

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
	        centralGame.setOpaque(false);
	        
	        

	        // Parte superior
	        topFrame = new JPanel();
	        topFrame.setOpaque(false);
	    	topFrame.setPreferredSize(new Dimension(100, 50));
	    	

	        // Parte izquierda
	        leftFrame = new JPanel();
	        leftFrame.setOpaque(false);
	    	leftFrame.setPreferredSize(new Dimension(50, 0));

	        // Parte derecha -> STATS
	        rightFrame = new JPanel();
	        buildRightFrame(rightFrame);

	        // Parte inferior
	        bottomFrame = new JPanel();
	        buildBottomFrame(bottomFrame);
	        
	        
	        
	        
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
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newFarm");
		            }
		        });

	        	// Carpentry
		        newCarpentry.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Carpentry";
		            	newItemImage = "newCarpentry";
		            	newItemDialogo = "<html>A carpentry allows you to increase your Civilization's <br> wood production by 10%.<br><br>The cost to build a carpentry is:</html>";
		            	newItemLabel = "How many Carpentries do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCarpentry");
		            }
		        });
	        	// Blacksmith
		        newBlacksmith.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Blacksmith";
		            	newItemImage = "newBlacksmith";
		            	newItemDialogo = "<html>A blacksmith workshop allows you to increase your Civilization's <br> iron production by 10%.<br><br>The cost to build a blacksmith workshop is:</html>";
		            	newItemLabel = "How many Blacksmith Workshops do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newBlacksmith");
		            }
		        });
	        	// Magic Tower
		        newMagicTower.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Magic Tower";
		            	newItemImage = "newMagicTower";
		            	newItemDialogo = "<html>The magic tower generates +3000 mana each time resources are produced.<br><br>The cost to build a magic tower is:</html>";
		            	newItemLabel = "How many Magic Towers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newMagicTower");
		            }
		        });
	        	// Church
		        newChurch.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Church";
		            	newItemImage = "newChurch";
		            	newItemDialogo = "<html>Each Church will allow you to generate 1 Priest.</html>";		            	
		            	newItemLabel = "How many Churches do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newChurch");
		            }
		        });
	        
//	        Upgrade
				 // Attack Technology
				 // Defense Technology
	        
//	        Units
				 // Swordsman
		        newSwordsman.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Swordsman";
		            	newItemImage = "newSwordsman";
		            	newItemDialogo = "<html>The sharp sword of the Swordsman will cut through <br> your enemies with precision and skill!\n"
		            			+ "</html>";		            	
		            	newItemLabel = "How many Swordsmen do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newSwordsman");
		            }
		        });
				 // Spearman
		        newSpearman.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Spearman";
		            	newItemImage = "newSpearman";
		            	newItemDialogo = "<html>The Spearman is the spear that will pierce through <br> enemy ranks with its sharp tip!\n"
		            			+ "</html>";
		            	newItemLabel = "How many Spearmen do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newSpearman");
		            }
		        });
		         // Crosswob
		        newCrossbow.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Crossbow";
		            	newItemImage = "newCrossbow";
		            	newItemDialogo = "<html>The Crossbow shoots deadly arrows with lethal <br> accuracy from a distance!\n"
		            			+ "</html>";
		            	newItemLabel = "How many Crossbows do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCrossbow");
		            }
		        });
				 // Cannon
		        newCannon.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Cannon";
		            	newItemImage = "newCannon";
		            	newItemDialogo = "<html>The Cannon will unleash a rain of destruction <br> upon your enemies with its powerful shot!\n"
		            			+ "</html>";
		            	newItemLabel = "How many Cannons do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCannon");
		            }
		        });
				 // Arrow Tower
		        newArrowTower.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Arrow Tower";
		            	newItemImage = "newArrowTower";
		            	newItemDialogo = "<html>The Arrow Tower is the silent guardian that rains <br> arrows on invaders mercilessly!\n"
		            			+ "</html>";
		            	newItemLabel = "How many Towers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newArrowTower");
		            }
		        });
				 // Catapult
		        newCatapult.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Catapult";
		            	newItemImage = "newCatapult";
		            	newItemDialogo = "<html>The Catapult will launch massive projectiles to crush <br> enemy defenses with overwhelming force!\n"
		            			+ "</html>";
		            	newItemLabel = "How many Catapults do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newCatapult");
		            }
		        });
				 // Rocket Launcher
		        newRocketLauncher.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Rocket Launcher";
		            	newItemImage = "newRocketLauncher";
		            	newItemDialogo = "<html>The Rocket Launcher will unleash fire and explosions <br> to clear the battlefield with its devastating power!</html>";
		            	newItemLabel = "How many Launchers do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newRocketLauncher");
		            }
		        });
				 // Magician
		        newMagician.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Magician";
		            	newItemImage = "newMagician";
		            	newItemDialogo = "<html>The Magician will conjure arcane spells to unbalance <br> your enemies with mysterious and powerful magic!</html>";
		            	newItemLabel = "How many Magicians do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newMagician");
		            }
		        });
				 // Priest
		        newPriest.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	newItemTitle = "New Priest";
		            	newItemImage = "newPriest";
		            	newItemDialogo = "<html>The Priest is the source of divine healing and protection <br> that will strengthen your troops and heal <br> their wounds in battle!</html>";		            	
		            	newItemLabel = "How many Priests do you want to create?";
		            	createNewObject(newItemTitle, newItemImage, newItemDialogo, newItemLabel, "newPriest");
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
	    
	    // Nueva Construccion o Unidad
	    private void createNewObject(String newItemTitle, String newItemImage, String newItemDialogo, String newItemLabel, String actionCommand) {
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

	            // Coste de recursos para crear una granja
	            gbc.gridy = 1; // Siguiente fila
	            costPanel = new JPanel();
	            costPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

	            // Imágenes de recursos
	            foodIcon = new ImageIcon("./src/layouts/resources/food.png");
	            woodIcon = new ImageIcon("./src/layouts/resources/wood.png");
	            ironIcon = new ImageIcon("./src/layouts/resources/iron.png");
	            manaIcon = new ImageIcon("./src/layouts/resources/mana.png");
	            foodIcon = new ImageIcon(foodIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	            woodIcon = new ImageIcon(woodIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	            ironIcon = new ImageIcon(ironIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	            manaIcon = new ImageIcon(manaIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

	            // Etiquetas con imágenes y texto
	            foodLabel = new JLabel(foodIcon);
	            foodLabel.setText(": X");
	            foodLabel.setHorizontalTextPosition(JLabel.RIGHT);

	            woodLabel = new JLabel(woodIcon);
	            woodLabel.setText(": X");
	            woodLabel.setHorizontalTextPosition(JLabel.RIGHT);

	            ironLabel = new JLabel(ironIcon);
	            ironLabel.setText(": X");
	            ironLabel.setHorizontalTextPosition(JLabel.RIGHT);

	            manaLabel = new JLabel(manaIcon);
	            manaLabel.setText(": X");
	            manaLabel.setHorizontalTextPosition(JLabel.RIGHT);

	            // Agregar etiquetas al costPanel
	            costPanel.add(foodLabel);
	            costPanel.add(woodLabel);
	            costPanel.add(ironLabel);
	            costPanel.add(manaLabel);

	            // Añadir el panel de recursos al mainPanel
	            gbc.gridwidth = 2; // Ocupa 2 columnas
	            mainPanel.add(costPanel, gbc);

	            // Pregunta sobre la cantidad de granjas
	            gbc.gridx = 0;
	            gbc.gridy = 2; // Siguiente fila
	            gbc.gridwidth = 1; // Ocupa 1 columna
	            label = new JLabel(newItemLabel);
	            mainPanel.add(label, gbc);

	            gbc.gridx = 1; // Siguiente columna
	            gbc.gridy = 2; // Mantiene la fila
	            textField = new JTextField(5);
	            textField.setHorizontalAlignment(JTextField.CENTER);
	            mainPanel.add(textField, gbc);

	            // Panel para exception
	            gbc.gridx = 0;
	            gbc.gridy = 4; // Siguiente fila
	            gbc.gridwidth = 2; // Ocupa 2 columnas
	            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
	            exceptionMessage = new JLabel("Please enter a number.");
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
	            createButton.setEnabled(false);
	            buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Para alinear los botones
	            buttonPanel.add(cancelButton);
	            buttonPanel.add(createButton);
	            mainPanel.add(buttonPanel, gbc);
	            
	            
	            
	            // Eventos
	            cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose(); // Cerrar el diálogo
                    }
                });
	            
	            
	            textField.addKeyListener(new KeyAdapter() {
	                @Override
	                public void keyReleased(KeyEvent e) {
	                    String text = textField.getText();
	                    
	                    // Comprobar solo numeros
	                    if (text.isEmpty()) {
	                        exceptionMessage.setText("Please enter a number.");
	        	            createButton.setEnabled(false);
	                    } else if (!text.chars().allMatch(Character::isDigit)) { 
	                        exceptionMessage.setText("Only numbers are allowed!");
	        	            createButton.setEnabled(false);
	                    } else if (text.startsWith("0")) {
	                    	exceptionMessage.setText("Enter a number bigger than 0");
	        	            createButton.setEnabled(false);
	                    } else {
	        	            createButton.setEnabled(true);
	                        exceptionMessage.setText(" ");
	                    }
	                }
	            });
	            
	  
	            createButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    String sourceButton = actionCommand;

	                    if (sourceButton.equals("newFarm")) {
	                        System.out.println("New Farm was created");
	                        
	                    } else if (sourceButton.equals("newCarpentry")) {
	                        System.out.println("New Carpentry was created");
	                        
	                    } else if (sourceButton.equals("newBlacksmith")) {
	                        System.out.println("New Blacksmith was created");
	                        
	                    } else if (sourceButton.equals("newMagicTower")) {
	                        System.out.println("New Magic Tower was created");
	                        
	                    } else if (sourceButton.equals("newChurch")) {
	                        System.out.println("New Church button was created");
	                        
	                    } else if (sourceButton.equals("newSwordsman")) {
	                        System.out.println("New Swordsman was created");
	                        
	                    } else if (sourceButton.equals("newSpearman")) {
	                        System.out.println("New Spearman was created");
	                        
	                    } else if (sourceButton.equals("newCrossbow")) {
	                        System.out.println("New Crossbow was created");
	                        
	                    } else if (sourceButton.equals("newCannon")) {
	                        System.out.println("New Cannon was created");
	                        
	                    } else if (sourceButton.equals("newArrowTower")) {
	                        System.out.println("New Arrow Tower was created");
	                        
	                    } else if (sourceButton.equals("newCatapult")) {
	                        System.out.println("New Catapult was created");
	                        
	                    } else if (sourceButton.equals("newRocketLauncher")) {
	                        System.out.println("New Rocket Launcher was created");
	                        
	                    } else if (sourceButton.equals("newMagician")) {
	                        System.out.println("New Magician was created");
	                        
	                    } else if (sourceButton.equals("newPriest")) {
	                        System.out.println("New Priest was created");
	                    }
	                    
	                    dialog.dispose();
	                }
	            });
	            
	            
	            

	            // Agregar el panel principal al diálogo
	            dialog.add(mainPanel, BorderLayout.CENTER);

	            // Ajustes del JDialog
	            dialog.pack(); 
                dialog.setLocationRelativeTo(null); 
                dialog.setResizable(false); 
                dialog.setVisible(true);
                
                
                
                
                
                
                
                
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
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
	    private void buildRightFrame(JPanel rightFrame) {
//	    	rightFrame.setOpaque(false);
	    	rightFrame.setPreferredSize(new Dimension(400, 200)); // Establecer un tamaño predeterminado

	    	
	    	// Contador
	    	contador = new JPanel();
	    	contador.setOpaque(false);
	    	contador.setLayout(new GridBagLayout());
	    	gbc_contador = new GridBagConstraints();
	    	labelTiempo = new JLabel("00:00");
	    	labelTiempo.setFont(new Font("Arial", Font.PLAIN, 40)); 

	    	// Establecer márgenes
	    	gbc_contador.insets = new Insets(30, 20, 30, 20); // Margen superior, izquierdo, inferior, derecho

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
	    	gbc_info.fill = GridBagConstraints.BOTH; // Expandirse para llenar la celda
	    	gbc_info.insets = new Insets(5, 5, 5, 5); // Márgenes alrededor de cada componente

	    	
	    	// Contenido
	    	
//	    	Recursos
	    	labelRecursos = new JLabel("Recursos");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 0; 
	    	infoCivilization.add(labelRecursos, gbc_info);
	    	
	    	panelRecursos = new JPanel();
	    	panelRecursos.setOpaque(false);
	    	
	    	
	    	// Añadir Comida
	    	imagenComida = iconoComida.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    	iconoComidaRedimensionado = new ImageIcon(imagenComida);

	    	imagenMadera = iconoMadera.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    	iconoMaderaRedimensionado = new ImageIcon(imagenMadera);

	    	imagenHierro = iconoHierro.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    	iconoHierroRedimensionado = new ImageIcon(imagenHierro);

	    	imagenMana = iconoMana.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    	iconoManaRedimensionado = new ImageIcon(imagenMana);
	    	
	    	labelComida = new JLabel(iconoComidaRedimensionado);
	    	panelRecursos.add(labelComida);
	    	labelComidaUnidades = new JLabel("0");
	    	panelRecursos.add(labelComidaUnidades);

	    	labelMadera = new JLabel(iconoMaderaRedimensionado);
	    	panelRecursos.add(labelMadera);
	    	labelMaderaUnidades = new JLabel("0");
	    	panelRecursos.add(labelMaderaUnidades);

	    	labelHierro = new JLabel(iconoHierroRedimensionado);
	    	panelRecursos.add(labelHierro);
	    	labelHierroUnidades = new JLabel("0");
	    	panelRecursos.add(labelHierroUnidades);

	    	labelMana = new JLabel(iconoManaRedimensionado);
	    	panelRecursos.add(labelMana);
	    	labelManaUnidades = new JLabel("0");
	    	panelRecursos.add(labelManaUnidades);

	        
	        gbc_info.gridx = 0; 
	    	gbc_info.gridy = 1;
	        infoCivilization.add(panelRecursos, gbc_info);
	    	
	    	
	    	
	    	
//	    	Edificios
	    	labelEdificios = new JLabel("Edificios");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 5; 
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
	        panelEdificios.add(new JLabel("10"), gbc_building);

	        // Carpentry
	        gbc_building.gridx = 0;
	        gbc_building.gridy = 1;
	        panelEdificios.add(new JLabel("Carpentry"), gbc_building);
	        gbc_building.gridx = 1;
	        panelEdificios.add(new JLabel("20"), gbc_building);

	        // Blacksmith
	        gbc_building.gridx = 0;
	        gbc_building.gridy = 2;
	        panelEdificios.add(new JLabel("Blacksmith"), gbc_building);
	        gbc_building.gridx = 1;
	        panelEdificios.add(new JLabel("30"), gbc_building);

	        // Magic Tower
	        gbc_building.gridx = 2;
	        gbc_building.gridy = 0;
	        panelEdificios.add(new JLabel("Magic Tower"), gbc_building);
	        gbc_building.gridx = 3;
	        panelEdificios.add(new JLabel("40"), gbc_building);

	        // Church
	        gbc_building.gridx = 2;
	        gbc_building.gridy = 1;
	        panelEdificios.add(new JLabel("Church"), gbc_building);
	        gbc_building.gridx = 3;
	        panelEdificios.add(new JLabel("50"), gbc_building);

	        // Añadir la tabla de edificios al panel principal
	        gbc_info.gridy = 6; 
	        infoCivilization.add(panelEdificios, gbc_info);
	    	
	    	

//	    	Unidades
	    	labelUnidades = new JLabel("Unidades");
	    	gbc_info.gridx = 0; 
	    	gbc_info.gridy = 7; 
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
	        panelUnidades.add(new JLabel("10"), gbc_units);
	        
	        // Spearman
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 1;
	        panelUnidades.add(new JLabel("Spearman"), gbc_units);
	        gbc_units.gridx = 1;
	        panelUnidades.add(new JLabel("11"), gbc_units);
	        
	        // Crossbow
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 2;
	        panelUnidades.add(new JLabel("Crossbow"), gbc_units);
	        gbc_units.gridx = 1;
	        panelUnidades.add(new JLabel("12"), gbc_units);
	        
	        // Cannon
	        gbc_units.gridx = 0; 
	        gbc_units.gridy = 3;
	        panelUnidades.add(new JLabel("Cannon"), gbc_units);
	        gbc_units.gridx = 1;
	        panelUnidades.add(new JLabel("13"), gbc_units);
	        
	        // Arrow Tower
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 0; 
	        panelUnidades.add(new JLabel("Arrow Tower"), gbc_units);
	        gbc_units.gridx = 3; 
	        panelUnidades.add(new JLabel("14"), gbc_units);

	        // Catapult
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 1; 
	        panelUnidades.add(new JLabel("Catapult"), gbc_units);
	        gbc_units.gridx = 3; 
	        panelUnidades.add(new JLabel("15"), gbc_units);

	        // Rocket Launcher
	        gbc_units.gridx = 2; 
	        gbc_units.gridy = 2;
	        panelUnidades.add(new JLabel("Rocket Launcher"), gbc_units);
	        gbc_units.gridx = 3;
	        panelUnidades.add(new JLabel("16"), gbc_units);

	        
	        // Magician
	        gbc_units.gridx = 4; // Nueva columna para Magician y Priest
	        gbc_units.gridy = 0;
	        panelUnidades.add(new JLabel("Magician"), gbc_units);
	        gbc_units.gridx = 5;
	        panelUnidades.add(new JLabel("17"), gbc_units);

	        // Priest
	        gbc_units.gridx = 4;
	        gbc_units.gridy = 1;
	        panelUnidades.add(new JLabel("Priest"), gbc_units);
	        gbc_units.gridx = 5;
	        panelUnidades.add(new JLabel("18"), gbc_units);

	        // Añadir la tabla de units al panel principal
	        gbc_info.gridy = 8; 
	        infoCivilization.add(panelUnidades, gbc_info);
	    	

	    	
//	    	Tecnologias
	    	labelTencologias = new JLabel("Tecnologías");
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
	        panelTecnologias.add(new JLabel("Tecnología de ataque"), gbc_tech);
	        gbc_tech.gridx = 1;
	        panelTecnologias.add(new JLabel("Nivel 1"), gbc_tech);
	        
	        // Tecnologías de defensa
	        gbc_tech.gridx = 0; 
	        gbc_tech.gridy = 1;
	        panelTecnologias.add(new JLabel("Tecnologías de defensa"), gbc_tech);
	        gbc_tech.gridx = 1;
	        panelTecnologias.add(new JLabel("Nivel 2"), gbc_tech);
	        
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

	        JPanel customConsole = new JPanel();
	        customConsole.setLayout(new BorderLayout());
	        customConsole.setOpaque(false);
	        customConsole.setBorder(new EmptyBorder(10, 100, 10, 100));

	        JTextArea textArea = new JTextArea(10, 50);
	        textArea.setBackground(Color.BLACK);
	        textArea.setForeground(Color.WHITE);
	        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
	        textArea.setEditable(false);

	        JScrollPane scrollPane = new JScrollPane(textArea);
	        customConsole.add(scrollPane, BorderLayout.CENTER);

	        JTextField commandField = new JTextField(50);
	        commandField.setBackground(Color.BLACK);
	        commandField.setForeground(Color.WHITE);
	        commandField.setFont(new Font("Consolas", Font.PLAIN, 12));
	        commandField.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String command = commandField.getText();
	                textArea.append("> " + command + "\n");
	                // Aquí puedes procesar el comando ingresado
	                commandField.setText("");
	            }
	        });

	        customConsole.add(commandField, BorderLayout.SOUTH);

	        OutputStream outputStream = new OutputStream() {
	            @Override
	            public void write(int b) {
	                SwingUtilities.invokeLater(() -> {
	                    textArea.append(String.valueOf((char) b));
	                    textArea.setCaretPosition(textArea.getDocument().getLength());
	                });
	            }

	            @Override
	            public void write(byte[] b, int off, int len) {
	                SwingUtilities.invokeLater(() -> {
	                    textArea.append(new String(b, off, len));
	                    textArea.setCaretPosition(textArea.getDocument().getLength());
	                });
	            }

	            @Override
	            public void write(byte[] b) {
	                write(b, 0, b.length);
	            }
	        };

	        PrintStream printStream = new PrintStream(outputStream, true);
	        System.setOut(printStream);
	        System.setErr(printStream);

	        bottomFrame.add(customConsole, BorderLayout.CENTER);
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

        
	    public MenuButtons(Frame frame, CardLayout cardLayout, JPanel centerPanel, Game gameFrame) {
	    	
	    	
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
		
		private boolean trueName, trueEra;
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
		
		private boolean trueName, trueEra;
		private JButton createGame, goBack;

		private GridBagConstraints gbc;
		
		private Game gameFrame;
		
		
		
		public MenuLabels(JFrame parentFrame) {
			
//			this.setVisible(false);
			
			trueName = true;
			trueEra = true;
			
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
		            + "<li>The Era in which you start</li>"
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

		    // Etiqueta para Era
		    era = new JLabel("Select Era:");
		    gbc.gridx = 0; // Primera columna
		    gbc.gridy = 3; // Cuarta fila
		    gbc.gridwidth = 1; // Componente ocupa una sola columna
		    this.add(era, gbc); // Agregar etiqueta

		    String[] eras = {
		            "Select your era", // Opción predeterminada para no tener nada seleccionado
		            "Ancient Era (4000 BC ~ 1000 BC)",
		            "Classical Era (1000 BC ~ 500 AD)",
		            "Medieval Era (500 ~ 1350)",
		            "Renaissance Era (1350 ~ 1725)",
		            "Industrial Era (1725 ~ 1890)",
		            "Modern Era (1890 ~ 1945)",
		            "Atomic Era (1945 ~ 1995)",
		            "Information Era (1995 ~ 2020)",
		            "GS-Only Future Era (2020 ~ 2050)"
		        };
		    
		    
		    // JComboBox para seleccionar Era
	        eraComboBox = new JComboBox<>(eras);
	        eraComboBox.setSelectedIndex(0); // Selecciona la primera opción, que es el mensaje "Select your era"
	        gbc.gridx = 1; // Segunda columna
	        gbc.gridy = 3; // Cuarta fila
	        gbc.gridwidth = 2; // Componente ocupa dos columnas
	        this.add(eraComboBox, gbc); // Agregar JComboBox

		    // Etiqueta para mensajes de excepción
		    exceptionMessage = new JLabel("Please select a valid era.");
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
		    createGame.setEnabled(false);
		    
		    
		    
		    
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

		    
		    
		    // Configurar el ActionListener para el JComboBox
		    eraComboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (eraComboBox.getSelectedIndex() > 0) {
	                    trueEra = true;
	                    exceptionMessage.setText("");
	                } else {
	                    trueEra = false;
//	                    exceptionMessage.setText("Please select a valid era.");
	                }
	                updateCreateGameState(); 
	            }
	        });
	        
	        // Iniciar juego
	        createGame.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Resetear el combo box
	                eraComboBox.setSelectedIndex(0);
	                
	                // Ocultar el frame principal
	                setVisible(false);
	                
	                // Usar SwingUtilities.invokeLater para abrir el nuevo frame después
	                SwingUtilities.invokeLater(new Runnable() {
	                    @Override
	                    public void run() {
	                        // Crear y mostrar el nuevo frame
	                        gameFrame = new Game(parentFrame);
	                        
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
			if (trueName && trueEra) {
		        createGame.setEnabled(true);
		        exceptionMessage.setText("");
		    } else {
		        createGame.setEnabled(false);
		        
		        if (!trueEra) {
		            exceptionMessage.setText("Please select a valid era.");
		        } else if (!trueName) {
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


