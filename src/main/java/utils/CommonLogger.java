package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonLogger {
	Logger logger;
	
	public <T> CommonLogger(Class<T> cls) {
		logger = Logger.getLogger(cls);
		PropertyConfigurator.configure("~/log4.properties");
	}
	
	public Logger getLogger() {
		return logger;
	}

}