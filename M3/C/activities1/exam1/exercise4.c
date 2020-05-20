/*Dada una serie de numeros acabada en 0 realiza un algoritmo que nos diga cual 
es el mayor de la serie.*/

#include <stdio.h>
void main(){
	int n, max;
	printf("Introduce una serie numerica acabada en 0\n");	
	scanf("%d", &n);
	max=0;
	while(n!=0){
		if(n>max){
			max=n;
		}
	scanf("%d", &n);
	}
	printf("El numero mes gran es %d\n", max);
}
