package yamblz.hack.learning.network;

import java.util.ArrayList;

public class Translation {

    private String word;
    private ArrayList<String> translations;

    public Translation() {
        translations = new ArrayList<>();
    }

    void addTranslation(String translation) {
        translations.add(translation);
    }

    void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translations.get(0);
    }

    public String getWord() {
        return word;
    }
}
