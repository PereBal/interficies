<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="section no-pad-bot" id="index-banner"></div>

      <div id="g1" class="container" style="display:none">
        <div class="card">
          <div  class="card-content">
            <h4 class="card-title">Cantidad de tuits por tendencia</h4>
            <div class="row">

              <div class="col s12">
                <div class="input-field col s5">
                  <select id="gr1A">
                    <option value="2015">2015</option>
                    <option value="2014">2014</option>
                    <option value="2013">2013</option>
                    <option value="2012">2012</option>
                    <option value="2011">2011</option>
                    <option value="2010">2010</option>
                  </select>
                  <label>Año</label>
                </div>
                <div class="input-field col s5">
                  <select id="gr1M">
                    <option value="ALL">Media anual</option>
                    <option value="enero">Enero</option>
                    <option value="febrero">Febrero</option>
                    <option value="marzo">Marzo</option>
                    <option value="abril">Abril</option>
                    <option value="mayo">Mayo</option>
                    <option value="junio">Junio</option>
                    <option value="julio">Julio</option>
                    <option value="agosto">Agosto</option>
                    <option value="septiembre">Septiembre</option>
                    <option value="octubre">Octubre</option>
                    <option value="noviembre">Noviembre</option>
                    <option value="diciembre">Diciembre</option>
                  </select>
                  <label>Mes</label>
                </div>
                <div class="input-field col s2">
                  <a onClick="graphColumn('#graph1', '', [], 'tuits', '',$('#gr1A').val(), $('#gr1M').val());" class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div style="min-height:400px !important" id="graph1" class="col s12">

              </div>
            </div>

            <div class="card-action">
              <a onClick="cerrar('#g1')" href="#">Cerrar</a>
            </div>
          </div>
        </div>  
      </div>

      <div id="g2" class="container" style="display:none">
        <div class="card">
          <div  class="card-content">
            <h4 class="card-title">Porcentaje de uso de idiomas</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select id="gr2A">
                    <option value="2015">2015</option>
                    <option value="2014">2014</option>
                    <option value="2013">2013</option>
                    <option value="2012">2012</option>
                    <option value="2011">2011</option>
                    <option value="2010">2010</option>
                  </select>
                  <label>Año</label>
                </div>
                <div class="input-field col s5">
                  <select id="gr2M">
                    <option value="ALL">Media anual</option>
                    <option value="enero">Enero</option>
                    <option value="febrero">Febrero</option>
                    <option value="marzo">Marzo</option>
                    <option value="abril">Abril</option>
                    <option value="mayo">Mayo</option>
                    <option value="junio">Junio</option>
                    <option value="julio">Julio</option>
                    <option value="agosto">Agosto</option>
                    <option value="septiembre">Septiembre</option>
                    <option value="octubre">Octubre</option>
                    <option value="noviembre">Noviembre</option>
                    <option value="diciembre">Diciembre</option>
                  </select>
                  <label>Mes</label>
                </div>
                <div class="input-field col s2">
                  <a onClick="graphSemiCircle('#graph2', '', $('#gr2A').val(), $('#gr2M').val());" class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div style="min-height:400px !important" id="graph2" class="col s12">

              </div>
            </div>

            <div class="card-action">
              <a onClick="cerrar('#g2')" href="#">Cerrar</a>
            </div>
          </div>
        </div>  
      </div>

      <div id="g4" class="container" style="display:none">
        <div class="card">
          <div  class="card-content">
            <h4 class="card-title">Cantidad de tuits por isla y estación del año</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select id="gr4A">
                    <option value="2015">2015</option>
                    <option value="2014">2014</option>
                    <option value="2013">2013</option>
                    <option value="2012">2012</option>
                    <option value="2011">2011</option>
                    <option value="2010">2010</option>
                  </select>
                  <label>Año</label>
                </div>
                <div class="input-field col s2">
                  <a onClick="graphPolar('#graph4', '', ['Fuera de Baleares','Ibiza','Formentera','Unknown','Mallorca','Menorca'], 'tuits', '', $('#gr4A').val());" class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div style="min-height:400px !important" id="graph4" class="col s12">

              </div>
       
            </div>

            <div class="card-action">
              <a onClick="cerrar('#g4')" href="#">Cerrar</a>
            </div>
          </div>
        </div>
      </div>

      <div id="g3" class="container" style="display:none">
        <div class="card">
          <div  class="card-content">
            <h4 class="card-title">Polaridad por día de la semana</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select id="gr3A">
                    <option value="2015">2015</option>
                    <option value="2014">2014</option>
                    <option value="2013">2013</option>
                    <option value="2012">2012</option>
                    <option value="2011">2011</option>
                    <option value="2010">2010</option>
                  </select>
                  <label>Año</label>
                </div>
                <div class="input-field col s5">
                  <select id="gr3M">
                    <option value="ALL">Media anual</option>
                    <option value="enero">Enero</option>
                    <option value="febrero">Febrero</option>
                    <option value="marzo">Marzo</option>
                    <option value="abril">Abril</option>
                    <option value="mayo">Mayo</option>
                    <option value="junio">Junio</option>
                    <option value="julio">Julio</option>
                    <option value="agosto">Agosto</option>
                    <option value="septiembre">Septiembre</option>
                    <option value="octubre">Octubre</option>
                    <option value="noviembre">Noviembre</option>
                    <option value="diciembre">Diciembre</option>
                  </select>
                  <label>Mes</label>
                </div>
                <div class="input-field col s2">
                  <a onClick="graphLine('#graph3', '', ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'], 'tuits', '',$('#gr3A').val(), $('#gr3M').val());" class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div style="min-height:400px !important" id="graph3" class="col s12 ">

              </div>
            </div>

            <div class="card-action">
              <a onClick="cerrar('#g3')" href="#">Cerrar</a>
            </div>
          </div>
        </div>
      </div>

      <div id="g5" class="container" style="display:none">
        <div class="card">
          <div  class="card-content">
            <h4 class="card-title">Las islas son tendencia!</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select id="gr5A">
                    <option value="2015">2015</option>
                    <option value="2014">2014</option>
                    <option value="2013">2013</option>
                    <option value="2012">2012</option>
                    <option value="2011">2011</option>
                    <option value="2010">2010</option>
                  </select>
                  <label>Año</label>
                </div>
                <div class="input-field col s5">
                  <select id="gr5M">
                    <option value="ALL">Media anual</option>
                    <option value="enero">Enero</option>
                    <option value="febrero">Febrero</option>
                    <option value="marzo">Marzo</option>
                    <option value="abril">Abril</option>
                    <option value="mayo">Mayo</option>
                    <option value="junio">Junio</option>
                    <option value="julio">Julio</option>
                    <option value="agosto">Agosto</option>
                    <option value="septiembre">Septiembre</option>
                    <option value="octubre">Octubre</option>
                    <option value="noviembre">Noviembre</option>
                    <option value="diciembre">Diciembre</option>
                  </select>
                  <label>Mes</label>
                </div>
                <div class="input-field col s2">
                  <a onClick="graphPieLegend('#graph5', '', $('#gr5A').val(), $('#gr5M').val());" class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div style="min-height:400px !important" id="graph5" class="col s12">

              </div>
            </div>

            <div class="card-action">
              <a onClick="cerrar('#g5')" href="#">Cerrar</a>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="container">
      <div class="section">

        <div class="row">

          <div class="col s3"  id="cardsRow">
            <div class="row">
              <div class="col s12">
                <div class="center card">
                  <div class="card-image">
                    <img class="img-graph" src="img/gr01.png">
                    <span style="padding-bottom: 0px;" class="card-title orange-text text-darken-2">Tendencias</span>
                  </div>
                  <div class="card-action">
                    <a onClick="ver('#g1')"  class="blue-text text-darken-1">Ver gráfica</a>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col s12">
                <div class="card center ">
                  <div class="card-image">
                    <img class="img-graph" src="img/gr02.png">
                    <span style="padding-bottom: 0px;" class="card-title orange-text text-darken-2">Idiomas</span>
                  </div>
                  <div class="card-action">
                    <a onClick="ver('#g2')" class="blue-text text-darken-1" href="#">Ver gráfica</a>
                  </div>
                </div>
              </div>
            </div>

          </div>


          <div class="col s6">
            <div class="card center ">
              <div class="card-image">         
                <img class="img-graph" src="img/gr04.png"></img>
                <span style="padding-bottom: 0px;" class="card-title orange-text text-darken-2">Distribución geográfica</span>
                
              </div>
              <div class="card-action" id="centerCardAction">
                <a onClick="ver('#g4')" class="blue-text text-darken-1" href="#">Ver gráfica</a>
              </div>
            </div>
            <!--
            <div class="card center col s12">
              <div class="col s5 offset-s1"><a>VER TODO</a></div>
              <div class="col s5 offset-s1"><a class="orange-text text-darken-1 ">CERRAR TODO</a></div>
            </div>-->
          </div>


          <div class="col s3">

            <div class="row">
              <div class="col s12">
                <div class="card center ">
                  <div class="card-image">
                    <img class="img-graph" src="img/gr03.png">
                    <span style="padding-bottom: 0px;" class="card-title orange-text text-darken-2">Polaridad</span>
                  </div>
                  <div class="card-action">
                    <a onClick="ver('#g3');" class="blue-text text-darken-1" href="#">Ver gráfica</a>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col s12">
                <div class="card center ">
                  <div class="card-image">
                    <img class="img-graph" src="img/gr05.png">
                    <span style="padding-bottom: 0px;" class="card-title orange-text text-darken-2">Actividad balear</span>
                  </div>
                  <div class="card-action">
                    <a onClick="ver('#g5')" class="blue-text text-darken-1" class="blue-text text-darken-1" href="#">Ver gráfica</a>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>

      </div>
    </div>
  </main>
  <jsp:include page="footer.jsp"/>
  <jsp:include page="scripts.jsp"/>
  <!--Scripts-->
  <script type="text/javascript">

    var centerRow = $('#cardsRow');
    var centerCardBody = $('#centerCardBody');
    var centerCardAction = $('#centerCardAction');

    $(document).ready(function () {
 
      setHeight();
    });

    $(window).resize(function () {

      setHeight();
    });
    
    var setHeight = function () {

      var rowHeight = centerRow.height() - centerCardAction.height();
      centerCardBody.css({height: rowHeight});
    }
   

    function ver(id) {
      $(id).toggle(400, function () {
        // animation complete
        switch(id){
        case '#g1':
          graphColumn('#graph1', '', [], 'tuits', '', 2015, 'ALL');
          break;
        case '#g2':
          graphSemiCircle('#graph2', '', 2015, 'ALL');
          break;
        case '#g3':
          graphLine('#graph3', '', ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'], 'tuits', '', 2015, 'ALL');
          break;
        case '#g4':
           graphPolar('#graph4', '', ['Fuera de Baleares','Ibiza','Formentera','Unknown','Mallorca','Menorca'], 'tuits', '', 2015);
          break;
        case '#g5':
          graphPieLegend('#graph5', '', 2015, 'ALL');
          break;       
      }  
      });
            
    }

    function cerrar(id) {
      $(id).hide(400, function () {
        // animation complete
      });
    }



  </script>
</body>
</html>
