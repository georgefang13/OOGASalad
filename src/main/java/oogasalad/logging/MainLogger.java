package oogasalad.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yegor Kursakov
 */

public class MainLogger {
    private static MainLogger instance;
    private Logger logger;

    private MainLogger(String className) {
        logger = (Logger) LoggerFactory.getLogger(className);
        logger.setLevel(Level.INFO); //Level.INFO - to set only progress log
    }

    public static MainLogger getInstance(Class<?> className) {
        return new MainLogger(className.getName());
    }

    public void setLogLevel(Level level) {
        logger.setLevel(level);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void trace(String message) {
        logger.trace(message);
    }
}