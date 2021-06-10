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

    protected byte[] xorList(byte[] a, byte[] b){
        int lena = a.length;
        int lenb = b.length;

        int maxLen = lena;
        int minLen = lenb;
        byte[] longer = a;
        byte[] shorter = a;
        if (lenb > maxLen){
            maxLen = lenb;
            minLen = lena;
            longer = b;
            shorter = a;
        }

        int currentIndex = 0;
        byte[] text = new byte[maxLen];

        while (currentIndex < maxLen){
            for (int i = 0; i < minLen; i++, currentIndex++){
                if (currentIndex >= maxLen){
                    break;
                }
                text[currentIndex] = (byte) (shorter[i] ^ longer[currentIndex]);
            }
        }

        return text;
    }

    public String calculateHash() {
        byte[] bytemsg = plainText.getBytes(Charset.forName(charset));
        byte[] bytesalt = salt.getBytes(Charset.forName(charset));
        byte[] text = xorList(bytemsg, bytesalt);

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
