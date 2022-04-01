package external;

import java.util.ArrayList;
import java.util.List;

public class MockPaymentSystem implements PaymentSystem {

    private List<Transaction> transactions;

    public MockPaymentSystem() {
        transactions = new ArrayList<>();
    }

    // TODO: Discuss when to decline a payment.
    @Override
    public boolean processPayment(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        Transaction newTransaction = new Transaction(buyerAccountEmail, sellerAccountEmail, transactionAmount, TransactionType.PAYMENT);
        transactions.add(newTransaction);
        return true;
    }

    @Override
    public boolean processRefund(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        Transaction newTransaction = new Transaction(buyerAccountEmail, sellerAccountEmail, transactionAmount, TransactionType.REFUND);
        for (Transaction transaction : transactions) {
            if (transaction.equals(newTransaction)) {
                transactions.add(newTransaction);
                return true;
            }
        }
        return false;
    }

}