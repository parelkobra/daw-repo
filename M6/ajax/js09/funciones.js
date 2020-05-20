$(document).ready(function() {
  $('#boton1').click(function() {
    var v = $('#dni').val();

    if (v === '1' || v === '2' || v === '3') {
      $.getJSON('pagina1.php', { dni: v }, respuesta);
    } else {
      alert('error!! valores permitidos: 1, 2 o 3.');
      $('#dni').val('');
      $('#resultados').html('');
    }
  });

  function respuesta(datos) {
    $('#resultados').html(
      '<strong>Nombre: </strong>' +
        datos.nombre +
        '<br>' +
        '<strong>Apellido: </strong>' +
        datos.apellido +
        '<br>' +
        '<strong>Dirección: </strong>' +
        datos.direccion
    );
  }
});
