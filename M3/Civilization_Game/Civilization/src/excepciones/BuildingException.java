package excepciones;

public class BuildingException extends Exception{

	// Constructor
    public BuildingException() {
        super();  
    }

    // Constructor con mensaje
    public BuildingException(String message) {
        super(message);  
    }
}