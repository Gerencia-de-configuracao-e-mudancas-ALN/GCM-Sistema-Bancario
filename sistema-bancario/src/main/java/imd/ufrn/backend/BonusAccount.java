package imd.ufrn.backend;

public class BonusAccount extends Account {

    int punctuation;

    public BonusAccount(int accountNumber, double balance, int punctuation) {
        super(accountNumber, balance);
        this.punctuation = punctuation;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(int pontuation) {
        this.punctuation = pontuation;
    }
}