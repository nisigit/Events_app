package model;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class User {

    private String email;
    private String paymentAccountEmail;
    private String passHash;


    protected User(String email, String password, String paymentAccountEmail) {
        this.email = email;
        this.paymentAccountEmail = paymentAccountEmail;
        passHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public boolean checkPasswordMatch(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passHash);
        return result.verified;
    }

    public void updatePassword(String newPassword) {
        passHash = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (paymentAccountEmail != null ? !paymentAccountEmail.equals(user.paymentAccountEmail) : user.paymentAccountEmail != null)
            return false;
        return passHash != null ? passHash.equals(user.passHash) : user.passHash == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (paymentAccountEmail != null ? paymentAccountEmail.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        return result;
    }
}