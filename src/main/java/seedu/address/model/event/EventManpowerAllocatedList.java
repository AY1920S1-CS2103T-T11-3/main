package seedu.address.model.event;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.employee.Employee;

/**
 * List of employees allocated as manpower for the Event.
 */
public class EventManpowerAllocatedList {
    public final List<Employee> manpowerList;

    /**
     * Constructs a {@code EmployeeName}.
     */
    public EventManpowerAllocatedList() {
        manpowerList = new ArrayList<>();
    }

    /**
     * Allocates employee to the Manpower List for an Event.
     *
     * @param employee to be allocated
     * @return boolean to represent if employee is successfully allocated to event
     */
    public boolean allocateEmployee(Employee employee) {
        if (manpowerList.contains(employee)) {
            return false;
        } else {
            return manpowerList.add(employee);
        }
    }

    @Override
    public String toString() {
        return manpowerList.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventManpowerAllocatedList // instanceof handles nulls
                && manpowerList.equals(((EventManpowerAllocatedList) other).manpowerList)); // state check
    }

    @Override
    public int hashCode() {
        return manpowerList.hashCode();
    }
}
