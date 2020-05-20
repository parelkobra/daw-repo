#include <stdio.h>
#define cmax 255

void main(){
	char vect[cmax], firstChar, lastChar;
	int i=0, leng, cont=0;
	
	printf("\nPROBLEMA 3\n");
	printf("Introduce una frase acabada en punto\n");
	printf("\n >> ");	
	scanf("%[^\n]", vect);	
	
	for(i=0; vect[i]!='\0'; i++){
		if(vect[i] == ' '){
			firstChar = vect[i+1];
			while((vect[i] != ' ')&&(vect[i] != '\0')){
				i++;				
			}
			lastChar = vect[i-1];
			if(firstChar == lastChar){
				cont++;			
			}		
		}
	}

	printf("\nLa frase tiene %d palabras que empiezan y acaban por la misma letra.\n", cont);
}
