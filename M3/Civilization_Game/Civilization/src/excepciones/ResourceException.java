package excepciones;

public class ResourceException extends Exception {
	
	// Constructor
    public ResourceException() {
        super();  
    }

    // Constructor con mensaje
    public ResourceException(String message) {
        super(message);  
    }

}
