/* Class for handling password hashing (PBKDF2WithHmacSHA1) and salting.
 * 
 * Sources for implementing Java password hashing and salting:
 * http://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
 * http://stackoverflow.com/questions/14413169/which-java-library-provides-base64-encoding-decoding
 */
package project.hr;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;
import javax.xml.bind.DatatypeConverter;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Samuli
 */
public class PasswordSecurity {
    
    public PasswordSecurity() {
    
    }
    
    /* Generate hash and salt for a given password.
     * Returns hash and salt separated by '.' (<hash>.<salt>).
    */
    public String generateHashedSaltedPassword(String password) {
        Random random = new SecureRandom();
        byte[] salt = new byte[16];
        SecretKeyFactory secretKeyFactory = null;
        byte[] hash = null;
        String hashAndSalt = null;
        
        random.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 
                                        128);
        
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA1");
            hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            // Should be thrown?
        }
        
        hashAndSalt = DatatypeConverter.printBase64Binary(hash) + "." + 
                DatatypeConverter.printBase64Binary(salt);
        
        return hashAndSalt;
    }
    
    /* Checks password validity by recreating hash from given
     * password and salt, matches the outcome with given hash (separated
     * by '.' [<hash>.<salt>] in the parameter).
    */
    public boolean isPasswordValid(String password, String passwordHashAndSalt){
        boolean isValidPassword = false;

        System.out.println("validation given pw: " + passwordHashAndSalt);
        
        String[] hashAndSaltSplitted = passwordHashAndSalt.split("\\.");
        
        System.out.println("hash"+hashAndSaltSplitted[0]+"salt"+hashAndSaltSplitted[1]);
        
        String givenHash = hashAndSaltSplitted[0];
        String givenSalt = hashAndSaltSplitted[1];
        
        byte[] salt = DatatypeConverter.parseBase64Binary(givenSalt);
        
        //random.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 
                                        128);
        
        SecretKeyFactory secretKeyFactory = null;
        byte[] hash = null;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA1");
            hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            // Should be thrown?
        }
        
        isValidPassword = Objects.equals(
                DatatypeConverter.printBase64Binary(hash), givenHash);
        
        System.out.println(DatatypeConverter.printBase64Binary(hash) + "." + 
                DatatypeConverter.printBase64Binary(salt));
        
        return isValidPassword;
    }
}
