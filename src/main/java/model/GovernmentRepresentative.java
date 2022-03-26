ackage model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class GovernmentRepresentative extends User {

    public GovernmentRepresentative(String email, String password, String paymentAccountEmail) throws InvalidKeySpecException, NoSuchAlgorithmException {
        super(email, password, paymentAccountEmail);
    };

    @Override
    public String toString() {
        return super.toString();
    }
};
