import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VulnApp {
    private static final Logger logger = LogManager.getLogger(VulnApp.class);

    public static void main(String[] args) {
        String userInput = (args.length > 0) ? args[0] : "no-input";
        System.out.println("[*] Received input: " + userInput);
        logger.error("User input: {}", userInput);   // Log4j parses ${jndi:...} here
        System.out.println("[*] Logging done.");
    }
}