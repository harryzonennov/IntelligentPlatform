package com.company.IntelligentPlatform.common.service;


// TODO-LEGACY: import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SpringContextDestroyListener implements ServletContextListener {

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
                System.out.printf("ContextFinalizer:Driver %s deregistered%n", d);
            } catch (SQLException ex) {
                System.out.println(String.format("ContextFinalizer:Error deregistering driver %s", d) + ":" + ex);
            }
        }
        // TODO-LEGACY: AbandonedConnectionCleanupThread.shutdown();
        // TODO-LEGACY: AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
