package external;

import java.util.ArrayList;
import java.util.List;

public class MockPaymentSystem implements PaymentSystem {

    private List<Transaction> transactions;

    public MockPaymentSystem() {
        transactions = new ArrayList<>();
    }

    @Override
    public boolean processPayment(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        // Create a new payment transaction object of current payment and add it to the list
        Transaction newTransaction = new Transaction(buyerAccountEmail, sellerAccountEmail, transactionAmount, TransactionType.PAYMENT);
        transactions.add(newTransaction);
        return true;
    }

    @Override
    public boolean processRefund(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        // Create a new refund transaction object and add it to the list
        Transaction newTransaction = new Transaction(buyerAccountEmail, sellerAccountEmail, transactionAmount, TransactionType.REFUND);
        // Successful only if all the information matches an existed payment transaction
        for (Transaction transaction : transactions) {
            if (transaction.equals(newTransaction)) {
                transactions.add(newTransaction);
                return true;
            }
        }
        return false;
    }

}