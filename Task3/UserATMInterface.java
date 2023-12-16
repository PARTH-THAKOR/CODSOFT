// ATM Interface

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Interfae for ATM Opretaions
interface ATMmachineOperations {

    // Withdraw
    public void withdraw(double amount);

    // Deposite
    public void depositing(double amount);

    // Check Balance
    public void checkBalance();

}

// User Bank Account Class
class UserBankAccount {

    // Initial Account Balance
    private double accountBalance = 50000;

    // Getter of AccountBalance
    public double getAccountBalance() {
        return accountBalance;
    }

    // Setter of AccountBalance
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

}

// ATMmachine Class has UserAccount and ATMOperations
class ATMmachine extends UserBankAccount implements ATMmachineOperations {

    // Withdraw Opration Body
    @Override
    public void withdraw(double amount) {
        if (getAccountBalance() < amount) {
            System.out.println("ACCOUNT HAS NOT ENOUGH MONEY TO WITHDRAW.");
        } else {
            setAccountBalance(getAccountBalance() - amount);
            System.out.println("AMOUNT " + amount + " WITHDRAWN.");
            System.out.println("AVAILABLE BALANCE : " + getAccountBalance());
        }
    }

    // Deposite Opration Body
    @Override
    public void depositing(double amount) {
        if (amount > 2000000) {
            System.out.println("ACCOUNT HAS A LIMIT FOR DEPOSITE IS 2000000.");
        } else {
            setAccountBalance(getAccountBalance() + amount);
            System.out.println("AMOUNT " + amount + " DEPOSITED.");
            System.out.println("AVAILABLE BALANCE : " + getAccountBalance());
        }
    }

    // CheckBalance Opration Body
    @Override
    public void checkBalance() {
        System.out.println("AVAILABLE BALANCE : " + getAccountBalance());
    }

}

// UserATMInterface Class
public class UserATMInterface {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

        // Object of ATMmachine
        ATMmachine atMmachine = new ATMmachine();
        while (true) {
            try {
                System.out.println("\nWELCOME IN ATM INTERFACE\n");

                // Opreation Choice Selection Precess
                System.out.println("PRESS 1 FOR WITHDRAW AMOUNT.");
                System.out.println("PRESS 2 FOR DEPOSITE AMOUNT.");
                System.out.println("PRESS 3 FOR CHECK ACCOUNT BALANCE.");
                int choice = Integer.parseInt(scanner.readLine());

                // Switch Task according to choice
                switch (choice) {
                    case 1:
                        // Withdraw Process
                        System.out.print("\nENTER AMOUNT TO WITHDRAW : ");
                        double withdrawAmount = Double.parseDouble(scanner.readLine());
                        atMmachine.withdraw(withdrawAmount);
                        break;
                    case 2:
                        // Deposite Process
                        System.out.print("\nENTER AMOUNT TO DEPOSITE : ");
                        double depositAmount = Double.parseDouble(scanner.readLine());
                        atMmachine.depositing(depositAmount);
                        break;
                    case 3:
                        // CheckBalance Process
                        atMmachine.checkBalance();
                        break;
                    default:
                        // Invalid Choice Message
                        System.out.println("\nINVALID CHOICE.");
                        break;
                }
            } catch (Exception e) {
                // Invalid Input Message
                System.out.println("\nINVALID INPUT.");
            }
        }
    }

}
