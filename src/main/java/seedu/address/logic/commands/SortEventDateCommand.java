package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Fetches the details of an existing event in the event book.
 */
public class SortEventDateCommand extends Command {
    public static final String COMMAND_WORD = "sort_ev_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorted Events according to Date";

    public static final String MESSAGE_SUCCESS = "sorted Event by Date";

    private Comparator<Event> comparatorToSortIndexEvents;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getEventBook().getEventList().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EVENTS_LISTED_IS_EMPTY);
        }
        comparatorToSortIndexEvents = Comparator.comparing(x -> x.getStartDate().date);
        SortedList<Event> sortedEventList = model.updateSortedEventList(comparatorToSortIndexEvents);
        String parseSortedOutput = ParserUtil.parseSortbyDate(sortedEventList);
        return new CommandResult(parseSortedOutput);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FetchEventCommand)) {
            return false;
        }

        // state check
        SortEventDateCommand e = (SortEventDateCommand) other;
        return e.equals(other);
    }
}
