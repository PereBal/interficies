<%-- 
    Document   : register
    Created on : Feb 4, 2016, 7:19:14 PM
    Author     : Rafa Cuart 8
--%>
<!DOCTYPE html>
<html lang="en">
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container">
        <!--FORM REGISTER-->
        <div class="row login" >
          <div class="card col s12 ">
            <form class="col s12" method="POST" action="/blabla.jsp">
              <div class="row">
                <div class="input-field col s12">
                  <h5><i class="material-icons prefix" style="position: relative;top: 0.2em;">account_circle</i>Información personal:</h5>
                </div>

                <div class="col s10 offset-s1">
                  <div class="input-field col s6">
                    <input name="name" type="text" class="validate" required>
                    <label for="name">Name</label>
                  </div>
                  <div class="input-field col s6">
                    <input name="birth_day" type="date" class="datepicker">
                    <label for="birth_day">Birthday</label>
                  </div>
                  <div class="input-field col s10">
                    <input name="last_name" type="text" class="validate" required>
                    <label for="last_name">Surname</label>
                  </div>
                  <div class="input-field col s2">
                    <p>
                      <input class="with-gap" name="sex" type="radio" value="H" id="test1" checked required/>
                      <label for="test1">Male</label>
                    </p>
                    <p>
                      <input class="with-gap" name="sex" type="radio" value="M" id="test2" required/>
                      <label for="test2">Female</label>
                    </p>
                  </div>
                  <div class="input-field col s12">
                    <input name="email" type="email" class="validate" required>
                    <label for="email" data-error="invalid email">Correo electrónico</label>
                  </div> 
                </div> <!--End of personal information-->

                <div class="input-field col s12">
                  <h5><i class="material-icons prefix" style="position: relative;top: 0.2em;">perm_identity</i>Información de usuario:</h5>
                </div>
                <div class="col s10 offset-s1">

                  <div class="input-field col s6">
                    <input name="psw" type="password" class="validate" length="32" required>
                    <label for="psw">Password</label>
                  </div>
                  <!--              IMAGENES
                                    <div class="file-field input-field col s6">
                                      <input class="file-path validate" type="text" name="img"/><input type="file" />
                                      <label >Imagen</label>
                                    </div>
                                    <div class="file-field input-field col s6">
                                      <input class="validate" type="url" name="url">
                                      <label >Imagen (URL)</label>
                                    </div>
                  -->
                  <div class="input-field col s6">
                    <input name="psw2" type="password" class="validate" length="32" required>
                    <label for="psw2">Repeat password</label>
                  </div>

                  <div class="input-field col s12">
                    <textarea name="quote" class="materialize-textarea" length="256"></textarea>
                    <label for="quote">Favourite quote</label>
                  </div>
                </div> <!--End of user information-->
              </div>

              <button style=" margin-bottom:1.75em;" class="btn waves-effect waves-light col s3 offset-s4" 
                      type="button" onclick="ValidarRegistro()">Register
                <i class="material-icons">send</i>
              </button>

            </form>

          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>

    <script type="text/javascript">

      $(document).ready(function () {
        $(".button-collapse").sideNav();
        var $calendar = $('.datepicker').pickadate({
          selectMonths: true, // Creates a dropdown to control month
          selectYears: 160, // Creates a dropdown of 80 years to control year
          clear: false
        });

        $('select').material_select();
      });

    </script>

  </body>
</html>

