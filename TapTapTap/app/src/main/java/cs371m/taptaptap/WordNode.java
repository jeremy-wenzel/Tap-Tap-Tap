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

    public WordNode(String word) {
        if (word == null)
            throw new IllegalArgumentException("Word for constructor is null");

        correctWord = word;
        userWord = null;
        coloredIWord = null;
        coloredCWord = null;
    }

    public void updateUserWord(String input, boolean isComplete) {
        if (input == null)
            return;
        userWord = input;
        coloredIWord = WordColorer.colorWord(userWord, correctWord, isComplete);
        //coloredCWord
    }

    public String getCorrectWord() { return correctWord; }
    public String getUserWord() { return userWord; }
    public String getColoredIWord() { return coloredIWord; }
    public String getColoredCWord() { return coloredCWord; }

    public boolean isTyped() { return userWord != null; }
}
