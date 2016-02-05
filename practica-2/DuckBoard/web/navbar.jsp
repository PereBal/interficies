<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="servlets.tools.Helper, model.User"%>
<%
  pageContext.setAttribute("logged", Helper.isLogged(request));
  pageContext.setAttribute("user", Helper.getCurrentUser(request));
%>
<c:choose>
  <c:when test="${logged}">
    <!-- Dropdown Structure -->
    <ul id="navbar-dropdown" class="dropdown-content">
      <li>
        <a href="/duckboard/users">Perfil</a>
      </li>
      <li class="divider"></li>
      <li>
        <a href="#">Salir</a>
      </li>
    </ul>
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="/duckboard" class="brand-logo"><img style="width:1em" src="img/favicon.png"/> DuckBoard</a>
        <a href="#" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li>
            <a href="#" class="waves-effect waves-light">Usuarios</a>
          </li>
          <li>
            <a href="/duckboard/chats" class="waves-effect waves-light">Chat</a>
          </li>
          <!-- Dropdown Trigger -->
          <li>
            <a class="dropdown-button" href="#!" data-activates="navbar-dropdown">
              <c:out value="${user.name}"/>
              <i class="material-icons right">arrow_drop_down</i>
            </a>
          </li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li>
            <a href="#"><i class="material-icons left">supervisor_account</i>Usuarios</a>
          </li>
          <li>
            <a href="/duckboard/chats"><i class="material-icons left">forum</i>Chat</a>
          </li>
          <li>
            <a href="/duckboard/users"><i class="material-icons left">assignment_ind</i>Perfil</a>
          </li>
          <li class="divider"></li>
          <li>
            <a href="#"><i class="material-icons left">settings_power</i>Salir</a>
          </li>
        </ul>
      </div>
    </nav>
  </c:when>
  <c:otherwise>
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="/duckboard" class="brand-logo"><img style="width:1em" src="img/favicon.png"/> DuckBoard</a>
        <a href="#" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li>
            <a id="loginButton" href="#" class="waves-effect waves-light">Acceder</a>
          </li>
          <li>
            <a href="/duckboard/users" class="waves-effect waves-light">Registrar</a>
          </li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li><a href="#">Salir</a></li>
        </ul>
      </div>
    </nav>
  </c:otherwise>
</c:choose>