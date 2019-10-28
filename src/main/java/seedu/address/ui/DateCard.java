package seedu.address.ui;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code DistinctDate}.
 */
public class DateCard extends UiPart<Region> {

    private static final String FXML = "DateListCard.fxml";

    public final DistinctDate date;
    private MainWindow mainWindow;
    private Integer index;

    @FXML
    private HBox cardPane;

    @FXML
    private Label dateLabel;
    @FXML
    private Label eventsLabel;


    public DateCard(DistinctDate distinctDate, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.date = distinctDate;
        this.mainWindow = mainWindow;
        this.index = displayedIndex - 1;
        dateLabel.setText(date.getDate().toString());
        eventsLabel.setText(generateString(distinctDate));

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mainWindow.handleFetch(index);
            }
        };
        cardPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    /**
     * Generates a string output based on the list of events inside the DistinctDate Object.
     * @param date contains a list of events that i used to generate the string
     * @return a string of the information of the events inside the object
     */
    private String generateString(DistinctDate date) {
        List<Event> list = date.getListOfEvents();
        String outputString = "";
        for (int i = 0; i < list.size(); i++) {
            Event currentEvent = list.get(i);
            String eventDesc = (i + 1) + ") Event Name: " + currentEvent.getName().toString()
                    + "  Venue: " + currentEvent.getVenue().toString() + "\n"
                    + "Current Manpower List (ID) : " + currentEvent.getManpowerAllocatedList().toString() + "\n";
            outputString = outputString + eventDesc + "\n";
        }
        return outputString;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        DateCard card = (DateCard) other;
        return index.equals(card.index)
                && date.equals(card.date);
    }
}
