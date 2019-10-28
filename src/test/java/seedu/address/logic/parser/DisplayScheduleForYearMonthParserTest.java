package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.schedule.DisplayScheduleForYearMonthCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.schedule.DisplayScheduleForYearMonthParser;
import seedu.address.model.event.EventContainsKeyYearMonthPredicate;

public class DisplayScheduleForYearMonthParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayScheduleForYearMonthCommand.MESSAGE_USAGE);

    private DisplayScheduleForYearMonthParser parser = new DisplayScheduleForYearMonthParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Year Month format : Input - "1/2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_YEAR_MONTH_1, MESSAGE_INVALID_FORMAT);
        // invalid date format : Input - "2019/10"
        assertParseFailure(parser, CommandTestUtil.INVALID_YEAR_MONTH_2, MESSAGE_INVALID_FORMAT);
        // invalid date format : Input - "2019 August"
        assertParseFailure(parser, CommandTestUtil.INVALID_YEAR_MONTH_3, MESSAGE_INVALID_FORMAT);
        // invalid date format : Input - "August 2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_YEAR_MONTH_4, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validYearMonthSpecified1_success() throws ParseException {
        YearMonth expectedYearMonth = ParserUtil.parseYearMonth("10/2019");
        DisplayScheduleForYearMonthCommand expectedCommand =
                new DisplayScheduleForYearMonthCommand(new EventContainsKeyYearMonthPredicate(expectedYearMonth));
        assertParseSuccess(parser, CommandTestUtil.VALID_YEAR_MONTH_1, expectedCommand);
    }

    @Test
    public void parse_validYearMonthSpecified2_success() throws ParseException {
        YearMonth expectedYearMonth = ParserUtil.parseYearMonth("01/2019");
        DisplayScheduleForYearMonthCommand expectedCommand =
                new DisplayScheduleForYearMonthCommand(new EventContainsKeyYearMonthPredicate(expectedYearMonth));
        assertParseSuccess(parser, CommandTestUtil.VALID_YEAR_MONTH_2, expectedCommand);
    }
}
