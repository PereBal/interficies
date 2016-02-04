/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Guillem Barceló
 */
public class DBConnection implements java.lang.AutoCloseable {

  private Connection con;

  public DBConnection() {
    con = null;
  }

  public void open() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection("jdbc:mysql://"
              + DBProperties.HOST + ":" + DBProperties.PORT
              + "/" + DBProperties.DB, DBProperties.USER, DBProperties.PWD);
    } catch (ClassNotFoundException | SQLException ex) {
      java.util.logging.Logger.getLogger(DBConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
  }

  @Override
  public void close() {
    try {
      con.close();
    } catch (Exception ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
  }

  public Connection getConection() {
    return con;
  }
}
