<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="servlets.tools.Helper, model.User"%>
<%
  pageContext.setAttribute("logged", Helper.isLogged(request));
  pageContext.setAttribute("user", Helper.getCurrentUser(request));
  pageContext.setAttribute("path", request.getRequestURI().equals("/duckboard/register.jsp"));
%>
<c:choose>
  <c:when test="${logged}">
    <!-- Dropdown Structure -->
    <ul id="navbar-dropdown" class="dropdown-content">
      <li>
        <a href="/duckboard/users?id=${user.id}" class="light-blue-text text-darken-1"><i class="material-icons left">assignment_ind</i>Perfil</a>
      </li>
      <li class="divider"></li>
      <li>
        <a href="/duckboard/logout" class="light-blue-text text-darken-1"><i class="material-icons left">settings_power</i>Salir</a>
      </li>
    </ul>
    <ul id="dropdown-enterprise" class="dropdown-content">
      <li>
        <a href="/duckboard/users" class="light-blue-text text-darken-1"><i class="material-icons left">contacts</i>Usuarios</a>
      </li>
      <li class="divider"></li>
      <li>
        <a href="/duckboard/chats" class="light-blue-text text-darken-1"><i class="material-icons left">forum</i>Chat</a>
      </li>
      <li class="divider"></li>
      <li>
        <a href="/duckboard/newsletter" class="light-blue-text text-darken-1"><i class="material-icons left">thumb_up</i>Motivacional</a>
      </li>
    </ul>
    <nav class="blue-grey darken-1">
      <div class="nav-wrapper container">
        <a href="/duckboard" class="brand-logo"><img style="width:1em" src="img/favicon.png"/> DuckBoard</a>
        <a href="#" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li>
            <a href="/duckboard/graphs"><i class="material-icons left">dashboard</i>Dashboard</a>
          </li>
           <li>
            <a class="dropdown-button" href="#!" data-activates="dropdown-enterprise">
              <i class="material-icons left">perm_identity</i>
              Compañía
              <i class="material-icons right">arrow_drop_down</i>
            </a>
          </li>
          <!-- Dropdown Trigger -->
          <li>
            <a class="dropdown-button" href="#!" data-activates="navbar-dropdown">
              <img style="width: 2.5em;vertical-align: middle;" class="circle" <c:out value="src=${user.quote}"/>  />
              <c:out value="${user.name}"/>
              <i class="material-icons right">arrow_drop_down</i>
            </a>
          </li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li>
            <a href="/duckboard/graphs"><i class="material-icons left">dashboard</i>Dashboard</a>
          </li>
          <li class="divider"></li>
          <li>
            <a href="/duckboard/users"><i class="material-icons left">contacts</i>Usuarios</a>
          </li>
          <li>
            <a href="/duckboard/chats"><i class="material-icons left">forum</i>Chat</a>
          </li>
          <li>
            <a href="/duckboard/users?id=${user.id}"><i class="material-icons left">assignment_ind</i>Perfil</a>
          </li>
          <li class="divider"></li>
          <li>
            <a href="/duckboard/newsletter" class="waves-effect waves-light right"><i class="material-icons left">Motivacional</i>Chat</a>
          </li>
           <li class="divider"></li>
          <li>
            <a href="/duckboard/logout"><i class="material-icons left">settings_power</i>Salir</a>
          </li>
        </ul>
      </div>
    </nav>
  </c:when>
  <c:otherwise>
    <nav class="blue-grey darken-1">
      <div class="nav-wrapper container">
        <a href="/duckboard" class="brand-logo"><img style="width:1em" src="img/favicon.png"/> DuckBoard</a>
        <a href="#" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <c:if test="${path == false}">
            <li>
              <a href="#" class="loginButton waves-effect waves-light"><i class="material-icons left">input</i>Acceder</a>
            </li>
          </c:if>
          <li>
            <a href="/duckboard/users?id=-1" class="waves-effect waves-light">
              <i class="material-icons left">assignment_ind</i>
              Registrar
            </a>
          </li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <c:if test="${path == false}">
            <li>
              <a href="#" class="loginButton waves-effect waves-light"><i class="material-icons left">input</i>Acceder</a>
            </li>
          </c:if>
          <li>
            <a href="/duckboard/users?id=-1" class="waves-effect waves-light">
              <i class="material-icons left">assignment_ind</i>
              Registrar
            </a>
          </li> 
        </ul>
      </div>
    </nav>
  </c:otherwise>
</c:choose>