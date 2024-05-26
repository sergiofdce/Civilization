package BBDD;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import clase.ArrowTower;
import clase.AttackUnit;
import clase.Battle;
import clase.Cannon;
import clase.Catapult;
import clase.Civilization;
import clase.Crossbow;
import clase.DefenseUnit;
import clase.Magician;
import clase.Priest;
import clase.RocketLauncherTower;
import clase.Spearman;
import clase.SpecialUnit;
import clase.Swordsman;

import excepciones.BuildingException;
import excepciones.ResourceException;
import interfaces.MilitaryUnit;
import interfaces.Variables;

public class BBDD implements Variables {
    
	private String urlDatos = "jdbc:oracle:thin:@localhost:1521/xe?serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private String USERNAME = "CIVI";
    private String PASSWORD = "123";
	

	   public BBDD() {
		   
		   
		   
	   }
	   
	   
	   public void guardarBatalla(Civilization civilization) {
       	
       	
       	try {
       		
       	
       		saveReport(civilization.getReportes());
				this.guardarTablaInventario(civilization);
				this.guardarTablaUnidadesDeAtaque(civilization.getArmy());
				this.guardarTablaUnidadesEnemigo(civilization.getEnemyArmy());
				this.guardarRecursosEnemigo(civilization);
			} catch (ResourceException | BuildingException e1) {
				e1.printStackTrace();
			}
       	
       }

	   public void guardarTablaCivilizacion(Civilization civilization) throws ResourceException, BuildingException {
//		    System.out.println("Guardando información de la civilización...");
		    
		    

		    try {
		        Class.forName("oracle.jdbc.driver.OracleDriver");
		        borrarDatosTablas();
		        
		        Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//		        System.out.println("Conexión creada");

		        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

	            String insertQuery = "INSERT INTO civilizacion(nombre) VALUES ('" + civilization.getName() + "')";
//	            System.out.println("Ejecutamos query: " + insertQuery);
	            st.executeUpdate(insertQuery);
//	            System.out.println("Se ha creado una nueva civilización");
	        

		        conn.close();
		    } catch (ClassNotFoundException | SQLException e) {
		        System.out.println(e.fillInStackTrace());
		    }
		}




	    
	    public void guardarTablaInventario(Civilization civilization) throws ResourceException, BuildingException {
//	        System.out.println("Creación de una nueva civilization...");

	      

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("conexion creada");

	            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

	            // Verificar si la tabla inventario tiene datos para la civilización actual
	            String checkInventoryQuery = "SELECT COUNT(*) FROM inventario WHERE id_civilizacion = ?";
	            PreparedStatement checkInventory = conn.prepareStatement(checkInventoryQuery);
	            checkInventory.setInt(1, 1);
	            ResultSet rs = checkInventory.executeQuery();
	            rs.next();
	            int count = rs.getInt(1);

	            String query;
	            PreparedStatement stmt;

	            if (count > 0) {
	                // Actualizar los datos existentes
	                query = "UPDATE inventario SET madera = ?, hierro = ?, mana = ?, comida = ?, victorias = ? WHERE id_civilizacion = 1";
	                stmt = conn.prepareStatement(query);

	                stmt.setInt(1, civilization.getWood());
	                stmt.setInt(2, civilization.getIron());
	                stmt.setInt(3, civilization.getMana());
	                stmt.setInt(4, civilization.getFood());
	                stmt.setInt(5, civilization.getBattles());
	            

//	                System.out.println("Ejecutando query: " + query);
	            } else {
	                // Insertar nuevos datos
	                query = "INSERT INTO inventario (id_civilizacion, madera, hierro, mana, comida, victorias) VALUES (?,?,?,?,?,?)";
	                stmt = conn.prepareStatement(query);
//	                System.out.println("HOlaaaaaaaaaaaaaaaa");
	                stmt.setInt(1, 1);
	                stmt.setInt(2, civilization.getWood());
	                stmt.setInt(3, civilization.getIron());
	                stmt.setInt(4, civilization.getMana());
	                stmt.setInt(5, civilization.getFood());
	                stmt.setInt(6, civilization.getBattles());

//	                System.out.println("Ejecutando query: " + query);
	            }

	            stmt.executeUpdate();

//	            if (count > 0) {
//	                System.out.println("Datos de inventario actualizados para la civilización con ID " + 1);
//	            } else {
//	                System.out.println("Se ha creado la civilización '" + civilization.getName() + "' con la ID " + 1);
//	            }	

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	   System.out.println( e.fillInStackTrace());
	               
	            
	        }
	    }

	    
	    

	        public void saveReport(ArrayList[] reportes) throws ResourceException, BuildingException {
//	            System.out.println("Creación de una nueva civilización...");

	           

	            try {
	                Class.forName("oracle.jdbc.driver.OracleDriver");
	                Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	                System.out.println("Conexión creada");

		            String deleteQuery = "DELETE FROM ReporteGeneralBatalla" ;
	                PreparedStatement stmt = conn.prepareStatement(deleteQuery);
	                stmt.executeUpdate();
	                
	                
	                String insertQuery = "INSERT INTO ReporteGeneralBatalla (numero, reporteCorto, reporteLargo) VALUES (?, ?, ?)";
	                stmt = conn.prepareStatement(insertQuery);
	                
	                for(int j = 0; j < reportes[0].size(); j++) {
	                    stmt.setInt(1, j);
	                    
	                    // Insertar el reporte corto como VARCHAR2
	                    String reporteCorto = (String) reportes[0].get(j);
	                    stmt.setString(2, reporteCorto);
	                    
	                    // Insertar el reporte largo como VARCHAR2
	                    String reporteLargo = (String) reportes[1].get(j);
	                    stmt.setString(3, reporteLargo);
	                    
	                    stmt.executeUpdate();
	                }

	                conn.close();
	            } catch (ClassNotFoundException | SQLException e) {
	            	   System.out.println( e.fillInStackTrace());
	            }
	        }
	      
	    
	    
	    public void guardarTablaEdificios(Civilization civilization) throws ResourceException, BuildingException {
//	        System.out.println("Guardando edificios.");

	       

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("conexion creada");

	            // Verificar si la tabla edificios tiene datos para la civilización actual
	            String checkEdificiosQuery = "SELECT COUNT(*) FROM edificios WHERE id_civilizacion = ?";
	            PreparedStatement checkEdificios = conn.prepareStatement(checkEdificiosQuery);
	            checkEdificios.setInt(1, 1);
	            ResultSet rs = checkEdificios.executeQuery();
	            rs.next();
	            int count = rs.getInt(1);

	            String query;
	            PreparedStatement stmt;

	            if (count > 0) {
	                // Actualizar los datos existentes
	                query = "UPDATE edificios SET herreria = ?, granja = ?, carpinteria = ?, torre_magica = ?, iglesia = ? WHERE id_civilizacion = 1";
	                stmt = conn.prepareStatement(query);

	                stmt.setInt(1, civilization.getSmithy());
	                stmt.setInt(2, civilization.getFarm());
	                stmt.setInt(3, civilization.getCarpentry());
	                stmt.setInt(4, civilization.getMagicTower());
	                stmt.setInt(5, civilization.getChurch());
	             

//	                System.out.println("Ejecutando query: " + query);
	            } else {
	                // Insertar nuevos datos
	                query = "INSERT INTO edificios (id_civilizacion, herreria, granja, carpinteria, torre_magica, iglesia) VALUES ( ?, ?, ?, ?, ?,?)";
	                stmt = conn.prepareStatement(query);

	                stmt.setInt(1, 1);
	                stmt.setInt(2, civilization.getSmithy());
	                stmt.setInt(3, civilization.getFarm());
	                stmt.setInt(4, civilization.getCarpentry());
	                stmt.setInt(5, civilization.getMagicTower());
	                stmt.setInt(6, civilization.getChurch());

//	                System.out.println("Ejecutando query: " + query);
	            }

	            stmt.executeUpdate();

	        

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	  System.out.println( e.fillInStackTrace());
	            
	        }
	    }

	    
	    public void guardarTablaTecnologias(Civilization civilization) throws ResourceException, BuildingException {
//	        System.out.println("Creación de una nueva civilization...");

	       
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("conexion creada");

	            // Verificar si la tabla tecnologias tiene datos para la civilización actual
	            String checkTecnologiasQuery = "SELECT COUNT(*) FROM Tecnologias WHERE id_civilizacion = ?";
	            PreparedStatement checkTecnologias = conn.prepareStatement(checkTecnologiasQuery);
	            checkTecnologias.setLong(1, 1);
	            ResultSet rs = checkTecnologias.executeQuery();
	            rs.next();
	            int count = rs.getInt(1);

	            String query;
	            PreparedStatement stmt;

	            if (count > 0) {
	                // Actualizar los datos existentes
	                query = "UPDATE Tecnologias SET ataque = ?, defensa = ? WHERE id_civilizacion = ?";
	                stmt = conn.prepareStatement(query);

	                stmt.setInt(1, civilization.getTechnologyAttack());
	                stmt.setInt(2, civilization.getTechnologyDefense());
	                stmt.setInt(3, 1);

//	                System.out.println("Ejecutando query: " + query);
	            } else {
	                // Insertar nuevos datos
	                query = "INSERT INTO Tecnologias (id_civilizacion, ataque, defensa) VALUES (?, ?, ?)";
	                stmt = conn.prepareStatement(query);

	                stmt.setInt(1, 1);
	                stmt.setInt(2, civilization.getTechnologyAttack());
	                stmt.setInt(3, civilization.getTechnologyDefense());

//	                System.out.println("Ejecutando query: " + query);
	            }

	            stmt.executeUpdate();

//	            if (count > 0) {
//	                System.out.println("Datos de tecnologías actualizados para la civilización con ID " + 1);
//	            } else {
//	                System.out.println("Se ha creado la civilización '" + civilization.getName() + "' con la ID " +1);
//	            }

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	          
	        }
	    }


	    public void guardarRecursosEnemigo(Civilization civilization)throws ResourceException, BuildingException {
		       
//	        System.out.println("guardaRecursosEnemigo");
	       
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);

	            // Verificar si ya existen datos para la civilización
	            String checkQuery = "SELECT COUNT(*) FROM RecursosEnemy";
	            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
	          
	            ResultSet rs = checkStatement.executeQuery();
	            rs.next();
	            int count = rs.getInt(1);

	            if (count > 0) {
	                // Actualizar los datos existentes
	                String updateQuery = "UPDATE RecursosEnemy SET madera = ?, hierro = ?, comida = ? ";
	                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
	                updateStatement.setInt(1,civilization.getEnemyWood());
	                updateStatement.setInt(2, civilization.getEnemyIron() );
	                updateStatement.setInt(3,  civilization.getEnemyFood());
	               
	                updateStatement.executeUpdate();
	                System.out.println("Datos actualizados para la civilización con ID " + 1);
	            } else {
	                // Insertar nuevos datos
	                String insertQuery = "INSERT INTO RecursosEnemy (madera, hierro, comida) VALUES (?, ?, ?)";
	                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
	               
	                insertStatement.setInt(1,civilization.getEnemyWood());
	                insertStatement.setInt(2, civilization.getEnemyIron() );
	                insertStatement.setInt(3, civilization.getEnemyFood());
	                insertStatement.executeUpdate();
	             
	            }

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	  System.out.println( e.fillInStackTrace());
	        }
	    }
	    
	    public void guardarTablaUnidadesDeAtaque(ArrayList<ArrayList<MilitaryUnit>>  civilizationArmy) throws ResourceException, BuildingException {
//	        System.out.println("Creación Ejercitooo");

	     

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("conexion creada");
	           
	            String deleteQuery = "DELETE FROM Unidades" ;
                PreparedStatement stmt = conn.prepareStatement(deleteQuery);
                stmt.executeUpdate();
	           

	            String query;
	           

	         
	                // Actualizar los datos existentes
	                query = "INSERT INTO Unidades (id_civilizacion, unitGroup, armor, base_damage, experience, sanctified) VALUES (?, ?, ?, ?, ?, ?)";
	                stmt = conn.prepareStatement(query);

	               
	                	for(int i = 0; i< civilizationArmy.size(); i++) {
	                		for(int j = 0; j< civilizationArmy.get(i).size(); j++) {
	                		   
	                			
	                			stmt.setInt(1, 1);
	    	                    stmt.setInt(2, i);
	    	                    stmt.setInt(3, civilizationArmy.get(i).get(j).getActualArmor());
	    	                    stmt.setInt(4, civilizationArmy.get(i).get(j).attack());
	    	                    stmt.setInt(5, civilizationArmy.get(i).get(j).getExperience() );
	    	                    
	    	                    if(civilizationArmy.get(i).get(j).isSanctified() == true) {
	    	                    	
	  	    	                    stmt.setInt(6, 1 );
	    	                    	
	    	                    	
	    	                    }else {
	    	                    	stmt.setInt(6, 0);
	    	                    	
	    	                    }
	    	                    
	    	           
	    	                    stmt.executeUpdate();
	    	                }
		                		
		                	}
	                		
	                

//	                System.out.println("Datos de unidades de ataque actualizados para la civilización con ID " + 1);


	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	 System.out.println( e.fillInStackTrace());
	        }
	    
}

	    
	    
	    
	    public void guardarTablaUnidadesEnemigo(ArrayList<ArrayList<MilitaryUnit>>  enemyArmy) throws ResourceException, BuildingException {
//	        System.out.println("Creación de una nueva civilization...");

	    

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("conexion creada");
	           
	            String deleteQuery = "DELETE FROM Enemy" ;
                PreparedStatement stmt = conn.prepareStatement(deleteQuery);
                stmt.executeUpdate();
	         

	            String query;
	           

	         
	                // Actualizar los datos existentes
	                query = "INSERT INTO Enemy (unitGroup, cantidad) VALUES (?, ?)";
	                stmt = conn.prepareStatement(query);

	               
	                	for(int i = 0; i< enemyArmy.size(); i++) {
	
	                			stmt.setInt(1, i);
	    	                    stmt.setInt(2, enemyArmy.get(i).size());
	    	                    stmt.executeUpdate();
	    	                
		                	}
	                	
//	                System.out.println("Unidades del enemigo guardadas.\n");


	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	            e.getStackTrace();
	        }
	    
	       
}
	    
	    


	    public void borrarDatosTablas() throws ResourceException, BuildingException {
//	        System.out.println("Borrando toda la información de las tablas...");

	     
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión establecida");

	            // Borrar todos los datos de las tablas
	            String[] tablas = {"civilizacion","inventario", "edificios", "tecnologias", "Unidades", "Enemy", "reportegeneralbatalla", "recursosenemy"};

	            for (String tabla : tablas) {
	                String deleteQuery = "DELETE FROM " + tabla;
	                PreparedStatement stmt = conn.prepareStatement(deleteQuery);
	                stmt.executeUpdate();
//	                System.out.println("Datos de la tabla " + tabla + " borrados exitosamente.");
	            }

//	            System.out.println("Toda la información de las tablas ha sido eliminada.");

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	  System.out.println( e.fillInStackTrace());
	        }
	    }


	    
	    public void obtenerCivilizacion(Civilization civilization) {
	       
	       
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
	            
	            String selectQuery = "SELECT nombre FROM civilizacion WHERE id = ?";
	            PreparedStatement ps = conn.prepareStatement(selectQuery);
	            ps.setLong(1, 1);
	            
	            ResultSet rs = ps.executeQuery();
	            
	            if (rs.next()) {
	                String nombre = rs.getString("nombre");
	                
	                // Actualizar los valores del objeto civilization con los datos recuperados
	                civilization.setName(nombre);
	                
	                
	            }
	            
	            rs.close();
	            ps.close();
	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	            
	        	 System.out.println( e.fillInStackTrace());
	        }
	        
	   
	    }
	    
	    public void obtenerInventario(Civilization civilization) throws ResourceException, BuildingException {
	      


	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión creada");

	            String query = "SELECT madera, hierro, mana, comida, victorias FROM inventario WHERE id_civilizacion = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setLong(1, 1);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	              
	                int wood = rs.getInt("madera");
	                int iron = rs.getInt("hierro");
	                int mana = rs.getInt("mana");
	                int food = rs.getInt("comida");
	                int battles = rs.getInt("victorias");

	                // Actualizar los valores de la civilización con los datos recuperados
	                civilization.setWood(wood);
	                civilization.setIron(iron);
	                civilization.setMana(mana);
	                civilization.setFood(food);
	                civilization.setBattles(battles);

	               
	            }

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	        	 System.out.println( e.fillInStackTrace());
	        }
	          
	     
	    }

	    
	    public void obtenerEdificios(Civilization civilization) throws ResourceException, BuildingException {
	       
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión creada");

	            String query = "SELECT herreria, granja, carpinteria, torre_magica, iglesia FROM edificios WHERE id_civilizacion = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setLong(1, 1);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                int smithy = rs.getInt("herreria");
	                int farm = rs.getInt("granja");
	                int carpentry = rs.getInt("carpinteria");
	                int magicTower = rs.getInt("torre_magica");
	                int church = rs.getInt("iglesia");

	                // Actualizar los valores de edificios de la civilización
	                civilization.setSmithy(smithy);
	                civilization.setFarm(farm);
	                civilization.setCarpentry(carpentry);
	                civilization.setMagicTower(magicTower);
	                civilization.setChurch(church);
	            }

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	          
	        	 System.out.println( e.fillInStackTrace());

	        }

	    }
	    
	    public void readReports(ArrayList[] reportes) throws ResourceException, BuildingException {
	         


            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//                System.out.println("Conexión creada");

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM ReporteGeneralBatalla");

                while (rs.next()) {
                 
                    String reporteCorto = rs.getString("reporteCorto");
                    String reporteLargo = rs.getString("reporteLargo");

                    reportes[0].add(reporteCorto);
                   reportes[1].add(reporteLargo);
                }

                conn.close();
            } catch (ClassNotFoundException | SQLException e) {
              
	        	 System.out.println( e.fillInStackTrace());

            }}
	    
	    public void obtenerTecnologias(Civilization civilization) throws ResourceException, BuildingException {
	      
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión creada");

	            String query = "SELECT ataque, defensa FROM Tecnologias WHERE id_civilizacion = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setLong(1, 1);

	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                int attack = rs.getInt("ataque");
	                int defense = rs.getInt("defensa");

	                // Actualizar los valores de tecnologías de la civilización
	                civilization.setTechnologyAttack(attack);
	                civilization.setTechnologyDefense(defense);
	            }

	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	          
	        	 System.out.println( e.fillInStackTrace());

	        }

	    }

	    public void obtenerUnidadesAtaque(Civilization civilization) throws ResourceException, BuildingException {
	     

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión creada");

	            String query = "SELECT unitGroup,  armor, base_damage, experience, sanctified FROM Unidades";
	            PreparedStatement stmt = conn.prepareStatement(query);
	           

	            ResultSet rs = stmt.executeQuery();
	      
	            while (rs.next()) {
	            	
	            	int group = rs.getInt("unitGroup");
	            	
	            	
	            	 switch (group) {
	            	 
	            	 case 0: 
	            		 
	            		 Swordsman x = new Swordsman(rs.getInt("armor"),rs.getInt("base_damage") );
	            		 x.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x);
	            		 
	            		 break;
	            	 case 1:
                         Spearman x1 = new Spearman(rs.getInt("armor"),rs.getInt("base_damage"));
                         x1.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x1.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x1);
	            		 
	            		 break;
                     case 2:
                         Crossbow x2 = new Crossbow(rs.getInt("armor"),rs.getInt("base_damage"));
                         x2.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x2.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x2);
	            		 break;
                     case 3:
                         Cannon x3 = new Cannon(rs.getInt("armor"),rs.getInt("base_damage"));
                         x3.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x3.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x3);
	            		 break;
	            	 
                     case 4:
                         ArrowTower x4 = new ArrowTower(rs.getInt("armor"),rs.getInt("base_damage"));
                         x4.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x4.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x4);
                         break;
                     case 5:
                         Catapult x5 = new Catapult(rs.getInt("armor"),rs.getInt("base_damage"));
                         x5.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x5.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x5);
                         break;
                     case 6:
                         RocketLauncherTower x6 = new RocketLauncherTower(rs.getInt("armor"),rs.getInt("base_damage"));
                         x6.setExperience(rs.getInt("experience"));
	            		 
	            		 if( rs.getInt("sanctified") == 1) {
	            			 
	            			 x6.setSanti();
	            		 }
	            		 
	            		 civilization.getArmy().get(group).add(x6);
                         break;
                         
                     case 7:
                     	Magician x7 = new Magician(rs.getInt("armor"),rs.getInt("base_damage"));
                     	x7.setExperience(rs.getInt("experience"));
	            		 
	            		
	            		 civilization.getArmy().get(group).add(x7);
                         break;
                     case 8:
                     	Priest x8 = new Priest(rs.getInt("armor"),rs.getInt("base_damage"));
                     	x8.setExperience(rs.getInt("experience"));
	            		 
	            		
	            		 civilization.getArmy().get(group).add(x8);
                        break;
                     
                 }
	            	 
	            	 }
	            	
          conn.close();
          
	        } catch (ClassNotFoundException | SQLException e) {
	        	 System.out.println( e.fillInStackTrace());

	        }

	       
	    }
	    

	    
	   
	  
	
	   
	    
	    public void cargarRecursosEnemigo(Civilization civilization) throws ResourceException, BuildingException {
	      
//	        System.out.println("cargarRecursosEnemigo");

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);

	            // Obtener los datos de recursos del enemigo para la civilización
	            String selectQuery = "SELECT madera, hierro, comida FROM RecursosEnemy";
	            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
	       
	            ResultSet rs = selectStatement.executeQuery();

	            if (rs.next()) {
	                int woodCost = rs.getInt("madera");
	                int ironCost = rs.getInt("hierro");
	                int foodCost = rs.getInt("comida");
	                
	                civilization.setEnemyFood(foodCost);
	                civilization.setEnemyWood(woodCost);
	                civilization.setEnemyIron(ironCost);
	             

	            conn.close();
	            }} catch (ClassNotFoundException | SQLException e) {
		        	 System.out.println( e.fillInStackTrace());

	    }}
	    

	    public void recuperarUnidadesEnemigo(ArrayList<ArrayList<MilitaryUnit>> enemyArmy) throws ResourceException, BuildingException {
		    
			


	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            Connection conn = DriverManager.getConnection(urlDatos, USERNAME, PASSWORD);
//	            System.out.println("Conexión creada");

	            String query = "SELECT unitGroup, cantidad from Enemy";
	            PreparedStatement stmt = conn.prepareStatement(query);
	           

	            ResultSet rs = stmt.executeQuery();
	      
	            while (rs.next()) {
	            	
	            	int group = rs.getInt("unitGroup");
	            	
	            	int cantidad = rs.getInt("cantidad");
	            	
	            	 switch (group) {
	            	 
	            	 case 0: 
	            		 for(int i = 0; i<cantidad; i++) {
	            			 
	      
	            			 Swordsman x = new Swordsman(ARMOR_SWORDSMAN,BASE_DAMAGE_SWORDSMAN);
			            		
		            		 
		            		 enemyArmy.get(group).add(x);
	            		 }
	            	
	            		 
	            		 break;
	            	 case 1:
	            		 
	            		 
	            		 for(int i = 0; i<cantidad; i++) {
	            			 
	           		      
	            			 Spearman x = new Spearman(ARMOR_SPEARMAN,BASE_DAMAGE_SPEARMAN);
			            		
		            		 
	            			 enemyArmy.get(group).add(x);            		 }
	                     
	       
	            		 break;
	                 case 2:
	                	 for(int i = 0; i<cantidad; i++) {
	            			 
		           		      
	                		 Crossbow x = new Crossbow(ARMOR_CROSSBOW,BASE_DAMAGE_CROSSBOW);
			            		
		            		 
	                		 enemyArmy.get(group).add(x);            		 }
	                     
	       
	            		 break;
	                 case 3:
	                	 for(int i = 0; i<cantidad; i++) {
	            			 
		           		 
	                		 Cannon x = new Cannon(ARMOR_CANNON,BASE_DAMAGE_CANNON);
	                		   
		            		 
	                		 enemyArmy.get(group).add(x);            		 }
	                     
	       
	            		 break;
	               
	            }


	        
	          
	            }     
	            
//	            System.out.println(enemyArmy);
	            
	            conn.close();  } catch (ClassNotFoundException | SQLException e) {
	            	
		        	 System.out.println( e.fillInStackTrace());

	  
	   
	}

		}
      
      
	    
	    public void guardarNuevoJuego(Civilization civilization) {
        	
        	
        	try {
        		guardarTablaCivilizacion(civilization);
        	
        		saveReport(civilization.getReportes());
				this.guardarTablaInventario(civilization);
				this.guardarTablaEdificios(civilization);
				this.guardarTablaUnidadesDeAtaque(civilization.getArmy());
					this.guardarTablaTecnologias(civilization);
					this.guardarTablaUnidadesEnemigo(civilization.getEnemyArmy());
					this.guardarRecursosEnemigo(civilization);
			} catch (ResourceException | BuildingException e1) {
				e1.printStackTrace();
			}
        	
        }
	       
	            public void guardarJuego(Civilization civilization) {
	            	
	            	
	            	try {
	            		
	            	
	            		saveReport(civilization.getReportes());
						this.guardarTablaInventario(civilization);
						this.guardarTablaEdificios(civilization);
						this.guardarTablaUnidadesDeAtaque(civilization.getArmy());
 						this.guardarTablaTecnologias(civilization);
 						this.guardarTablaUnidadesEnemigo(civilization.getEnemyArmy());
 						this.guardarRecursosEnemigo(civilization);
					} catch (ResourceException | BuildingException e1) {
						e1.printStackTrace();
					}
	            	
	            }
	            
	            public void InsertarJuego(Civilization civilization) {
	            	
	            	
	            	try {
	            		this.obtenerCivilizacion(civilization);
						this.obtenerInventario(civilization);
						this.obtenerEdificios(civilization);
						this.obtenerTecnologias(civilization);
						this.obtenerUnidadesAtaque(civilization);
						this.readReports(civilization.getReportes());
						
 						
					} catch (ResourceException | BuildingException e1) {
						e1.printStackTrace();
					}
	            	
	            }

	        }