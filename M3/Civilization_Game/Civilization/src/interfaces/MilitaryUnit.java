package interfaces;

public interface MilitaryUnit {

	
	// 	Nos devolverá el poder de ataque que tenga la unidad.
	abstract int attack();
	
	// 	Restará a nuestro blindaje el daño recibido por parámetro.
	abstract void takeDamage(int receivedDamage);
	
	// 	Nos devolverá el blindaje que tengamos actualmente, después de haber recibido un ataque.
	abstract int getActualArmor();
	
	//	Nos devolverá el coste de Comida que tiene crear una nueva unidad.
	abstract int getFoodCost();
	
	//	Nos devolverá el coste de Madera que tiene crear una nueva unidad.
	abstract int getWoodCost();
	
	// 	Nos devolverá el coste de Hierro que tiene crear una nueva unidad.
	abstract int getIronCost();
	
	// 	Nos devolverá el coste de Mana que tiene crear una nueva unidad.
	abstract int getManaCost();
	
	
	// 	Nos restablecerá nuestro blindaje a su valor original. 
	abstract void resetArmor();
	
	// 	Establecerá la experiencia a un nuevo valor. 
	abstract void setExperience(int n);
	
	// Nos devolverá la experiencia actual de la unidad.
	abstract int getExperience();
	abstract int getId_civi();

	abstract void setId_civi(int id_civi);
	abstract void setArmor(int armor);

	abstract String getSimpleName();
	
	abstract void setSanti();
	
	abstract void setBaseDamage(int x);

	abstract boolean isSanctified();

	
}
