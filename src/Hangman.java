import java.util.*;

public class Hangman{
    public static final String[] gallows = {"+---+\n" +
            "|   |\n" +
            "    |\n" +
            "    |\n" +
            "    |\n" +
            "    |\n" +
            "=========",

            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|   |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/    |\n" +
                    "     |\n" +
                    " =========",

            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/ \\  |\n" +
                    "     |\n" +
                    " ========="};

    public static final int MAX_MISSES = 6;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        char guess = ' ';
        int hintCounter = 0;
        int missed = 0;

        start();

        char[] chosenWord = inputAWord().toCharArray();
        char[] guessArray = generateGuessArray(chosenWord);
        HashSet<Character> misses = new HashSet<>();

        hangmanGame(guess,missed,hintCounter,misses,guessArray,chosenWord);

        scanner.close();
    }

    public static void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hangman! Press anything to play!");
        printGallows(MAX_MISSES);
        scanner.nextLine();
    }

    public static String inputAWord(){
        Scanner scanner = new Scanner(System.in);

        String word = "";
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print("Your word: ");
                word = scanner.nextLine();
                NoSpecialCharactersOrDigitsException.validateWord(word);
                isValid = true;
            } catch(NoSpecialCharactersOrDigitsException e) {
                System.err.println(e.getMessage());
            }
        }

        return word;
    }

    public static void hangmanGame(char guess, int missed, int hintCounter, HashSet<Character> misses, char[] guessArray, char[] chosenWord){
        Scanner scanner = new Scanner(System.in);

        boolean checker;
        while(true) {
            if (guess == '?') {
                System.out.println("Current letter: ");
            } else {
                System.out.println("Current letter: " + guess);
            }
            System.out.print("Missed letters: ");
            printMissesArray(misses);
            System.out.print("\n");
            printGallows(missed);
            printGuessArray(guessArray);
            System.out.print("\n");
            if (hintCounter == 0) {
                System.out.println("Wanna use a hint? Press '?'");
            }
            System.out.print("\n");
            System.out.print("Your letter: ");

            try{
                guess = scanner.next().charAt(0);
                InvalidCharacterException.validateInput(guess);
            } catch(InvalidCharacterException e){
                System.err.println(e.getMessage());
                continue;
            }

            guess = Character.toLowerCase(guess);

            if (guess == '?' && hintCounter == 0) {
                hint(guessArray, chosenWord);
                hintCounter++;
                continue;
            } else if (guess == '?' && hintCounter != 0) {
                System.out.println("You can't use a hint once again :(");
                continue;
            }

            checker = checkGuess(guess, chosenWord);

            if (checker) {
                replaceLetters(guessArray, chosenWord, guess);
            } else {
                System.out.println("Wrong letter!");
                if (!checkMisses(guess, misses)) {
                    misses.add(guess);
                    missed++;
                }
            }

            if (isFinished(guessArray)) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You won!");
                break;
            }

            if (missed == 6) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You lost!");
                System.out.println("Word: " + Arrays.toString(chosenWord));
                break;
            }

        }

    }
    /**
     * Function name:
     *
     * @return int Returns a random number between 0 and 6
     */
    public static char[] generateGuessArray(char[] chosenWordArray) {
        char[] guessBoard = new char[chosenWordArray.length];

        for (int i = 0; i < chosenWordArray.length; i++) {
            guessBoard[i] = '_';
        }
        return guessBoard;
    }

    public static boolean checkGuess(char guess, char[] chosenWord) {

        for (char c : chosenWord) {
            if (guess == c) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkMisses(char guess, HashSet<Character> misses){
        if(misses.contains(guess)){
            return true;
        }
        return false;
    }

    public static void replaceLetters(char[] guessBoard, char[] chosenWord, char guess) {

        for (int i = 0; i < chosenWord.length; i++) {
            if (guess == chosenWord[i] && guessBoard[i] == '_') {
                guessBoard[i] = guess;
            }
        }
    }

    public static void printGuessArray(char[] guessBoard) {
        for (int i = 0; i < guessBoard.length; i++) {
            System.out.print(guessBoard[i] + " ");
        }
    }

    public static void printMissesArray(HashSet<Character> misses) {
        for(Character c : misses){
            System.out.print(c + " ");
        }
    }

    public static void printGallows(int missed) {
        System.out.println(gallows[missed]);
    }

    public static void hint(char[] guessBoard, char[] chosenWord) {
        Random rand = new Random();
        boolean isFound = false;
        char letter = ' ';

        while (!isFound) {
            int index = rand.nextInt(chosenWord.length);
            if (guessBoard[index] == '_') {
                letter = chosenWord[index];
                replaceLetters(guessBoard,chosenWord,letter);
                isFound = true;
            }
        }
    }

    public static boolean isFinished(char[] guessBoard) {

        for (int i = 0; i < guessBoard.length; i++) {
            if (guessBoard[i] == '_') {
                return false;
            }
        }
        return true;
    }
}