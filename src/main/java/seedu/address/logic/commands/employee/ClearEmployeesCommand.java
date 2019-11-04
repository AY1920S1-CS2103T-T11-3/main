package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all entries in the EmployeeBook.
 */
public class ClearEmployeesCommand extends Command {

    public static final String COMMAND_WORD = "clear_em";
    public static final String MESSAGE_SUCCESS = "EmployeeBook has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, "Employee");
    }
}
