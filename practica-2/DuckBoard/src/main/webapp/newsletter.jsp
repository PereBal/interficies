<%@page import="servlets.tools.Helper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <jsp:include page="head.jsp"/>
    <body>
        <jsp:include page="navbar.jsp"/>
        <main>
          <div class="section">
            <nav>
                <div class="nav-wrapper">
                    <div class="col s12 blue-grey darken-1" style="padding-left: 1em">
                    <a class="breadcrumb">Duckoard</a>
                    <a class="breadcrumb">Newsletter</a>
                  </div>
                </div>
            </nav>
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

                <!--   videos  -->
                <div class="row">     
                    <div class="col s9"> 
                        <div class="card center" style="padding-bottom: 10px;">
                            <h4 class="card-title" style="padding-top: 10px;">Último video</h4>
                            <iframe  id="player" type="video/html"
                                     src="https://www.youtube.com/embed/U6z36JjGyCQ"
                                     frameborder="0" width="95%" height="450px" allowfullscreen>                       
                            </iframe>
                        </div>
                    </div>
                    <div class="col s3 ">
                        <div class="card center">
                            <h5 class="card-title" style="padding-top: 10px;" >Otros videos</h5>
                            <div class="video-lat">

                                <div class="col s12">           
                                    <iframe id="player" type="video/html"
                                            src="https://www.youtube.com/embed/YAb3Bq96-K8"
                                            frameborder="0" width="98%" height="150px" allowfullscreen>                       
                                    </iframe>
                                </div>
                                <div class="col s12">           
                                    <iframe id="player" type="video/html"
                                            src="https://www.youtube.com/embed/icb0apfAEVU"
                                            frameborder="0" width="98%" height="150px" allowfullscreen>                       
                                    </iframe>
                                </div>

                                <div class="col s12">           
                                    <iframe id="player" type="video/html"
                                            src="https://www.youtube.com/embed/zJNsv79RgNM"
                                            frameborder="0" width="98%" height="150px" allowfullscreen>                       
                                    </iframe>
                                </div>
                                <div class="col s12">           
                                    <iframe id="player" type="video/html"
                                            src="https://www.youtube.com/embed/PN5_BdqZCnk"
                                            frameborder="0" width="98%" height="150px" allowfullscreen>                       
                                    </iframe>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- noticias -->
                    <div class="col s9"> 
                        <div class="card center" >
                            <h4 class="card-title" style="padding-top:10px;"> Últimas noticias</h4>
                            <ul class="collection notice-left">
                                
                                <li class="collection-item avatar row">
                                    <div class="col s3">
                                        <img src="http://lorempixel.com/400/400/technics/1" alt="" style="width: 100%">
                                    </div>
                                    <div class="col s9 left-align">
                                    <h5 class="title">Noticia 1</h5>
                                    <p>
                                        "<i>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua.
                                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</i>"
                                    </p>
                                    <a href="#!" class="right">Leer más...</a>
                                    </div>
                 
                                </li>
                                <li class="collection-item avatar row">
                                    <div class="col s3">
                                        <img src="http://lorempixel.com/400/400/technics/2" alt="" style="width: 100%">
                                    </div>
                                    <div class="col s9 left-align">
                                    <h5 class="title">Noticia 2</h5>
                                    <p>
                                        "<i>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua.
                                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</i>"
                                    </p>
                                    <a href="#!" class="right">Leer más...</a>
                                    </div>
                 
                                </li>
                                <li class="collection-item avatar row">
                                    <div class="col s3">
                                        <img src="http://lorempixel.com/400/400/technics/3" alt="" style="width: 100%">
                                    </div>
                                    <div class="col s9 left-align">
                                    <h5 class="title">Noticia 3</h5>
                                    <p>
                                        "<i>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua.
                                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</i>"
                                    </p>
                                    <a href="#!" class="right">Leer más...</a>
                                    </div>
                 
                                </li>
                                <li class="collection-item avatar row">
                                    <div class="col s3">
                                        <img src="http://lorempixel.com/400/400/technics/4" alt="" style="width: 100%">
                                    </div>
                                    <div class="col s9 left-align">
                                    <h5 class="title">Noticia 4</h5>
                                    <p>
                                        "<i>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua.
                                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</i>"
                                    </p>
                                    <a href="#!" class="right">Leer más...</a>
                                    </div>
                 
                                </li>
                                <li class="collection-item avatar row">
                                    <div class="col s3">
                                        <img src="http://lorempixel.com/400/400/technics/5" alt="" style="width: 100%">
                                    </div>
                                    <div class="col s9 left-align">
                                    <h5 class="title">Noticia 5</h5>
                                    <p>
                                        "<i>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua.
                                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</i>"
                                    </p>
                                    <a href="#!" class="right">Leer más...</a>
                                    </div>
                 
                                </li>
                                <li class=" center" style="padding-bottom: 10px;padding-top:10px;">
                                    <span ><i class=" fa fa-circle-o-notch fa-spin"></i></span>
                                </li>

                            </ul>
                        </div>
                    </div>

                    <div class="col s3 ">
                        <div class="card left" style="margin-top: 15px;">
                            <h5 class="center-align card-title" style=" padding-bottom:5px">Destacadas</h5>
                             <div class="divider"></div>
                            <div class="notice-lat">
                                <div class="col s12 left">
                                    
                                    <blockquote>
                                        <h5><b>Título1</b></h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua...<a href="#!" class="grey-text "> ver más!</a></p>
                                        
                                    </blockquote>
                                        
                                    <div class="divider"></div>
                                </div>
                               <div class="col s12 left">
                                    
                                    <blockquote>
                                        <h5><b>Título2</b></h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua...<a href="#!" class="grey-text "> ver más!</a></p>
                                        
                                    </blockquote>
                                        
                                    <div class="divider"></div>
                                </div>
                                <div class="col s12 left">
                                    
                                    <blockquote>
                                        <h5><b>Título3</b></h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua...<a href="#!" class="grey-text "> ver más!</a></p>
                                        
                                    </blockquote>
                                        
                                    <div class="divider"></div>
                                </div>
                                <div class="col s12 left">
                                    
                                    <blockquote>
                                        <h5><b>Título4</b></h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua...<a href="#!" class="grey-text "> ver más!</a></p>
                                        
                                    </blockquote>
                                        
                                    <div class="divider"></div>
                                </div>
                                <div class="col s12 left">
                                    
                                    <blockquote>
                                        <h5><b>Título5</b></h5>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore 
                                            et dolore magna aliqua...<a href="#!" class="grey-text "> ver más!</a></p>
                                        
                                    </blockquote>
                                        
                                    <div class="divider"></div>
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
