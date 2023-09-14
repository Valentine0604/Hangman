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

        String word = "";
        boolean checker;
        boolean isValid = false;

        char guess = ' ';
        int hintCounter = 0;
        int missed = 0;
        int guessed = 0;

        System.out.println("Welcome to Hangman! Press anything to play!");
        PrintGallows(MAX_MISSES);
        scanner.nextLine();

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

        char[] chosenWord = word.toCharArray();
        char[] guessArray = GenerateGuessArray(chosenWord);
        HashSet<Character> misses = new HashSet<>();

        while(true) {
            if (guess == '?') {
                System.out.println("Current letter: ");
            } else {
                System.out.println("Current letter: " + guess);
            }
            System.out.print("Missed letters: ");
            PrintMissesArray(misses);
            System.out.print("\n");
            PrintGallows(missed);
            PrintGuessArray(guessArray);
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
                Hint(guessArray, chosenWord);
                guessed++;
                hintCounter++;
                continue;
            } else if (guess == '?' && hintCounter != 0) {
                System.out.println("You can't use a hint once again :(");
                continue;
            }

            checker = CheckGuess(guess, chosenWord);

            if (checker) {
                ReplaceLetters(guessArray, chosenWord, guess);
                guessed++;
            } else {
                System.out.println("Wrong letter!");
                if (!checkMisses(guess, misses)) {
                    misses.add(guess);
                    missed++;
                }
            }

            if (isFinished(guessArray)) {
                PrintGallows(guessed);
                PrintGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You won!");
                break;
            }

            if (missed == 6) {
                PrintGallows(missed);
                PrintGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You lost!");
                System.out.println("Word: " + Arrays.toString(chosenWord));
                break;
            }

        }
        scanner.close();
    }

    /**
     * Function name:
     *
     * @return int Returns a random number between 0 and 6
     */
    public static char[] GenerateGuessArray(char[] chosenWordArray) {
        char[] guessBoard = new char[chosenWordArray.length];

        for (int i = 0; i < chosenWordArray.length; i++) {
            guessBoard[i] = '_';
        }
        return guessBoard;
    }

    public static boolean CheckGuess(char guess, char[] chosenWord) {

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

    public static void ReplaceLetters(char[] guessBoard, char[] chosenWord, char guess) {

        for (int i = 0; i < chosenWord.length; i++) {
            if (guess == chosenWord[i] && guessBoard[i] == '_') {
                guessBoard[i] = guess;
            }
        }
    }

    public static void PrintGuessArray(char[] guessBoard) {
        for (int i = 0; i < guessBoard.length; i++) {
            System.out.print(guessBoard[i] + " ");
        }
    }

    public static void PrintMissesArray(HashSet<Character> misses) {
        for(Character c : misses){
            System.out.print(c + " ");
        }
    }

    public static void PrintGallows(int missed) {
        System.out.println(gallows[missed]);
    }

    public static void Hint(char[] guessBoard, char[] chosenWord) {
        Random rand = new Random();
        boolean isFound = false;
        char letter = ' ';

        while (!isFound) {
            int index = rand.nextInt(chosenWord.length);
            if (guessBoard[index] == '_') {
                letter = chosenWord[index];
                ReplaceLetters(guessBoard,chosenWord,letter);
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