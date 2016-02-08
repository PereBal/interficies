<%@page import="servlets.tools.Helper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
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
                        <a class="breadcrumb">Motivacional</a>
                      </div>
                    </div>
                </nav>
            </div>
        </div>
      <div class="container">
        
        <div class="section no-pad-bot" id="index-banner"></div>
        
        <div id="toastMessages"></div>
        
        <div id="login" class="row linear-transition" style="display:none;">
          <form class="col s8 offset-s2" method="POST" action="/duckboard/login">
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

        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12">           
            <iframe src="https://embed.spotify.com/?uri=spotify:user:spotify_espa%C3%B1a:playlist:2JIAFFwXL4tAGF0sZFIklk" width="100%" height="100rem" frameborder="0" allowtransparency="true"></iframe>
            <div class="slider">
              <ul class="slides">
                <li>
                  <img src="http://lorempixel.com/580/250/people/1"> <!-- random image -->
                  <div class="caption center-align">
                    <h3>"If you cannot do great things, do small things in a great way."</h3>
                    <h5 class="light grey-text text-lighten-3">- Napoleon Hill</h5>
                  </div>
                </li>
                <li>
                  <img src="http://lorempixel.com/580/250/people/2"> <!-- random image -->
                  <div class="caption left-align">
                    <h3>"Everything should be made as simple as possible, but not simpler."</h3>
                    <h5 class="light grey-text text-lighten-3">- Albert Einsten</h5>
                  </div>
                </li>
                <li>
                  <img src="http://lorempixel.com/580/250/people/3"> <!-- random image -->
                  <div class="caption right-align">
                    <h3>"Have no fear of perfeciton, you'll never reach it."</h3>
                    <h5 class="light grey-text text-lighten-3">- Salvador Dalí­.</h5>
                  </div>
                </li>
                <li>
                  <img src="http://lorempixel.com/580/250/people/4"> <!-- random image -->
                  <div class="caption center-align">
                    <h3> "Design is not just what it looks like and feels like. Design is how it works."</h3>
                    <h5 class="light grey-text text-lighten-3">- Steve Jobs.</h5>
                  </div>
                </li>
              </ul>
            </div>
          </div>

          <div class="row">
            <div class="col s12" style="height:29em;margin-top: 1em; ">
              <iframe id="player" type="video/html"
                      src="https://www.youtube.com/embed/26U_seo0a1g"
                      frameborder="0" width="100%" height="100%" allowfullscreen>                       
              </iframe>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/2">
                  <span class="card-title">Tetris Gameboy</span>
                </div>
                <div class="card-action">
                  <a class="blue-text text-lighten-1" href="https://ihatetomatoes.net/happy-25th-birthday-game-boy/">Leer más...</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/3">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a class="blue-text text-lighten-1" href="#">Leer más...</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/4">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a class="blue-text text-lighten-1" href="#">Leer más...</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/5">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a class="blue-text text-lighten-1" href="#">Leer más...</a>
                </div>
              </div>
            </div>
          </div>
          <!-- end for -->
        </div>

    </main>

    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>

    <script type="text/javascript">

      $(document).ready(function () {
          
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
      /*********************************/
      /******** IMAGE CAROUSEL *********/
      /*********************************/

      $('.slider').slider({full_width: true});
         
         
      /*********************************/
      /******** TOAST MSGS *************/
      /*********************************/
      });

    </script>

  </body>
</html>
