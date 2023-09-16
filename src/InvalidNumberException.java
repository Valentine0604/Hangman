public class InvalidNumberException extends Exception{
    public InvalidNumberException(String message) throws InvalidNumberException{
        super(message);
    }

    public static void validateNumber(String input) throws InvalidNumberException{
        try{
            int number = Integer.parseInt(input);

            if(number < 1 || number > 3){
                throw new InvalidNumberException("Invalid number entered. Please enter 1, 2, or 3.");
            }
        } catch(NumberFormatException e){
            throw new InvalidNumberException("The input is not a number. Please enter 1, 2, or 3.");
        }
    }

}
