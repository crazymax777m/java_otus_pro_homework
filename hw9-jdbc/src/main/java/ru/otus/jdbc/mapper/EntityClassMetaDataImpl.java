package ru.otus.jdbc.mapper;

import ru.otus.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private List<Field> fieldList;
    private List<Field> allFieldsList;
    private Field idField;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {

        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public Field getIdField() {

        if (idField == null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class))
                    idField = field;
            }
        }

        return idField;
    }

    @Override
    public List<Field> getAllFields() {

        if (allFieldsList == null)
            allFieldsList = Arrays.stream(clazz.getDeclaredFields()).toList();

        return allFieldsList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {

        if (fieldList == null)
            fieldList = Arrays.stream(clazz.getDeclaredFields()).filter(f -> !f.isAnnotationPresent(Id.class)).collect(Collectors.toList());

        return fieldList;
    }
}