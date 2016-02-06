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
          <form class="col s12">
            <div class="row">
              <div class="input-field col s6 offset-s3">
                <i class="material-icons prefix">search</i>
                <input id="userSearch" type="text" class="validate">
                <label for="icon_prefix">Buscar</label>
              </div>
            </div>
          </form>
        </div>

        <div class="row">
          <div class="col s12">
            <ul id="usersList" class="collapsible popout" data-collapsible="accordion">
            </ul>
          </div>
        </div>
      </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="scripts.jsp"/>
    <script type="text/javascript">
        
        var userFilter = '';
        
        var inputFilter = $('#userSearch');
        
        var collapsibleList = $('#usersList');
      
        /**
         * Repaints collapsible headers
         */  
        var paintCollapsibleHeaders = function(collapsibleHeaders) {
          
          // Empty collapsible list
          collapsibleList.empty();
          
          // Paint collapsible list
          $.each(collapsibleHeaders, function(index, collapsible){
            
            var toAppend = '<li>'+
                                '<div class="collapsible-header" onclick="appendCollapsibleBody(\'collapsible-'+ collapsible.id +'\');">'+
                                  '<i class="material-icons">perm_identity</i>'+
                                    'Nombre: ' + collapsible.name +  ' - ' + 'Email: ' + collapsible.email +
                                '</div>'+
                                '<div id="collapsible-'+ collapsible.id +'" class="collapsible-body inner-padding grey-text text-darken-2">'+ 
                                '</div>'+
                            '</li>'
              
            collapsibleList.append(toAppend);
          });
        };
      
      /**
       * 
       * @param {type} collapsible
       * @returns {undefined}
       */
      var appendCollapsibleBody = function(collapsible) {
                  
          id = collapsible.split('-')[1];
          
          console.log(id)
           // ajax query
          
          $.ajax({
            type: 'GET',
            url: '/duckboard/users',
            async: true,
            dataType: "json",
            data: ({
              'id': id,
            }),
            success: function (data) {
              
                paintCollapsibleBody(data);
                
                console.log(data)
            },
            error: function (){}
          }); 
        };
        
        /**
         * 
         * @param {type} collapsibleBody
         * @returns {undefined}
         */
        var paintCollapsibleBody = function(collapsibleBody) {
          
          var target = $('#collapsible-' + collapsibleBody.id);
          
          var sex = '';
          
          if (collapsibleBody.sex = 'H') {
            
            sex = 'Hombre <i class="fa fa-mars"></i>'
          } else {
            
            sex = 'Mujer <i class="fa fa-venus"></i>'
          }
          
          var toAppend = '<div class="row">'+
                          '<div class="col s4">'+
                            '<p><b>Nombre: </b>'+collapsibleBody.name+'</p>'+
                          '</div>'+
                          '<div class="col s5">'+
                            '<p><b>Apellidos: </b>'+collapsibleBody.lastName+'</p>'+
                          '</div>'+
                        '</div>'+
                        '<div class="row">'+
                          '<div class="col s4">'+
                            '<p><b>Email: </b>'+ collapsibleBody.email+'</p>'+
                          '</div>'+
                          '<div class="col s5">'+
                            '<p><b>Fecha de nacimiento: </b>'+collapsibleBody.birthDay+'</p>'+
                          '</div>'+
                        '</div>'+
                        '<div class="row">'+
                          '<div class="col s2">'+
                            '<p><b>Sexo: </b>'+sex+'</p>'+
                          '</div>'+
                        '</div>'+
                        '<div class="row">'+
                          '<div class="col s12">'+
                            '<p>'+
                              '<b>Cita: <br/></b>'+
                              '<b>"</b>'+collapsibleBody.quote+'<b>"</b></p>'+
                          '</div>'+
                        '</div>'
          
          // Empty collapsible list
          target.empty();
          
          target.append(toAppend);
        };
      
      
      $(document).ready(function () {
        
        // ajax query
        $.ajax({
          type: 'GET',
          url: '/duckboard/users',
          async: true,
          dataType: "json",
          data: ({
            'q': userFilter,
          }),
          success: function (data) {

              paintCollapsibleHeaders(data);
          },
          error: function (){}
        });
          
        
        $('.collapsible').collapsible({
          accordion: true // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
        
        inputFilter.keyup(function() {
          
          userFilter = inputFilter.val();
                    
          // ajax query
          
          $.ajax({
            type: 'GET',
            url: '/duckboard/users',
            async: true,
            dataType: "json",
            data: ({
              'q': userFilter,
            }),
            success: function (data) {
              
                paintCollapsibleHeaders(data);
            },
            error: function (){}
          });
        });
        
      });
    </script>
  </body>
</html> 
