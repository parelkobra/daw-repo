/*Dada una frase acabada en punto cuenta el numero de caracteres en una frase
sin contar los espacios en blanco*/

#include <stdio.h>
void main(){
	int cont;
	char c;
	printf("Introduce una frase acabada en punto.\n");
	cont=0;
	scanf("%c", &c);	
	while(c!='.'){
		if(c==' '){
			scanf("%c", &c);			
		}
		else{
			cont++;
			scanf("%c", &c);
		}
	}
	printf("La frase esta formada por %d caracteres\n", cont);
}
