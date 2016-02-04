<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container">
        <div class="section">
          <div id="login" class="row linear-transition" style="display:none;">
            <form class="col s8 offset-s2">
              <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                  <span class="card-title">Login</span>
                  <div class="input-field">
                    <input id="user_name" type="text" class="validate"/>
                    <label for="user_name">Username</label>
                  </div>
                  <div class="input-field">
                    <input id="password" type="password" class="validate"/>
                    <label for="password">Password</label>
                  </div>
                </div>
                <div class="card-action">
                  <div class="">
                    <a id="cancelLogin" class="" style="cursor:pointer;">Cancelar</a>
                    <a href="#" class="btn">Entrar</a>
                  </div>
                </div>
              </div>
            </form>
          </div>
          <!-- Icon Section -->
          <div class="row">
            <div class="col s12 ">
              <ul class="collapsible" data-collapsible="accordion">
                <li>
                  <div class="collapsible-header">
                    <i class="material-icons">chat_bubble</i>Chat
                  </div>
                </li>
                <li>
                  <div class="chat row">
                    <div class="col s4 contacts">
                      <div class="chat-contacts" id="test1">
                        <ul class="collection">
                          <!--++++ POSIBLE BUSCADOR DE CONTACTOS ++++-->
                          <li class="collection-item conversation-li">
                            <textarea id="textarea1" class="text-area">Escribe aquí...</textarea>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons grey-text">search</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/1/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny green-text">done_all</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/2/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny grey-text">comment</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/3/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny grey-text">comment</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/4/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny green-text">done_all</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/5/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny green-text">done_all</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/6/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny grey-text">comment</i>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/7/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <i class="material-icons tiny green-text">done_all</i>
                            </a>
                          </li>
                        </ul>
                      </div>
                      <div class="chat-contacts" id="test2">
                        <ul class="collection">
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/1/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <span class="new badge conversation-span red accent-4">4</span>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/2/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <span class="new badge conversation-span red accent-4">1</span>
                            </a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/3/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content"></a>
                          </li>
                          <li class="collection-item avatar conversation-li">
                            <img src="http://lorempixel.com/42/42/people/4/" alt="" class="circle"/>
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                            <a href="#!" class="secondary-content">
                              <span class="new badge conversation-span red accent-4">4</span>
                            </a>
                          </li>
                          <li></li>
                        </ul>
                      </div>
                      <div class="chat-contacts" id="test3">
                        <ul class="collection">
                          <li class="collection-item  conversation-li" style="text-align: center;">
                            <img src="http://lorempixel.com/150/150/people/10/" alt="" class="circle"/>
                          </li>
                          <li class="collection-item conversation-li">
                            <span class="title">Steve Jobs</span>
                            <p>@applestevejobs</p>
                          </li>
                        </ul>
                      </div>
                      <div class="contact-menu grey lighten-5 ">
                        <ul class="tabs chat-tabs">
                          <li class="chat-tab tab col s3">
                            <a class="grey-text " href="#test1">
                              <i class="fa fa-users chat-tab-icon fa-3x"></i>
                              <p>Contactos</p>
                            </a>
                          </li>
                          <li class="chat-tab tab col s3">
                            <a class="grey-text active" href="#test2">
                              <i class="fa fa-comments chat-tab-icon fa-3x"></i>
                              <p>Chats</p>
                            </a>
                          </li>
                          <li class="chat-tab tab col s3">
                            <a class="grey-text " href="#test3">
                              <i class="fa fa-user chat-tab-icon fa-3x"></i>
                              <p>Perfil</p>
                            </a>
                          </li>
                        </ul>
                      </div>
                    </div>
                    <div class="col s8 conversation">
                      <div class="chat-conversation">
                        <div class="row row-fit">
                          <div class="chat-bubble light-green lighten-4 right">
                            <div class="chat-bubble-text">Hola<span class="chat-timestamp grey-text text-darken-1">
                                <i class="fa fa-clock-o"></i>10:00</span>
                            </div>
                          </div>
                        </div>
                        <div class="row row-fit">
                          <div class="chat-bubble blue-grey lighten-5 left">
                            <div class="chat-bubble-text"> Hola ;)<span class="chat-timestamp grey-text text-darken-1">
                                <i class="fa fa-clock-o"></i>10:05</span>
                            </div>
                          </div>
                        </div>
                        <div class="row row-fit">
                          <div class="chat-bubble light-green lighten-4 right">
                            <div class="chat-bubble-text">¿Qué tal?<span class="chat-timestamp grey-text text-darken-1">
                                <i class="fa fa-clock-o"></i>10:12</span>
                            </div>
                          </div>
                        </div>
                        <div class="row row-fit">
                          <div class="chat-bubble blue-grey lighten-5 left">
                            <div class="chat-bubble-text"> Muy bien, programando.<br>y tu?</br>
                              <span class="chat-timestamp grey-text text-darken-1">
                                <i class="fa fa-clock-o"></i>10:33
                              </span>
                            </div>
                          </div>
                          <div class="row row-fit">
                            <div class="chat-bubble light-green lighten-4 right">
                              <div class="chat-bubble-text">Mirando porno, como de costumbre! jajajajaja
                                <span class="chat-timestamp grey-text text-darken-1">
                                  <i class="fa fa-clock-o"></i>10:34
                                </span>
                              </div>
                            </div>
                          </div>
                          <div class="row row-fit">
                            <div class="chat-bubble blue-grey lighten-5 left">
                              <div class="chat-bubble-text"> jajaja<br>XD</br>
                                <span class="chat-timestamp grey-text text-darken-1">
                                  <i class="fa fa-clock-o"></i>10:41
                                </span>
                              </div>
                            </div>
                          </div>
                          <div class="row row-fit">
                            <div class="chat-bubble light-green lighten-4 right">
                              <div class="chat-bubble-text">Pues igual deberías dejar de machacartela y estudiar un poco.<br>Ya pareces Francesc! XD</br>
                                <span class="chat-timestamp grey-text text-darken-1">
                                  <i class="fa fa-clock-o"></i>10:43
                                </span>
                              </div>
                            </div>
                          </div>
                          <div class="row row-fit">
                            <div class="chat-bubble blue-grey lighten-5 left">
                              <div class="chat-bubble-text">Tampoco te pases tio, yo tengo el pelo largo...
                                <span class="chat-timestamp grey-text text-darken-1">
                                  <i class="fa fa-clock-o"></i>10:44
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="conversation-text-area grey lighten-5">
                        <textarea class="col s10 text-area" placeholder="Escribir un mensaje..."></textarea>
                        <div class="input-field col s1" style="margin-top: 2em; margin-left: 5px;">
                          <a style="vertical-align:top;" name="action">Enviar <i class="material-icons"></i></a>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript">
      $(document).ready(function () {
        // Activate Dropdown menu
        $(".dropdown-button").dropdown({
          hover: false
        });
        // Activate button-collapse for mobile
        $(".button-collapse").sideNav();
        // ****************************
        // ***** RAFA QUE ES ESTO *****
        $('.collapsible').collapsible({
          // A setting that changes the collapsible behavior to expandable instead of the default accordion style
          accordion: false
        });
        $('ul.tabs').tabs();
        // ****************************
      });
    </script>
  </body>
</html>