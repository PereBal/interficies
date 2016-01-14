/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
      <%
            DBActions dba = new DBActions();
            ArrayList<User> userList = dba.getUsers();
            for (User u : userList) {
                out.print(u.getName() + "<br/>");
            }
        %>
 */
package Objects;

/**
 *
 * @author Guillem Barceló
 */
public class User {

    private Integer id;
    private String name;
    private String password;
    private String level;

    public User() {
    }

    public User(Integer id, String name, String password, String level) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
