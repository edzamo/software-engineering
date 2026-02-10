package ec.com.desing.oop.encapsulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase BankAccount
 */
@DisplayName("Tests para BankAccount - Encapsulación")
class BankAccountTest {
    
    private BankAccount account;
    
    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC001", "Juan Pérez", 1000.0, "SAVINGS");
    }
    
    @Test
    @DisplayName("Debería crear una cuenta bancaria correctamente")
    void shouldCreateBankAccount() {
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountNumber());
        assertEquals("Juan Pérez", account.getAccountHolder());
        assertEquals(1000.0, account.getBalance());
        assertEquals("SAVINGS", account.getAccountType());
    }
    
    @Test
    @DisplayName("Debería validar el balance inicial negativo")
    void shouldValidateNegativeInitialBalance() {
        BankAccount negativeAccount = new BankAccount("ACC002", "Test", -100.0, "CHECKING");
        assertEquals(0.0, negativeAccount.getBalance());
    }
    
    @Test
    @DisplayName("Debería depositar dinero correctamente")
    void shouldDepositMoney() {
        assertTrue(account.deposit(500.0));
        assertEquals(1500.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería rechazar depósitos negativos o cero")
    void shouldRejectInvalidDeposits() {
        assertFalse(account.deposit(-100.0));
        assertFalse(account.deposit(0.0));
        assertEquals(1000.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería retirar dinero correctamente")
    void shouldWithdrawMoney() {
        assertTrue(account.withdraw(300.0));
        assertEquals(700.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería rechazar retiros que excedan el balance")
    void shouldRejectExcessiveWithdrawals() {
        assertFalse(account.withdraw(2000.0));
        assertEquals(1000.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería rechazar retiros negativos o cero")
    void shouldRejectInvalidWithdrawals() {
        assertFalse(account.withdraw(-100.0));
        assertFalse(account.withdraw(0.0));
        assertEquals(1000.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería transferir dinero entre cuentas")
    void shouldTransferMoney() {
        BankAccount targetAccount = new BankAccount("ACC002", "María", 500.0, "CHECKING");
        
        assertTrue(account.transfer(targetAccount, 300.0));
        assertEquals(700.0, account.getBalance());
        assertEquals(800.0, targetAccount.getBalance());
    }
    
    @Test
    @DisplayName("Debería rechazar transferencia a cuenta nula")
    void shouldRejectTransferToNullAccount() {
        assertFalse(account.transfer(null, 100.0));
        assertEquals(1000.0, account.getBalance());
    }
    
    @Test
    @DisplayName("Debería actualizar el nombre del titular")
    void shouldUpdateAccountHolder() {
        account.setAccountHolder("Pedro Sánchez");
        assertEquals("Pedro Sánchez", account.getAccountHolder());
    }
    
    @Test
    @DisplayName("Debería rechazar nombres vacíos o nulos")
    void shouldRejectInvalidAccountHolder() {
        String originalName = account.getAccountHolder();
        account.setAccountHolder("");
        account.setAccountHolder(null);
        assertEquals(originalName, account.getAccountHolder());
    }
}

