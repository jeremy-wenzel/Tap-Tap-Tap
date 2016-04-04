package cs371m.taptaptap;

/**
 * Created by Rafik on 4/4/2016.
 */
public class ScoreSystem {
    private int score;
    private int mistakes;
    private int upper_limit;


    public ScoreSystem(){
        score = 0;
        mistakes = 0;
        upper_limit = 0;
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

    public void add_mistake(){
        mistakes = mistakes + 1;
    }

    public int get_mistakes(){
        return mistakes;
    }

    public void set_upper_limit(int paragraph_length){
        upper_limit = paragraph_length;
    }

    public void time_addition(int time){
        //upper_limit = upper_limit - time;
        if(time < upper_limit) {
            score = score + upper_limit;
        }
    }

}
