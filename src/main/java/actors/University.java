package actors;

import model.impl.Database;
import model.interfaces.Model;

public class University {
    private final Model database = new Database();
    private final Secretary secretary = new Secretary(database);
    private final Teacher teacher = new Teacher(database);
    private final Dean dean = new Dean(database);

    public Secretary getSecretary() {
        return secretary;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Dean getDean() {
        return dean;
    }

    public Model getDatabase() {
        return database;
    }
}
