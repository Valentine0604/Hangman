public class NoSpecialCharactersOrDigitsException extends Exception{
    public NoSpecialCharactersOrDigitsException(String message){
        super(message);
    }

    public static void validateWord(String input) throws NoSpecialCharactersOrDigitsException{
        if(!input.matches("^[a-zA-Z]*$")){
            throw new NoSpecialCharactersOrDigitsException("Your word cannot include special characters or digits.");
        }
        else if(input.isBlank()){
            throw new NoSpecialCharactersOrDigitsException("You must choose a word");
        }
    }
}
