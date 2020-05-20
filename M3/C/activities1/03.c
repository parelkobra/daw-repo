
/*LUNES 27 EXAMEN M3*/

/*

	scanf("%[^\n]s", cadena);
	
	leng = strlen(cadena);

	borrar_espacios(char cadena, char c)  ##char c para el apartado b)
				
		while(cadena[i]!='\0')

*/

#include <stdio.h>
#include <string.h>
#define cmax 155
void main(){
	char cadena[cmax];
	int n=-1, leng;
	while(n!=0){
		printf("-- Ejercicio 1a --\n");
		printf("-- Elimina los espacios en blanco de una cadena --\n");
		printf("0. Salir\n");
		printf("1. Introduce la cadena\n");
		printf("2. Imprimir la longitud de la cadena\n");
		printf("3. Elimina los espacios\n");

		printf("---------\n");
		scanf("%d", &n);
		getchar();
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf(">> ");
				scanf("%[^\n]s", cadena);
				printf("\n");
				break;
			case 2:leng = strlen(cadena);
				printf("%d caracteres (espacios incluidos)\n", leng);
				printf("\n");
				break;
			case 3:borrar_espacios(cadena);
				printf("%s\n", cadena);
				break;
		}		
	}
}
int borrar_espacios(char cadena[]){
	int i=0, j=0;					/*Violació de segment*/
	while(cadena[i]!='\0'){
		if(cadena[i]==' '){
			j=i;
			cadena[j]=cadena[j+1];
			while(cadena[j+1]!=' '){
				cadena[j]=cadena[j+1];
				j++;			
			}
			i=j;					
		}
		i++;	
	}
	cadena[i]='\0';	
}






