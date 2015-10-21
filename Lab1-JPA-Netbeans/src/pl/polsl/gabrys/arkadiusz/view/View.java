package pl.polsl.gabrys.arkadiusz.view;

import java.util.Date;
import java.util.List;
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
            + "       persist Book   <Title> <Pages> <Date> <AuthorId>\n"
            + "\n"
            + "Adds new Author or Book entity to the database.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -p Author Stephen King\n"
            + "    java -jar Lab1-JPA.jar -persist Book \"The Waste Lands\" 351 \"1991.01.23\" 1\n";
    
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
            + "       merge Book   <Id> <Title> <Pages> <Date> <AuthorId>\n"
            + "\n"
            + "Changes values for entity with given id.\n"
            + "\n"
            + "Examples:\n"
            + "    java -jar Lab1-JPA.jar -m 1 Author Stephen King\n"
            + "    java -jar Lab1-JPA.jar -merge 2 Book \"Drawing of the Three\" 284 \"1987.03.13\" 1\n";
    
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
                .optionalArg(true)
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
        
        switch (selected.getOpt()) {
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
            
            switch(value) {
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
     * Persists new entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer persist(Option selected) {
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_PERSIST);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            String entity = values.get(0).toLowerCase().trim();
            Long id = Long.parseLong(values.get(1));
        } catch (NumberFormatException nfe) {
            return ERROR_CODE_OPTION_ERROR;
        } catch (Exception ex) {
            System.out.println("Unknown error!\n");
            System.out.println(ex.toString());
            System.out.println();
            System.out.println(HELP_REMOVE);
            
            return ERROR_CODE_UNKNOWN_ERROR;
        }
        
        return ERROR_CODE_OK;
    }

    /**
     * Finds entities that match with specified parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer find(Option selected) {
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_FIND);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            String entity = values.get(0).toLowerCase().trim();
            String pattern = values.get(1).toLowerCase().trim();
            
            switch (entity) {
                case "author":
                    DatabaseManager<Author> authorManager = new DatabaseManager<Author>();
                    
                    List<Author> authors;
                    if (pattern.equals("all")) {
                        authors = authorManager.getAll(Author.class);
                    } else {
                        authors = authorManager.getByName(pattern);
                    }
                    
                    if (authors != null) {
                        for (Author a: authors) {
                            System.out.println(a.toString());
                        }
                    }
                    
                    authorManager.close();
                    break;
                    
                case "book":
                    DatabaseManager<Book> bookManager = new DatabaseManager<Book>();
                    
                    List<Book> books;
                    if (pattern.equals("all")) {
                        books = bookManager.getAll(Book.class);
                    } else {
                        books = bookManager.getByTitle(pattern);
                    }
                    
                    if (books != null) {
                        for (Book a: books) {
                            System.out.println(a.toString());
                        }
                    }
                    
                    bookManager.close();
                    break;
                    
                default:
                    System.out.println("Wrong entity name!\n");
                    System.out.println(HELP_REMOVE);

                    return ERROR_CODE_OPTION_ERROR;
            }
        } catch (Exception ex) {
            System.out.println("Unknown error!\n");
            System.out.println(ex.toString());
            System.out.println();
            System.out.println(HELP_FIND);
            
            return ERROR_CODE_UNKNOWN_ERROR;
        }
        
        return ERROR_CODE_OK;
    }

    /**
     * Merges entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer merge(Option selected) {
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_MERGE);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            String entity = values.get(0).toLowerCase().trim();
            Long id = Long.parseLong(values.get(1));
            
            switch(entity) {
                case "author":
                    if (values.size() < 4) {
                        System.out.println(HELP_MERGE);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    String name = values.get(2).trim();
                    String lastName = values.get(3).trim();
                    
                    DatabaseManager<Author> authorManager = new DatabaseManager<Author>();
                    authorManager.startTransaction();
                    Author authorToMerge = authorManager.find(Author.class, id);
                    
                    if (authorToMerge == null) {
                        authorManager.close();
                        System.out.println("Author with given Id: " + id + " couldn't be found in database!");
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    authorToMerge.setName(name);
                    authorToMerge.setLastName(lastName);
                    
                    if (authorManager.merge(authorToMerge) == null) {
                        authorManager.close();
                        System.out.println("Error while merging " + authorToMerge.toString());
                        return ERROR_CODE_UNKNOWN_ERROR;
                    }
                    
                    authorManager.commitTransaction();
                    authorManager.close();
                    
                    break;
                    
                case "book":
                    if (values.size() < 6) {
                        System.out.println(HELP_MERGE);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    String title = values.get(1).trim();
                    Long pages = 0L;
                    Date date;
                    Long authorId = 0L;
                    
                    try {
                        pages = Long.parseLong(values.get(2));
                    } catch (Exception ex) {
                        System.out.println("Wrong nuber of pages parameter!\n");
                        System.out.println(HELP_MERGE);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                   
                    try {
                        date = new Date(Date.parse(values.get(3)));
                    } catch (Exception ex) {
                        System.out.println("Wrong date format!\n");
                        System.out.println(HELP_MERGE);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    try {
                        authorId = Long.parseLong(values.get(4));
                    } catch (Exception ex) {
                        System.out.println("Wrong author id!\n");
                        System.out.println(HELP_MERGE);
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    DatabaseManager<Book> bookManager = new DatabaseManager<Book>();
                    bookManager.startTransaction();
                    Book bookToMerge = bookManager.find(Book.class, id);
                    
                    if (bookToMerge == null) {
                        bookManager.close();
                        System.out.println("Book with given Id: " + id + " couldn't be found in database!");
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    authorManager = new DatabaseManager<Author>();
                    authorToMerge = authorManager.find(Author.class, authorId);
                    
                    if (authorToMerge == null) {
                        authorManager.close();
                        System.out.println("Author with given Id: " + authorId + " couldn't be found in database!");
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    bookToMerge.setTitle(title);
                    bookToMerge.setPages(pages);
                    bookToMerge.setReleaseDate(date);
                    bookToMerge.setAuthor(authorToMerge);
                    
                    if (bookManager.merge(bookToMerge) == null) {
                        bookManager.close();
                        System.out.println("Error while merging " + bookToMerge.toString());
                        return ERROR_CODE_UNKNOWN_ERROR;
                    }
                    
                    bookManager.commitTransaction();
                    authorManager.close();
                    bookManager.close();
                    break;
                    
                default:
                    System.out.println("Wrong entity name!\n");
                    System.out.println(HELP_MERGE);

                    return ERROR_CODE_OPTION_ERROR;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Id!\n");
            System.out.println(HELP_MERGE);
            
            return ERROR_CODE_OPTION_ERROR;
        } catch (Exception ex) {
            System.out.println("Unknown error!\n");
            System.out.println(ex.toString());
            System.out.println();
            System.out.println(HELP_MERGE);
            
            return ERROR_CODE_UNKNOWN_ERROR;
        }
        
        return ERROR_CODE_OK;
    }

    /**
     * Removes entity specified in parameters
     * @param selected the given parameters
     * @return the error code
     */
    private Integer remove(Option selected) {
        List<String> values = selected.getValuesList();
        
        if (values.size() < 2) {
            System.out.println(HELP_REMOVE);
            return ERROR_CODE_OPTION_ERROR;
        }
        
        try {
            String entity = values.get(0).toLowerCase().trim();
            Long id = Long.parseLong(values.get(1));
            
            switch (entity) {
                case "author":
                    DatabaseManager<Author> authorManager = new DatabaseManager<Author>();
                    authorManager.startTransaction();
                    Author authorToRemove = authorManager.find(Author.class, id);
                    
                    if (authorToRemove == null) {
                        authorManager.close();
                        System.out.println("Author with given Id: " + id + " couldn't be found in database!");
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    if (!authorManager.remove(authorToRemove)) {
                        authorManager.close();
                        System.out.println("Error while removing " + authorToRemove.toString());
                        return ERROR_CODE_UNKNOWN_ERROR;
                    }
                    
                    authorManager.commitTransaction();
                    authorManager.close();
                    break;
                    
                case "book":
                    DatabaseManager<Book> bookManager = new DatabaseManager<Book>();
                    bookManager.startTransaction();
                    Book bookToRemove = bookManager.find(Book.class, id);
                    
                    if (bookToRemove == null) {
                        bookManager.close();
                        System.out.println("Book with given Id: " + id + " couldn't be found in database!");
                        return ERROR_CODE_OPTION_ERROR;
                    }
                    
                    if (!bookManager.remove(bookToRemove)) {
                        bookManager.close();
                        System.out.println("Error while removing " + bookToRemove.toString());
                        return ERROR_CODE_UNKNOWN_ERROR;
                    }
                    
                    bookManager.commitTransaction();
                    bookManager.close();
                    break;
                    
                default:
                    System.out.println("Wrong entity name!\n");
                    System.out.println(HELP_REMOVE);

                    return ERROR_CODE_OPTION_ERROR;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Id!\n");
            System.out.println(HELP_REMOVE);
            
            return ERROR_CODE_OPTION_ERROR;
        } catch (Exception ex) {
            System.out.println("Unknown error!\n");
            System.out.println(ex.toString());
            System.out.println();
            System.out.println(HELP_REMOVE);
            
            return ERROR_CODE_UNKNOWN_ERROR;
        }
        
        return ERROR_CODE_OK;
    }
    
}
