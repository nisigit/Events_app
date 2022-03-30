package model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public abstract class User {

    private String email;
    private byte[] passHash;
    private String paymentAccountEmail;

    protected User(String email, String password, String paymentAccountEmail) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.email = email;
        this.paymentAccountEmail = paymentAccountEmail;
        passHash = this.hashPassword(password);
    };

    // TODO: Have to hash password using Becrypt function given in the javadoc.
    // Can also make it static.
    private byte[] hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash;
    }

    public String getEmail() {
        return email;
    };

    public void setEmail(String newEmail) {
        this.email = newEmail;
    };

    public boolean checkPasswordMatch(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.passHash == this.hashPassword(password);
    };

    public void updatePassword(String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.passHash = this.hashPassword(newPassword);
    };

    public String getPaymentAccountEmail() {
        return paymentAccountEmail;
    };

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {
        this.paymentAccountEmail = newPaymentAccountEmail;
    };

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", paymentAccountEmail='" + paymentAccountEmail + '\'' +
                '}';
    }
}