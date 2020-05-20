#include <stdio.h>
void main(){
	char c, first_let, last_let;
	int cont;
	printf("Introdueix una frase acabada en punt.\n");
	cont=0;
	scanf("%c", &c);
	first_let=c;
	while(c!='.'){
		while(c!=' ' && c!='.'){
			last_let=c;
			scanf("%c", &c);
		}
		if(first_let==last_let){
			cont++;
			scanf("%c", &c);
			first_let=c;		
		}
		else{
			scanf("%c", &c);
			first_let=c;
		}		
	}
	printf("Hi han %d paraules que comencen i acaben amb la mateixa lletra\n", cont);
}
