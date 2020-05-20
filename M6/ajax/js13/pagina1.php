<?php
 header('Content-Type: text/html; charset=utf-8');
 
if (isset($_REQUEST['pos'])){
  $inicio=$_REQUEST['pos'];
}else{
  $inicio=0;
}

$host = "localhost";
$user = "cfgs";
$pw = "ira491";
$db = "cfgs";

$conexion = new mysqli($host, $user, $pw, $db);

#Comprobar la conexión
if ($conexion->connect_error) {
    printf("Conexión fallida: %s", $conexion->connect_error);
    exit();
}

#Consulta
$consulta = $conexion->query("SELECT * FROM comentarios limit $inicio,2");

$impresos=0;

 while ($reg = $consulta->fetch_object()) {
    $impresos++;
    echo "<article>".utf8_encode ($reg->nombre)."&nbsp;&nbsp;<span>".$reg->fecha."</span><br>";
    echo utf8_encode ($reg->comentario)."</article><br>";
    echo "<br>";
  }
  
  echo "<span class='pag-ant'>";
  if ($inicio==0){
    echo "<span>Anteriores</span> ";
  }else {
    $anterior=$inicio-2;
    echo "<a href=\"pagina1.php?pos=$anterior\" id=\"ant\">Anteriores 
    </a>";
  }
  echo "</span>";
  
  echo "<span class='pag-sig'>";
  if ($impresos==2) {
    $proximo=$inicio+2;
    echo "<a href=\"pagina1.php?pos=$proximo\" id=\"sig\">Siguientes</a>";
  } else{
    echo "<span>Siguientes</span>";
  }
  echo "</span>";
?>
