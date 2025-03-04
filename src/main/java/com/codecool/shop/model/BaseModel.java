package com.codecool.shop.model;


import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;

public class BaseModel {
    @Expose
    protected int id;
    @Expose
    protected String description;

    public BaseModel(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName()).append(":").append(value).append(",");
                }
            } catch (IllegalAccessException ignored) {

            }
        }
        return sb.toString();
    }

}
