package ru.otus.processor;

import lombok.AllArgsConstructor;
import ru.otus.model.Message;
import ru.otus.processor.provider.DateTimeProvider;

@AllArgsConstructor
public class ProcessorExceptionOnEvenSecond implements Processor
{
    private DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message)
    {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0)
            throw new RuntimeException("Even second exception");
        return message;
    }
}
