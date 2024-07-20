package imd.ufrn.model.api;

public class AccountResponse {

    private Integer accountNumber;
    private Double balance;
    private Integer accountType;

    private Integer punctuation;

    public AccountResponse(Integer accountNumber, Double balance, Integer accountType, Integer punctuation) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.punctuation = punctuation;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Integer punctuation) {
        this.punctuation = punctuation;
    }

    public AccountResponse() {
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
