package ru.crazymax.spring.data.jdbc.https.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("CATEGORIES")
public class Category {
    @Id
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @PersistenceCreator
    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
