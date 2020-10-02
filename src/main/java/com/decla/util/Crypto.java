package com.decla.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Clase de criptografia
 */
public class Crypto {
	private static final Logger log = LoggerFactory.getLogger(Crypto.class);
	
    public static final String Base64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static final String UrlSafeBase64Digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    
	public static String getRandomBytes(int bytes) {
		SecureRandom rs = new SecureRandom();
		final byte[] number = new byte[bytes];
		rs.nextBytes(number);

		return new String(Base64.encodeBase64(number));
	}

	public static SecretKey generateAESKey() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(256);
			return generator.generateKey();
		} catch (Exception e) {
			log.error("generateAESKey: {}",e.getMessage());
			return null;
		}
	}

	public static String decryptRsa(String cipherText, Key key) throws Exception {
		
		//Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
		
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.decodeBase64(cipherText)));
		
	}

	public static String encryptRsa(String plainText, Key key) throws Exception {
		
		//Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");											 

		cipher.init(Cipher.ENCRYPT_MODE, key);
		return new String(Base64.encodeBase64(cipher.doFinal(plainText.getBytes())));
		
	}

	public static String createHmac(String response, Key key) {
		try {
			Mac hMac = Mac.getInstance("HmacSHA256");
			hMac.init(key);

			return new String(Base64.encodeBase64(hMac.doFinal(response.getBytes())));
			
		} catch (Exception e) {
			log.error("createHmac: {}",e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param text
	 * @param key
	 * @param hmac
	 * @return
	 */
	public static boolean verifyHmac(String text, Key key, String hmac) {
		byte[] hmacBytes = Base64.decodeBase64(hmac);
		String calculatedHmac = createHmac(text, key);
		return calculatedHmac != null
				&& MessageDigest.isEqual(hmacBytes,
						Base64.decodeBase64(calculatedHmac));
	}

	public static String createSignature(String response, PrivateKey key) {
		try {
			Signature signature = Signature.getInstance("SHA512withRSA");
			signature.initSign(key);
			signature.update(response.getBytes());

			return new String(Base64.encodeBase64(signature.sign()));
		} catch (Exception e) {
			log.error("createSignature: {}",e);
			return null;
		}
	}

	public static boolean verifySignature(String response, PublicKey key,
			String signature) {
		try {
			Signature sig = Signature.getInstance("SHA512withRSA");
			sig.initVerify(key);
			sig.update(response.getBytes());

			return sig.verify(Base64.decodeBase64(signature));
		} catch (Exception e) {
			log.error("verifySignature: {}",e);
			return false;
		}
	}

	public static String md5(String text) {

		String generatedPassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;

	}	
	
	public static String getSecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException nsae) {
        	log.error("String getSecurePassword(String passwordToHash, String salt) : {}", nsae);
        }
        return generatedPassword;
    }
     
    //Add salt
    public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return Arrays.toString(salt);
    }
   
    //java 8
    public static String b64Encode(String raw) {
    	return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    //java 8
    public static String b64Decode(String raw) {
    	String decode = null;
    	try {

    		byte[] dataBytes = java.util.Base64.getDecoder().decode(raw);
        	decode = new String(dataBytes, StandardCharsets.UTF_8.name());
			
		} catch (UnsupportedEncodingException uee) {
			
		}
		return decode;
    }


}
