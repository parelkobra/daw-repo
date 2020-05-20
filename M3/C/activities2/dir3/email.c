#include <stdio.h>
#include <string.h>
#define cmax 155
void main(){
	char cadena[cmax];
	int n=-1, leng, res;
	while(n!=0){
		printf("\n-- Ejercicio 2 --\n");
		printf("-- Validació Correu Electronic --\n");
		printf("0. Salir\n");
		printf("1. Introdueix el email\n");
		printf("2. Validació\n");

		printf("---------\n");
		scanf("%d", &n);
		getchar();
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf(">> ");
				scanf("%s", cadena);
				printf("\n");
				break;
			case 2:leng = strlen(cadena);
				res = validacio(cadena, leng);
				if(res == -1){
					printf("Falta '@'\n");				
				}
				else if(res == -2){
					printf("Ha de tenir com a minim 2 caracters abans de '@'\n");
				}
				else if(res == -3){
					printf("Falta un punt despres de '@'\n");
				}
				else if(res == -4){
					printf("Menys de 2 caràcters després del '.'\n");				
				}
				else{
					printf("OK\n");				
				}
				break;	
		}	
	}
}
int validacio(char cadena[], int leng){
	int i = 0, j = 0;
	while((cadena[i] != '\0')&&(cadena[i] != '@')){
		i++;
	}
	if(cadena[i] == '@'){			
		if(i < 1){
			return -2;			
		}
		else{
			while((cadena[i] != '\0')&&(cadena[i] != '.')){
				i++;			
			}
			if(cadena[i] == '\0'){
				return -3;			
			}
			else{
				j = i;
				while(cadena[i] != '\0'){
					i++;				
				}
				if((i-j)< 2){
					return -4;				
				}
				else{
					return -5;
				}		
			}	
		}			
	}
	else{
		return -1;		
	}		
}








