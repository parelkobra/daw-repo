var peticion_http;

function inicio(){
    peticion();
}

function peticion(){
    peticion_http = new XMLHttpRequest();
    peticion_http.onreadystatechange = respuesta;
    peticion_http.open("GET", "lista.xml", true);
    peticion_http.send(null);
}

function respuesta(){
    var res = "";
    if (peticion_http.readyState === 4) {
        if (peticion_http.status === 200) {
            var documento_xml = peticion_http.responseXML;
            var libros = documento_xml.getElementsByTagName("Libro");
            for (i=0; i<libros.length; i++) {
                var autor = libros[i].firstElementChild.innerHTML;
                var titulo = libros[i].lastElementChild.innerHTML;
                res += "<strong>Libro " + (i+1) + ": Autor:</strong> " + autor + "<strong> Titulo: </strong>" + titulo + "</br>";  
            }
            document.getElementById("contenido").innerHTML=res;
        }
        else {
            alert("Error!!");
        }
    }
    else {
        document.getElementById("contenido").innerHTML="cargando";
    }
}

window.onload=inicio;
