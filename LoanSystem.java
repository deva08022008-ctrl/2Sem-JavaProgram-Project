import java.util.*;

// Customer class
class Customer {
    String name;
    double monthlyIncome;
    int creditScore;

    Customer(String name, double income, int creditScore) {
        this.name = name;
        this.monthlyIncome = income;
        this.creditScore = creditScore;
    }
}

// Loan class
class Loan {
    double loanAmount;
    double annualInterestRate;
    int tenureMonths;
    double emi;

    Loan(double amount, double rate, int tenure) {
        this.loanAmount = amount;
        this.annualInterestRate = rate;
        this.tenureMonths = tenure;
        this.emi = calculateEMI();
    }

    // EMI Calculation
    double calculateEMI() {
        double monthlyRate = annualInterestRate / (12 * 100);
        double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
        return emi;
    }
}

// Loan Service
class LoanService {

    // Check Eligibility
    boolean isEligible(Customer c, double requestedLoan) {
        if (c.creditScore < 650) {
            System.out.println("Low credit score!");
            return false;
        }

        double maxEligibleLoan = c.monthlyIncome * 20; // simple rule
        if (requestedLoan > maxEligibleLoan) {
            System.out.println("Loan exceeds eligibility!");
            return false;
        }

        return true;
    }
}

// EMI Tracker
class EMITracker {
    int totalMonths;
    int paidMonths = 0;

    EMITracker(int totalMonths) {
        this.totalMonths = totalMonths;
    }

    void payEMI() {
        if (paidMonths < totalMonths) {
            paidMonths++;
            System.out.println("EMI Paid for month: " + paidMonths);
        } else {
            System.out.println(" Loan Fully Paid!");
        }
    }

    void showStatus() {
        System.out.println(" EMI Status: " + paidMonths + "/" + totalMonths + " months paid");
    }
}

// Main Application
public class LoanSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input Customer Details
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Monthly Income: ");
        double income = sc.nextDouble();

        System.out.print("Enter Credit Score(of 900): ");
        int credit = sc.nextInt();

        Customer customer = new Customer(name, income, credit);

        // Loan Request
        System.out.print("Enter Loan Amount: ");
        double loanAmount = sc.nextDouble();

        System.out.print("Enter Interest Rate (%): ");
        double rate = sc.nextDouble();

        System.out.print("Enter Tenure (months): ");
        int tenure = sc.nextInt();

        LoanService service = new LoanService();

        // Eligibility Check
        if (!service.isEligible(customer, loanAmount)) {
            System.out.println(" Loan Rejected!");
            return;
        }

        System.out.println(" Loan Approved!");

        // Loan Creation
        Loan loan = new Loan(loanAmount, rate, tenure);
        System.out.println(" EMI Amount: " + loan.emi);

        // EMI Tracking
        EMITracker tracker = new EMITracker(tenure);

        while (true) {
            System.out.println("\n1. Pay EMI");
            System.out.println("2. View Status");
            System.out.println("3. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    tracker.payEMI();
                    break;
                case 2:
                    tracker.showStatus();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}