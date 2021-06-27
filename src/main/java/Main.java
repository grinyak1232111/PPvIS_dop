import actors.Dean;
import actors.Secretary;
import actors.Teacher;
import actors.University;
import model.impl.Group;
import model.impl.Student;
import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

public class Main {
    private final Element[] STUDENTS = {
            new Student("Dennis", "Ritchie", "A", "addr"),
            new Student("Richard", "Stallman", "B", "addr1"),
            new Student("Linus", "Torvalds", "C", "addr2"),
            new Student("Ada", "Lovelace", "H", "addr"),
            new Student("Ada", "Lovelace", "H", "addr"),
            new Student("TestAda", "Lovelace", "Hhh", "addrrr"),
            new Student("John", "Von neumann", "G", "addr2"),
            new Student("Alan", "Turing", "D", "addr1"),
            new Student("Norbert;", "Wiener", "X", "everywhere"),
            new Student("Alonzo", "Church", "F", "addr1"),
            new Student("Kevin", "Mitnick", "E", "addr2"),
            new Student("Robert", "Morris", "N", "addr1"),
            new Student("404", "not found", "null", "everywhere")
    };

    private final ElementsContainer[] GROUPS = {
            new Group("programmers", 1),
            new Group("hackers", 2),
            new Group("science", 3),
            new Group("other", 1),
            new Group("for_deleting", 2)
    };

    public static void main(String[] args) {
        Main main = new Main();
        University university = new University();
        main.testSecretary(university);
        System.out.println("----------------------------------------------------------------");
        main.testTeacher(university);
        System.out.println("----------------------------------------------------------------");
        main.testDean(university);
    }

    public void testSecretary(University university) {
        System.out.println("***SECRETARY TEST STARTED***");
        Secretary secretary = university.getSecretary();
        print(university.getDatabase());
        testSecretaryCreateGroup(secretary);
        print(university.getDatabase());
        testSecretaryRemoveGroup(secretary);
        print(university.getDatabase());
        testSecretaryAddStudentToTheGroup(secretary);
        print(university.getDatabase());
        testSecretaryGetStudentsByCourse(secretary);
        print(university.getDatabase());
        System.out.println("***SECRETARY TEST ENDED***");
    }

    public void testSecretaryCreateGroup(Secretary secretary) {
        System.out.println("\tSECRETARY CREATE GROUP TEST STARTED");
        for (ElementsContainer group : GROUPS) {
            secretary.creteGroup(group);
        }
        System.out.println("\tSECRETARY CREATE GROUP TEST ENDED");
    }

    public void testSecretaryRemoveGroup(Secretary secretary) {
        System.out.println("\tSECRETARY REMOVE GROUP TEST STARTED");
        secretary.removeGroup(GROUPS[GROUPS.length - 1]);
        System.out.println("\tSECRETARY REMOVE GROUP TEST ENDED");
    }

    public void testSecretaryAddStudentToTheGroup(Secretary secretary) {
        System.out.println("\tSECRETARY ADD STUDENT TO THE GROUP TEST STARTED");
        for (int i = 0, j = 0; i < STUDENTS.length; i++, j++) {
            secretary.addStudentToTheGroup(STUDENTS[i], GROUPS[j]);
            if (j == GROUPS.length - 2)
                j = 0;
        }

        System.out.println("\tSECRETARY ADD STUDENT TO THE GROUP TEST ENDED");
    }

    public void testSecretaryGetStudentsByCourse(Secretary secretary) {
        System.out.println("\tSECRETARY GET STUDENTS TEST STARTED");
        System.out.println("For course number = 1:");
        secretary.getStudentsByCourse(1).forEach(System.out::println);
        System.out.println("\tSECRETARY GET STUDENTS TEST ENDED");
    }

    public void testTeacher(University university) {
        System.out.println("***TEACHER TEST STARTED***");
        Teacher teacher = university.getTeacher();
        print(university.getDatabase());
        testTeacherGetStudentBySecondName(teacher);
        print(university.getDatabase());
        testTeacherGetStudentByGroup(teacher);
        print(university.getDatabase());
        System.out.println("***TEACHER TEST ENDED***");
    }

    public void testTeacherGetStudentBySecondName(Teacher teacher) {
        System.out.println("\tTEACHER GET STUDENTS BY SECOND NAME TEST STARTED");
        String secondName = "Lovelace";
        System.out.println("For second name = " + secondName);
        teacher.getStudentsBySecondName(secondName).forEach(e -> {
            System.out.println(e.getFirstElement());
            System.out.println(e.getSecondElement());
        });

        System.out.println("\tTEACHER GET STUDENTS BY SECOND NAME TEST ENDED");
    }

    public void testTeacherGetStudentByGroup(Teacher teacher) {
        System.out.println("\tTEACHER GET STUDENTS BY THE GROUP TEST STARTED");
        ElementsContainer group = GROUPS[2];
        System.out.println("For " + group);
        teacher.getStudentsByGroup(group).forEach(System.out::println);

        System.out.println("\tTEACHER GET STUDENTS BY THE GROUP TEST ENDED");
    }

    public void testDean(University university) {
        System.out.println("***DEAN TEST STARTED***");
        Dean dean = university.getDean();
        print(university.getDatabase());
        testDeanGetStudentsByAddress(dean);
        print(university.getDatabase());
        testDeanMoveStudentToTheGroup(dean);
        print(university.getDatabase());
        System.out.println("***DEAN TEST ENDED***");
    }

    public void testDeanGetStudentsByAddress(Dean dean) {
        System.out.println("\tDEAN GET STUDENTS BY ADDRESS TEST STARTED");
        String address = "addr";
        System.out.println("For address addr: ");
        dean.getStudentsByAddress(address).forEach(e -> {
            System.out.println(e.getFirstElement());
            System.out.println(e.getSecondElement());
        });
        System.out.println("\tDEAN GET STUDENTS BY ADDRESS TEST ENDED");
    }

    public void testDeanMoveStudentToTheGroup(Dean dean) {
        System.out.println("\tDEAN STUDENT TO ANOTHER GROUP OR CHANGE STUDENT STATUS TEST STARTED");
        Element student1 = STUDENTS[2];
        Element student2 = STUDENTS[0];
        ElementsContainer fromGroup = GROUPS[2];
        ElementsContainer fromGroup2 = GROUPS[0];
        ElementsContainer toGroup = GROUPS[1];

        System.out.println("Change student status ( " + student1 + ")");
        dean.moveStudentToTheGroup(student1, fromGroup, null);
        System.out.println("Move student (" + student2 + ") from group (" + fromGroup2 + ") to another group (" + toGroup + ")");
        dean.moveStudentToTheGroup(student2, fromGroup2, toGroup);
        System.out.println("\tDEAN STUDENT TO ANOTHER GROUP OR CHANGE STUDENT STATUS TEST ENDED");
    }

    public void print(Model database) {
        System.out.println("Database{ ");
        for (ElementsContainer group : database.getGroups()) {
            System.out.println("\t" + group + "{");
            for (Element student : group.getStudentsList()) {
                System.out.println("\t\t" + student);
            }
            System.out.println("\t}");
        }
        System.out.println("\tDon't studying{");
        for (Element student : database.getNotStudents()) {
            System.out.println("\t\t" + student);
        }
        System.out.println("\t}");
        System.out.println("}");
    }

}
