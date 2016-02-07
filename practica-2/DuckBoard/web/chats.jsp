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
      <div class="container">
        <div class="section">
          <!-- Icon Section -->
          <div class="row">
            <div class="col s12 ">
              <ul class="collapsible" data-collapsible="accordion">
                <div class="chat row">
                  <div class="col s4 contacts">
                    <div id="contactsList" class="chat-contacts" id="test2">
                      <ul class="collection">

                        <c:choose>
                          <c:when test="${empty chats}">
                            no hay conversaciones
                          </c:when>
                          <c:otherwise>
                            <c:forEach var="chat" items="${chats}">
                              <li class="collection-item avatar conversation-li">
                                <img src="http://lorempixel.com/42/42/people/1/" alt="" class="circle"/>
                                <span class="title">Steve Jobs</span>
                                <p>@applestevejobs</p>
                                <a href="#!" class="secondary-content">
                                  <span class="new badge conversation-span red accent-4">4</span>
                                </a>
                              </li>
                            </c:forEach>
                          </c:otherwise>
                        </c:choose>

                      </ul>
                    </div>
                  </div>


                  <div class="col s8 conversation">
                    <c:choose>
                      <c:when test="${empty currentChat}">
                        no hay chats
                      </c:when>
                      <c:otherwise>
                        <div id="conversation" class="chat-conversation" style="background-color: whitesmoke;">
                          <c:forEach var="msg" items="${currentChat.messages}">
                            <c:choose>
                              <c:when test="${msg.user.id != cuser.id}">
                                <div class="row row-fit">
                                  <div class="chat-bubble blue-grey lighten-5 left">
                                    <div class="chat-bubble-text"><c:out value="${msg.text}"/>
                                      <span class="chat-timestamp grey-text text-darken-1">
                                        <i class="fa fa-clock-o"></i><fmt:formatDate pattern="HH:mm" value="${msg.createdAt}"/>
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
                                        <i class="fa fa-clock-o"></i><fmt:formatDate pattern="HH:mm" value="${msg.createdAt}"/>
                                      </span>
                                    </div>
                                  </div>
                                </div>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>

                        </div>

                      </c:otherwise>
                    </c:choose>
                    <div class="grey lighten-5">
                      <form class="col s12" style="padding-top: 1.5em;">
                        <div class="row">
                          <div class="col s10">
                            <textarea id="chatText" class="duckboard-textaera" rows="3" placeholder="escribe aquí tu mensaje..."></textarea>
                          </div>
                          <a class="waves-effect waves-teal btn-flat s2 light-blue-text text-darken-1"
                             style="padding: inherit;">Enviar</a>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript">

      var niceScrollConf = {
        cursoropacitymax: 0.4, // change opacity when cursor is active (scrollabar "visible" state), range from 1 to 0
        cursorwidth: "10px"
      }

      $(document).ready(function () {
        // Activate Dropdown menu
        $(".dropdown-button").dropdown();
        // Activate button-collapse for mobile
        $(".button-collapse").sideNav();

        $('#contactsList').niceScroll(niceScrollConf);
        $('#chatText').niceScroll(niceScrollConf);
        $('#conversation').niceScroll(niceScrollConf);
      });
    </script>
  </body>
</html>