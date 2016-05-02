package cs371m.taptaptap;

/**
 * Created by Rafik on 4/4/2016.
 */
public class ScoreSystem {
    private int score;
    private int mistakes;
    private int upper_limit;
    private double grossWordsPerMinute;
    private double correctWordsPerMinute;
    private int gameType;

    public ScoreSystem(){
        score = 0;
        mistakes = 0;
        upper_limit = 0;
        grossWordsPerMinute = 0.0;
        correctWordsPerMinute = 0.0;
    }

    public ScoreSystem(int score, int grossWordsPerMinute, int correctWordsPerMinute, int gameType) {
        this.score = score;
        this.grossWordsPerMinute = grossWordsPerMinute;
        this.correctWordsPerMinute = correctWordsPerMinute;
        this.gameType = gameType;
    }

    public int get_score(){
        return score;
    }

    public void add_score(){
        score = score + 1;
    }

    public void add_word_score(int word_length){
        score = score + word_length;
    }

    public void subtract_score(){
        if(score > 0)
            score = score - 1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void add_mistake(){
        mistakes = mistakes + 1;
    }

    public int get_mistakes(){
        return mistakes;
    }

    public void set_upper_limit(int paragraph_length){
        upper_limit = paragraph_length;
    }

    public double getGrossWordsPerMinute(){
        return grossWordsPerMinute;
    }

    public void setGrossWordsPerMinute(double grossWordsPerMinute) {
        this.grossWordsPerMinute = grossWordsPerMinute;
    }

    public double getCorrectWordsPerMinute() {
        return correctWordsPerMinute;
    }

    public void setCorrectWordsPerMinute(double correctWordsPerMinute) {
        this.correctWordsPerMinute = correctWordsPerMinute;
    }

    public int getGameType() {
        return gameType;
    }

    public void time_addition(int time){
        //upper_limit = upper_limit - time;
        if(time < upper_limit) {
            score = score + upper_limit;
        }
    }
}
