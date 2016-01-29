/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.highcharts;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PereBal
 * @author dobleme
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
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void close() {
    try {
      con.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Connection getConection() {
    return con;
  }
}
