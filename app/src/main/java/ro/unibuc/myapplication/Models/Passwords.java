package ro.unibuc.myapplication.Models;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Passwords {
    String plainText;
    String salt;
    final static String charset = "UTF-8";

    public Passwords(String pass, String name){
        plainText = pass;
        salt = name;
    }

    public void setPlainText(String password){
        plainText = password;
    }

    public void setSalt(String name){
        salt = name;
    }

    public String calculateHash() {
        byte[] text = (plainText + salt).getBytes(Charset.forName(charset));;

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-384");
            final byte[] hashbytes = digest.digest(text);
            return Arrays.toString(hashbytes);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "error";
    }
}
