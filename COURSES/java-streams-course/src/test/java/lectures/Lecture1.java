package lectures;

import static org.assertj.core.api.Assertions.assertThat;

import beans.Person;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mockdata.MockData;
import org.assertj.core.util.Lists;
import org.junit.Test;


public class Lecture1 {

  @Test
  public void imperativeApproach() throws IOException {
    List<Person> people = MockData.getPeople();

    // 1. Find people aged less or equal 18
    List<Person> youngPeople = Lists.newArrayList();
    for (Person person : people) {
      if (person.getAge() <= 18) {
        youngPeople.add(person);
      }
    }
    System.out.println(youngPeople);
    for (Person person : youngPeople) {
      assertThat(person.getAge()).isLessThanOrEqualTo(18);
    }

    // 2. Then change implementation to find first 10 people
    final int limit = 10;
    List<Person> peopleFirst10 = Lists.newArrayList();
    for (int i = 0; i < limit; i++) {
      peopleFirst10.add(people.get(i));
    }
    System.out.println(peopleFirst10);
    for (int i = 0; i < limit; i++) {
      assertThat(people.get(0)).isEqualTo(peopleFirst10.get(0));
    }
    assertThat(peopleFirst10).hasSize(limit);
  }

  @Test
  public void declarativeApproachUsingStreams() throws Exception {
    ImmutableList<Person> people = MockData.getPeople();

    // 1
    List youngPeople = people.stream()
        .filter(person -> person.getAge() <= 18)
        .collect(Collectors.toList());
    System.out.println(youngPeople);

    // 2
    int limit = 10;
    people.stream()
        .filter(person -> person.getAge() <= 18)
        .limit(limit)
        .collect(Collectors.toList())
        .forEach(System.out::println);
  }
}
