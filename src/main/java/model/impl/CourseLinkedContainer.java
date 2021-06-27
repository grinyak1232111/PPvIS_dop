package model.impl;

import model.interfaces.ElementsContainer;

import java.util.LinkedHashSet;

class CourseLinkedContainer extends LinkedHashSet<ElementsContainer> {
    private final int courseNumber;

    public CourseLinkedContainer(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public int getCourseNumber() {
        return courseNumber;
    }
}
