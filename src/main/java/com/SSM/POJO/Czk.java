package com.SSM.POJO;

public class Czk {
    private String name;
    private String note;

    @Override
    public String toString() {
        return "Czk{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
