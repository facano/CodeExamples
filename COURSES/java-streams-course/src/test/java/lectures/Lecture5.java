package lectures;


import static org.assertj.core.api.Assertions.assertThat;

import beans.Car;
import beans.Person;
import beans.PersonDTO;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mockdata.MockData;
import org.junit.Test;

public class Lecture5 {

  @Test
  public void understandingFilter() throws Exception {
    ImmutableList<Car> cars = MockData.getCars();

    Predicate<Car> carPredicate = car -> car.getPrice() < 1000;
    List<Car> carsFiltered = cars.stream()
        .filter(carPredicate)
        .collect(Collectors.toList());
    cars.forEach(System.out::println);
  }

  @Test
  public void ourFirstMapping() throws Exception {
    // transform from one data type to another
    List<Person> people = MockData.getPeople();
    // V1
    List<PersonDTO> dtos = people.stream()
        .map(person -> new PersonDTO(person.getId(), person.getFirstName(), person.getAge()))
        .collect(Collectors.toList());
    // Also works with .map(PersonDTO::map) defining PersonDTO.map

    dtos.forEach(System.out::println);
    assertThat(dtos).hasSize(1000);
  }

  @Test
  public void averageCarPrice() throws Exception {
    // calculate average of car prices
    ImmutableList<Car> cars = MockData.getCars();
    Double averageCars = cars.stream()
        .mapToDouble(car -> car.getPrice())
        .average()
        .orElse(0);
    // Also works with .mapToDouble(Car::getPrice())
    System.out.println(averageCars);
  }

  @Test
  public void test() throws Exception {

  }
}



