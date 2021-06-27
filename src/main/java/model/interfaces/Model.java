package model.interfaces;

import java.util.List;

public interface Model extends Container {
    boolean addStudent(Element student);

    boolean addStudent(Element student, ElementsContainer group);

    boolean removeStudent(Element student, ElementsContainer group);

    boolean removeStudent(Element student);

    boolean createGroup(ElementsContainer group);

    ElementsContainer getGroup(int courseNumber, String groupNumber);

    boolean removeGroup(ElementsContainer group);

    List<ElementsContainer> getGroups();

    List<ElementsContainer> getGroupsByCourse(int courseNumber);

    List<Element> getNotStudents();

}
