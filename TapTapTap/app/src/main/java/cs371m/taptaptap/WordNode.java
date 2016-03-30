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
    private String coloredWord;

    public WordNode(String word) {
        if (word == null)
            throw new IllegalArgumentException("Word for constructor is null");

        correctWord = word;
        userWord = null;
        coloredWord = null;
    }

    public void updateUserWord(String input, boolean isComplete) {
        if (input == null)
            return;
        userWord = input;
        correctWord = WordColorer.colorWord(userWord, correctWord, isComplete);
    }

    public String getColoredWord() {
        return coloredWord;
    }
}
