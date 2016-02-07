<%@page import="model.User"%>
<%@page import="model.Chat"%>
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
                            <p>No hay conversaciones</p>
                          </c:when>
                          <c:otherwise>
                            <c:forEach var="chat" items="${chats}">
                                <%
                                 Chat chat = (Chat) pageContext.getAttribute("chat");
                                 User user = (User) pageContext.getAttribute("user");

                                 pageContext.setAttribute("nameChat", chat.getChatName(user.getId()));
                                 pageContext.setAttribute("emailChat", chat.getChatEmail(user.getId()));
                                 pageContext.setAttribute("haveUnreadMessages", chat.haveUnreadMessages(user.getId()));
                                 pageContext.setAttribute("unreadMessages", chat.countUnreadMessages(user.getId()));
                               %>
                              <li class="collection-item avatar conversation-li waves-effect" 
                                  onclick="retrieveConversation('${chat}')" style="cursor: pointer; width: 100%"
                                  id="${chat}">
                                <img src="" alt="" class="circle"/>

                                <span class="title"><c:out value="${nameChat}"/></span>
                                <div class="row">
                                  <div class="col s-9">
                                    <p><c:out value="${emailChat}"/></p>
                                  </div>
                                  <div class="col s3">
                                      <a onclick="deleteChat('${chat}')" style="cursor: pointer">
                                      <i class="material-icons red-text right">delete</i>
                                    </a>
                                  </div>
                                </div>
                                <c:if test="${haveUnreadMessages}">
                                  <div class="secondary-content">
                                    <span class="new badge conversation-span red accent-4"><c:out value="${unreadMessages}"/></span>
                                  </div>
                                </c:if>
                              </li>
                            </c:forEach>
                          </c:otherwise>
                        </c:choose>
                      </ul>
                    </div>
                  </div>


                  <div class="col s8 conversation">
                    <div>

                        <div id="conversation" class="chat-conversation" style="background-color: whitesmoke;">
                        </div>

                      <div class="grey lighten-5">
                        <form class="col s12" style="padding-top: 1.5em;">
                          <div class="row">
                            <div class="col s10">
                              <textarea id="chatText" class="duckboard-textaera" rows="3" placeholder="escribe aqu� su mensaje..."></textarea>
                            </div>
                            <a class="waves-effect waves-teal btn-flat s2 light-blue-text text-darken-1" onclick="saveMessage()"
                               style="padding: inherit;">Enviar</a>
                          </div>
                        </form>
                      </div>
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
      /* GLOBAL VARIABLES */
      var openChats = $('#contacts');
      var conversation = $('#conversation');

      var niceScrollConf = {
        cursoropacitymax: 0.4, // change opacity when cursor is active (scrollabar "visible" state), range from 1 to 0
        cursorwidth: "8px"
      }

      /* SUPPORT FUNCTIONS */

    /**
     * Appends messages to the conversation div
     */
    var buildConversation = function(data) {

       var toAppend;

       conversation.empty();

       if (!$.empty('data.messages')){

        $.each(data.messages, function (message, userId) {


            if (message.user_id ===  userId) { // current user msg


            toAppend = '<div class="row row-fit">'+
                        '<div class="chat-bubble blue-grey lighten-5 left">'+
                          '<div class="chat-bubble-text">' + message.text + '</>'+
                            '<span class="chat-timestamp grey-text text-darken-1">'+
                              '<i class="fa fa-clock-o">' + message.created_at + '</i>'+
                            '</span>'+
                          '</div>'+
                        '</div>'+
                      '</div>';

            } else {


            toAppend = '<div class="row row-fit">'+
                        '<div class="chat-bubble light-green lighten-4 right">'+
                          '<div class="chat-bubble-text">' + message.text + '</>'+
                            '<span class="chat-timestamp grey-text text-darken-1">'+
                              '<i class="fa fa-clock-o">' + message.created_at + '</i>'+
                            '</span>'+
                          '</div>'+
                        '</div>'+
                      '</div>';
            }

            conversation.append(toAppend);
        });
      } else {
          conversation.append('No hay mensajes');
      }
    };

    /**
     * Gets conversation messages
     */
    var retrieveConversation = function(chatId) {

        // remove class from current chat element first, do it on success.
        $('#' + chatId).addClass('chat-selected');
        
        $.ajax({
            type: 'GET',
            url: '/duckboard/chats/messages',
            async: true,
            dataType: "json",
            data: ({
              'cid'    : chatId,
              'unread' : true,
              'skip'   : 0
            }),
            success: function (data) {

              buildConversation(data);
            },
            error: function (){}
          });
    };

    
    /**
     * Funciton to post new message
     * @param {type} param
     */
    var saveMessage = function() {
        
        var chatId = $('.chat-selected').attr('id');
        var message = $('#chattext').val();
        
        var message = {
            'text'       : 'hello',
            'created_at' : 'blabla'
        };
        
        paintMessage(message);
        
        $.ajax({
            type: 'POST',
            url: '/duckboard/chats/messages',
            async: true,
            dataType: "json",
            data: ({
              'cid'  : chatId,
              'text' : message
            }),
            success: function () {
              console.log('here'); 
              
               
            },
            error: function (){}
          }); 
    }
    
    var paintMessage = function(message) {
        
      var toAppend;  
        
      /*if (message.user_id ===  userId) { // current user msg*/


      toAppend = '<div class="row row-fit">'+
                  '<div class="chat-bubble blue-grey lighten-5 left">'+
                    '<div class="chat-bubble-text">' + message.text + '</>'+
                      '<span class="chat-timestamp grey-text text-darken-1">'+
                        '<i class="fa fa-clock-o">' + message.created_at + '</i>'+
                      '</span>'+
                    '</div>'+
                  '</div>'+
                '</div>';

      /*} else {*/


      toAppend = '<div class="row row-fit">'+
                  '<div class="chat-bubble light-green lighten-4 right">'+
                    '<div class="chat-bubble-text">' + message.text + '</>'+
                      '<span class="chat-timestamp grey-text text-darken-1">'+
                        '<i class="fa fa-clock-o">' + message.created_at + '</i>'+
                      '</span>'+
                    '</div>'+
                  '</div>'+
                '</div>';
      /*}*/

      conversation.append(toAppend);
    }

      $(document).ready(function () {
          
        /*$('#' + chatId).addClass('chat-selected');*/ 
        
        // Activate Dropdown menu
        $(".dropdown-button").dropdown();
        // Activate button-collapse for mobile
        $(".button-collapse").sideNav();

        openChats.niceScroll(niceScrollConf);
        $('#chatText').niceScroll(niceScrollConf);
        conversation.niceScroll(niceScrollConf);
      });

      function deleteChat(chatId) {
        // $.ajax({
        //   url: '/duckboard/chats',
        //   method: 'DELETE',
        //   async: false,
        //   data: { cid: chatId }
        //   succes: function () {
        //     console.log("hello")
        //   }
        //   //error: function () {
        //
        //   //}
        // });
        console.log("hola");
      }
    </script>
  </body>
</html>
