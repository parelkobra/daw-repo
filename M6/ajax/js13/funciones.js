$(document).ready(function() {
  $.get('pagina1.php', { pos: 0 }, function respuesta(datos) {
    $('#resultados').html(datos);
    $('a').click(function(event) {
      event.preventDefault();
      var a = $(this).attr('href');
      $.get(a, respuesta);
    });
  });
});
