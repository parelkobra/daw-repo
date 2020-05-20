<html>
<head>
    <title>Problema</title>
    <link rel="StyleSheet" href="estilos.css" type="text/css">
</head>
<body>
    <h1>Signos del horóscopo.</h1>
    <div id="menu">
        <p><a id="enlace1" href="sinajax1.php?cod=1">Aries</a></p>
        <p><a id="enlace2" href="sinajax1.php?cod=2">Tauro</a></p>
        <p><a id="enlace3" href="sinajax1.php?cod=3">Geminis</a></p>
        <p><a id="enlace4" href="sinajax1.php?cod=4">Cancer</a></p>
        <p><a id="enlace5" href="sinajax1.php?cod=5">Leo</a></p>
        <p><a id="enlace6" href="sinajax1.php?cod=6">Virgo</a></p>
        <p><a id="enlace7" href="sinajax1.php?cod=7">Libra</a></p>
        <p><a id="enlace8" href="sinajax1.php?cod=8">Escorpio</a></p>
        <p><a id="enlace9" href="sinajax1.php?cod=9">Sagitario</a></p>
        <p><a id="enlace10" href="sinajax1.php?cod=10">Capricornio</a></p>
        <p><a id="enlace11" href="sinajax1.php?cod=11">Acuario</a></p>
        <p><a id="enlace12" href="sinajax1.php?cod=12">Piscis</a></p>
    </div>
    <div id="detalles">
    <?php
    if (!isset($_REQUEST['cod'])){
      echo "Seleccione su signo.";
    }else{
    if ($_REQUEST['cod']==1)
      echo "<strong>Aries:</strong> Hoy los cambios serán físicos, 
      personales, de carácter, Te sentirás impulsivo y tomarás 
      iniciativas. Período en donde considerarás unirte a agrupaciones 
      de beneficencia, o de ayuda a los demás.";
    if ($_REQUEST['cod']==2)
      echo "<strong>Tauro:</strong> Hoy los cambios serán privados, 
      íntimos. Recuerdos. Ayuda, solidaridad. Asuntos en lugares de retiro. Tu 
      cónyuge puede aportar buen status a tu vida o apoyo a tu profesión.";
    if ($_REQUEST['cod']==3)
      echo "<strong>Géminis:</strong> Los asuntos de hoy tienen 
      que ver con las amistades, reuniones, actividades con ellos. Día esperanzado, 
      ilusiones. Mucha energía sexual y fuerza emocional. Deseos difíciles 
      de controlar.";
    if ($_REQUEST['cod']==4)
      echo "<strong>Cancer:</strong> Este día la profesión 
      y las relaciones con superiores y con tu madre serán de importancia. Actividad 
      en relación a estos temas. Momentos positivos con compañeros de 
      trabajo. Actividad laboral agradable.";
    if ($_REQUEST['cod']==5)
      echo "<strong>Leo:</strong> Este día los estudios, los 
      viajes, el extranjero y la espiritualidad serán lo importante. Pensamientos, 
      religión y filosofía también. Vivencias kármicas de 
      la época te vuelven responsable tomando decisiones.";
    if ($_REQUEST['cod']==6)
      echo "<strong>Virgo:</strong> Para este día toma importancia 
      tu vida sexual, tal vez miedos, temas legales, juicios o herencias. Experiencias 
      extrañas. Hay karma de prueba durante este período en tu parte psicológica, 
      generándose algunos replanteos.";
    if ($_REQUEST['cod']==7)
      echo "<strong>Libra:</strong> Hoy todo asunto tiene que ver con 
      tu pareja, también con socios, con la gente o el público. Ellos 
      serán lo más importante del día. Ganancias a través 
      de especulaciones o del juego. Actividades vocacionales artísticas.";
    if ($_REQUEST['cod']==8)
      echo "<strong>Escorpio:</strong> Hoy todo asunto tiene que ver 
      con temas de trabajo y de salud. Presta atención a ambos. Experiencias 
      diversas con compañeros. Durante este período tendrás muchos 
      recursos para ganar dinero.";
    if ($_REQUEST['cod']==9)
      echo "<strong>Sagitario:</strong> Durante este día se 
      vivirán cambios en relación a los noviazgos o a los hijos. Creatividad, 
      actividad, diversiones y salidas. Período de encuentros con personas o 
      situaciones que te impresionan.";
    if ($_REQUEST['cod']==10)
      echo "<strong>Capricornio:</strong> Los cambios del día 
      tienen que ver con tu hogar, con la convivencia y con el padre. Asuntos relativos 
      al carácter en la convivencia. El karma de responsabilidad de estos momentos 
      te acercará al mundo de lo desconocido, mucha madurez y contacto con el 
      más allá.";
    if ($_REQUEST['cod']==11)
      echo "<strong>Acuario:</strong> Hoy todo asunto tiene que ver 
      con el entorno inmediato, hermanos y vecinos, con la comunicación, los 
      viajes cortos o traslados frecuentes. El hablar y trasladarse será importante 
      hoy. Mentalidad e ideas activas.";
    if ($_REQUEST['cod']==12)
      echo "<strong>Piscis:</strong> Durante este día se vivirán 
      cambios en la economía, movimientos en los ingresos, negocios, valores. 
      Momentos de gran fuerza y decisión profesionales, buscarás el liderazgo.";
    }
    ?>
    </div>
</body>
</html>
