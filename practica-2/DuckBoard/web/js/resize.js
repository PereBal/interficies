// Script to resize components to screen resolution
// Funtion on load page
$(window).load(resize());
// Funtion on resize screen
$(window).resize(resize());
// Common resize function
function resize() {
  $(".dynamic-height").css({"max-height": $(window).height() - 184});
  $(".dynamic-height").css({"height": $(window).height() - 184});
}