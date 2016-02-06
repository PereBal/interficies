<!--Import scripts-->
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/helper.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script type="text/javascript">
  $(document).ready(function () {
    // Activate Dropdown menu
    $(".dropdown-button").dropdown();
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
  });
</script>