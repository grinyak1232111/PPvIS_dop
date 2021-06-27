package model.impl;


import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Database implements Model {
    //Course list. Each course is a linked list of groups.
    private final List<CourseLinkedContainer> coursesList = new ArrayList<>();

    //List of students, that are not studying at any group
    private final List<Element> dontStudy = new ArrayList<>();

    //List of all added students. This list serves only as a more productive method of obtaining a list of all students
    private final List<Element> allStudents = new ArrayList<>();


    @Override
    public synchronized boolean addStudent(Element student, ElementsContainer toGroup) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(toGroup);

        if (!isGroupExists(toGroup))
            throw new RuntimeException("This group doesn't exist in database: course number = "
                    + toGroup.getCourseNumber() + "; group name = " + toGroup.getGroupName());

        boolean result = ((Group) toGroup).addStudent(student);
        allStudents.add(student);
        if (result)
            dontStudy.remove(student);
        return result;
    }

    @Override
    public List<Element> getStudentsList() {
        return Collections.unmodifiableList(allStudents);
    }

    @Override
    public synchronized boolean addStudent(Element student) {
        Objects.requireNonNull(student);
        return dontStudy.add(student) && allStudents.add(student);
    }

    @Override
    public synchronized boolean removeStudent(Element student, ElementsContainer fromGroup) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(fromGroup);
        if (!isGroupExists(fromGroup))
            throw new RuntimeException("This group doesn't exist in database: course number = "
                    + fromGroup.getCourseNumber() + "; group name = " + fromGroup.getGroupName());

        return ((Group) fromGroup).removeStudent(student) && dontStudy.add(student);
    }

    @Override
    public synchronized boolean removeStudent(Element student) {
        Objects.requireNonNull(student);
        if (!allStudents.contains(student))
            throw new RuntimeException("This student doesn't exist in database: " + student);
        return dontStudy.remove(student) && allStudents.remove(student);
    }

    @Override
    public synchronized boolean createGroup(ElementsContainer group) {
        Objects.requireNonNull(group);
        for (CourseLinkedContainer course : coursesList) {
            if (course.getCourseNumber() == group.getCourseNumber()) {
                return course.add(group);
            }
        }
        CourseLinkedContainer tempList = new CourseLinkedContainer(group.getCourseNumber());
        tempList.add(group);
        return coursesList.add(tempList);
    }

    @Override
    public ElementsContainer getGroup(int courseNumber, String groupName) {
        if (groupName.isEmpty())
            throw new RuntimeException("Group with an empty name cannot exist");
        for (CourseLinkedContainer course : coursesList) {
            if (course.getCourseNumber() == courseNumber) {
                for (ElementsContainer group : course) {
                    if (group.getGroupName().equals(groupName)) {
                        Group resultGroup = new Group(group.getGroupName(), group.getCourseNumber());
                        group.getStudentsList().forEach(resultGroup::addStudent);
                        return resultGroup;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<ElementsContainer> getGroups() {
        List<ElementsContainer> result = new ArrayList<>(coursesList.size() * 5);
        for (CourseLinkedContainer course : coursesList) {
            result.addAll(course);
        }
        return Collections.unmodifiableList(result);
    }

    public List<ElementsContainer> getGroupsByCourse(int courseNumber) {
        for (CourseLinkedContainer course : coursesList) {
            if (course.getCourseNumber() == courseNumber) {
                List<ElementsContainer> result = new ArrayList<>(course.size());
                result.addAll(course);
                return Collections.unmodifiableList(result);
            }
        }
        return List.of();
    }

    @Override
    public List<Element> getNotStudents() {
        return Collections.unmodifiableList(dontStudy);
    }

    @Override
    public synchronized boolean removeGroup(ElementsContainer group) {
        Objects.requireNonNull(group);
        for (CourseLinkedContainer course : coursesList) {
            if (course.getCourseNumber() == group.getCourseNumber()) {
                for (ElementsContainer gr : course) {
                    if (gr.getGroupName().equals(group.getGroupName())) {
                        dontStudy.addAll(gr.getStudentsList());
                        ((Group) gr).clear();
                        if (course.remove(gr)) {
                            if (course.isEmpty()) {
                                coursesList.remove(course);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isGroupExists(ElementsContainer group) {
        return getGroups().contains(group);
    }

}
