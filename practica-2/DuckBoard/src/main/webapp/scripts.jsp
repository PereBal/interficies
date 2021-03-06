<!--Import scripts-->
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/helper.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script type="text/javascript" src="js/jquery.nicescroll.min.js"></script>
<script type="text/javascript">
  var niceScrollConf = {
    cursoropacitymax: 0.4, // change opacity when cursor is active (scrollabar "visible" state), range from 1 to 0
    cursorwidth: "10px"
  }

  $(document).ready(function () {

    $('html').niceScroll(niceScrollConf);

    // Activate Dropdown menu
    $('.dropdown-button').dropdown();
    $('.dropdown-enterprise').dropdown();
    // Activate button-collapse for mobile
    $(".button-collapse").sideNav();

    $(".button-collapse").sideNav();
    var $calendar = $('.datepicker').pickadate({
      selectMonths: true, // Creates a dropdown to control month
      selectYears: 160, // Creates a dropdown of 80 years to control year
      format: 'yyyy-mm-dd',
      clear: false
    });


    $('select').material_select();

    var flashes = <% out.println(servlets.tools.Helper.getFlash(request).toString());%>;
    showFlashes(flashes);

  });
</script>