package imd.ufrn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public class getAccountByNumber {
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

}
