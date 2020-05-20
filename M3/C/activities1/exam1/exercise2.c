/*Compara la cantidad de A y B en una frase acabada en punto*/

#include <stdio.h>
void main(){
	char c;
	int cont_a, cont_b;	
	printf("Introduce una frase acabada en punto\n");
	cont_a=0;
	cont_b=0;
	scanf("%c", &c);
	while(c!='.'){
		if(c=='a'){
			cont_a++;
		}
		if(c=='b'){
			cont_b++;		
		}
		scanf("%c", &c);
	}
	if(cont_a > cont_b){
		printf("Hi han més A que B\n");
	}
	if(cont_b > cont_a){
		printf("Hi han més B que A\n");
	}
	if(cont_b == cont_a){
		printf("Hi ha el mateix numero de A que de B\n");
	}
}
