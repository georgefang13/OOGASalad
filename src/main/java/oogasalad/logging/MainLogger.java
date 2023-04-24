package oogasalad.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class MainLogger {
    private static MainLogger instance;
    private Logger logger;

    private MainLogger() {
        logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    public static synchronized MainLogger getInstance() {
        if (instance == null) {
            instance = new MainLogger();
        }
        return instance;
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
}