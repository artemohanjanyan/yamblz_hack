package yamblz.hack.learning.db;

import java.util.ArrayList;

/**
 * Created by vorona on 23.07.16.
 */

public class Word {
    boolean english;
    private String word;
    ArrayList<String> translations;
    private int progress = 0;

    Word() {
        translations = new ArrayList<>();
    }


    public void addTranslation(String translation) {
        translations.add(translation);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translations.get(0);
    }

    public String getWord() {
        return word;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public void incProgress() {
        progress++;
    }
    public void decProgress() {
        progress--;
    }

    public int getProgress() {
        return progress;
    }
}
