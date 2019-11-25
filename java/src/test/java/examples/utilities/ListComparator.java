package examples.utilities;

import examples.beans.Person;
import java.util.Comparator;

public class ListComparator implements Comparator<Person> {

  public int compare(Person p1, Person p2) {
    return p1.getName().compareTo(p2.getName());
  }
}
