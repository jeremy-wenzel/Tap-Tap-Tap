package cs371m.taptaptap;

public class WordColorer {

    protected final int colorStartIndex = 14;
    protected final static String redBegin = "<font color='#FF0000'>";
    protected final static String greenBegin = "<font color='#00FF00'>";
    protected final static String blackBegin = "<font color='#000000'>";
    protected final static String endTag = "</font>";

    private static WordColorer instance = new WordColorer();

    private WordColorer () {

    }

    /*

        Consider cases where the number of letters in the typed word != the number of letters in the correct word

     */

    private static String colorLetters ( String userWord, String correctWord ) {
        StringBuilder toReturn = new StringBuilder();

        int i = 0;
        for ( ; i < userWord.length() ; ++i )
            if (userWord.charAt(i) == correctWord.charAt(i))
                toReturn.append(greenBegin + userWord.charAt(i) + endTag);
            else
                toReturn.append(redBegin + userWord.charAt(i) + endTag);

        for ( ; i < correctWord.length() ; ++i )
            toReturn.append(blackBegin + correctWord.charAt(i) + endTag);

        return toReturn.toString();
    }

    public static String colorIncompleteWord ( String userWord, String correctWord ) {
        return colorLetters(userWord, correctWord);
    }

    public static String colorCompleteWord ( String word, boolean correct ) {
        String toReturn;
        toReturn = (correct) ? greenBegin : redBegin;
        return toReturn + word + endTag;
    }



}
