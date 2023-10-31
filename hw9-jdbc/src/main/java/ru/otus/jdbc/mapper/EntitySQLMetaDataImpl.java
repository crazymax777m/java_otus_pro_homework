package ru.otus.jdbc.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@AllArgsConstructor
@Getter
public class EntitySQLMetaDataImpl implements EntitySQLMetaData{

    private EntityClassMetaData classMetaData;

    @Override
    public String getSelectAllSql() {
        return "select * from " + classMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + classMetaData.getName() + " where " + classMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {

        StringBuilder insertSql = new StringBuilder("insert into " + classMetaData.getName() + " (");

        for (Object f : classMetaData.getFieldsWithoutId())
        {
            Field field = (Field) f;
            insertSql.append(field.getName()).append(",");
        }

        insertSql.deleteCharAt(insertSql.length()-1);
        insertSql.append(") values (?");

        insertSql.append(",?".repeat(Math.max(0, classMetaData.getFieldsWithoutId().size() - 1)));
        insertSql.append(")");

        return insertSql.toString();
    }

    @Override
    public String getUpdateSql() {

        StringBuilder updateSql = new StringBuilder("update " + classMetaData.getName() + " set ");

        for (Object f : classMetaData.getFieldsWithoutId())
        {
            Field field = (Field) f;
            updateSql.append(field.getName()).append(" = ?,");
        }

        updateSql.deleteCharAt(updateSql.length()-1);
        updateSql.append(" where ")
                .append(classMetaData.getIdField().getName())
                .append(" = ?");

        return updateSql.toString();
    }
}