#include <stdio.h>
void main(){
	int cont;
	char c, c_ant;
	printf("Introduce una frase acabada en punto.\n");
	scanf("%c", &c);
	cont=0;
	c_ant=c;
	while(c!='.'){
		if(c=='a' && c_ant=='c'){
			cont++;		
		}
		c_ant=c;
		scanf("%c", &c);	
	}
	printf("La subcadena CA esta %d vegades\n", cont);
}
