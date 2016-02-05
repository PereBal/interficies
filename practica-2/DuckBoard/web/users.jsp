<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container top-row-buffer">
        <div class="row">
          <div class="col s12">
            <h4>Users</h4>
          </div>
        </div>
        <div class="row">
          <div class="col s12">
            <ul class="collapsible popout" data-collapsible="accordion">
              <li class="avatar">
                <div class="collapsible-header">
                  <i class="material-icons">perm_identity</i>email - username</div>
                <div class="collapsible-body inner-padding grey-text text-darken-2">

                  <div class="row">
                    <div class="col s4">
                      <p><b>Nombre: </b>nombre</p>
                    </div>
                    <div class="col s5">
                      <p><b>Apellidos: </b>Apellido1 Apellido2</p>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s4">
                      <p><b>Email: </b>email@email.com</p>
                    </div>
                    <div class="col s5">
                      <p><b>Fecha de nacimiento: </b>cumpleañosfeliiiiz</p>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s2">
                      <p><b>Sexo: </b>Hombre <i class="fa fa-mars"></i></p>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s12">
                      <p>
                        <b>Cita: <br/></b>
                        <b>"</b>Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de l
                        as industrias desde el año <b>"</b></p>
                    </div>
                  </div>

                </div>  
              </li>
            </ul>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript">
      $(document).ready(function () {
        $('.collapsible').collapsible({
          accordion: true // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
      });
    </script>
  </body>
</html> 
