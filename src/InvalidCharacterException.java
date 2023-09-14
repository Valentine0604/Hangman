/**
 * Custom exception class to handle invalid characters in Hangman game input.
 */
public class InvalidCharacterException extends Exception {

    /**
     * Constructs an InvalidCharacterException with the given error message.
     *
     * @param message The error message to display when this exception is thrown.
     */
    public InvalidCharacterException(String message){
        super(message);
    }

    /**
     * Validates whether the input character is a valid guess in Hangman.
     * Throws an InvalidCharacterException if the input is not a letter or '?'.
     *
     * @param input The character to be validated.
     * @throws InvalidCharacterException If the input character is invalid.
     */
    public static void validateInput(char input) throws InvalidCharacterException{
        if(!Character.isLetter(input) && input != '?'){
            throw new InvalidCharacterException("Try again");
        }
    }
}
