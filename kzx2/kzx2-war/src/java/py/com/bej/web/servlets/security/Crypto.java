/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.web.servlets.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Diego_M
 */
public class Crypto {

    /**
     * Convertir en Hexadecimal un conjunto de bytes.
     * 
     * @param data Conjunto de Bytes del dato.
     * @return
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * Genera el SHA1 de un texto.
     * 
     * @param text Texto en Claro
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String sha1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    /**
     * Metodo para generar una clave compatible para 3DES
     * @param a valor de la clave de entrada
     * @return clave del formato aceptado por el algoritmo DES
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey generateSecretKey(byte[] a)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        DESedeKeySpec dks = new DESedeKeySpec(a);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        return factory.generateSecret(dks);
    }

    /**
     * Metodo para Encriptar los mensajes.
     * 
     * @param keyMaterial clave para cifrar en 3DES
     * @param source mensaje a cifrar
     * @return mensaje cifrado
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     */
    private static String tripleDESEncrypt(String clave, String texto)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException,
            NoSuchPaddingException, IllegalBlockSizeException {

        byte[] keyMaterial = clave.getBytes();
        byte[] source = texto.getBytes();

        SecretKey key = generateSecretKey(keyMaterial);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        Cipher cipher = Cipher.getInstance("DESede");//Es el cifrador instanciado para 3DES
        cipher.init(Cipher.ENCRYPT_MODE, key, random);//Se pone el en modo de cifrado

        String encodeTexto = new String(Base64.encodeBase64(cipher.doFinal(source)));

        return encodeTexto;
    }

    /**
     * Metodo para Desencripta mensajes.
     * 
     * @param keyMaterial clave de cifrado/descifrado en 3DES 
     * @param source mensaje cifrado en 3DES
     * @return mensaje claro
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     */
    private byte[] tripleDESDecrypt(byte[] keyMaterial, byte[] source)
            throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException,
            NoSuchPaddingException, IllegalBlockSizeException {
        SecretKey key = generateSecretKey(keyMaterial);	//Clave formada para 3DES	

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        Cipher cipher = Cipher.getInstance("DESede");//Es el cifrador instanciado para 3DES

        cipher.init(Cipher.DECRYPT_MODE, key, random);//Se pone en el modo de descifrado

        return cipher.doFinal(source);
    }

    public static String encriptar(String password) {
        String key = "1231232320490934093290913290940930940329049320940329132899893849832489328493284928948";
        try {
            String des3String = Crypto.tripleDESEncrypt(key, password);
            String sha1String = Crypto.sha1(des3String);

            return sha1String;
        } catch (Exception e) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, "Error al encriptar!", e);
            return null;
        }
    }
}
