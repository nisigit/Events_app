package external;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private String buyerAccountEmail;
    private String sellerAccountEmail;
    private double transactionAmount;
    private LocalDateTime transactionTime;
    private TransactionType transactionType;

    /**
     * Class that stores the information of each transaction
     * @param buyerAccountEmail buyer's email used for payments
     * @param sellerAccountEmail seller's email used for payments
     * @param transactionAmount the amount of current transaction
     * @param transactionType if it's payment or refund
     */
    Transaction(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount, TransactionType transactionType) {
        this.buyerAccountEmail = buyerAccountEmail;
        this.sellerAccountEmail = sellerAccountEmail;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionTime = LocalDateTime.now();
    }

    public String getBuyerAccountEmail() {
        return buyerAccountEmail;
    }

    public String getSellerAccountEmail() {
        return sellerAccountEmail;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.transactionAmount, transactionAmount) == 0 && Objects.equals(buyerAccountEmail, that.buyerAccountEmail) && Objects.equals(sellerAccountEmail, that.sellerAccountEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerAccountEmail, sellerAccountEmail, transactionAmount);
    }
}
