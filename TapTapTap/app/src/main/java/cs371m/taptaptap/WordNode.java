package cs371m.taptaptap;

/**
 * A node for containing all the information about a word. The node will contain the following
 * information:
 *
 * The correct version of the word
 * The version of the word input by the user
 * The colored version of the word
 *
 */
public class WordNode {

    private String correctWord;
    private String userWord;
    private String coloredIWord;
    private String coloredCWord;

    /**
     * WordNode constructor. Creates a node from the word given in the argument. Sets
     * the user word and coloredWords to null.
     *
     * @param word The correct word
     */
    public WordNode(String word) {
        if (word == null)
            throw new IllegalArgumentException("Word for constructor is null");

        correctWord = word;
        userWord = null;
        coloredIWord = null;
        coloredCWord = null;
    }

    /**
     * Updates the color of the user word given the input from the user. If the word is not
     * complete, then we color individual letters. If the word is complete, we color the entire
     * word one color. See WordColorer for how the coloring works.
     *
     * @param input The user input word from the editText field.
     * @param isComplete If the user has completed the word (i.e. pressed space or enter)
     */
    public void updateUserWord(String input, boolean isComplete) {
        if (input == null)
            return;
        userWord = input;
        coloredIWord = WordColorer.colorWord(userWord, correctWord, isComplete);
    }

    /**
     * Getters
     */
    public String getCorrectWord() { return correctWord; }
    public String getUserWord() { return userWord; }
    public String getColoredIWord() { return coloredIWord; }
    public String getColoredCWord() { return coloredCWord; }

    /**
     * Method to check if the user is typing this word currently
     * @return user is typing the word currently = true, false otherwise
     */
    public boolean isTyped() { return userWord != null; }

    /**
     * Method to reset the node back to it's original state
     */
    public void resetWordNode() {
        userWord = null;
        coloredIWord = null;
        coloredCWord = null;
    }
}
