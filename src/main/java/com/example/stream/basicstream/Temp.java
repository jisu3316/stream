package com.example.stream.basicstream;

import java.util.Objects;

public class Temp {

    private final Long id;
    private final String name;

    public Temp(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Temp temp = (Temp) o;
//        return Objects.equals(id, temp.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
