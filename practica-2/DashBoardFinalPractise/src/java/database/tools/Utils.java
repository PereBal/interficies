/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author pere
 */
public class Utils {

  public static String cleanEmail(String email) {
    return email;
  }

  public static String cleanName(String name) {
    return name;
  }

  public static String cleanLastName(String lastName) {
    return lastName;
  }

  public static String cleanPwd(String pwd) {
    return pwd;
  }

  public static String cleanAuthToken(String authToken) {
    return authToken;
  }

  public static boolean cleanAdmin(String isAdmin) {
    return isAdmin.equals("1");
  }

  public static String encrypt(String pwd) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    /*
     * Every implementation of the Java platform is required to support the following standard MessageDigest algorithms:
     * MD5
     * SHA-1
     * SHA-256
     * Source: MessageDigest class on javaApi
     */
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(Utils.cleanPwd(pwd).getBytes("UTF-8"));
    String spwd = Base64.getEncoder().encodeToString(md.digest());
    return spwd;
  }
}
