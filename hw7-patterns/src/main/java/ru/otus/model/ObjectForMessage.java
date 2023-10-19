package ru.otus.model;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class ObjectForMessage
{
    private List<String> data;

    public static ObjectForMessage cloneObjectForMessage(ObjectForMessage objectForMessage)
    {
        if (objectForMessage == null)
            return null;

        ObjectForMessage clone = new ObjectForMessage();

        if (objectForMessage.getData() != null)
        {
            clone.setData(ImmutableList.copyOf(objectForMessage.getData()));
        }

        return clone;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}