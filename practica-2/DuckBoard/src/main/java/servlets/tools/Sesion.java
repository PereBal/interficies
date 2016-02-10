package servlets.tools;

public class Sesion {

  private int userId = -1;
  private String authToken = null;
  private model.User user = null;
  private org.json.JSONArray flash = null;
  private final javax.servlet.http.HttpSession session;

  public Sesion(javax.servlet.http.HttpSession session) {
    if (session == null) {
      throw new NullPointerException("Void Session");
    }
    this.session = session;
    this.userId = getUserId();
    this.authToken = getAuthToken();
  }
  
  public model.User getUser() {
    if (user != null && getUserId() == user.getId()) {
      return user;
    } else {
      this.user = db.www.DBActions.getUserById(getUserId());
      return user;
    }
  }

  public void setUser(model.User user) {
    this.user = user;
    setUserId(user.getId());
  }

  public int getUserId() {
    if (userId != -1) {
      return userId;
    } else if (session.getAttribute("user_id") != null) {
      userId = (int) session.getAttribute("user_id");
      return userId;
    } else if (user != null) {
      userId = user.getId();
      session.setAttribute("user_id", userId);
      return userId;
    } else {
      return -1;
    }
  }

  public void setUserId(int userId) {
    this.userId = userId;
    this.session.setAttribute("user_id", userId);
  }

  public String getAuthToken() {
    if (authToken != null) {
      return authToken;
    } else if (session.getAttribute("auth_token") != null) {
      authToken = (String) session.getAttribute("auth_token");
      return authToken;
    } else {
      return null;
    }
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
    this.session.setAttribute("auth_token", authToken);
  }

  public org.json.JSONArray getFlash() {
    if (flash != null) {
      org.json.JSONArray f = flash;
      flash = null;
      session.removeAttribute("flash");
      return f;
    } else if (session.getAttribute("flash") != null) {
      org.json.JSONArray f = (org.json.JSONArray) session.getAttribute("flash");
      session.removeAttribute("flash");
      return f;
    } else {
      return new org.json.JSONArray();
    }
  }

  public void setFlash(Flash f, boolean create) {
    org.json.JSONObject jsonFlash = f.toJSON();
    if (flash != null) {
      if (!create) {
        flash.put(jsonFlash);
      } else {
        flash = new org.json.JSONArray();
        flash.put(jsonFlash);
      }
    } else {
      flash = new org.json.JSONArray();
      flash.put(jsonFlash);
    }
    session.setAttribute("flash", flash);
  }

  public Object getAttribute(String name) {
    return session.getAttribute(name);
  }

  public void setAttribute(String name, Object attr) {
    session.setAttribute(name, attr);
  }

  public boolean isValid() {
    return session != null && getUserId() != -1;
  }

  public void autenticate(String email) {
    setUser(db.www.DBActions.getUserByEmail(email));
    String authToken = java.util.UUID.randomUUID().toString();
    db.www.DBActions.setAuthToken(email, authToken);
    setAuthToken(authToken);
  }

  public void autenticate(model.User u) {
    setUser(u);
    String authToken = java.util.UUID.randomUUID().toString();
    db.www.DBActions.setAuthToken(u.getEmail(), authToken);
    setAuthToken(authToken);
  }

  public void invalidate() {
    db.www.DBActions.destroyAuthToken(getUserId());
    session.invalidate();
    userId = -1;
    user = null;
    authToken = null;
  }

  public javax.servlet.http.HttpSession conf() {
    return session;
  }

  public static boolean isAutenticated(Sesion s) {
    if (s != null) {
      int userId = s.getUserId();
      String authToken = s.getAuthToken();
      if (userId >= 0 && authToken != null) {
        return db.www.DBActions.userExists(userId, authToken);
      }
    }
    return false;
  }
}
