<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="servlets.tools.Helper, model.User"%>
<%
  pageContext.setAttribute("isLogged", Helper.isLogged(request));
  pageContext.setAttribute("user", Helper.getCurrentUser(request));
%>
<c:choose>
  <c:when test="${isLogged}">
    <!-- Dropdown Structure -->
    <ul id="navbar-dropdown" class="dropdown-content">
      <li><a href="#!"><i class="material-icons left">perm_identity</i>Profile</a></li>
      <li class="divider"></li>
      <li><a href="#!"><i class="material-icons left">settings_power</i>Logout</a></li>
    </ul>
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="#!" class="brand-logo"><img style="width:1em;" src="http://www.iconarchive.com/download/i24289/martin-berube/animal/duck.ico"></img>DuckBoard</a>
        <a href="#!" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li><a href="/chats" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
          <!-- Dropdown Trigger -->
          <li><a class="dropdown-button" href="#!" data-activates="navbar-dropdown"><c:out value="${user.name}"></c:out><i class="material-icons right">arrow_drop_down</i></a></li>
          </ul>
          <ul class="side-nav" id="mobile-navbar">
            <li><a href="chat.html" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
            <li><a href="#!"><i class="material-icons left">perm_identity</i>Profile</a></li>
            <li class="divider"></li>
            <li><a href="#!"><i class="material-icons left">settings_power</i>Logout</a></li>
          </ul>
        </div>
      </nav>
  </c:when>
  <c:otherwise>
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="#!" class="brand-logo"><img style="width:1em;" src="http://www.iconarchive.com/download/i24289/martin-berube/animal/duck.ico"></img>DuckBoard</a>
        <a href="#!" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li><a id="loginButton" href="#!" class="waves-effect waves-light">Login</a></li>
          <li><a href="/duckboard/users" class="waves-effect waves-light">Register</a></li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li><a href="#!">Login</a></li>
        </ul>
      </div>
    </nav>
  </c:otherwise>
</c:choose>