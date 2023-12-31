package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader
{
    Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg)
    {
        messageMap.put(msg.getId(), Message.cloneMessage(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id)
    {
        return Optional.ofNullable(messageMap.get(id));
    }
}