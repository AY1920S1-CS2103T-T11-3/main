package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeyYearMonthPredicate;

/**
 * Finds and lists all events in event list whose start and end dates are within the specified Year and Month.
 * Keyword matching is case insensitive and in the following format MM/yyyy.
 */
public class DisplayScheduleForYearMonthCommand extends Command {
    public static final String COMMAND_WORD = "display_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose on the specific month, year"
            + "Example: " + COMMAND_WORD + " for/12/2019";

    private final EventContainsKeyYearMonthPredicate predicate;

    public DisplayScheduleForYearMonthCommand(EventContainsKeyYearMonthPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredScheduledEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW,
                        model.getFilteredScheduledEventList().size()), "Schedule", predicate.getYearMonth());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayScheduleForYearMonthCommand // instanceof handles nulls
                && predicate.equals(((DisplayScheduleForYearMonthCommand) other).predicate)); // state check
    }
}
