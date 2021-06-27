package actors;

import actors.util.Pair;
import actors.util.Searcher;
import model.interfaces.Element;
import model.interfaces.ElementsContainer;
import model.interfaces.Model;

import java.util.List;
import java.util.Objects;

public class Dean {
    private final Model database;

    public Dean(Model database) {
        this.database = database;
    }

    public boolean moveStudentToTheGroup(Element element, ElementsContainer fromGroup, ElementsContainer toGroup) {
        Objects.requireNonNull(element);
        if (fromGroup == null && toGroup == null)
            return database.removeStudent(element);

        if (fromGroup != null) {
            database.removeStudent(element, fromGroup);
        }
        if (toGroup != null) {
            return database.addStudent(element, toGroup);
        }
        return false;
    }

    public List<Pair<Element, ElementsContainer>> getStudentsByAddress(String address) {
        List<Pair<Element, ElementsContainer>> result = Searcher.getPairIf(database, e -> e.getKnownAddress().equals(address));

        for (Element student : database.getNotStudents()) {
            if (student.getKnownAddress().equals(address)) {
                result.add(new Pair<>(student, null));
            }
        }
        return result;
    }

    public Model getDatabase() {
        return database;
    }

}
