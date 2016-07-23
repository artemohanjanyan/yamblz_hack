package yamblz.hack.learning.db;

public class WordPair {
    private int direction, learnCount;
    private String firstWord, secondWord;

    public WordPair(int direction, int learnCount, String firstWord, String secondWord) {
        this.direction = direction;
        this.learnCount = learnCount;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public int getDirection() {
        return direction;
    }

    public int getLearnCount() {
        return learnCount;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }
}
