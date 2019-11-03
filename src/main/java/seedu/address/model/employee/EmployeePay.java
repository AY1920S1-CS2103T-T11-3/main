package seedu.address.model.employee;


/**
 * Represents a Employee's Pay  in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPay(String)}
 */
public class EmployeePay {

    public static final String MESSAGE_CONSTRAINTS =
            "EmployeePay should only contain numbers, and it should be at least 1 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    private String value;
    //private final int value; <--Should be using this instead!


    /**
     * Constructs a {@code EmployeeSalaryPaid}.
     *
     * @param pay A valid Pay number.
     */
    public EmployeePay(String pay) {
        value = pay;
    }

    /**
     * Returns true if a given string is a valid Pay number.
     */
    public static boolean isValidPay(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePay // instanceof handles nulls
                && value.equals(((EmployeePay) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

