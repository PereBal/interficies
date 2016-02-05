
<%-- 
    Document   : profile
    Created on : Feb 4, 2016, 7:18:25 PM
    Author     : francesc
--%>

<!DOCTYPE html>
<html lang="en">
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>

      <div class="container">
        <div class="row">
          <div class="col s10">
            <h4>Informacion de usuario</h4>
          </div>
          <div class="col s2">
            <button class="btn waves-effect waves-light light-blue darken-1" type="submit" name="action" style="margin-top: 1.4rem">Editar
              <i class="fa fa-edit fa-2x"></i>
            </button>
          </div>
        </div>
        <div class="row">
          <div class="card-panel grey-text text-darken-2">
            <div class="row">
              <div class="col s4">
                <b>Nombre: </b>nombre
              </div>
              <div class="col s5">
                <b>Apellidos: </b>Apellido1 Apellido2
              </div>
            </div>
            <div class="row">
              <div class="col s4">
                <b>Email: </b>email@email.com
              </div>
              <div class="col s5">
                <b>Fecha de nacimiento: </b>cumpleañosfeliiiiz
              </div>
            </div>
            <div class="row">
              <div class="col s2">
                <b>Sexo: </b>Hombre <i class="fa fa-mars"></i>
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
        </div>

      </div>

    </div>

  </main>

  <jsp:include page="footer.jsp"/>
  <jsp:include page="scripts.jsp"/>

  <script type="text/javascript">


  </script>

</body>
</html>
