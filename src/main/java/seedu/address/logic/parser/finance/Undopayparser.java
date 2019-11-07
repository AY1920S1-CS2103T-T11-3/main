package seedu.address.logic.parser.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY_PAID;
import static seedu.address.logic.parser.Prefix.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.finance.UndoPayCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object
 */
public class Undopayparser implements Parser<UndoPayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PayCommand
     * and returns an PayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoPayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SALARY_PAID);


        if (!arePrefixesPresent(argMultimap, PREFIX_SALARY_PAID)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoPayCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        double salaryPaidToUndo = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_SALARY_PAID).get());

        return new UndoPayCommand(index, salaryPaidToUndo);
    }
}
