<?php
$preg=$_GET['preg'];

$host = "localhost";
$user = "root";
$pw = "";
$db = "encuesta";

$conexion = new mysqli($host, $user, $pw, $db);

#Comprobar la conexión
if ($conexion->connect_error) {
    printf("Conexión fallida: %s", $conexion->connect_error);
    exit();
}

#Actualizar la tabla encuesta
if ($preg==1){
    $conexion->query("update encuesta set pregunta1=pregunta1+1 where codigo=1")|| die("Error al actualizar datos");
}elseif($preg==2){
    $conexion->query("update encuesta set pregunta2=pregunta2+1 where codigo=1")|| die("Error al actualizar datos");
}elseif($preg==3){
    $conexion->query("update encuesta set pregunta3=pregunta3+1 where codigo=1")|| die("Error al actualizar datos");
}else{
 die("seleccion encuesta incorrecta!!");    
}

#Retornar votos
$consulta = $conexion->query("SELECT * FROM encuesta WHERE codigo=1");
$reg = $consulta->fetch_object();


echo   "<canvas id='myChart' width='400' height='400' class='chart'></canvas>
        <script>
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['PHP', 'ASP', 'JSP'],
                    datasets: [{
                        label: 'Resultados de la encuesta',
                        data: [$reg->pregunta1,$reg->pregunta2,$reg->pregunta3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{

                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Número de votos'
                            },

                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    }
                }
            });
        </script>"
?>
