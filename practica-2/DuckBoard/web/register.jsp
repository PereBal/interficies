<%-- 
    Document   : register
    Created on : Feb 4, 2016, 7:19:14 PM
    Author     : Rafa Cuart 
--%>
<%@page import="servlets.tools.Helper"%>
<!DOCTYPE html>
<html lang="en">
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container">
        <!--FORM REGISTER-->
        <h4>Registro de usuario</h4>
        <div class="row login" >
          <div class="card col s12 ">
            <form class="col s12" method="POST" action="/duckboard/users">
              <div class="row">
                <div class="input-field col s12">
                  <h5><i class="material-icons prefix" style="position: relative;top: 0.2em;">account_circle</i>Información personal:</h5>
                </div>

                <div class="col s10 offset-s1 grey-text text-darken-3">
                  <div class="input-field col s6">
                    <input name="name" type="text" class="validate" required>
                    <label for="name">Name</label>
                  </div>
                  <div class="input-field col s6">
                    <input name="birth_day" type="date" class="datepicker" required>
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
                <div class="col s10 offset-s1 grey-text text-darken-3">
                  <div class="input-field col s6">
                    <input id="pwd" name="pwd" type="password" length="32" required>
                    <label for="pwd">Password</label>
                  </div>

                  <div class="input-field col s6">
                    <input id="pwd2" name="pwd2" type="password" length="32" required>
                    <label for="pwd2">Repeat password</label>
                  </div>

                  <div class="input-field col s12">
                    <textarea name="quote" class="materialize-textarea" length="256"></textarea>
                    <label for="quote">Favourite quote</label>
                  </div>
                </div> <!--End of user information-->
              </div>

              <div class="card-action right-align">
                <a href="/duckboard">VOLVER</a>
                <button  class="btn waves-effect waves-light light-blue darken-1" type="submit"
                         type="button">REGISTRARSE
                </button>
              </div>

            </form>

          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>

    <script type="text/javascript">

      $(document).ready(function () {        
      /*********************************/
      /******** TOAST MSGS *************/
      /*********************************/

      var pwd = document.getElementById('pwd');
      var pwd2 = document.getElementById('pwd2');
      $('#pwd').keypress(function (event) {
        if (event.which === 13) {
          event.preventDefault();
        }
        pwdcheck();
      });
      $('#pwd2').keypress(function (event) {
        if (event.which === 13) {
          event.preventDefault();
        }
        pwdcheck();
      });

      function pwdcheck() {
        if (pwd.value !== pwd2.value) {
          // Wrong label
          document.getElementById('');
        } else {
          // Matches label
        }
      };
    </script>
  </body>
</html>