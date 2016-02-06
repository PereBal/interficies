/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 *
 * @author pere
 */
public class Utils {

  public static String cleanEmail(String email) {
    return "'" + email.replaceAll("[^A-Za-z0-9@.]", "") + "'";
  }

  public static String cleanName(String name) {
    return "'" + name.toUpperCase().replaceAll("[^A-Z0-9]", "") + "'";
  }

  public static String cleanLastName(String lastName) {
    if (lastName == null)
      return null;
    return "'" + lastName.toUpperCase().replaceAll("[^A-Z0-9 ]", "") + "'";
  }

  public static String cleanPwd(String pwd) {
    return pwd;
  }

  public static String cleanAuthToken(String authToken) {
    if (authToken == null)
      return null;
    return "'" + authToken + "'";
  }

  public static String cleanSex(char sex) {
    if (sex == '\0')
      return null;
    char sexClean = Character.toUpperCase(sex);
    return (sexClean != 'H' && sexClean != 'M') ? null : "'" + sexClean + "'";
  }

  public static String cleanSex(String sex) {
    if (sex == null)
      return null;
    return "'" + sex.toUpperCase().replaceAll("[^HM]", "").charAt(0) + "'";
  }

  public static String cleanBirthDay(String birthDay) {
    // Efficience bitches!!
    if (birthDay == null || !Pattern.matches("[0-9]{4,4}(-[0-9]{2,2}){2,2}", birthDay)) {
      return null;
    }
    return "'" + birthDay + "'";
  }

  public static String cleanQuote(String quote) {
    if (quote == null)
      return null;
    return "'" + quote.replaceAll("[^A-Za-z0-9.,:-_ ]", "") + "'";
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
    md.update(cleanPwd(pwd).getBytes("UTF-8"));
    String spwd = Base64.getEncoder().encodeToString(md.digest());
    return "'" + spwd + "'";
  }
}
