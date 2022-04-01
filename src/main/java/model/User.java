package model;

import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class User {

    private String email;
    private String paymentAccountEmail;
    private String passHashString;

    protected User(String email, String password, String paymentAccountEmail) {
        this.email = email;
        this.paymentAccountEmail = paymentAccountEmail;
        passHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public boolean checkPasswordMatch(String password) {
        String inputPassHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return passHashString.equals(inputPassHash);
    }

    public void updatePassword(String newPassword) {
        passHashString = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
    }

    public String getPaymentAccountEmail() {
        return paymentAccountEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {
        this.paymentAccountEmail = newPaymentAccountEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", paymentAccountEmail='" + paymentAccountEmail + '\'' +
                '}';
    }
}