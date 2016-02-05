<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
              <c:forEach var="user" items="${users}">
                <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="loadChat()">
                  <i class="material-icons circle">folder</i>
                  <span class="title"><c:out value="${user.username}"/></span>
                  <span class="new badge"><c:out value="${user.unread}"/></span>
                  <p><c:out value="${user.last.message}"/></p>
                </li>
              </c:forEach>
              <li class="collection-li collection-item avatar" style="cursor: pointer" onclick="loadChat()">
                <!--<img src="images/yuna.jpg" alt="" class="circle">-->
                <i class="material-icons circle">folder</i>
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
    <script type="text/javascript">
                // Set interval update every 5s
                setInterval(refresh(), 5000);
                function refresh() {
                  // Request unread messages
                  $.ajax({
                    type: "POST",
                    url: "/chats?unread=&skip=:cid",
                    data: data,
                    success: success,
                    dataType: dataType
                  });
                }
    </script>
  </body>
</html>