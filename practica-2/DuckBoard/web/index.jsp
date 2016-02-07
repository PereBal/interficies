<%@page import="servlets.tools.Helper"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <%
    pageContext.setAttribute("logged", Helper.isLogged(request));
    pageContext.setAttribute("user", Helper.getCurrentUser(request));
    %>
<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="parallax-container">
        <div class="parallax"><img src="img/parallax1.png"></div>
        <ul id="staggered-test">
          <li>
            <c:choose>
            <c:when test="${!logged}">
            <h1 class="center">
              Bienvenido!
            </h1>
            </c:when>
            <c:otherwise>
              <h1 class="center">
                Hola de nuevo, ${user.name}!
            </c:otherwise>
            </c:choose>
            
          </li>
          <li>
            <h4 class="center">Haz scroll para descubrir más!</h4>
          </li>
          <div id="login" class="row linear-transition" style="display:none;">
            <form class="col s6 offset-s3" method="POST" action="/duckboard/login">
              <div class="card white">
                <div class="card-content grey-text text-darken-3">
                  <span class="card-title grey-text text-darken-2">Login</span>
                  <div class="input-field">
                    <input id="email" type="email" class="validate" name="email">
                    <label for="email">Email</label>
                  </div>
                  <div class="input-field">
                    <input id="password" type="password" class="validate" name="password">
                    <label for="password">Password</label>
                  </div>
                </div>
                <div class="card-action right-align">
                  <div class="">
                    <a id="cancelLogin" class="" style="cursor:pointer;">Cancelar</a>
                    <button href="#" class="btn waves-effect waves-light light-blue darken-1 " type="submit">Entrar</button>
                  </div>
                </div>
              </div> 
            </form>     
          </div>
        </ul>
      </div>
      <div class="section white" id="staggered-test2">
        <div class="row container">
          <h2 class="header"><img style="width:1em" src="img/favicon.png"/>DuckBoard</h2>
          <p class="grey-text text-darken-3 lighten-3">DuckBoard es un dashboard diseñado por un grupo de estudiantes de la <a href="https://www.uib.es/">Universidad de las Islas Baleares <img style="width:1em" src="img/logo-uib.jpg"/></a>.
            <br>En él podrás consultar gráficos dinámicos sobre la actividad en <a href="https://twitter.com/?lang=es">twitter <i class="blue-text fa fa-twitter"></i></a>, además de
          disfrutar de un servicio de mensajería instantanea entre los usuarios y un apartado de noticias de actualidad y motivación.</p>
        </div>
      </div>
      <div class="parallax-container">
        <div class="parallax"><img src="img/parallax2.jpg"></div>
      </div>
      <div class="section white" id="staggered-test3">
        <div class="row container">
          <h2 class="header">Conócenos <i class="grey-text text-lighten-1 fa fa-hand-peace-o" ></i></h2>
          <p class="grey-text text-darken-3 lighten-3">Aquí puedes ver información sobre los desarrolladores de DuckBoard&reg;</p>
          <div  class="row">
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Rafael Cuart</h6>
              <span class="grey-text text-lighten-1">Ing. de sistemas<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Francesc Bisquerra</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Lucas Martorell</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Pere Balaguer</h6>
              <span class="grey-text text-lighten-1">Ing. de computación<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Tomeu Crespí</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Guillem Barceló</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://es.linkedin.com/"><i class="blue-text text-darken-3 fa fa-linkedin-square"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            
          </div>
        </div>
      </div>
      <div class="parallax-container">
        <div class="parallax"><img src="img/parallax3.jpg"></div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript">
       $(document).ready(function(){
        $('.parallax').parallax();
        Materialize.showStaggeredList('#staggered-test');
       });
        
        var options = [
          {selector: '#staggered-test2', offset: 550, callback: 'Materialize.toast("¿Ya sabes que hacemos?", 1500 )' },
          {selector: '#staggered-test3', offset: 675, callback: 'Materialize.toast("Si! Estos somos nostros!", 1500 )' }
          ]
        Materialize.scrollFire(options);
        
             /*********************************/
              /****** LOGIN FUNCTIONS **********/
              /*********************************/

        $('.loginButton').click(function (e) {

        $('#login').toggle(400, function () {
        // animation complete
        });
        });
        $('#cancelLogin').click(function (e) {

        $('#login').hide(400, function () {
        // animation complete
        });
      });
      
    </script>
  </body>
</html>