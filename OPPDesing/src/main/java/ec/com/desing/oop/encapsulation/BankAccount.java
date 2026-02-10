package ec.com.desing.oop.encapsulation;

/**
 * Ejemplo de encapsulación: oculta los detalles internos y controla el acceso
 * a los datos mediante métodos públicos (getters y setters).
 * 
 * La encapsulación protege los datos y asegura que solo se puedan modificar
 * de manera controlada y validada.
 */
public class BankAccount {
    
    // Atributos privados - no accesibles directamente desde fuera de la clase
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private String accountType;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance >= 0 ? initialBalance : 0; // Validación
        this.accountType = accountType;
    }
    
    // Getters - métodos públicos para acceder a los datos privados
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    // Setters - métodos públicos para modificar los datos con validación
    
    /**
     * Establece el nombre del titular de la cuenta
     * @param accountHolder nombre del titular
     */
    public void setAccountHolder(String accountHolder) {
        if (accountHolder != null && !accountHolder.trim().isEmpty()) {
            this.accountHolder = accountHolder;
        } else {
            System.out.println("Error: El nombre del titular no puede estar vacío");
        }
    }
    
    /**
     * Establece el tipo de cuenta
     * @param accountType tipo de cuenta (SAVINGS, CHECKING, etc.)
     */
    public void setAccountType(String accountType) {
        if (accountType != null && !accountType.trim().isEmpty()) {
            this.accountType = accountType;
        }
    }
    
    // Métodos de negocio - encapsulan la lógica de operaciones
    
    /**
     * Deposita dinero en la cuenta
     * @param amount cantidad a depositar
     * @return true si el depósito fue exitoso, false en caso contrario
     */
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Depósito exitoso: $" + amount + ". Nuevo balance: $" + balance);
            return true;
        } else {
            System.out.println("Error: La cantidad a depositar debe ser mayor a 0");
            return false;
        }
    }
    
    /**
     * Retira dinero de la cuenta
     * @param amount cantidad a retirar
     * @return true si el retiro fue exitoso, false en caso contrario
     */
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Retiro exitoso: $" + amount + ". Nuevo balance: $" + balance);
            return true;
        } else if (amount > balance) {
            System.out.println("Error: Fondos insuficientes. Balance actual: $" + balance);
            return false;
        } else {
            System.out.println("Error: La cantidad a retirar debe ser mayor a 0");
            return false;
        }
    }
    
    /**
     * Transfiere dinero a otra cuenta
     * @param targetAccount cuenta destino
     * @param amount cantidad a transferir
     * @return true si la transferencia fue exitosa, false en caso contrario
     */
    public boolean transfer(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            System.out.println("Error: La cuenta destino no puede ser nula");
            return false;
        }
        
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            System.out.println("Transferencia exitosa de $" + amount + 
                             " a la cuenta " + targetAccount.getAccountNumber());
            return true;
        }
        return false;
    }
    
    /**
     * Muestra la información de la cuenta
     */
    public void displayAccountInfo() {
        System.out.println("=== Información de la Cuenta ===");
        System.out.println("Número de cuenta: " + accountNumber);
        System.out.println("Titular: " + accountHolder);
        System.out.println("Tipo de cuenta: " + accountType);
        System.out.println("Balance: $" + balance);
        System.out.println("================================");
    }
    
    // Nota: No hay setter para balance ni accountNumber
    // porque estos valores no deberían modificarse directamente
    // El balance solo cambia mediante operaciones controladas (deposit, withdraw, transfer)
}

