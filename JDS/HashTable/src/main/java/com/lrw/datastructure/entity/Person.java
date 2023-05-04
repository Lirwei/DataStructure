package com.lrw.datastructure.entity;

public class Person {

    private final int age;
    private final float height;

    private final String name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;

        Person person = (Person) obj;
        return person.age == age && person.height == height && (person.name == null ? name == null : person.name.equals(name));
    }
}
