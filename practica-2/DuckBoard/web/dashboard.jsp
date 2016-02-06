<!DOCTYPE html>
<html>
  <jsp:include page="head.jsp"/>
  <body>
    <jsp:include page="navbar.jsp"/>
    <main>
      <div class="section no-pad-bot" id="index-banner"></div>

      <div id="g1" class="container" style="display:none">
        <div class="card">
          <div class="card-content">
            <h4 class="card-title">Cantidad de tuits por tendencia</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select>
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
                  <select>
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
                  <a class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div id="graph1" class="col s12">

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
          <div class="card-content">
            <h4 class="card-title">Porcentaje de uso de idiomas</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select>
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
                  <select>
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
                  <a class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div id="graph2" class="col s12">

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
          <div class="card-content">
            <h4 class="card-title">Cantidad de tuits por isla y estación del año</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select>
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
                  <a class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div id="graph4" class="col s12">

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
          <div class="card-content">
            <h4 class="card-title">Polaridad por día de la semana</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select id="g3A">
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
                  <select id="g3M">
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
                  <a class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div id="graph3" class="col s12 ">

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
          <div class="card-content">
            <h4 class="card-title">Las islas son tendencia!</h4>
            <div class="row">
              <div class="col s12">
                <div class="input-field col s5">
                  <select>
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
                  <select>
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
                  <a class="blue darken-1 waves-effect waves-light btn"><i class="fa fa-refresh"></i></a>
                </div>
              </div>
              <div id="graph5" class="col s12">

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
        <!-- Information cards -->
        <!-- for card in cards -->
        <div class="row">
          <div class="col s3">
            <div class="col s12">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/10">
                  <span class="card-title">Tendencias</span>
                </div>
                <div class="card-action">
                  <a onClick="ver('#g1')">Ver gráfica</a>
                </div>
              </div>
            </div>

            <div class="col s12">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/9">
                  <span class="card-title">Idiomas</span>
                </div>
                <div class="card-action">
                  <a onClick="ver('#g2')" href="#">Ver gráfica</a>
                </div>
              </div>
            </div>
          </div> 
          <div class="col s6" style="margin-top:1.5em">
            <div class="card">
              <div class="card-image blue-grey lighten-1">
                <i class="white-text fa fa-area-chart fa-5x"></i>
                <span class="orange-text text-darken-1 card-title">Distribución geográfica</span>
              </div>
              <div class="card-action">
                <a onClick="ver('#g4')" href="#">Ver gráfica</a>
              </div>
            </div>
          </div>
          <div class="col s3">
            <div class="col s12">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/10">
                  <span class="card-title">Polaridad</span>
                </div>
                <div class="card-action">
                  <a onClick="ver('#g3')" href="#">Ver gráfica</a>
                </div>
              </div>
            </div>

            <div class="col s12">
              <div class="card">
                <div class="card-image">
                  <img src="http://lorempixel.com/400/250/people/9">
                  <span class="card-title">Actividad en Baleares</span>
                </div>
                <div class="card-action">
                  <a onClick="ver('#g5')" href="#">Ver gráfica</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- end for -->
      </div>
    </div>
  </main>
  <jsp:include page="footer.jsp"/>
  <jsp:include page="scripts.jsp"/>
  <!--Scripts-->
  <script type="text/javascript">
    
  $(document).ready(function() {    
    graphColumn('#graph1', '', [], 'tuits', '', 2015, 'ALL');
    graphSemiCircle('#graph2', '', 2015,'ALL');
    graphLine('#graph3', '', ['Lunes','Martes','Miercoles','Jueves','Viernes','Sábado','Domingo'], 'tuits', '', 2015, 'ALL');
    graphPolar('#graph4', '', [], '', '', 2015);
    graphPieLegend('#graph5', '', 2015, 'ALL');
    });

    function ver(id) {
      $(id).toggle(400, function () {
        // animation complete
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
