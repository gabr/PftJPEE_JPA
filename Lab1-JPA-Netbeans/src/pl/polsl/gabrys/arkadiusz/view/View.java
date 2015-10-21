package pl.polsl.gabrys.arkadiusz.view;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import pl.polsl.gabrys.arkadiusz.model.Author;
import pl.polsl.gabrys.arkadiusz.model.Book;
import pl.polsl.gabrys.arkadiusz.model.DatabaseManager;

/**
 * Class provides CLI and interactive console interface
 * @author Arkadiusz Gabryś
 * @version 1.0
 */
public class View {
    
    /**
     * Error code for no errors
     */
    public final Integer ERROR_CODE_OK = 0;
    
    /**
     * Error code when option is invalid
     */
    public final Integer ERROR_CODE_OPTION_ERROR = 1;
    
    /**
     * Error code for unknown errors
     */
    public final Integer ERROR_CODE_UNKNOWN_ERROR = 2;
    
    /**
     * Help message for help option
     */
    private final String HELP_HELP = "help\n"
            + "usage: help [option]\n"
            + "\n"
            + "Without any option shows main help message.\n"
            + "With specified option shows help message for\n"
            + "specified option.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -h\n"
            + "    java -jar Lab1-JPA.jar -help\n"
            + "    java -jar Lab1-JPA.jar -h f\n"
            + "    java -jar Lab1-JPA.jar -h find\n";
    
//    /**
//     * Help message for interactive option
//     */
//    private final String HELP_INTERACTIVE = "interactive\n"
//            + "usage: interactive\n"
//            + "\n"
//            + "Starts interactive console interface.\n"
//            + "\n"
//            + "Examples:\n"
//            + "    java -jar Lab1-JPA.jar -i\n"
//            + "    java -jar Lab1-JPA.jar -interactive\n";
    
    /**
     * Help message for persist option
     */
    private final String HELP_PERSIST = "persist\n"
            + "usage:\n"
            + "       persist Author <Name> <LastName>\n"
            + "       persist Book   <Title> <Date> <AuthorId>\n"
            + "\n"
            + "Adds new Author or Book entity to the database.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -p Author Stephen King\n"
            + "    java -jar Lab1-JPA.jar -persist Book \"The Waste Lands\" 1991 1\n";
    
    /**
     * Help message for find option
     */
    private final String HELP_FIND = "find\n"
            + "usage:\n"
            + "       find Author All|<Name>\n"
            + "       find Book   All|<Title>\n"
            + "\n"
            + "Finds all entities or entities with given value.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -f Author Stephen\n"
            + "    java -jar Lab1-JPA.jar -find Book All\n";
    
    /**
     * Help message for merge option
     */
    private final String HELP_MERGE = "merge\n"
            + "usage:\n"
            + "       merge Author <Id> <Name> <LastName>\n"
            + "       merge Book   <Id> <Title> <Date> <AuthorId>\n"
            + "\n"
            + "Changes values for entity with given id.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -m 1 Author Stephen King\n"
            + "    java -jar Lab1-JPA.jar -merge 2 Book \"Drawing of the Three\" 1987 1\n";
    
    /**
     * Help message for remove option
     */
    private final String HELP_REMOVE = "remove\n"
            + "usage:\n"
            + "       remove Author <Id>\n"
            + "       remove Book   <Id>\n"
            + "\n"
            + "Removes entity with given id.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -r 1\n"
            + "    java -jar Lab1-JPA.jar -remove 2\n";
    
    /**
     * Database manager for author entities
     */
    DatabaseManager<Author> authorManager;
    
    /**
     * Database manager for book entities
     */
    DatabaseManager<Book> bookManager;
    
    /**
     * Options structure for parsing
     */
    private Options options;

    /**
     * Creates options structure for parsing and database managers
     */
    public View() {
        // create database managers
        authorManager = new DatabaseManager<Author>();
        bookManager = new DatabaseManager<Book>();
        
        // create options structure
        options = new Options();
        OptionGroup interactiveHelpCRUD = new OptionGroup();
        
        interactiveHelpCRUD.addOption(Option.builder("h")
                .longOpt("help")
                .optionalArg(true)
                .numberOfArgs(1)
                .argName("option name")
                .desc("prints this help or option help")
                .build());
        
//        interactiveHelpCRUD.addOption(Option.builder("i")
//                .longOpt("interactive")
//                .desc("interactive mode")
//                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("p")
                .longOpt("persist")
                .hasArgs()
                .argName("args")
                .numberOfArgs(4)
                .desc("persists new entity")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("f")
                .longOpt("find")
                .hasArgs()
                .argName("args")
                .numberOfArgs(2)
                .desc("finds entities")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("m")
                .longOpt("merge")
                .hasArgs()
                .argName("args")
                .numberOfArgs(5)
                .desc("merges entity")
                .build());
        
        interactiveHelpCRUD.addOption(Option.builder("r")
                .longOpt("remove")
                .hasArgs()
                .argName("args")
                .numberOfArgs(2)
                .desc("removes entity")
                .build());
        
        interactiveHelpCRUD.setRequired(true);
        options.addOptionGroup(interactiveHelpCRUD);
    }

    /**
     * Decides about action based on given arguments
     * @return the error code
     */
    public Integer manageUserInput(String[] args) {
        
        Integer errorCode = ERROR_CODE_OK;
        
        CommandLine commandLine;
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException pe) {
            formatter.printHelp("java -jar Lab1-JPA.jar", "\nLibrary database CRUD", options, "\n" + pe.getMessage());
            return ERROR_CODE_OPTION_ERROR;
        } catch (Exception ex) {
            formatter.printHelp("java -jar Lab1-JPA.jar", "\nLibrary database CRUD", options, "\nUndefined error");
            return ERROR_CODE_UNKNOWN_ERROR;
        }
        
        // only one option can be passed
        Option selected = commandLine.getOptions()[0];
        
        switch (selected.getOpt())
        {
            case "h":
                errorCode = help(selected);
                break;
//            case "i":
//                System.out.println("i");
//                break;
            case "p":
                errorCode = persist(selected);
                break;
            case "f":
                errorCode = find(selected);
                break;
            case "m":
                errorCode = merge(selected);
                break;
            case "r":
                errorCode = remove(selected);
                break;
        }
        
        return errorCode;
    }
    
    /**
     * Manages help messages
     * @param option the one of CLI options
     * @return the error code
     */
    private Integer help(Option option) {
        String value = option.getValue();
        
        if (value == null || value.isEmpty())
        {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar Lab1-JPA.jar",
                    "\nLibrary database CRUD",
                    options,
                    "\nFor details type -h with option name.");
            return ERROR_CODE_OK;
        } else {
            
            switch(value)
            {
                case "h":
                case "help":
                    System.out.println(HELP_HELP);
                    break;
                    
//                case "i":
//                case "interactive":
//                    System.out.println(HELP_INTERACTIVE);
//                    break;
                    
                case "p":
                case "persist":
                    System.out.println(HELP_PERSIST);
                    break;
                    
                case "f":
                case "find":
                    System.out.println(HELP_FIND);
                    break;
                    
                case "m":
                case "merge":
                    System.out.println(HELP_MERGE);
                    break;
                    
                case "r":
                case "remove":
                    System.out.println(HELP_REMOVE);
                    break;
                    
                default:
                    System.out.println("UNKNOWN OPTION!\n");
                    System.out.println(HELP_HELP);
                    return ERROR_CODE_OPTION_ERROR;
            }
            
            return ERROR_CODE_OK;
        }
    }

    /**
     * 
     * @param selected the given parameters
     * @return the error code
     */
    private Integer persist(Option selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param selected the given parameters
     * @return the error code
     */
    private Integer find(Option selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param selected the given parameters
     * @return the error code
     */
    private Integer merge(Option selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param selected the given parameters
     * @return the error code
     */
    private Integer remove(Option selected) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
