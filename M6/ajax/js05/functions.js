addEvent(window,'load',inicializarEventos,false);

function inicializarEventos()
{
  var ob=document.getElementById('boton1');
  addEvent(ob,'click',presionBoton,false);
}

function presionBoton(e)
{
  var ob1=document.getElementById('voto');
  var ob2=document.getElementById('nombre');
  cargarVoto(ob1.value,ob2.value);
}

var conexion1;
function cargarVoto(voto,nom) 
{
  conexion1=crearXMLHttpRequest();
  conexion1.onreadystatechange = procesarEventos;
  conexion1.open('GET','192.168.3.10/ajax2.php?puntaje='+voto+'&nombre='+nom, true);
  conexion1.send(null);
}

function procesarEventos()
{
  var resultados = document.getElementById("resultados");
  if(conexion1.readyState == 4)
  {
    resultados.innerHTML = conexion1.responseText;
  } 
  else 
  {
    resultados.innerHTML = 'Cargando...';
  }
}

//***************************************
//Funciones comunes a todos los problemas
//***************************************
function addEvent(elemento,nomevento,funcion,captura)
{
  if (elemento.attachEvent)
  {
    elemento.attachEvent('on'+nomevento,funcion);
    return true;
  }
  else  
    if (elemento.addEventListener)
    {
      elemento.addEventListener(nomevento,funcion,captura);
      return true;
    }
    else
      return false;
}

function crearXMLHttpRequest() 
{
  var xmlHttp=null;
  if (window.ActiveXObject) 
    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
  else 
    if (window.XMLHttpRequest) 
      xmlHttp = new XMLHttpRequest();
  return xmlHttp;
}
