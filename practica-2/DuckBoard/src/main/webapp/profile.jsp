
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="servlets.tools.Helper"%>
<%-- 
    Document   : profile
    Created on : Feb 4, 2016, 7:18:25 PM
    Author     : francesc
--%>

<!DOCTYPE html>
<html lang="en">

  <%
    pageContext.setAttribute("user", Helper.getCurrentUser(request));
  %>
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
                        <a class="breadcrumb">Perfil</a>
                      </div>
                    </div>
                </nav>
            </div>
        </div>
      <div class="container">

        <div class="section no-pad-bot" id="index-banner"></div>


        <div class="row linear-transition" id="updateForm" style="display:none;">
          <div class="card col s12 ">
            <form class="col s12" method="POST" action="/duckboard/users?id=${user.id}">
              <div class="row">
                <div class="input-field col s12">
                  <h5><i class="material-icons prefix" style="position: relative;top: 0.2em;">account_circle</i>Información personal:</h5>
                </div>

                <div class="col s10 offset-s1">
                  <div class="input-field col s6">
                    <input name="name" type="text" class="validate" required value="${user.name}">
                    <label for="name">Name</label>
                  </div>
                  <div class="input-field col s6">
                    <input name="birth_day" type="date" class="datepicker" value="${user.birthDay}" required>
                    <label for="birth_day">Birthday</label>
                  </div>
                  <div class="input-field col s10">
                    <input name="last_name" type="text" class="validate" required value="${user.lastName}">
                    <label for="last_name">Surname</label>
                  </div>
                  <div class="input-field col s2">
                    <p>
                      <input class="with-gap" name="sex" type="radio" value="H" id="test1" 
                             <c:if test="${user.sex == 'H'}">checked</c:if> required/>
                             <label for="test1">Male</label>
                      </p>
                      <p>
                        <input class="with-gap" name="sex" type="radio" value="M" id="test2" 
                        <c:if test="${user.sex == 'M'}">checked</c:if> required/>
                        <label for="test2">Female</label>
                      </p>
                    </div>
                    <div class="input-field col s12">
                      <input name="email" type="email" class="validate" required value="${user.email}">
                    <label for="email" data-error="invalid email">Correo electrónico</label>
                  </div> 
                </div> <!--End of personal information-->

                <div class="input-field col s12">
                  <h5><i class="material-icons prefix" style="position: relative;top: 0.2em;">perm_identity</i>Información de usuario:</h5>
                </div>
                <div class="col s10 offset-s1">

                  <div class="input-field col s6">
                    <input name="pwd" type="password" class="validate" length="32">
                    <label for="pwd">Password</label>
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
                    <input name="pwd2" type="password" class="validate" length="32">
                    <label for="pwd2">Repeat password</label>
                  </div>

                  <div class="input-field col s12">
                    <textarea name="quote" class="materialize-textarea" length="256" >${user.quote}</textarea>
                    <label for="quote">Favourite quote</label>
                  </div>
                </div> <!--End of user information-->
              </div>

              <div class="card-action">
                <a id="cancelUpdate" style="cursor: pointer;">CANCELAR</a>
                <button class="btn waves-effect waves-light light-blue darken-1" 
                        type="submit">ACTUALIZAR
                </button>
              </div>

            </form>

          </div>
        </div>

        <div class="row">
          <div class="col s10">
            <h4>Informacion de usuario</h4>
          </div>
        </div>
        <div class="row">
          <div class="card grey-text text-darken-2">
            <div class="card-content">
              <div class="row">
                <div class="col s3">
                  <div class="row">
                    <div class="col s12">
                      <img style="width: 100%; border-radius: 3px;" <c:out value="src=${user.gravatarUrl}"/>>
                    </div>
                  </div>
                </div>
                <div class="col s9">
                  <div class="row">
                    <div class="col s4">
                      <b>Nombre: </b>${user.name}
                    </div>
                    <div class="col s5">
                      <b>Apellidos: </b>${user.lastName}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s4">
                      <b>Email: </b>${user.email}
                    </div>
                    <div class="col s5">
                      <b>Fecha de nacimiento: </b>${user.birthDay}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s2">
                      <b>Sexo: </b>
                      <c:choose>
                        <c:when test="${user.sex == 'H'}">Hombre <i class="blue-text fa fa-mars"></i></c:when>
                        <c:when test="${user.sex == 'M'}">Mujer <i class="pink-text fa fa-venus"></i></c:when>
                      </c:choose>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col s12">
                      <p>
                        <b>Cita: <br/></b>
                        <b>"</b><i>${user.quote} </i><b>"</b></p>
                    </div>
                  </div>
                </div>
              </div>

            </div>

            <div class="card-action">
              <a id="deleteAccount" class="red-text"  style="cursor: pointer">ELIMINAR</a>
              <button  id="editButton" class="btn waves-effect waves-light light-blue darken-1">
                EDITAR
                <i class="fa fa-edit fa-2x"></i>
              </button>
            </div>
          </div> 
        </div>

      </div>

    </div>

  </main>

  <jsp:include page="footer.jsp"/>
  <jsp:include page="scripts.jsp"/>

  <script type="text/javascript">

    $(document).ready(function () {
      $('#deleteAccount').click(function (e) {
        $.ajax({
          url: '/duckboard/users',
          type: 'DELETE',
          success: function (data) {
            // very safe, much wow...
            window.location.replace('/duckboard');
          },
          error: function (err) {
            window.location.replace('/duckboard');
          }
        });
      });

      $('#editButton').click(function (e) {

        $('#updateForm').show(400, function () {
          // animation complete
        });
      });

      $('#cancelUpdate').click(function (e) {

        $('#updateForm').hide(400, function () {
          // animation complete
        });
      });

      /*********************************/
      /******** TOAST MSGS *************/
      /*********************************/
    });

  </script>

</body>
</html>
