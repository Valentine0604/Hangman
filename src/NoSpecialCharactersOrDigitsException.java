/**
 * Custom exception class to handle words with special characters or digits in Hangman game input.
 */
public class NoSpecialCharactersOrDigitsException extends Exception{

    /**
     * Constructs a NoSpecialCharactersOrDigitsException with the given error message.
     *
     * @param message The error message to display when this exception is thrown.
     */
    public NoSpecialCharactersOrDigitsException(String message){
        super(message);
    }


    /**
     * Validates whether the input word contains only letters and is not blank.
     * Throws a NoSpecialCharactersOrDigitsException if the word contains special characters or digits,
     * or if it is blank.
     *
     * @param input The word to be validated.
     * @throws NoSpecialCharactersOrDigitsException If the word contains special characters, digits, or is blank.
     */
    public static void validateWord(String input) throws NoSpecialCharactersOrDigitsException{
        if(!input.matches("^[a-zA-Z]*$")){
            throw new NoSpecialCharactersOrDigitsException("Your word cannot include special characters or digits.");
        }
        else if(input.isBlank()){
            throw new NoSpecialCharactersOrDigitsException("You can't start a game without a word.");
        }
    }
}
