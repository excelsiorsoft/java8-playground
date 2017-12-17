package permissions;

import org.junit.BeforeClass;

import java.util.HashSet;
import java.util.Set;

public class CollectingStateInStages {

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



    }
}
