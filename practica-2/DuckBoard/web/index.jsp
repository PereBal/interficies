<!DOCTYPE html>
<html lang="en">
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="section no-pad-bot" id="index-banner">

      </div>


      <div class="container">

        <div id="login" class="row linear-transition" style="display:none;">
          <form class="col s8 offset-s2">
            <div class="card blue-grey darken-1">
              <div class="card-content white-text">
                <span class="card-title">Login</span>
                <div class="input-field">
                  <input id="user_name" type="text" class="validate">
                  <label for="user_name">Username</label>
                </div>
                <div class="input-field">
                  <input id="password" type="password" class="validate">
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

        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12">      
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
                    <h5 class="light grey-text text-lighten-3">- Salvador Dalí.</h5>
                  </div>
                </li>
                <li>
                  <img src="http://lorempixel.com/580/250/people/4"> <!-- random image -->
                  <div class="caption center-align">
                    <h3> “Design is not just what it looks like and feels like. Design is how it works.”</h3>
                    <h5 class="light grey-text text-lighten-3">- Steve Jobs.</h5>
                  </div>
                </li>
              </ul>
            </div>
          </div>

         

          <div class="row">
            <div class="col s12">
              <iframe id="player" type="text/html"
                      src="http://www.youtube.com/embed/M7lc1UVf-VE?enablejsapi=1&origin=http://example.com"
                      frameborder="0"></iframe>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/8">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a href="#">View</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/9">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a href="#">View</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/10">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a href="#">View</a>
                </div>
              </div>
            </div>
            <div class="col s12 m3">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/9">
                  <span class="card-title">Card Title</span>
                </div>
                <div class="card-action">
                  <a href="#">View</a>
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

      /*********************************/
      /******** IMAGE CAROUSEL *********/
      /*********************************/

      $(document).ready(function () {

        $('.slider').slider({full_width: true});
      });



    </script>

  </body>
</html>
