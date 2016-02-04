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
                <div class="collapsible-body">
                  <h3>INFORMACION DE USER</h3>
                </div>  
              </li>
              <li class="avatar">
                <div class="collapsible-header">
                  <i class="material-icons">perm_identity</i>email - username</div>
                <div class="collapsible-body">
                  <h3>INFORMACION DE USER</h3>
                </div>  
              </li>
              <li class="avatar">
                <div class="collapsible-header">
                  <i class="material-icons">perm_identity</i>email - username</div>
                <div class="collapsible-body">
                  <h3>INFORMACION DE USER</h3>
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
