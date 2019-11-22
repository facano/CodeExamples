package lectures;

import beans.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mockdata.MockData;
import org.junit.Test;

public class Lecture12 {
  @Test
  public void understandingCollect() throws Exception {
    List<String> emails = MockData.getPeople()
        .stream()
        .map(Person::getEmail)
        // original
        //.collect(Collectors.toList());

        // v1 explicitly
        //.collect(
        //    () -> new ArrayList<String>(), // supplier, initial Collection
        //    (list, element) -> list.add(element), // how to accumulate
        //    (list1, list2) -> list1.addAll(list2)); // how to combine results in multithreaded process

        //v2 with method references
        .collect(
        ArrayList::new,
        ArrayList::add,
        ArrayList::addAll);

    emails.forEach(System.out::println);
  }
}
