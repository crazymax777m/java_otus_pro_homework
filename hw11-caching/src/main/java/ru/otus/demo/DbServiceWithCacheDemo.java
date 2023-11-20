package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceWithCacheDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceWithCacheDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        long timeWithoutCache = runDbService(false, transactionManager, clientTemplate);
        log.info("Time without cache = " + timeWithoutCache);
        long timeWithCache = runDbService(true, transactionManager, clientTemplate);
        log.info("Time with cache = " + timeWithCache);

    }

    private static long runDbService(boolean useCache, TransactionManagerHibernate transactionManager,
                                     DataTemplateHibernate<Client> clientTemplate)
    {
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, useCache);

        long time = System.currentTimeMillis();

        for (long i = 0; i < 1000; i++){
            var client = dbServiceClient.saveClient(new Client(i, "dbService" + i, new byte[1024 * 1024]));
        }

        return System.currentTimeMillis()-time;
    }

}