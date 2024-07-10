package com.paymybuddy.api.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.api.exception.CustomException;
import com.paymybuddy.api.models.Transaction;
import com.paymybuddy.api.models.UserModel;
import com.paymybuddy.api.repositories.TransactionRepository;
import com.paymybuddy.api.services.TransactionServiceImpl;
import com.paymybuddy.api.services.UserServiceImpl;

@SpringBootTest
public class TransactionServiceImplTest {

	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private UserServiceImpl userServiceImpl;

	@Test
	public void testSaveTransaction() {
		Transaction transaction = new Transaction();
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		Transaction savedTransaction = transactionServiceImpl.saveTransaction(transaction);

		assertNotNull(savedTransaction);
		verify(transactionRepository, times(1)).save(transaction);
	}

    @Test
    public void testFindAllTransactionsByUser() {
        UserModel user = new UserModel();
        when(transactionRepository.findBySenderOrReceiver(user, user)).thenReturn(new ArrayList<>());

        List<Transaction> transactions = transactionServiceImpl.findAllTransactionsByUser(user);

        assertNotNull(transactions);
        verify(transactionRepository, times(1)).findBySenderOrReceiver(user, user);
    }
    
    @Test
    public void testProcessTransaction_InsufficientBalance() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);

        UserModel sender = new UserModel();
        sender.setBalance(50.0);

        when(userServiceImpl.findByEmail(anyString())).thenReturn(Optional.of(sender));

        Exception exception = assertThrows(CustomException.class, () -> {
            transactionServiceImpl.processTransaction(transaction, "alice@example.com");
        });

        String expectedMessage = "Solde insuffisant pour effectuer ce transfert.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeposit() {
        UserModel user = new UserModel();
        user.setBalance(300.0);  // Solde initial

        double amount = 100.0;
        double transactionTax = amount * 0.005;  // 0.5% de taxe
        double expectedBalance = 300.0 + (amount - transactionTax);  // Solde attendu après dépôt et déduction de la taxe

        transactionServiceImpl.deposit(user, amount);

        assertEquals(expectedBalance, user.getBalance(), 0.01);
        verify(userServiceImpl, times(1)).save(user);
    }
    
    @Test
    public void testTransferToBank() {
        UserModel user = new UserModel();
        user.setBalance(100.0);
        user.setBankAccountNumber("123456");
        user.setBankRoutingNumber("654321");

        double amount = 50.0;

        transactionServiceImpl.transferToBank(user, amount);

        assertEquals(user.getBalance(), 50.0 - (amount * 0.5 / 100), 0.01);
        verify(userServiceImpl, times(1)).save(user);
    }
    
    @Test
    public void testHasBankDetails() {
        UserModel user = new UserModel();
        user.setBankAccountNumber("123456");
        user.setBankRoutingNumber("654321");

        assertTrue(transactionServiceImpl.hasBankDetails(user));
    }
}
