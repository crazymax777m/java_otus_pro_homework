package ru.crazymax.spring.data.jdbc.https.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionWrapperService {
    @Transactional
    public void doInTransaction(Runnable runnable) {
        runnable.run();
    }
}
