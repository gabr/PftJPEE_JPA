package pl.polsl.gabrys.arkadiusz.view;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
     * Options structure for parsing
     */
    private Options options;

    /**
     * Creates options structure for parsing
     */
    public View() {
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
        
        interactiveHelpCRUD.addOption(Option.builder("i")
                .longOpt("interactive")
                .desc("interactive mode")
                .build());
        
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
            case "i":
                System.out.println("i");
                break;
            case "p":
                System.out.println("p");
                break;
            case "f":
                System.out.println("f");
                break;
            case "m":
                System.out.println("m");
                break;
            case "r":
                System.out.println("r");
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
                    break;
                    
                case "i":
                case "interactive":
                    break;
                    
                case "p":
                case "persist":
                    break;
                    
                case "f":
                case "find":
                    break;
                    
                case "m":
                case "merge":
                    break;
                    
                case "r":
                case "remove":
                    break;
                    
                default:
                    return ERROR_CODE_OPTION_ERROR;
            }
            
            return ERROR_CODE_OK;
        }
    }
    
}
