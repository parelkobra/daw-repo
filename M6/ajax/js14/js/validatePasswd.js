/*

Los criterios que tomaremos para validar que la contrase�a sea fuerte son los siguientes:

    Debe tener 8 car�cteres como m�nimo.
    Debe contener letras may�sculas.
    Debe contener letras min�sculas.
    Debe contener n�meros.
    Debe contener caracteres especiales (todos los caracteres del teclado que no se definen como letras o n�meros).



Llamar a la siguiente funci�n pas�ndole la contrase�a como par�metro:	 validar_clave(contrase�a).

La funci�n retorna "true" si la contrase�a es fuerte y "false" si es d�bil.


*/

function validar_clave(contrasenna){
    if(contrasenna.length >= 8){		
        var mayuscula = false;
        var minuscula = false;
        var numero = false;
        var caracter_raro = false;

        for(var i = 0;i<contrasenna.length;i++){
            if(contrasenna.charCodeAt(i) >= 65 && contrasenna.charCodeAt(i) <= 90){
                mayuscula = true;
            }
            else if(contrasenna.charCodeAt(i) >= 97 && contrasenna.charCodeAt(i) <= 122){
                minuscula = true;
            }
            else if(contrasenna.charCodeAt(i) >= 48 && contrasenna.charCodeAt(i) <= 57){
                numero = true;
            }
            else{
                caracter_raro = true;
            }
        }
        
        if(mayuscula == true && minuscula == true && caracter_raro == true && numero == true){
            return true;
        }
    }
    
    return false;
}
