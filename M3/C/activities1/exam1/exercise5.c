/*Realiza dos algoritmos. 
	1.Que nos diga cuantos pares hay en la serie
	2.Que nos diga si almenos hay un par en la serie

(comenta/descomenta antes de probarlo)*/

#include <stdio.h>
/*
void main(){
	int n, cont;
	printf("Introduce una serie numerica acabada en 0\n");
	cont=0;
	scanf("%d", &n);
	while(n!=0){
		if(n%2==0){
			cont++;
		}
		scanf("%d", &n);
	}
	printf("Hay %d numeros pares\n", cont);
}
*/
void main(){
	int n;
	printf("Introduce una serie numerica acabada en 0\n");
	scanf("%d", &n);
	while(n!=0 && n%2!=0){
		scanf("%d", &n);
	}
	if(n!=0){
		printf("Como minimo un numero es par\n");
	}
	else{
		printf("No hay ningun numero par\n");
	}		
}
