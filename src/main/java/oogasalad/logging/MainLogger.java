package oogasalad.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

/**
 * @author Yegor Kursakov
 */

public class MainLogger {
    private static MainLogger instance;
    private Logger logger;

    private MainLogger(String className) {
        logger = (Logger) LoggerFactory.getLogger(className);
        loadConfiguration();
    }

    private void loadConfiguration() {
        // Load logging configuration from properties file
        URL configUrl = getClass().getClassLoader().getResource("logging/logging_config.properties");
        if (configUrl != null) {
            Properties props = new Properties();
            try {
                props.load(configUrl.openStream());
                String level = props.getProperty("logging.level");
                if (level != null) {
                    logger.setLevel(Level.toLevel(level));
                }
            } catch (Exception e) {
                // Failed to load configuration, log error and continue with default settings
                logger.error("Failed to load logging configuration", e);
                logger.setLevel(Level.INFO);
            }
        } else {
            logger.warn("Logging configuration file not found. Set default to INFO");
            logger.setLevel(Level.INFO);
        }
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