<%@page import="model.*, java.util.List"%>
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
            <div class="section container-fluid">
                <div class="row">
                    <nav class="col s10 offset-s1 blue-grey darken-1">
                        <div class="nav-wrapper">
                            <div class="col s12">
                                <a class="breadcrumb">DuckBoard</a>
                                <a class="breadcrumb">Chat</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
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
                                                                try {
                                                                    pageContext.setAttribute("nameChat", chat.getChatName(user.getId()));
                                                                    pageContext.setAttribute("emailChat", chat.getChatEmail(user.getId()));
                                                                } catch (NullPointerException ex) {
                                                                    System.out.println("========================================");
                                                                    System.out.println("========================================");
                                                                    ex.toString();
                                                                    System.out.println(chat);
                                                                    System.out.println(user);
                                                                    System.out.println("========================================");
                                                                    System.out.println("========================================");
                                                                }
                                                                pageContext.setAttribute("haveUnreadMessages", chat.haveUnreadMessages(user.getId()));
                                                                pageContext.setAttribute("unreadMessages", chat.countUnreadMessages(user.getId()));
                                                                List<Message> messages = chat.getMessages();
                                                                if (messages.size() > 0) {
                                                                    pageContext.setAttribute("lastReadMessage", messages.get(messages.size() - 1).getText());
                                                                } else {
                                                                    pageContext.setAttribute("lastReadMessage", "");
                                                                }
                                                            %>
                                                            <li class="conversation-li collection-item avatar waves-effect"
                                                                onclick="retrieveConversation('${chat}')" style="cursor: pointer; width: 100%"
                                                                id="${chat}">
                                                                <i class="material-icons circle">contacts</i>
                                                                <div class="row chat-contact-fit">
                                                                    <div class="col s10">
                                                                        <span class="title truncate">${nameChat}</span>
                                                                        <p class="truncate">${lastReadMessage}</p>
                                                                    </div>
                                                                    <div class="col s2">
                                                                        <div class="row">
                                                                            <c:if test="${haveUnreadMessages}">
                                                                                <span class="new badge red">${unreadMessages}</span>
                                                                            </c:if>
                                                                        </div>
                                                                        <div class="row">
                                                                            <a id="sendBtn" onclick="deleteChat('${chat}')" style="cursor: pointer">
                                                                            <i class="material-icons black-text right">delete</i></a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </c:otherwise>
                                                </c:choose>
                                            </ul>
                                        </div>
                                    </div>
                                    <div  class="col s8 conversation">
                                        <div>
                                            <div id="conversation" class="chat-conversation" style="background-color: whitesmoke;">
                                            </div>

                                            <div class="grey lighten-5">
                                                <div id="msgChatForm" class="col s8 offset-s2" style="padding-top:2rem; font-size: 16px;">
                                                    Escoja un chat para iniciar una conversacion.. <i class="material-icons">chat</i>
                                                </div>
                                                <form class="col s12" style="padding-top: 1.5em;" id="chatForm">
                                                    <div class="row">
                                                        <div class="col s10">
                                                            <textarea id="chatText" class="duckboard-textaera" rows="3" placeholder="Escriba aqu� su mensaje..."></textarea>
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
            var openChats = $('#contactsList');
            var conversation = $('#conversation');
            var chatTextArea = $('#chatText');
            var chatForm = $('#chatForm');
            var msgChatForm = $('#msgChatForm');

            var niceScrollConf = {
                cursoropacitymax: 0.4, // change opacity when cursor is active (scrollabar "visible" state), range from 1 to 0
                cursorwidth: "8px"
            }

            /* SUPPORT FUNCTIONS */

            /**
             * Appends messages to the conversation div
             */
            var buildConversation = function (data) {

                var toAppend;

                conversation.empty();

                if (data.messages != undefined && data.messages !== null && data.messages.length > 0) {

                    $.each(data.messages, function (index, message) {

                        if (message.user_id !== ${user.id}) { // current user msg

                            toAppend = '<div class="row row-fit">' +
                                    '<div class="chat-bubble blue-grey lighten-5 left">' +
                                    '<div class="chat-bubble-text">' + message.text + '</>' +
                                    '<span class="chat-timestamp grey-text text-darken-1">' +
                                    '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                                    '</span>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';

                        } else {

                            toAppend = '<div class="row row-fit">' +
                                    '<div class="chat-bubble light-green lighten-4 right">' +
                                    '<div class="chat-bubble-text">' + message.text + '</>' +
                                    '<span class="chat-timestamp grey-text text-darken-1">' +
                                    '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                                    '</span>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';
                        }

                        conversation.append(toAppend);
                    });
                }
            }

            /**
             * Gets conversation messages
             */
            var retrieveConversation = function (chatId) {

                $.ajax({
                    type: 'GET',
                    url: '/duckboard/chats/messages',
                    async: true,
                    dataType: "json",
                    data: ({
                        'cid': chatId,
                        'unread': false,
                        'skip': 0
                    }),
                    success: function (data) {

                        $('.chat-selected').removeClass('chat-selected');
                        $('#' + chatId).addClass('chat-selected');

                        chatForm.show();
                        msgChatForm.hide();

                        buildConversation(data);
                    },
                    error: function () {}
                });
            };


            /**
             * Funciton to post new message
             * @param {type} param
             */
            var saveMessage = function () {

                var chatId = $('.chat-selected').attr('id');
                var message = $('#chatText').val();

                if ($.trim(message) !== '') {

                    $.ajax({
                        type: 'POST',
                        url: '/duckboard/chats/messages',
                        async: true,
                        dataType: "json",
                        data: ({
                            'cid': chatId,
                            'text': message
                        }),
                        success: function (data) {

                            paintMessage(data);
                            $('#chatText').val('');
                        },
                        error: function () {}
                    });
                }
            }

            var paintMessage = function (message) {

                var toAppend;

                if (message.user_id !== ${user.id}) { // current user msg*/

                    toAppend = '<div class="row row-fit">' +
                            '<div class="chat-bubble blue-grey lighten-5 left">' +
                            '<div class="chat-bubble-text">' + message.text + '</>' +
                            '<span class="chat-timestamp grey-text text-darken-1">' +
                            '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                            '</span>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                } else {

                    toAppend = '<div class="row row-fit">' +
                            '<div class="chat-bubble light-green lighten-4 right">' +
                            '<div class="chat-bubble-text">' + message.text + '</>' +
                            '<span class="chat-timestamp grey-text text-darken-1">' +
                            '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                            '</span>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                }

                conversation.append(toAppend);
            }

            $(document).ready(function () {
                // Activate Dropdown menu
                $(".dropdown-button").dropdown();
                // Activate button-collapse for mobile
                $(".button-collapse").sideNav();

                openChats.niceScroll(niceScrollConf);
                chatTextArea.niceScroll(niceScrollConf);
                conversation.niceScroll(niceScrollConf);

                if ('${currentChat}' !== '' && '${currentChat}' !== undefined) {

                    $('#' + '${currentChat}').addClass('chat-selected');
                    retrieveConversation('${currentChat}');

                    chatForm.show();
                    msgChatForm.hide();
                } else {

                    chatForm.hide();
                    msgChatForm.show();
                }

                var intervalID = setInterval(
                        function () {

                            var currentChat = $('.chat-selected').attr('id');

                            if (currentChat !== undefined && currentChat !== null && currentChat !== '') {

                                retrieveConversation(currentChat);
                            }
                        }, 1000);
            });

            /*function deleteChat(chatId) {
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
             };*/

            $('#conversation').scroll(function () {
                /* con message (sin el spin)
                 var max= $('#conversation').position()['top'];
                 var actual= $('#message').position()['top'];
                 console.log("max: "+max+" - actual: "+actual);
                 if((max-actual)<=0.5){
                 //Aqu� va la funci�n que carga mensajes.
                 console.log('putoamo');
                 }*/

                var max = $('#conversation').offset()['top'];
                var spin = $('#spin').offset()['top'];
                console.log("max: " + max + " - spin: " + spin);
                if (spin >= max) {
                    //Aqu� va la funci�n que carga mensajes.

                }

            });
        </script>
    </body>
</html>
