package anyMatch;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
//import static org.junit.jupiter.api.Assertions.assertTrue;


//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;



public class UsingAnyMatch {


    private static  List<String> stringArrayList;

    @BeforeClass
    public static void setUp() {
        stringArrayList = new ArrayList<>();
        stringArrayList.add("string1 is present");
        stringArrayList.add("string2 is present");
        stringArrayList.add("string3 is present");
        stringArrayList.add("string4 is present");
    }

    @Test
    public void usingAnyMatchForContainmentCheck() {
        boolean found = stringArrayList.stream().anyMatch(s -> s.contains("string2"));
        System.out.println("found: " + found);
    }


    @Test
    public void anotherWay() {
        stringArrayList.stream()
                .filter(e -> e.contains("string2"))
                .findFirst()
                .ifPresent(e -> System.out.println("Match found"));
    }



}

