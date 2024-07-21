package imd.ufrn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import imd.ufrn.model.Account;
import imd.ufrn.model.BonusAccount;
import imd.ufrn.model.SavingsAccount;
import imd.ufrn.repository.BankRepository;

@ExtendWith(SpringExtension.class)
public class BankServiceTests {

    @InjectMocks
    private BankService bankService;

    @Mock
    private BankRepository bankRepository;

    @Nested
    public class CreateAccount {
        private int accountNumber = 10;
        private double balance = 100;

        @Test
        public void shouldCreateNormalAccount() {
            int accountType = 1;
            var response = bankService.createAccount(accountNumber, accountType, balance);

            var expectedAccount = new Account(accountNumber, balance);

            assertTrue(response);
            verify(bankRepository, times(1)).saveAccount(eq(expectedAccount));
        }

        @Test
        public void shouldCreateSavingsAccount() {
            int accountType = 2;
            var response = bankService.createAccount(accountNumber, accountType, balance);

            var expectedAccount = new SavingsAccount(accountNumber, balance);

            assertTrue(response);
            verify(bankRepository, times(1)).saveAccount(eq(expectedAccount));
        }

        @Test
        public void shouldCreateBonusAccount() {
            int accountType = 3;
            var response = bankService.createAccount(accountNumber, accountType, balance);

            var expectedAccount = new BonusAccount(accountNumber, balance, 10);

            assertTrue(response);
            verify(bankRepository, times(1)).saveAccount(eq(expectedAccount));
        }

    }

    @Nested
    public class GetAccountByNumber {
        private int accountNumber = 10;
        private double balance = 200;

        @Test
        public void shouldGetNormalAccountByNumber() {
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.getAccountByNumber(accountNumber);

            assertEquals(expectedAccount, response);
            assertEquals(response.getClass(), Account.class);
        }

        @Test
        public void shouldGetSavingsAccountByNumber() {
            var expectedAccount = new SavingsAccount(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.getAccountByNumber(accountNumber);

            assertEquals(expectedAccount, response);
            assertEquals(response.getClass(), SavingsAccount.class);
        }

        @Test
        public void shouldGetBonusAccountByNumber() {
            var expectedAccount = new BonusAccount(accountNumber, balance, 10);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.getAccountByNumber(accountNumber);

            assertEquals(expectedAccount, response);
            assertEquals(response.getClass(), BonusAccount.class);
        }
    }

    @Nested
    public class CheckBalance {
        private int accountNumber = 10;
        private double balance = 200;

        @Test
        public void shouldCheckBalance() {
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.checkBalance(accountNumber);

            assertEquals(balance, response);
        }
    }

    @Nested
    public class RealizeCredit {
        private int accountNumber = 10;
        private double balance = 200;

        @Test
        public void shouldRealizeCredit() {
            double value = 100;
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeCredit(accountNumber, value, false);

            assertEquals(balance + value, response);
        }

        @Test
        public void shouldNotRealizeCreditWhenValueIsNegative() {
            double value = -100;
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeCredit(accountNumber, value, false);

            assertEquals(balance, response);
        }

        @Test
        public void shouldAddBonusPointsForBonusAccount() {
            double value = 100;
            var expectedAccount = new BonusAccount(accountNumber, balance, 10);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeCredit(accountNumber, value, false);

            assertEquals(balance + value, response);
            assertEquals(11, expectedAccount.getPunctuation());
        }
    }

    @Nested
    public class RealizeDebit {
        private int accountNumber = 10;
        private double balance = 200;

        @Test
        public void shouldRealizeDebit() {
            double value = 100;
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeDebit(accountNumber, value);

            assertTrue(response.isPresent());
            assertEquals(balance - value, response.get());
        }

        @Test
        public void shouldNotRealizeDebitWhenValueIsNegative() {
            double value = -100;
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeDebit(accountNumber, value);

            assertTrue(response.isEmpty());
            verify(bankRepository, times(0)).saveAccount(expectedAccount);
        }

        @Test
        public void shouldNotAllowNegativeBalance() {
            double value = 300;
            var expectedAccount = new Account(accountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(accountNumber)).thenReturn(expectedAccount);

            var response = bankService.realizeDebit(accountNumber, value);

            assertTrue(response.isEmpty());
            verify(bankRepository, times(0)).saveAccount(expectedAccount);

        }
    }

    @Nested
    public class RealizeTransfer {
        private int originAccountNumber = 10;
        private int destinationAccountNumber = 20;
        private double balance = 200;

        @Test
        public void shouldRealizeTransfer() {
            double value = 100;
            var originAccount = new Account(originAccountNumber, balance);
            var destinationAccount = new Account(destinationAccountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(originAccountNumber)).thenReturn(originAccount);
            Mockito.when(bankRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(destinationAccount);

            var response = bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);

            assertTrue(response);
            assertEquals(balance - value, originAccount.getBalance());
            assertEquals(balance + value, destinationAccount.getBalance());
        }

        @Test
        public void shouldNotAllowNegativeTransferValue() {
            double value = -100;
            var originAccount = new Account(originAccountNumber, balance);
            var destinationAccount = new Account(destinationAccountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(originAccountNumber)).thenReturn(originAccount);
            Mockito.when(bankRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(destinationAccount);

            var response = bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);

            assertFalse(response);
            assertEquals(balance, originAccount.getBalance());
            assertEquals(balance, destinationAccount.getBalance());
        }

        @Test
        public void shouldNotAllowNegativeBalanceAfterTransfer() {
            double value = 300;
            var originAccount = new Account(originAccountNumber, balance);
            var destinationAccount = new Account(destinationAccountNumber, balance);
            Mockito.when(bankRepository.getAccountByAccountNumber(originAccountNumber)).thenReturn(originAccount);
            Mockito.when(bankRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(destinationAccount);

            var response = bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);

            assertFalse(response);
            assertEquals(balance, originAccount.getBalance());
            assertEquals(balance, destinationAccount.getBalance());
        }

        @Test
        public void shouldAddBonusPointsForBonusAccount() {
            double value = 200;
            var originAccount = new Account(originAccountNumber, balance);
            var destinationAccount = new BonusAccount(destinationAccountNumber, balance, 10);
            Mockito.when(bankRepository.getAccountByAccountNumber(originAccountNumber)).thenReturn(originAccount);
            Mockito.when(bankRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(destinationAccount);

            var response = bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);

            assertTrue(response);
            assertEquals(balance - value, originAccount.getBalance());
            assertEquals(balance + value, destinationAccount.getBalance());
            assertEquals(11, ((BonusAccount) destinationAccount).getPunctuation());
        }
    }

    @Nested
    public class PayFees {
        @Test
        public void shouldRenderInterestForSavingsAccount() {
            double fee = 5.0;

            var savingsAccount1 = new SavingsAccount(10, 200.0);
            var savingsAccount2 = new SavingsAccount(20, 300.0);
            var savingsAccount3 = new SavingsAccount(30, 400.0);

            Map<Integer, Account> accountsMap = new HashMap<>();
            accountsMap.put(savingsAccount1.getAccountNumber(), savingsAccount1);
            accountsMap.put(savingsAccount2.getAccountNumber(), savingsAccount2);
            accountsMap.put(savingsAccount3.getAccountNumber(), savingsAccount3);

            Mockito.when(bankRepository.findAll()).thenReturn(accountsMap);

            double expectedBalance1 = savingsAccount1.getBalance() + (savingsAccount1.getBalance() * fee / 100);
            double expectedBalance2 = savingsAccount2.getBalance() + (savingsAccount2.getBalance() * fee / 100);
            double expectedBalance3 = savingsAccount3.getBalance() + (savingsAccount3.getBalance() * fee / 100);

            bankService.payFees(fee);

            assertEquals(expectedBalance1, savingsAccount1.getBalance(), 0.01);
            assertEquals(expectedBalance2, savingsAccount2.getBalance(), 0.01);
            assertEquals(expectedBalance3, savingsAccount3.getBalance(), 0.01);

            verify(bankRepository, times(1)).saveAccount(savingsAccount1);
            verify(bankRepository, times(1)).saveAccount(savingsAccount2);
            verify(bankRepository, times(1)).saveAccount(savingsAccount3);
        }
    }
}
