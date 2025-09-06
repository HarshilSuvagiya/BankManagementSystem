
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class BankAccount {

    // Displaying a welcome message to the user
    public void welcome() {
        System.out.println("======================================================================================");
        System.out.println("||                                                                                  ||");
        System.out.println("||                          WELCOME TO THE BANK MANAGEMENT SYSTEM                   ||");
        System.out.println("||                                                                                  ||");
        System.out.println("======================================================================================");
    }

    // Class representing a transaction
    class Transaction {

        int accountNo;
        String type; // Deposit, Withdraw, Transfer
        double amount;
        Date date;

        Transaction(int accountNo, String type, double amount) {
            this.accountNo = accountNo;
            this.type = type;
            this.amount = amount;
            this.date = new Date();
        }

        void display() {
            System.out.println("Account: " + accountNo + " | Type: " + type + " | Amount: " + amount + " | Date: " + date);
        }
    }

    // Class representing a loan
    class Loan {

        int accountNo;
        double amount;
        double interestRate;
        int duration; // in months
        double monthlyPayment;

        Loan(int accountNo, double amount, double interestRate, int duration) {
            this.accountNo = accountNo;
            this.amount = amount;
            this.interestRate = interestRate;
            this.duration = duration;
            this.monthlyPayment = (amount * interestRate / 100) / duration;
        }

        void display() {
            System.out.println("Loan Amount: " + amount + " | Interest Rate: " + interestRate + "% | Duration: " + duration + " months | Monthly Payment: " + monthlyPayment);
        }
    }

    // Class representing a fixed deposit
    class FixedDeposit {

        int accountNo;
        double amount;
        double interestRate;
        int duration; // in months
        double maturityAmount;

        FixedDeposit(int accountNo, double amount, double interestRate, int duration) {
            this.accountNo = accountNo;
            this.amount = amount;
            this.interestRate = interestRate;
            this.duration = duration;
            this.maturityAmount = amount + (amount * interestRate / 100 * duration / 12);
        }

        void display() {
            System.out.println("FD Amount: " + amount + " | Interest Rate: " + interestRate + "% | Duration: " + duration + " months | Maturity Amount: " + maturityAmount);
        }
    }

    // Class representing an account
    class Account {

        String name;
        int accountNo;
        String accountType;
        double balance;
        String mobileNo;
        String email;
        String address;
        String username;
        String password;
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Loan> loans = new ArrayList<>();
        ArrayList<FixedDeposit> fds = new ArrayList<>();

        Account(String name, int accountNo, String accountType, double balance, String mobileNo, String email, String address, String username, String password) {
            this.name = name;
            this.accountNo = accountNo;
            this.accountType = accountType;
            this.balance = balance;
            this.mobileNo = mobileNo;
            this.email = email;
            this.address = address;
            this.username = username;
            this.password = password;
        }

        void display() {
            System.out.println("Name: " + name);
            System.out.println("Account Number: " + accountNo);
            System.out.println("Account Type: " + accountType);
            System.out.println("Balance: " + balance);
            System.out.println("Mobile Number: " + mobileNo);
            System.out.println("Email: " + email);
            System.out.println("Address: " + address);
            System.out.println();
        }

        void displayTransactions() {
            System.out.println("Transaction History:");
            for (Transaction t : transactions) {
                t.display();
            }
        }

        void displayLoans() {
            System.out.println("Loans:");
            for (Loan l : loans) {
                l.display();
            }
        }

        void displayFDs() {
            System.out.println("Fixed Deposits:");
            for (FixedDeposit fd : fds) {
                fd.display();
            }
        }
    }

    // Bank class that manages all the operations for the accounts
    class Bank {

        Scanner sc = new Scanner(System.in);
        Account[] accounts = new Account[100];
        int nextAccountNo = 1;
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        // User authentication
        boolean authenticate(String username, String password) {
            for (Account account : accounts) {
                if (account != null && account.username.equals(username) && account.password.equals(password)) {
                    return true;
                }
            }
            return false;
        }

        // Method to create a new account
        void createAccount() {
            System.out.println("Enter account holder name:");
            String name = sc.nextLine();
            if (!isValidName(name)) {
                System.out.println("Name must contain only letters and not be empty.");
                return;
            }
            System.out.println("Account types: \nSaving - 1000 \nCurrent - 2000");
            System.out.println("Enter account type:");
            String accountType = sc.nextLine();
            setAccountType(accountType);

            System.out.println("Enter initial deposit:");
            double balance = sc.nextDouble();
            sc.nextLine();
            if (balance <= 0) {
                System.out.println("Initial deposit must be positive.");
                return;
            }
            System.out.println("Enter mobile number:");
            String mobileNo = sc.nextLine();
            validMobile(mobileNo);

            System.out.println("Enter email:");
            String email = sc.nextLine();
            validEmail(email);

            System.out.println("Enter address:");
            String address = sc.nextLine();

            System.out.println("Enter username:");
            String username = sc.nextLine();
            System.out.println("Enter password:");
            String password = sc.nextLine();

            Account account = new Account(name, nextAccountNo, accountType, balance, mobileNo, email, address, username, password);
            accounts[nextAccountNo - 1] = account;
            nextAccountNo++;
            System.out.println("Account created successfully! Your account number is " + (nextAccountNo - 1));
        }

        // Validates the account type
        void setAccountType(String type) {
            while (!(type.equalsIgnoreCase("Saving") || type.equalsIgnoreCase("Current"))) {
                System.out.println("Invalid account type. Please enter a valid account type:");
                type = sc.nextLine();
            }
        }

        // Validates the mobile number
        void validMobile(String mobileNo) {
            while (!isValidMobile(mobileNo)) {
                System.out.println("Enter a valid 10-digit mobile number:");
                mobileNo = sc.nextLine();
            }
        }

        boolean isValidMobile(String mobile) {
            if (mobile.length() != 10) {
                return false;
            }
            char firstDigit = mobile.charAt(0);
            if (firstDigit < '6' || firstDigit > '9') {
                return false;
            }
            for (int i = 0; i < mobile.length(); i++) {
                if (mobile.charAt(i) < '0' || mobile.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        }

        // Validates the email address
        void validEmail(String email) {
            while (!isValidEmail(email)) {
                System.out.println("Enter a valid email address (example@gmail.com):");
                email = sc.nextLine();
            }
        }

        boolean isValidEmail(String email) {
            return email.contains("@") && email.contains(".");
        }

        boolean isValidName(String name) {
            if (name == null || name.trim().isEmpty()) {
                return false;
            }
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    return false;
                }
            }
            return true;
        }

        // Method to deposit money into an account
        void deposit() {
            System.out.println("=====================================");
            System.out.println("          Deposit Money");
            System.out.println("=====================================");
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            if (accountNo <= 0 || accountNo >= nextAccountNo) {
                System.out.println("Invalid account number.");
                return;
            }
            System.out.println("Enter amount to deposit:");
            double amount = sc.nextDouble();
            sc.nextLine();
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.balance += amount;
                    Transaction t = new Transaction(accountNo, "Deposit", amount);
                    account.transactions.add(t);
                    allTransactions.add(t);
                    System.out.println("=====================================");
                    System.out.println("Deposit successful!");
                    System.out.println("New balance: $" + String.format("%.2f", account.balance));
                    System.out.println("=====================================");
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Method to withdraw money from an account
        void withdraw() {
            System.out.println("=====================================");
            System.out.println("          Withdraw Money");
            System.out.println("=====================================");
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            if (accountNo <= 0 || accountNo >= nextAccountNo) {
                System.out.println("Invalid account number.");
                return;
            }
            System.out.println("Enter amount to withdraw:");
            double amount = sc.nextDouble();
            sc.nextLine();
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    if (account.balance >= amount) {
                        account.balance -= amount;
                        Transaction t = new Transaction(accountNo, "Withdraw", amount);
                        account.transactions.add(t);
                        allTransactions.add(t);
                        System.out.println("=====================================");
                        System.out.println("Withdrawal successful!");
                        System.out.println("New balance: $" + String.format("%.2f", account.balance));
                        System.out.println("=====================================");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Method to update balance
        void updateBalance() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter new balance:");
            double newBalance = sc.nextDouble();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.balance = newBalance;
                    System.out.println("Balance updated successfully! New balance: " + account.balance);
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Method to transfer funds
        void transferFunds() {
            System.out.println("=====================================");
            System.out.println("          Transfer Funds");
            System.out.println("=====================================");
            System.out.println("Enter sender account number:");
            int senderNo = sc.nextInt();
            sc.nextLine();
            if (senderNo <= 0 || senderNo >= nextAccountNo) {
                System.out.println("Invalid sender account number.");
                return;
            }
            System.out.println("Enter receiver account number:");
            int receiverNo = sc.nextInt();
            sc.nextLine();
            if (receiverNo <= 0 || receiverNo >= nextAccountNo) {
                System.out.println("Invalid receiver account number.");
                return;
            }
            if (senderNo == receiverNo) {
                System.out.println("Sender and receiver cannot be the same.");
                return;
            }
            System.out.println("Enter amount to transfer:");
            double amount = sc.nextDouble();
            sc.nextLine();
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            Account sender = null, receiver = null;
            for (Account account : accounts) {
                if (account != null && account.accountNo == senderNo) {
                    sender = account;
                }
                if (account != null && account.accountNo == receiverNo) {
                    receiver = account;
                }
            }
            if (sender != null && receiver != null && sender.balance >= amount) {
                sender.balance -= amount;
                receiver.balance += amount;
                Transaction t1 = new Transaction(senderNo, "Transfer Out", amount);
                Transaction t2 = new Transaction(receiverNo, "Transfer In", amount);
                sender.transactions.add(t1);
                receiver.transactions.add(t2);
                allTransactions.add(t1);
                allTransactions.add(t2);
                System.out.println("=====================================");
                System.out.println("Transfer successful!");
                System.out.println("=====================================");
            } else {
                System.out.println("Transfer failed. Check account numbers and balance.");
            }
        }

        // Method to apply for loan
        void applyLoan() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter loan amount:");
            double amount = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter interest rate:");
            double rate = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter duration in months:");
            int duration = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    Loan loan = new Loan(accountNo, amount, rate, duration);
                    account.loans.add(loan);
                    System.out.println("Loan applied successfully!");
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Method to create fixed deposit
        void createFD() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter FD amount:");
            double amount = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter interest rate:");
            double rate = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter duration in months:");
            int duration = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    FixedDeposit fd = new FixedDeposit(accountNo, amount, rate, duration);
                    account.fds.add(fd);
                    System.out.println("Fixed Deposit created successfully!");
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Method to calculate interest
        void calculateInterest() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    double interest = account.balance * 0.05; // 5% annual interest
                    System.out.println("Interest for account " + accountNo + ": " + interest);
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Displays all accounts details
        void displayAccounts() {
            System.out.println("Account Details:");
            for (Account account : accounts) {
                if (account != null) {
                    account.display();
                }
            }
        }

        // Searches for an account based on account number
        void searchAccount() {
            System.out.println("Enter account number to search:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.display();
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Removes an account based on account number
        void removeAccount() {
            System.out.println("Enter account number to remove:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] != null && accounts[i].accountNo == accountNo) {
                    accounts[i] = null;
                    System.out.println("Account removed successfully.");
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Updates the details of an account
        void updateAccountDetails() {
            System.out.println("Enter account number to update:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            int choice;
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] != null && accounts[i].accountNo == accountNo) {
                    System.out.println("1. Change account holder name");
                    System.out.println("2. Change account type");
                    System.out.println("3. Change mobile number");
                    System.out.println("4. Change email");
                    System.out.println("5. Change address");
                    System.out.println("Enter your choice:");
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter new name:");
                            String newName = sc.nextLine();
                            if (!isValidName(newName)) {
                                System.out.println("Name must contain only letters and not be empty.");
                                break;
                            }
                            accounts[i].name = newName;
                            break;
                        case 2:
                            System.out.println("Account types: \nSaving - 1000 \nCurrent - 2000");
                            System.out.println("Enter new account type:");
                            String newAccountType = sc.nextLine();
                            setAccountType(newAccountType);
                            accounts[i].accountType = newAccountType;
                            break;
                        case 3:
                            System.out.println("Enter new mobile number:");
                            String newMobileNo = sc.nextLine();
                            validMobile(newMobileNo);
                            accounts[i].mobileNo = newMobileNo;
                            break;
                        case 4:
                            System.out.println("Enter new email:");
                            String newEmail = sc.nextLine();
                            accounts[i].email = newEmail;
                            break;
                        case 5:
                            System.out.println("Enter new address:");
                            String newAddress = sc.nextLine();
                            accounts[i].address = newAddress;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    System.out.println("Account details updated successfully.");
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Displays statistics of the bank
        void displayBankStatistics() {
            int savingCount = 0;
            int currentCount = 0;
            double totalBalance = 0;
            for (Account account : accounts) {
                if (account != null) {
                    if (account.accountType.equalsIgnoreCase("Saving")) {
                        savingCount++;
                    } else if (account.accountType.equalsIgnoreCase("Current")) {
                        currentCount++;
                    }
                    totalBalance += account.balance;
                }
            }
            System.out.println("Bank Statistics:");
            System.out.println("Saving Accounts: " + savingCount);
            System.out.println("Current Accounts: " + currentCount);
            System.out.println("Total Balance in Bank: " + totalBalance);
        }

        // Displays transaction history for an account
        void displayTransactionHistory() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.displayTransactions();
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Displays loans for an account
        void displayLoans() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.displayLoans();
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Displays fixed deposits for an account
        void displayFDs() {
            System.out.println("Enter account number:");
            int accountNo = sc.nextInt();
            sc.nextLine();
            for (Account account : accounts) {
                if (account != null && account.accountNo == accountNo) {
                    account.displayFDs();
                    return;
                }
            }
            System.out.println("Account not found.");
        }

        // Generates a report
        void generateReport() {
            System.out.println("Bank Report:");
            displayBankStatistics();
            System.out.println("All Transactions:");
            for (Transaction t : allTransactions) {
                t.display();
            }
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.welcome();
        Bank b = bankAccount.new Bank();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Update Balance");
            System.out.println("5. Transfer Funds");
            System.out.println("6. Apply for Loan");
            System.out.println("7. Create Fixed Deposit");
            System.out.println("8. Calculate Interest");
            System.out.println("9. Search Account Details");
            System.out.println("10. Display Account Details");
            System.out.println("11. Remove Account");
            System.out.println("12. Update Account Details");
            System.out.println("13. Display Bank Statistics");
            System.out.println("14. Display Transaction History");
            System.out.println("15. Display Loans");
            System.out.println("16. Display Fixed Deposits");
            System.out.println("17. Generate Report");
            System.out.println("18. Exit");
            System.out.print("Enter your choice:");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    b.createAccount();
                    break;
                case 2:
                    b.deposit();
                    break;
                case 3:
                    b.withdraw();
                    break;
                case 4:
                    b.updateBalance();
                    break;
                case 5:
                    b.transferFunds();
                    break;
                case 6:
                    b.applyLoan();
                    break;
                case 7:
                    b.createFD();
                    break;
                case 8:
                    b.calculateInterest();
                    break;
                case 9:
                    b.searchAccount();
                    break;
                case 10:
                    b.displayAccounts();
                    break;
                case 11:
                    b.removeAccount();
                    break;
                case 12:
                    b.updateAccountDetails();
                    break;
                case 13:
                    b.displayBankStatistics();
                    break;
                case 14:
                    b.displayTransactionHistory();
                    break;
                case 15:
                    b.displayLoans();
                    break;
                case 16:
                    b.displayFDs();
                    break;
                case 17:
                    b.generateReport();
                    break;
                case 18:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 18);
    }
}
