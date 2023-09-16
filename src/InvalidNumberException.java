/**
 * An exception class for handling invalid number inputs in the Hangman game.
 *
 * This custom exception is used to handle cases where the user enters an invalid number,
 * specifically when choosing the game mode in Hangman. It extends the standard Java Exception
 * class and provides custom error messages for different types of invalid inputs.
 */

public class InvalidNumberException extends Exception{

    /**
     * Constructs an InvalidNumberException with a specified error message.
     *
     * @param message The error message to be associated with the exception.
     * @throws InvalidNumberException The constructed exception with the provided message.
     */
    public InvalidNumberException(String message) throws InvalidNumberException{
        super(message);
    }

    /**
     * Validates whether the input is a valid number (1 or 2) for game mode selection.
     *
     * @param input The input to be validated.
     * @throws InvalidNumberException If the input is not a valid number or falls outside
     * the accepted range (1 or 2), an exception is throw with a corresponding error message.
     */

    public static void validateNumber(String input) throws InvalidNumberException{
        try{
            int number = Integer.parseInt(input);

            if(number < 1 || number > 2){
                throw new InvalidNumberException("Invalid number entered. Please enter 1 or 2.");
            }
        } catch(NumberFormatException e){
            throw new InvalidNumberException("The input is not a number. Please enter 1 or 2");
        }
    }

}
