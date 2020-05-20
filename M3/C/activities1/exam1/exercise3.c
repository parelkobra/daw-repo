/*Dada una serie de numeros acabada en 0. Realiza un algoritmo que calcule la 
media arimetica de la serie.*/

#include <stdio.h>
void main(){
	int n, cant, suma, res;
	printf("Introduce una serie numerica acabada en 0.\n");
	suma=0;
	cant=0;
	scanf("%d", &n);	
	while(n!=0){
		suma=suma+n;
		cant++;
		scanf("%d", &n);
	}
	res=suma/cant;
	printf("La media arimetica es %d\n", res);
}
