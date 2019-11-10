package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.FetchEventCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FetchEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FetchEventCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
class FetchEventCommandParserTest {

    private FetchEventCommandParser parser = new FetchEventCommandParser();

    @Test
    public void parse_validArgs_returnsFetchEventCommand() {
        assertParseSuccess(parser, "1", new FetchEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_validArgs2_returnsFetchEventCommand() {
        assertParseSuccess(parser, "   001    ", new FetchEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FetchEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs2_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FetchEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs3_throwsParseException() {
        assertParseFailure(parser, "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FetchEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs4_throwsParseException() {
        assertParseFailure(parser, "1.0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FetchEventCommand.MESSAGE_USAGE));
    }

}
