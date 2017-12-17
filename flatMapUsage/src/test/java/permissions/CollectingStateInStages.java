package permissions;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

public class CollectingStateInStages {

    private static List<User> users;

    @BeforeClass
    public static void setUp() {

        Set<Permission> adminPermissions = new HashSet<>();
        adminPermissions.add(Permission.SEARCH);
        adminPermissions.add(Permission.ADD);
        adminPermissions.add(Permission.EDIT);
        adminPermissions.add(Permission.DELETE);

        Role adminRole = new Role("admin", adminPermissions);

        Set<Permission> devPermissions = new HashSet<>();
        devPermissions.add(Permission.SEARCH);
        devPermissions.add(Permission.ADD);
        devPermissions.add(Permission.EDIT);

        Role devRole = new Role("developer", devPermissions);

        Set<Permission> userPermissions = new HashSet<>();
        userPermissions.add(Permission.SEARCH);

        Role userRole = new Role("user", userPermissions);


        Set<Role> sarahsRoles = new HashSet<>();
        sarahsRoles.add(adminRole);
        sarahsRoles.add(devRole);
        sarahsRoles.add(userRole);

        User sarah = new User(1, "Sarah", 35);
        sarah.setRoles(sarahsRoles);

        Set<Role> davidsRoles = new HashSet<>();
        davidsRoles.add(devRole);
        davidsRoles.add(userRole);

        User david = new User(2, "David", 22);
        david.setRoles(davidsRoles);

        Set<Role> petersRoles = new HashSet<>();
        petersRoles.add(userRole);

        User peter = new User(3, "Peter", 34);
        peter.setRoles(petersRoles);

        users = new ArrayList<>();
        users.add(sarah);
        users.add(david);
        users.add(peter);
    }

    @Test
    public void test(){

            Map<String, Set<User>> editors = users.stream()
                    .flatMap(u -> u.getRoles().stream()
                            .filter(r -> r.getPermissions().contains(Permission.EDIT))
                            .map(r -> new Tuple<>(r, u))
                    ).collect(groupingBy(p -> "\n" + p.getKey().getName()+"\n\t",
                            mapping(Tuple::getValue, toSet())));

            System.out.println(editors);



    }

    class Tuple<K, V> {
        private final K key;
        private final V value;

        Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }
    }
}
