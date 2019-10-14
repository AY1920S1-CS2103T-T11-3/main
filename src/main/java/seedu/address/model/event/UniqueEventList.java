package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.Employee.exceptions.EmployeeNotFoundException;

/**
 * A list of Events that enforces uniqueness
 */
public class UniqueEventList {
    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if the list contains an equivalent employee as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        //return internalList.stream().anyMatch(toCheck::isSameEvent);
        return false;
    }

    /**
     * Adds a employee to the list.
     * The employee must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEmployeeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the employee {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The employee identity of {@code editedPerson} must not be the same as another existing employee in the list.
     */
    public void setEvent(Event target, Event editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EmployeeNotFoundException();
        }

        if (!target.isSameEvent(editedPerson) && contains(editedPerson)) {
            throw new DuplicateEmployeeException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent employee from the list.
     * The employee must exist in the list.
     *
     * @param toRemove
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EmployeeNotFoundException();
        }
    }

    public void setPersons(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        /*if (!personsAreUnique(persons)) {
            throw new DuplicateEmployeeException();
        }*/

        internalList.setAll(events);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique events.
     */
    /*
    private boolean personsAreUnique(List<Employee> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

     */


}
