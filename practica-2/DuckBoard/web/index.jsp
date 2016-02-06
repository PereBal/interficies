<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
            <h1 style="margin-left:2em;" >
              Bienvenido!
              <h4 style="margin-left:2.8em;">Haz scroll para descubrir m�s!</h4>
            </h1>
          </li>
        </ul>
      </div>
      <div class="section white" id="staggered-test2">
        <div class="row container">
          <h2 class="header"><img style="width:1em" src="img/favicon.png"/>DuckBoard</h2>
          <p class="grey-text text-darken-3 lighten-3">DuckBoard es un dashboard dise�ado por un grupo de estudiantes de la <a href="https://www.uib.es/">Universidad de las Islas Baleares <img style="width:1em" src="img/logo-uib.jpg"/></a>.
            <br>En �l podr�s consultar gr�ficos din�micos sobre la actividad en <a href="https://twitter.com/?lang=es">twitter <i class="blue-text fa fa-twitter"></i></a>, adem�s de
          disfrutar de un servicio de mensajer�a instantanea entre los usuarios y un apartado de noticias de actualidad y motivaci�n.</p>
        </div>
      </div>
      <div class="parallax-container">
        <div class="parallax"><img src="img/parallax2.jpg"></div>
      </div>
      <div class="section white" id="staggered-test3">
        <div class="row container">
          <h2 class="header">Con�cenos <i class="grey-text text-lighten-1 fa fa-hand-peace-o" ></i></h2>
          <p class="grey-text text-darken-3 lighten-3">Aqu� puedes ver informaci�n sobre los desarrolladores de DuckBoard&reg;</p>
          <div  class="row">
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Rafael Cuart</h6>
              <span class="grey-text text-lighten-1">Ing. de sistemas<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
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
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Pere Balaguer</h6>
              <span class="grey-text text-lighten-1">Ing. de computaci�n<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Tomeu Cresp�</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
                <a href="https://github.com/"><i class="black-text fa fa-github"></i></a>
                <a href="https://plus.google.com/"><i class="red-text fa fa-google-plus"></i></a>
              </span>
            </div>
            <div class="col s2 center">
              <img class="parallax-img circle" src="img/user-pd.png" />
              <h6>Guillem Barcel�</h6>
              <span class="grey-text text-lighten-1">Ing. de hardware<br></span>
              <span>
                <a href="https://twitter.com/?lang=es"><i class="fa fa-twitter"></i></a>
                <a href="https://es-es.facebook.com/"><i class="blue-text text-darken-4 fa fa-facebook-official"></i></a>
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
       });
        Materialize.showStaggeredList('#staggered-test');
        var options = [
          {selector: '#staggered-test2', offset: 550, callback: 'Materialize.toast("�Ya sabes que hacemos?", 1500 )' },
          {selector: '#staggered-test3', offset: 675, callback: 'Materialize.toast("Si! Estos somos nostros!", 1500 )' }
          ];
        
  Materialize.scrollFire(options);
    </script>
  </body>
</html>