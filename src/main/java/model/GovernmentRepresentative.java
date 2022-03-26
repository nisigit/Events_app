package model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class GovernmentRepresentative extends User {

    private String email;
    private String password;
    private String paymentAccountEmail;


    public GovernmentRepresentative(String email, String password, String paymentAccountEmail) throws InvalidKeySpecException, NoSuchAlgorithmException {
        super(email, password, paymentAccountEmail);
    };

    @Override
    public String toString() {
        return "GovernmentRepresentative{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", paymentAccountEmail='" + paymentAccountEmail + '\'' +
                '}';
    }
};
