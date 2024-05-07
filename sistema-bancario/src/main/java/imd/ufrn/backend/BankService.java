package imd.ufrn.backend;

public class BankService {
    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void createAccount(int accountNumber) {
        Account account = new Account(accountNumber, 0.0);
        bankRepository.saveAccount(account);
    }

    public double realizeDebit(int accountNumber, double value) {
        Account selectedAccount = bankRepository.getAccountByAccountNumber(accountNumber);
        selectedAccount.setBalance(selectedAccount.getBalance() - value);
        bankRepository.saveAccount(selectedAccount);
        return selectedAccount.getBalance();
    }

    public boolean realizeTransfer(int originAccountNumber, int destinationAccountNumber, double value) {
        Account destinationAccount = bankRepository.getAccountByAccountNumber(destinationAccountNumber);
        realizeDebit(originAccountNumber, value);
        destinationAccount.setBalance(destinationAccount.getBalance() + value);
        return true;
    }

    public double checkBalance(int accountNumber) {
        Account account = bankRepository.getAccountByAccountNumber(accountNumber);
        return account.getBalance();
    }
}
