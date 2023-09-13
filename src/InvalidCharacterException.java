public class InvalidCharacterException extends Exception {
    public InvalidCharacterException(String message){
        super(message);
    }

    public static void validateInput(char input) throws InvalidCharacterException{
        if(!Character.isLetter(input) && input != '?'){
            throw new InvalidCharacterException("Invalid character, try again");
        }
    }
}
