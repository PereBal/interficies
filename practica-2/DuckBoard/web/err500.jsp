<%-- 
    Document   : err404
    Created on : 05-feb-2016, 16:40:56
    Author     : rafaccount
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="blue-grey darken-3">
  <jsp:include page="head.jsp"/>
  <body>
    <main>
      <!--
      <div class="row" style="margin-top:10em;">
        <div class="col s8 offset-s2">
          <div class="col s4">
            <img src="http://www.iconarchive.com/download/i24289/martin-berube/animal/duck.ico"></img>
          </div>
          <div class="col s8">
            <div class="grey lighten-4 err-page-message">
              <h1 clas="grey-text text-lighten-4">ERROR 404</h1>
              <h3 clas="grey-text text-lighten-2">PAGE NOT FOUND</h3>
            </div>
          </div>
        </div>
      </div>  
      -->
      <div class="row" style="margin-top:3em;">
        <div class="col s8 offset-s3">
          <div class="col s3 err-page-number orange-text text-darken-1">
            5
          </div>
          <div class="col s4">
            <img style="margin-top:3em; margin-left:-1em;" src="http://www.iconarchive.com/download/i24289/martin-berube/animal/duck.ico"></img>
          </div>
          <div class="col s4">
            <img style="margin-top:3em; margin-left:-1em;" src="http://www.iconarchive.com/download/i24289/martin-berube/animal/duck.ico"></img>
          </div>
        </div>
        <div class="col s8 offset-s3">
          <div class="col s10 err-page-error red-text text-darken-1 center-align">
            Ooups! Internal server error <i class="fa fa-meh-o"></i>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript"></script>
  </body>
</html>
