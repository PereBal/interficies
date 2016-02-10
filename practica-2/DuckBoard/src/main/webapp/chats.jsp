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
                                                            <textarea id="chatText" class="duckboard-textaera" rows="3" placeholder="Escriba aquï¿½ su mensaje..."></textarea>
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
            var messageLimit = 20;

            var niceScrollConf = {
                cursoropacitymax: 0.4, // change opacity when cursor is active (scrollabar "visible" state), range from 1 to 0
                cursorwidth: "8px"
            }

            /* SUPPORT FUNCTIONS */

            /**
             * Appends all messages to the conversation div on first load
             */
            var buildConversation = function (messages) {

                var toAppend;

                conversation.empty();

                if (messages != undefined && messages !== null && messages.length > 0) {

                    $.each(messages, function (index, message) {

                        appendMessage(message);

                        conversation.append(toAppend);
                        conversation.scrollTop($(conversation)[0].scrollHeight);
                    });
                }
                
                conversation.scrollTop($(conversation)[0].scrollHeight); // Set the scroll to the bottom of the conversation div.
            }

            /**
             * Gets conversation messages on first load
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

                        buildConversation(data.messages);
                    },
                    error: function () {}
                });
            };

            /**    
             * Gets unread messages
             */
            var retrieveUnreadMessages = function (chatId) {
                
                var unreadMessageCount = messageLimit;
                var unreadMessage = [];

                var skip = 0;

                while (unreadMessages === messageLimit) {

                    var amount = skip * messageLimit;

                    $.ajax({
                        type: 'GET',
                        url: '/duckboard/chats/messages',
                        async: true,
                        dataType: "json",
                        data: ({
                            'cid': chatId,
                            'unread': true,
                            'skip': amount
                        }),
                        success: function (data) {

                            unreadMessageCount = data.messages.length;

                            $.merge(unreadMessages, data.messages);
                        },
                        error: function () {}
                    });
                    
                    skip++;
                }
                
                return unreadMessages;
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
                            chatTextArea.val('');
                        },
                        error: function () {}
                    });
                }
            }

            /**
             * Paints a message at the bottom of the chat
             * @param {type} message
             * @returns {undefined}
             */
            var appendMessage = function (message) {

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


            /**
             * Paints a message at the top of the chat
             * @param {type} message
             * @returns {undefined}
             */

            var prependMessage = function (message) {

                var toPrepend;

                if (message.user_id !== ${user.id}) { // current user msg*/

                    toPrepend = '<div class="row row-fit">' +
                            '<div class="chat-bubble blue-grey lighten-5 left">' +
                            '<div class="chat-bubble-text">' + message.text + '</>' +
                            '<span class="chat-timestamp grey-text text-darken-1">' +
                            '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                            '</span>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                } else {

                    toPrepend = '<div class="row row-fit">' +
                            '<div class="chat-bubble light-green lighten-4 right">' +
                            '<div class="chat-bubble-text">' + message.text + '</>' +
                            '<span class="chat-timestamp grey-text text-darken-1">' +
                            '<i class="material-icons tiny green-text">done_all</i>' + message.created_at +
                            '</span>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                }

                conversation.prepend(toPrepend);
            }

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

                /**
                 * Function to poll messages
                 * @type type
                 */
                var intervalID = setInterval(
                        function () {

                            var currentChat = $('.chat-selected').attr('id');

                            if (currentChat !== undefined && currentChat !== null && currentChat !== '') {

                                
                            }
                        }, 1000);

                /**
                 * Gets the scroll event on the chat conversation
                 * @param {type} param
                 */
                conversation.scroll(function () {
                    /* con message (sin el spin)
                     var max= $('#conversation').position()['top']; 
                     var actual= $('#message').position()['top'];
                     console.log("max: "+max+" - actual: "+actual);
                     if((max-actual)<=0.5){
                     //Aquí va la función que carga mensajes.
                     console.log('putoamo');
                     }*/

                    var max = conversation.offset()['top'];
                    var spin = $('#spin').offset()['top'];
                    console.log("max: " + max + " - spin: " + spin);
                    if (spin >= max) {

                        //Aquí va la función que carga mensajes.
                        chatCache.count++;
                        retrieveConversation(chatCache.cid, chatCache.count * 20);
                    }
                });
            });

        </script>
    </body>
</html>
