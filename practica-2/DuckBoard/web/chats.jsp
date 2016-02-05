<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="servlets.tools.Helper"%>
<%
  pageContext.setAttribute("current-user", Helper.getCurrentUser(request));
  pageContext.setAttribute("chats", request.getParameter("chats"));
  pageContext.setAttribute("current-chat", request.getParameter("currentChat"));
%>
<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container" style="padding-top: 10px">
        <div class="row z-depth-3 dynamic-height">
          <div class="col s4 dynamic-height">
            <nav style="box-shadow: none !important">
              <div class="nav-wrapper" style="background-color: white !important">
                <form>
                  <div class="input-field">
                    <input id="search" type="search" required>
                    <label for="search"><i class="material-icons" style="color: graytext">search</i></label>
                    <i class="material-icons">close</i>
                  </div>
                </form>
              </div>
            </nav> 
            <ul id="contact-list" class="collection" style="overflow-y: auto">
              <c:forEach var="chat" items="${chats}">
                <li id="li" class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                  <i class="material-icons circle">contacts</i>
                  <c:forEach var="party" items="${chat.parties}">
                    <c:if test="current-user.id ne party.user.id">
                      <span class="title"><c:out value="${party.user.name}"/></span>
                      <span class="new badge"><c:out value="${party.countUnreadMessages()}"/></span>
                      <p><c:out value="${party.lastReadMessage.text}"/></p>
                    </c:if>
                  </c:forEach>
                </li>
              </c:forEach>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">contacts</i>
                <span class="title">Username</span>
                <span class="new badge">4</span>
                <p>@last-msg</p>
              </li>
            </ul>
          </div>
          <div class="col s8 dynamic-height"></div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript" src="js/resize.js"></script>
  </body>
</html>