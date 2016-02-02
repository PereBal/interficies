<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title>Starter Template - Materialize</title>

  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="css/dashboard.css" type="text/css" rel="stylesheet" media="screen,projection">
</head>
<body>
  <header>
    <nav class="light-blue lighten-1" role="navigation">
      <div class="nav-wrapper container"><a id="logo-container" href="#" class="brand-logo">Logo</a>
        <ul class="right hide-on-med-and-down">
          <li><a href="#">Navbar Link</a></li>
        <!-- Login form if no logged -->
          <li>  
            <a id="loginButton" class="waves-effect waves-light">Login</a>     
          </li>
        </ul>
        <!-- end Login form -->

        <ul id="nav-mobile" class="side-nav">
          <li><a href="#">Navbar Link</a></li>
           <!-- Login form if no logged -->
          <li>  
            <a id="loginButton" class="waves-effect waves-light">Login</a>     
          </li>
        </ul>

        <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
      </div>
    </nav>
  </header>

  <main>
    <div class="section no-pad-bot" id="index-banner">
      
    </div>


    <div class="container">
      <div class="section">

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
        </div>

      </div>
      <br><br>

      <div class="section">

      </div>
    </div>

  </main>
  <footer class="page-footer orange">
    <div class="footer-copyright">
      <div class="container">
      Made by <a class="orange-text text-lighten-3" href="http://materializecss.com">Materialize</a>
      </div>
    </div>
  </footer>


  <!--  Scripts-->
  <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script src="js/materialize.js"></script>
  <script src="js/init.js"></script>
  <script type="text/javascript">

  /*********************************/
  /****** LOGIN FUNCTUIONS *********/
  /*********************************/

    $('#loginButton').click(function(e) {

      $('#login').show("slow", function () {
        // animation complete
      });
    });

    $('#cancelLogin').click(function(e) {

      $('#login').hide("slow", function () {
        // animation complete
      });
    });


  /*********************************/
  /******** IMAGE CAROUSEL *********/
  /*********************************/

      $(document).ready(function(){

      $('.slider').slider({full_width: true});
    });
        


  </script>

  </body>
</html>
