package pl.polsl.gabrys.arkadiusz;

import pl.polsl.gabrys.arkadiusz.view.View;

/**
 * Main class with the main method
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
public class Controller {
    
    /**
     * Parses input arguments and controls the View object
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // create a View class and pass command line arguments
        View view = new View();
        
        // manage user input
        Integer errorCode = view.manageUserInput(args);
        
        // return error code to the shell
        System.exit(errorCode);
    }
}
