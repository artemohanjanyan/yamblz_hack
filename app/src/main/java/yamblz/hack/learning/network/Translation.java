package yamblz.hack.learning.network;

import java.util.ArrayList;
import java.util.List;

public class Translation {

    private String word;
    private List<String> translations;

    public Translation() {
        translations = new ArrayList<>();
    }

    void addTranslation(String translation) {
        translations.add(translation);
    }

    void setWord(String word) {
        this.word = word;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public String getWord() {
        return word;
    }
}
