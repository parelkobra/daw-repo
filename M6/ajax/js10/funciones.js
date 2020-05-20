var num;

$(document).ready(function() {
  $('#enviar').click(function(event) {
    event.preventDefault();

    num = parseFloat($('#nro').val());

    if (!isNaN(num)) {
      $.ajax({
        async: true,
        type: 'POST',
        dataType: 'html',
        contentType: 'application/x-www-form-urlencoded',
        url: 'pagina1.php',
        data: 'numero=' + num,
        beforeSend: inicioEnvio,
        success: llegadaDatos,
        timeout: 4000,
        error: problemas
      });
    } else {
      alert('error: introduce un valor numérico !!');
      $('#nro').val('');
    }
  });

  function inicioEnvio() {
    $('#resultados').html('<img src="cargando.gif">');
  }

  function llegadaDatos(datos) {
    $('#resultados').html('Resultado:  ' + num + '&sup2 = ' + datos);
    $('#nro').val('');
  }

  function problemas() {
    $('#resultados').text('Problemas en el servidor.');
  }
});
