package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_BIG_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Edits the details of an existing event in the event book.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "edit_ev";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "EVENT NAME] "
            + "[" + PREFIX_EVENT_VENUE + "VENUE] "
            + "[" + PREFIX_EVENT_MANPOWER_NEEDED + "MANPOWER NEEDED] "
            + "[" + PREFIX_EVENT_START_DATE + "START DATE] "
            + "[" + PREFIX_EVENT_END_DATE + "END DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "Drawing Competition "
            + PREFIX_EVENT_VENUE + "Utown Student Plaza";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book.";
    public static final String MESSAGE_INVALID_DATES = "Invalid start/end dates!";
    public static final String MESSAGE_INVALID_MANPOWER_COUNT_NEEDED = "Invalid manpower count needed. Free some"
            + " employees before executing this command again!";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownList = model.getFilteredEventList();
        } else {
            lastShownList = model.getFilteredScheduledEventList();
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (editedEvent.getStartDate().compareTo(editedEvent.getEndDate()) > 0) {
            throw new CommandException(MESSAGE_INVALID_DATES);
        }

        if (editedEvent.getManpowerNeeded().value < editedEvent.getCurrentManpowerCount()) {
            throw new CommandException(MESSAGE_INVALID_MANPOWER_COUNT_NEEDED);
        }

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        long dateDifference = editedEvent.getStartDate().dateDifference(editedEvent.getEndDate());

        if (dateDifference > 90) {
            throw new CommandException(String.format(
                    MESSAGE_DATE_BIG_RANGE, editedEvent.getStartDate(), editedEvent.getEndDate(), dateDifference));
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     * <p>
     * Event will be edited differently based on the fields that changed
     * If only Name, Venue, Manpower Needed or tags are changed, Event will inherit the ManpowerList & DateTimeMap.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedEventName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        EventVenue updatedEventVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        EventManpowerNeeded updatedManpowerNeeded = editEventDescriptor.getManpowerNeeded()
                .orElse(eventToEdit.getManpowerNeeded());
        EventDate updatedStartDate = editEventDescriptor.getStartDate().orElse(eventToEdit.getStartDate());
        EventDate updatedEndDate = editEventDescriptor.getEndDate().orElse(eventToEdit.getEndDate());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        EventManpowerAllocatedList updatedManpowerAllocatedList = new EventManpowerAllocatedList();
        EventDateTimeMap updatedDateTimeMap =
                new EventDateTimeMap(eventToEdit.getEventDateTimeMap().getDateTimeMap());

        if (updatedStartDate != eventToEdit.getStartDate()
                || updatedEndDate != eventToEdit.getEndDate()) {
            updatedDateTimeMap.flushEventDates(updatedStartDate, updatedEndDate);
        } else { //Editing name, venue, tags will keep the ManpowerList & the DateTimeMap

        }

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, updatedDateTimeMap, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        //Data Fields
        private EventName name;
        private EventVenue venue;
        private EventManpowerNeeded manpowerNeeded;
        private EventDate startDate;
        private EventDate endDate;
        private EventManpowerAllocatedList manpowerAllocatedList;
        private Set<Tag> tags;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setVenue(toCopy.venue);
            setManpowerNeeded(toCopy.manpowerNeeded);
            setStartDate(toCopy.startDate);
            setEndDate(toCopy.endDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, venue, manpowerNeeded,
                    startDate, endDate, tags);
        }

        public void setName(EventName name) {
            this.name = name;
        }

        public Optional<EventName> getName() {
            return Optional.ofNullable(name);
        }

        public void setVenue(EventVenue venue) {
            this.venue = venue;
        }

        public Optional<EventVenue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setManpowerNeeded(EventManpowerNeeded manpowerNeeded) {
            this.manpowerNeeded = manpowerNeeded;
        }

        public Optional<EventManpowerNeeded> getManpowerNeeded() {
            return Optional.ofNullable(manpowerNeeded);
        }

        public void setStartDate(EventDate startDate) {
            this.startDate = startDate;
        }

        public Optional<EventDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setEndDate(EventDate endDate) {
            this.endDate = endDate;
        }

        public Optional<EventDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getVenue().equals(e.getVenue())
                    && getManpowerNeeded().equals(e.getManpowerNeeded())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getTags().equals(e.getTags());
        }
    }
}
