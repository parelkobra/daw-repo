var x;
x = $(document);
x.ready(inicializarEventos);

function inicializarEventos() {
  var y;
  y = $('#enviar');
  y.click(presionSubmit);
}

function presionSubmit() {
  var v = parseFloat($('#nro').val());
  $.get('pagina1.php', { numero: v }, llegadaDatos);
  return false;
}

function llegadaDatos(datos) {
  $('#resultados').html(datos);
}
