<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="container top-row-buffer">
        <div class="row">
          <div class="col s12">
            <h4>Usuarios</h4>
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
            
            var toAppend = '<li class="white">'+
                              '<div class="collapsible-header" onclick="appendCollapsibleBody(\'collapsible-'+ collapsible.id +'\');">'+
                                '<i class="material-icons">perm_identity</i>'+
                                'Nombre: ' + collapsible.name +  ' - ' + 'Email: ' + collapsible.email +
                                '<a class="waves-effect waves-light right" onclick="startChat('+ collapsible.id +')">'+
                                  '<i class="material-icons right-align">comment</i>' + 
                                '</a>'+
                                '</div>' +
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
                  
          var id = collapsible.split('-')[1];
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
            
            sex = 'Hombre <i class="blue-text fa fa-mars"></i>'
          } else {
            
            sex = 'Mujer <i class="pink-text fa fa-venus"></i>'
          }
          
          var toAppend ='<div class="row">'+
                          '<div class="col s3">'+
                            '<div class="row">'+
                              '<div class="col s12">'+
                                '<img style="width: 100%; border-radius: 3px;" src="'+collapsibleBody.gravatarUrl+'"/>'+
                              '</div>'+
                            '</div>'+
                          '</div>'+
                          '<div class="col s9">'+
                            '<div class="row">'+
                              '<div class="col s4">'+
                                '<p><b>Nombre: </b>'+collapsibleBody.name+'</p>'+
                              '</div>'+
                              '<div class="col s5">'+
                                '<p><b>Apellidos: </b>'+collapsibleBody.last_name+'</p>'+
                              '</div>'+
                            '</div>'+
                            '<div class="row">'+
                              '<div class="col s4">'+
                                '<p><b>Email: </b>'+ collapsibleBody.email+'</p>'+
                              '</div>'+
                              '<div class="col s5">'+
                                '<p><b>Fecha de nacimiento: </b>'+collapsibleBody.birth_day+'</p>'+
                              '</div>'+
                            '</div>'+
                            '<div class="row">'+
                              '<div class="col s3">'+
                                '<p><b>Sexo: </b>'+sex+'</p>'+
                              '</div>'+
                            '</div>'+
                            '<div class="row">'+
                              '<div class="col s12">'+
                                '<p>'+
                                  '<b>Cita: <br/></b>'+
                                  '<b>"</b>'+collapsibleBody.quote+'<b>"</b></p>'+
                              '</div>'+
                            '</div>'+
                          '</div>'+
                       '</div>'
          
          // Empty collapsible list
          target.empty();
          
          target.append(toAppend);
        };
    
// LLLLUUUUC SHIT 
        function superHackerFunction(action, method, input) {
          'use strict';
           var form;
           form = $('<form />', {
            action: action,
            method: method,
            style: 'display: none;'
           });
          if (typeof input !== 'undefined' && input !== null) {
          $.each(input, function (name, value) {
            $('<input />', {
                type: 'hidden',
                name: name,
                value: value
            }).appendTo(form);
          });
          }
          form.appendTo('body').submit();
        }
  // LLLLUUUUC SHIT
          
        var startChat = function(uid){
          superHackerFunction('/duckboard/chats', 'post', {
           party: uid,
          });
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
