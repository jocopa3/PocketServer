package pocketserver;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogManager {
	public static Logger logger = Logger.getLogger("PocketServer");
	
	public ConsoleLogManager() {
		
	}
	
	public static void init() {
		ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
		logger.setUseParentHandlers(false);
		ConsoleHandler consolehandler = new ConsoleHandler();
		consolehandler.setFormatter(consolelogformatter);
		logger.addHandler(consolehandler);
		try {
			FileHandler filehandler = new FileHandler("server.log",true);
			filehandler.setFormatter(consolelogformatter);
			logger.addHandler(filehandler);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed to log to server.log",e);
		}
	}
}
