package lectures;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import mockdata.MockData;
import org.junit.Test;

public class Lecture11 {

  @Test
  public void joiningStrings() throws Exception {
    List<String> names = ImmutableList.of("anna", "john", "marcos", "helena", "yasmin");
    // Without Streams
    String concatNames = "";
    for (String name :  names){
      concatNames += name + ", ";
    }
    concatNames = concatNames.substring(0, concatNames.length()-2);
    System.out.println(concatNames);
  }

  @Test
  public void joiningStringsWithStream() throws Exception {
    List<String> names = ImmutableList.of("anna", "john", "marcos", "helena", "yasmin");
    String join = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.joining(", "));
    System.out.println(join);
  }
}
