package cs371m.taptaptap;

public class WordColorer {

    protected final int colorStartIndex = 14;
    protected final static String redBegin = "<font color='#FF0000'>";
    protected final static String greenBegin = "<font color='#00FF00'>";
    protected final static String blackBegin = "<font color='#000000'>";
    protected final static String endTag = "</font>";

    private static WordColorer instance = new WordColorer();

    private WordColorer ( ) { }


    public static String colorWord( String userWord, String correctWord, boolean isComplete) {
        if (userWord == null || correctWord == null)
            throw new IllegalArgumentException("userWord or correctWord is null");

        if (isComplete)
            return colorCompleteWord(userWord, correctWord.equals(userWord));
        else
            return colorIncompleteWord(userWord, correctWord);
    }

    private static String colorIncompleteWord ( String userWord, String correctWord ) {
        return colorLetters(userWord, correctWord);
    }

    private static String colorCompleteWord ( String word, boolean correct ) {
        String toReturn = (correct) ? greenBegin : redBegin;
        return toReturn + word + endTag;
    }

    private static String colorLetters ( String userWord, String correctWord ) {
        StringBuilder toReturn = new StringBuilder();

        // Adding appropriate color to letter (red is wrong, green if right)
        int i = 0;
        for ( ; i < userWord.length() ; ++i )
            if (userWord.charAt(i) == correctWord.charAt(i))
                toReturn.append(greenBegin + userWord.charAt(i) + endTag);
            else
                toReturn.append(redBegin + userWord.charAt(i) + endTag);

        // If the user word is less the correct word, append black to returning word
        if (userWord.length() < correctWord.length()) {
            toReturn.append(blackBegin);
            for (; i < correctWord.length(); ++i)
                toReturn.append(correctWord.charAt(i));
            toReturn.append(endTag);
        }
        // Otherwise userWord is incorrect
        else if (userWord.length() > correctWord.length()){
            toReturn.append(redBegin);
            for (; i < userWord.length(); )
                toReturn.append(correctWord.charAt(i));
            toReturn.append(endTag);
        }

        return toReturn.toString();
    }
}
