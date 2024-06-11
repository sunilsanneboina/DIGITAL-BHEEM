import java.util.Scanner;
// Transaction class to represent each transaction
class Transaction {
    private String type;
    private double amount;
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
}
// Account class to represent user's account
class Account {
    private String userId;
    private String pin;
    private double balance;
    private Transaction[] transactions;
    private int numOfTransactions;
    public Account(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0;
        this.transactions = new Transaction[100]; // Assume a maximum of 100 transactions
        this.numOfTransactions = 0;
    }
    public boolean authenticate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }
    public void deposit(double amount) {
        balance += amount;
        transactions[numOfTransactions++] = new Transaction("Deposit", amount);
    }
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions[numOfTransactions++] = new Transaction("Withdraw", amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactions[numOfTransactions++] = new Transaction("Transfer", amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (int i = 0; i < numOfTransactions; i++) {
            System.out.println(transactions[i].getType() + ": " + transactions[i].getAmount());
        }
    }
    public double getBalance() {
        return balance;
    }
}
// ATM class to manage ATM operations
public class ATM {
    private Account account;
    private Scanner scanner;
    public ATM() {
        this.scanner = new Scanner(System.in);
    }
    public void start() {
        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
// Dummy accounts for demonstration
        Account dummyAccount = new Account("user123", "1234");
        dummyAccount.deposit(1000);

        if (dummyAccount.authenticate(userId, pin)) {
            account = dummyAccount;
            showMenu();
        } else {
            System.out.println("Invalid User ID or PIN");
        }
    }
    private void showMenu() {
        int choice;
        do {
            System.out.println("\n1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    account.printTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientUserId = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    // For simplicity, assume recipient's account is same as logged-in user
                    Account recipient = account;
                    account.transfer(recipient, transferAmount);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 5);
    }
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}