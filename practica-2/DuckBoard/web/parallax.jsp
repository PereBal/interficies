<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="parallax-container">
        <div class="parallax"><img src="img/parallax1.jpg"></div>
      </div>
      <div class="section white">
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
      <div class="section white">
        <div class="row container">
          <h2 class="header">Parallax</h2>
          <p class="grey-text text-darken-3 lighten-3">Parallax is an effect where the background content or image in this case, is moved at a different speed than the foreground content while scrolling.</p>
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
    </script>
  </body>
</html>