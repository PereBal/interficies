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
      <div id="scrolldiv3" class="container" style="padding-top: 10px">
        <div id="scrolldiv2" class="row z-depth-3 dynamic-height">
          <div id="scrolldiv" class="col s4 dynamic-height">
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
            <ul id="contact-list" class="collection chat-conversations">
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
            </ul>
          </div>
          <!-- Conversation -->

          <div class="col s8 dynamic-overflow chat-border col-fit">
            <div class="dynamic-height-messages" style="overflow-y: auto">
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 left">
                  <div class="chat-bubble-text">Hola guapo
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:41
                    </span>
                  </div>
                </div>
              </div>
              <div class="row row-fit">
                <div class="chat-bubble blue-grey lighten-5 right">
                  <div class="chat-bubble-text">Mierda!
                    <span class="chat-timestamp grey-text text-darken-1">
                      <i class="fa fa-clock-o"></i>10:42
                    </span>
                  </div>
                </div>
              </div>
            </div>
            <form action="#" class="send-height">
              <div class="col s9 col-fit">
                <textarea id="message" class="text-area send-height" placeholder="Escribir un mensaje..."></textarea>
              </div>
              <div class="col s3 col-fit center">
                <a class="waves-effect waves-light send-height" name="send">Enviar</a>
              </div>
            </form>
              
          <div class="col s8 dynamic-height" style="border-left: 1px solid #e0e0e0">
          </div>
        </div>
      </div>
  </main>
  <jsp:include page="footer.jsp"/>
  <jsp:include page="scripts.jsp"/>
  <script type="text/javascript" src="js/resize.js">
    
    
      
      $('#scrolldiv').scroll( function() {
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