package com.example.tiendaapi.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    static {
        try {
            logger.info("Loading environment variables from .env file...");
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.debug("Set system property: {} = {}", entry.getKey(), entry.getValue());
            });
            logger.info(".env file loaded successfully (or skipped if not found).");
        } catch (DotenvException e) {
            logger.warn(".env file not found or failed to load, continuing with system environment variables");
        } catch (Exception e) {
            logger.error("Failed to load .env file", e);
        }
    }
}
