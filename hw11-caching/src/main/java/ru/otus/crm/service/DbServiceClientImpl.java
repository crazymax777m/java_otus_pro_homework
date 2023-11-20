package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.Client;
import ru.otus.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final MyCache<String, Client> clientCache;
    private final boolean useCache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, boolean useCache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.useCache = useCache;
        this.clientCache = new MyCache<>();
    }

    @Override
    public Client saveClient(Client client) {

        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);

                if (useCache)
                    clientCache.put(String.valueOf(clientCloned.getId()), clientCloned);

//                Runtime rt = Runtime.getRuntime();
//                System.out.printf("Available heap memory: %s%n", rt.freeMemory());

                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);

            if (useCache)
                clientCache.put(String.valueOf(clientCloned.getId()), clientCloned);

//                Runtime rt = Runtime.getRuntime();
//                System.out.printf("Available heap memory: %s%n", rt.freeMemory());

            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {

        if (useCache) {

            Client client = clientCache.get(String.valueOf(id));
            if (client != null) {

                log.info("client: {}", client);
                return Optional.of(client);
            }
        }

        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);

            if (useCache && clientOptional.isPresent()) {

                clientCache.put(String.valueOf(id), clientOptional.get());
            }

            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}