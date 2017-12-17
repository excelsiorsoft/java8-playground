

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;



public class FlatteningSubcollectionOfDevelopers {

    private static List<Developer> team;
    private static Developer polyglot;
    private static Developer busy;

    @BeforeClass
    public static void setUp() {
        team = new ArrayList<>();
        polyglot = new Developer("esoteric");
        polyglot.add("clojure");
        polyglot.add("scala");
        polyglot.add("groovy");
        polyglot.add("go");

        busy = new Developer("pragmatic");
        busy.add("java");
        busy.add("javascript");

        team.add(polyglot);
        team.add(busy);
    }

    @Test
    public void flatMap() {


        List<String> teamLanguages = team.stream().
                map(d -> d.getLanguages()).
                flatMap(l -> l.stream()).
                collect(Collectors.toList());
        assertTrue(teamLanguages.containsAll(polyglot.getLanguages()));
        assertTrue(teamLanguages.containsAll(busy.getLanguages()));
    }

    @Test
    public void justflatMap() {


        List<String> teamLanguages = team.stream().
                flatMap(d -> d.getLanguages().stream()).
                //flatMap(l -> l.stream()).
                collect(Collectors.toList());
        assertTrue(teamLanguages.containsAll(polyglot.getLanguages()));
        assertTrue(teamLanguages.containsAll(busy.getLanguages()));
    }
}

