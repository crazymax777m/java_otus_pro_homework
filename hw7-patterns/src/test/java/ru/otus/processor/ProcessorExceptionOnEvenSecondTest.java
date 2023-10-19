package ru.otus.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;

import java.time.LocalDateTime;

class ProcessorExceptionOnEvenSecondTest
{
    @Test
    public void testProcessWithException()
    {
        Message message = Mockito.mock(Message.class);
        Processor processor = new ProcessorExceptionOnEvenSecond(()-> LocalDateTime.of(2020, 12, 31, 23, 59, 2));

        Assertions.assertThrows(RuntimeException.class, ()-> processor.process(message), "Even second exception");
    }

    @Test
    public void testProcessWithoutException()
    {
        Message message = Mockito.mock(Message.class);
        Processor processor = new ProcessorExceptionOnEvenSecond(()-> LocalDateTime.of(2020, 12, 31, 23, 59, 1));

        Assertions.assertDoesNotThrow(()-> {
            processor.process(message);
        });
    }
}