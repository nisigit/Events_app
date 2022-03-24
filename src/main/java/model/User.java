package model;

public abstract class User extends Object {

    protected User(String email, String password, String paymentAccountEmail) {

    };

    public String getEmail() {

    };

    public void setEmail(String newEmail) {

    };

    public boolean checkPasswordMatch(String password) {

    };

    public void updatePassword(String newPassword) {

    };

    public String getPaymentAccountEmail() {

    };

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {

    };

    public String toString() {

    };

}