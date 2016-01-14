/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Objects.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillem Barceló
 */
public class DBActions {

    public ArrayList<User> getUsers() {
        DBConnection con = new DBConnection();
        ArrayList<User> userList = new ArrayList<User>();
        try {
            con.open();
            Statement st = con.getConection().createStatement();
            ResultSet rs = st.executeQuery("select * from users;");
            User auxUser;
            while (rs.next()) {
                auxUser = new User();
                auxUser.setId(rs.getInt("id"));
                auxUser.setName(rs.getString("name"));
                auxUser.setPassword(rs.getString("password"));
                auxUser.setLevel(rs.getString("level"));
                userList.add(auxUser);
            }
        } catch (Exception ex) {
        } finally {
            con.close();
        }
        return userList;
    }

    public Boolean getUser(String name, String password) {
        DBConnection con = new DBConnection();
        User auxUser = new User();
        try {
            con.open();
            Statement st = con.getConection().createStatement();
            ResultSet rs = st.executeQuery("select * from users where name=" + name + " and password=" + password + ";");
            while (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
        } finally {
            con.close();
        }
        return false;
    }

    public void insertUser(String name, String password) {
        DBConnection con = new DBConnection();
        User auxUser = new User();
        try {
            con.open();
            Statement st = con.getConection().createStatement();

            String Query = "INSERT INTO users (name, password, level) VALUES ('" + name + "', '" + password + "', '1');";
            st.executeUpdate(Query);

        } catch (SQLException ex) {
            Logger.getLogger(DBActions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }
}
