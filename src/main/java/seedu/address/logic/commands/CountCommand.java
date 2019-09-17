package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class CountCommand extends Command {

    public static final String COMMAND_WORD = "count";

     public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns the number of People currently stored in the address book";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("There are a total of " + model.getNumberOfUniquePerson() + " people in the list");
    }
}