<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="servlets.tools.Helper"%>
<%//boolean isLogged = Helper.isLogged(request);%>
<!-- LOGGED -->
<c:choose>
  <c:when test="${isLogged}">
    <!-- Dropdown Structure -->
    <ul id="navbar-dropdown" class="dropdown-content">
      <li><a href="#!"><i class="material-icons left">perm_identity</i>Profile</a></li>
      <li class="divider"></li>
      <li><a href="#!"><i class="material-icons left">settings_power</i>DuckBoardut</a></li>
    </ul>
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="#!" class="brand-logo">DuckBoard</a>
        <a href="#!" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li><a href="chat.jsp" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
          <!-- Dropdown Trigger -->
          <li><a class="dropdown-button" href="#!" data-activates="navbar-dropdown">{{user}}<i class="material-icons right">arrow_drop_down</i></a></li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li><a href="chat.html" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
          <li><a href="#!"><i class="material-icons left">perm_identity</i>Profile</a></li>
          <li class="divider"></li>
          <li><a href="#!"><i class="material-icons left">settings_power</i>DuckBoardut</a></li>
        </ul>
      </div>
    </nav>
  </c:when>
  <c:otherwise>
    <!-- NOT LOGGED -->
    <nav class="blue-grey lighten-1">
      <div class="nav-wrapper container">
        <a href="#!" class="brand-logo">DuckBoard</a>
        <a href="#!" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
          <li><a id="loginButton" href="#!" class="waves-effect waves-light">Login</a></li>
          <li><a href="#!" class="waves-effect waves-light">Register</a></li>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
          <li><a href="#!">Login</a></li>
        </ul>
      </div>
    </nav>
  </c:otherwise>
</c:choose>
<script type="text/javascript">
  $(document).ready(function () {
    // Activate Dropdown menu
    $(".dropdown-button").dropdown();
    // Activate button-collapse for mobile
    $(".button-collapse").sideNav();
  });
</script>
