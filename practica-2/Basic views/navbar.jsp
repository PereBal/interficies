<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- No login -->
<!-- After login -->
<!-- Dropdown Structure -->
<ul id="navbar-dropdown" class="dropdown-content">
    <!-- Mirar si es admin -->
    <c:if test="false">
    <li><a href="#!"><i class="material-icons left">perm_identity</i>Admin</a></li>
    </c:if>
    <li class="divider"></li>
    <li><a href="#!"><i class="material-icons left">settings_power</i>Logout</a></li>
</ul>
<nav class="light-blue lighten-1">
    <div class="nav-wrapper container">
        <a href="#!" class="brand-logo">Logo</a>
        <a href="#!" data-activates="mobile-navbar" class="button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
            <li><a href="chat.html" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
            <!-- Dropdown Trigger -->
            <!-- After login -->
            <c:if test="false">
            <li><a class="dropdown-button" href="#!" data-activates="navbar-dropdown">{{username}}<i class="material-icons right">arrow_drop_down</i></a></li>
            </c:if>
        </ul>
        <ul class="side-nav" id="mobile-navbar">
            <li><a href="chat.html" class="waves-effect waves-light"><i class="material-icons left">comment</i>Chat</a></li>
            <c:if test="condition"></c:if>
            <li><a href="#!"><i class="material-icons left">perm_identity</i>Admin</a></li>
            <li class="divider"></li>
            <li><a href="#!"><i class="material-icons left">settings_power</i>Logout</a></li>
        </ul>
    </div>
</nav>