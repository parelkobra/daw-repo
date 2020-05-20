#include <stdio.h>
void main(){
	char c, c_ant;
	printf("Introduce una frase acabada en punto.\n");
	scanf("%c", &c);
	c_ant=c;
	while(c!='.'&& (c!='a' && c_ant!='c')){
		c_ant=c;		
		scanf("%c", &c);		
	}
	if(c=='.'){
		printf("La subcadena CA no esta en la frase\n");
	}
	else{
		printf("La subcadena CA si que esta en la frase\n");	
	}
}
