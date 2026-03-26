package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SpringContextDestroyListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(SpringContextDestroyListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.info("ContextFinalizer: Driver {} deregistered", d);
            } catch (SQLException ex) {
                logger.warn("ContextFinalizer: Error deregistering driver {}: {}", d, ex.getMessage());
            }
        }
        // TODO-LEGACY: AbandonedConnectionCleanupThread.shutdown();
        // TODO-LEGACY: AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
