package dummy;

import com.dfsek.protolith.optics.SimpleLens;

import java.util.Objects;

public class Person {
    public static SimpleLens<Person, Integer> AGE = SimpleLens.of(Person::getAge, Person::withAge);
    public static SimpleLens<Person, String> NAME = SimpleLens.of(Person::getName, Person::withName);
    private final int age;
    private final String name;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public Person withAge(int age) {
        return new Person(this.name, age);
    }

    public Person withName(String name) {
        return new Person(name, this.age);
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}
