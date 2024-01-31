package ru.crazymax.spring.data.jdbc.https.dtos;

import java.util.List;

public class SimplestPageDto<T> {
    private List<T> content;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public SimplestPageDto() {
    }

    public SimplestPageDto(List<T> content) {
        this.content = content;
    }
}
