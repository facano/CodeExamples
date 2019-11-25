package examples;

import examples.beans.Person;
import examples.utilities.ListComparator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Sorting {
  private final List<Person> persons = Arrays.asList(
      new Person("Felipe", 32),
      new Person("Esteban", 36),
      new Person("Pedro", 21),
      new Person("Juan", 25),
      new Person("Vladimir", 33)
  );

  private List<String> names = persons.stream()
                                      .map(Person::getName)
                                      .collect(Collectors.toList());

  @BeforeEach
  public void logInitialState(){
    System.out.println(persons);
  }

  @Test
  public void sortingWithCollectionsAPI(){
    Collections.sort(names);
    System.out.println(names); // [Esteban, Felipe, Juan, Pedro, Vladimir]

    Collections.reverse(names);
    System.out.println(names); // [Vladimir, Pedro, Juan, Felipe, Esteban]
  }

  @Test
  public void sortingWithComparator(){
    Collections.sort(persons, new ListComparator());
    System.out.println(persons); // [Esteban:36, Felipe:32, Juan:25, Pedro:21, Vladimir:33]

    // Implementing the interface with a Anonymous Class
    Collections.sort(persons, new Comparator<Person>() {
      public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
      }
    });
    System.out.println(persons); // [Esteban:36, Felipe:32, Juan:25, Pedro:21, Vladimir:33]
  }

  @Test
  public void sortingWithComparable(){
    // implementing Comparable<Person> with compareTo method
    Collections.sort(persons);
    System.out.println(persons); // [Pedro:21, Juan:25, Felipe:32, Vladimir:33, Esteban:36]
  }

  @Test
  public void sortingWithLambda(){
    Collections.sort(persons, (p1, p2) -> p1.getName().compareTo(p2.getName()));
    System.out.println(persons); // [Esteban:36, Felipe:32, Juan:25, Pedro:21, Vladimir:33]

    // using lambda to implement interface with 1 method instead of anonymous class
    Comparator<Person> comparable = (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
    Collections.sort(persons, comparable);
    System.out.println(persons); // [Pedro:21, Juan:25, Felipe:32, Vladimir:33, Esteban:36]
  }

  @Test
  public void sortingWithLambdaWithInstanceMethod(){
    persons.sort((p1, p2)-> Integer.compare(p1.getAge(), p2.getAge()));
    System.out.println(persons); // [Pedro:21, Juan:25, Felipe:32, Vladimir:33, Esteban:36]
  }

}
