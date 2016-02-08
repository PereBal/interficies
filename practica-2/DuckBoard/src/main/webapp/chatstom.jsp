<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="servlets.tools.Helper"%>
<%pageContext.setAttribute("user", Helper.getCurrentUser(request));%>
<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main> 
      <div class="container" style="padding-top: 10px">
        <div class="row z-depth-3 dynamic-height">
          <div id="scrolldiv" class="col s4 col-fit dynamic-height">
            <nav id="scrollvariable" style="box-shadow: none !important">
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
              <c:choose>
                <c:when test="${empty chats}">
                  <li class="valign-wrapper">
                    <h5 class="valign">This should be vertically aligned</h5>
                  </li>
                </c:when>
                <c:otherwise>
                  <c:forEach var="chat" items="${chats}">
                    <li id="li" class="collection-li collection-item avatar" style="cursor: pointer" onclick="refresh()">
                      <i class="material-icons circle">contacts</i>
                      <c:forEach var="party" items="${chat.parties}">
                        <c:if test="${user.id != party.user.id}">
                          <span class="title"><c:out value="${party.user.name}"/></span>
                          <span class="new badge"><c:out value="${party.countUnreadMessages()}"/></span>
                          <p><c:out value="${party.lastReadMessage.text}"/></p>
                        </c:if>
                      </c:forEach>
                    </li>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </ul>
          </div>
          <!-- Conversation -->
          <c:choose>
            <c:when test="${empty currentChat}">
              <div class="col s8 col-fit chat-border">
                <div class="valign-wrapper">
                  <h5 class="valign">This should be vertically aligned</h5>
                </div>
              </div>
            </c:when>
            <c:otherwise>
              <div class="col s8 col-fit chat-border dynamic-overflow">
                <div class="dynamic-height-messages" style="overflow-y: auto">
                  <c:forEach var="msg" items="${currentChat.messages}">
                    <c:choose>
                      <c:when test="${msg.user.id != cuser.id}">
                        <div class="row row-fit">
                          <div class="chat-bubble blue-grey lighten-5 left">
                            <div class="chat-bubble-text"><c:out value="${msg.text}"/>
                              <span class="chat-timestamp grey-text text-darken-1">
                                <i class="material-icons tiny green-text">done_all</i><fmt:formatDate pattern="HH:mm" value="${msg.createdAt}"/>
                              </span>
                            </div>
                          </div>
                        </div>
                      </c:when>
                      <c:otherwise>
                        <div class="row row-fit">
                          <div class="chat-bubble light-green lighten-4 right">
                            <div class="chat-bubble-text"><c:out value="${msg.text}"/>
                              <span class="chat-timestamp grey-text text-darken-1">
                                <i class="material-icons tiny green-text">done_all</i><fmt:formatDate pattern="HH:mm" value="${msg.createdAt}"/>
                              </span>
                            </div>
                          </div>
                        </div>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </div>
                <form method="POST" action="#" class="send-height">
                  <div class="col s9 col-fit">
                    <textarea id="message" class="text-area send-height" placeholder="Escribir un mensaje..."></textarea>
                  </div>
                  <div class="col s3 col-fit center">
                    <a class="waves-effect waves-light send-height" name="send">Enviar</a>
                  </div>
                </form>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript" src="js/resize.js"></script>
    <script type="text/javascript">
                      $('#scrolldiv').scroll(function () {
                        /*var max= ('#scrolldiv').position()['top']; 
                         var actual=('#scrollvariable').position()['top'];
                         alert('el menos llego');
                         if(max==actual){
                         alert("LLUC CABRON");
                         }else{
                         alert("u scrolled !");
                         }*/
                        alert('qwerty');
                      });
    </script>
  </body>
</html>