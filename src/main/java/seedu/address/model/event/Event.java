package seedu.address.model.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the AddMin app.
 */
public class Event {

    //Identity Fields
    private final EventName name;
    private final EventStartDate startDate;
    private final EventEndDate endDate;

    //data fields
    private final EventVenue venue;
    private final EventManpowerNeeded manpowerNeeded;
    private final EventManpowerAllocatedList manpowerAllocatedList;
    private final HashMap<EventDate, EventDayTime> dateTimeMap = new HashMap<>();
    private final Set<Tag> tags = new HashSet<>();

    public Event(EventName name, EventVenue venue,
                 EventManpowerNeeded manpowerNeeded, EventStartDate startDate,
                 EventEndDate endDate, Set<Tag> tags) {
        this.name = name;
        this.venue = venue;
        this.manpowerNeeded = manpowerNeeded;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags.addAll(tags);
    }

    public EventName getName() {
        return name;
    }

    public EventVenue getVenue() {
        return venue;
    }

    public EventManpowerNeeded getManpowerNeeded() {
        return manpowerNeeded;
    }

    public EventStartDate getStartDate() {
        return startDate;
    }

    public EventEndDate getEndDate() {
        return endDate;
    }

    public EventManpowerAllocatedList getManpowerAllocatedList() {
        return manpowerAllocatedList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getManpowerNeeded().equals(getManpowerNeeded())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getManpowerAllocatedList().equals(getManpowerAllocatedList())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, venue, manpowerNeeded,
                startDate, endDate, manpowerAllocatedList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Event Venue: ").append(getVenue());
        builder.append(" Event Manpower Needed: ").append(getManpowerNeeded());
        builder.append(" Event Start Date: ").append(getStartDate());
        builder.append(" Event End Date: ").append(getEndDate());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
