$(document).ready(function() {
  $('#enviar').click(function enviarSeleccion() {
    if (!$('input[type="radio"]:checked')) {
      alert('Radio button not checked');
    } else {
      if ($('#radio1:checked')) {
        $.post('pagina1.php', { preg: 1 }, respuesta);
        llegadaDatos();
      } else if ($('#radio2:checked')) {
        $.post('pagina1.php', { preg: 2 }, respuesta);
        llegadaDatos();
      } else if ($('#radio3:checked')) {
        $.post('pagina1.php', { preg: 3 }, respuesta);
        llegadaDatos();
      } else {
        alert('error condicionales');
      }
    }
  });

  function llegadaDatos(datos) {
    $('#resultados').html(datos);
  }
});
