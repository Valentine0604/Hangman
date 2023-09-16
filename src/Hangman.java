import java.util.*;

/**
 * Hangman is a word-guessing game where one player thinks of a word, and the other player tries
 * to guess it by suggesting letters. The game continues until the guessing player either
 * correctly guesses the word or makes too many incorrect guesses. This program implements
 * a text-based version of the Hangman game.
 *
 * Rules:
 * - The maximum number of allowed incorrect guesses is 6.
 * - Players can use a hint to reveal a random letter in the word (limited to one hint).
 * - Special characters and digits are not allowed in the word to be guessed.
 *
 * Instructions:
 * - Enter a word for your opponent to guess, ensuring it contains only letters.
 * - The game displays an initial empty gallows and starts the guessing phase.
 * - Players guess letters by entering them.
 * - Players can use a hint by entering '?' (limited to one hint).
 * - The game ends when the word is guessed correctly or the maximum incorrect guesses are reached.
 *
 * Enjoy playing Hangman!
 */

public class Hangman{

    /**
     * An array representing different stages of the Hangman gallows.
     *
     * This array contains ASCII art representations of the Hangman gallows at various stages of the game.
     * Each element in the array corresponds to a different number of incorrect guesses.
     * The gallows become progressively complete as the number of incorrect guesses increases.
     */
    public static final String[] gallows = {
            // ASCII art for the gallows with 0 incorrect guesses
            "+---+\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 1 incorrect guess
            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 2 incorrect guesses
            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 3 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|   |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            // ASCII art for the gallows with 4 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            // ASCII art for the gallows with 5 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/    |\n" +
                    "     |\n" +
                    " =========",


            // ASCII art for the gallows with 6 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/ \\  |\n" +
                    "     |\n" +
                    " ========="};

    /**
     * The maximum number of allowed incorrect guesses in the Hangman game.
     * When a player reaches this limit, they lose the game.
     */
    public static final int MAX_MISSES = 6;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /**
         * The current guessed letter in the Hangman game.
         * It's initially set to a space character (' ').
         */
        char guess = ' ';

        /**
         * The number of hints used by the player in the Hangman game.
         * It's initially set to 0.
         */
        int hintCounter = 0;

        /**
         * The number of incorrect guesses made by the player in the Hangman game.
         * It's initially set to 0.
         */
        int missed = 0;

        start();
        int mode = chooseGameMode();

        /**
         * Converts the chosen word obtained from user input into a character array.
         */
        char[] chosenWord = inputAWord().toCharArray();

        /**
         * Initializes an array to store the current guesses in the Hangman game,
         * initially filled with underscores representing unguessed letters.
         */
        char[] guessArray = generateGuessArray(chosenWord);

        /**
         * Stores the missed letters in the Hangman game.
         * It's initially an empty ArrayList.
         */
        ArrayList<Character> misses = new ArrayList<>();

        if(mode == 1){
            System.out.println("EASY:");
            hangmanEasyMode(guess,missed,hintCounter,misses,guessArray,chosenWord);
        }
        else if(mode == 2){
            System.out.println("NORMAL");
            hangmanNormalMode(guess,missed,misses,guessArray,chosenWord);
        }
        else{
            System.out.println("HARD");
            hangmanHardMode(guess,missed,misses,guessArray,chosenWord);
        }

        scanner.close();
    }

    /**
     * Function name: start
     *
     * Displays a welcome message and prompts the user to start the game.
     * Also, initializes the game display with an empty gallows.
     */

    public static void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hangman! Press anything to play!");
        printGallows(MAX_MISSES);
        scanner.nextLine();
    }

    /**
     * Function name: inputAWord
     *
     * Reads a word input from the user, ensuring it contains no special characters or digits.
     *
     * @return A valid word entered by the user.
     */

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

    public static int chooseGameMode(){
        int mode;
        Scanner scanner = new Scanner(System.in);

        do{
            try{
                System.out.println("Choose game mode: ");
                System.out.println("1. Easy   [No time limit + one hint available]");
                System.out.println("2. Normal [1:30 min time limit + no hint available]");
                System.out.println("3. Hard   [1:00 min time limit + no hint available]");
                System.out.print("Game mode: ");
                String input = scanner.nextLine();
                InvalidNumberException.validateNumber(input);

                mode = Integer.parseInt(input);
            } catch(InvalidNumberException e){
                System.err.println(e.getMessage());
                mode = -1;
            }
        }while(mode !=1 && mode != 2 && mode != 3);

        return mode;
    }

    /**
     * Function game: hangmanEasyMode   TO UPDATE!!
     *
     * Starts the Hangman game loop, allowing the player to make guesses and manage the game.
     *
     * @param guess         The current guess made by the player.
     * @param missed        The number of incorrect guesses made by the player.
     * @param hintCounter   The number of hints used by the player [max. 1].
     * @param misses        The list of letters that have been guessed incorrectly.
     * @param guessArray    The array representing the current state of the word to guess.
     * @param chosenWord    The word that the player needs to guess.
     */
    public static void hangmanEasyMode(char guess, int missed, int hintCounter, ArrayList<Character> misses, char[] guessArray, char[] chosenWord){
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

    public static void hangmanNormalMode(char guess, int missed, ArrayList<Character> misses, char[] guessArray, char[] chosenWord){
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
            System.out.print("Your letter: ");

            try{
                guess = scanner.next().charAt(0);
                InvalidCharacterException.validateInput(guess);
            } catch(InvalidCharacterException e){
                System.err.println(e.getMessage());
                continue;
            }

            guess = Character.toLowerCase(guess);

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

    public static void hangmanHardMode(char guess, int missed, ArrayList<Character> misses, char[] guessArray, char[] chosenWord){
        Scanner scanner = new Scanner(System.in);

        boolean checker;
        while(true) {
            System.out.print("Missed letters: ");
            printMissesArray(misses);
            System.out.print("\n");
            printGallows(missed);
            printGuessArray(guessArray);
            System.out.print("\n");

            guess = Character.toLowerCase(guess);

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
     * Function name: generateGuessArray
     *
     * Initializes and returns an array representing the current state of the word to guess.
     * The array is filled with underscores ('_') to represent unguessed letters.
     *
     * @param chosenWordArray The array containing the characters of the word to guess.
     * @return An array of underscores with the same length as the chosen word.
     */
    public static char[] generateGuessArray(char[] chosenWordArray) {
        char[] guessBoard = new char[chosenWordArray.length];

        for (int i = 0; i < chosenWordArray.length; i++) {
            guessBoard[i] = '_';
        }
        return guessBoard;
    }

    /**
     * Function name: checkGuess
     *
     * Checks if a guessed letter exists in the chosen word.
     *
     * @param guess The letter guessed by the player.
     * @param chosenWord The array containing the characters of the chosen word.
     * @return true if the guessed letter exists in the chosen word, otherwise false.
     */

    public static boolean checkGuess(char guess, char[] chosenWord) {

        for (char c : chosenWord) {
            if (guess == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function name: checkMisses
     *
     * Checks if a guessed letter has already been missed.
     *
     * @param guess The letter guessed by the player.
     * @param misses A list of previously missed letters.
     * @return true if the guessed letter has already been missed, otherwise true.
     */
    public static boolean checkMisses(char guess, ArrayList<Character> misses){
        if(misses.contains(guess)){
            return true;
        }
        return false;
    }

    /**
     * Funtion name: replaceLetters
     *
     * Replaces underscores in the guess board with the correct letter if the guess is correct.
     *
     * @param guessBoard The current state of the guess board.
     * @param chosenWord The word that is being guessed.
     * @param guess      The letter guessed by the player.
     */
    public static void replaceLetters(char[] guessBoard, char[] chosenWord, char guess) {

        for (int i = 0; i < chosenWord.length; i++) {
            if (guess == chosenWord[i] && guessBoard[i] == '_') {
                guessBoard[i] = guess;
            }
        }
    }

    /**
     * Function name: printGuessArray
     *
     * Prints the current state of the guess board.
     *
     * @param guessBoard The current state of the guess board to be printed.
     */

    public static void printGuessArray(char[] guessBoard) {
        for (int i = 0; i < guessBoard.length; i++) {
            System.out.print(guessBoard[i] + " ");
        }
    }

    /**
     * Funtion name: printMissesArray
     *
     * Prints the list of missed letters.
     *
     * @param misses The list of missed letters to be printed.
     */
    public static void printMissesArray(ArrayList<Character> misses) {
        for(Character c : misses){
            System.out.print(c + " ");
        }
    }

    /**
     * Funtion name: printGallows
     *
     * Prints the hangman gallows based on the number of misses.
     *
     * @param missed The number of missed guesses to determine the hangman gallows.
     */

    public static void printGallows(int missed) {
        System.out.println(gallows[missed]);
    }

    /**
     * Function name: hint
     *
     * Provides a hint in the hangman game by revealing a random letter in the word.
     *
     * @param guessBoard The current state of the guessing board.
     * @param chosenWord The word to guess.
     */

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

    /**
     * Function name: isFinished
     *
     * Checks if the hangman game has been completed by verifying if there are no more underscores
     * in the guessing board.
     *
     * @param guessBoard The current state of the guessing board.
     * @return           True if the game is finished, false otherwise.
     */
    public static boolean isFinished(char[] guessBoard) {

        for (int i = 0; i < guessBoard.length; i++) {
            if (guessBoard[i] == '_') {
                return false;
            }
        }
        return true;
    }
}