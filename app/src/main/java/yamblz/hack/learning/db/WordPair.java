package yamblz.hack.learning.db;

public class WordPair {
    private int id;
    private int direction;
    private String firstWord, secondWord;
    private int learnCount;

    public WordPair(int id, int direction, String firstWord, String secondWord, int learnCount) {
        this.direction = direction;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.learnCount = learnCount;
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
