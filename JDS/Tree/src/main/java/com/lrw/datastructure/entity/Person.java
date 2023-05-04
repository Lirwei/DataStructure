package com.lrw.datastructure.entity;

public class Person implements Comparable<Person> {

    private final int age;
    private final String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    /**
     * @param person
     * @return
     */
    @Override
    public int compareTo(Person person) {
//        if (this.age > person.age)
//            return 1;
//        if (this.age < person.age)
//            return -1;
//        return 0;
        return this.age - person.age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
