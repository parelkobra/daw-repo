#include <stdio.h>
void main(){
	int n, res;
	printf("Introduce una serie numerica acabada en 0.\n");
	scanf("%d", &n);
	res=0;
	while(n!=0){
		if(n%7==0 && n%2==0){
			if(n%7==0){
				res=res+n;
			}
			else{
				res=res-n;							
			}
			scanf("%d", &n);
		}
		else{
			scanf("%d", &n);		
		}
	}
	printf("El resultat es: %d\n", res);
} /*REVISAR RESULTADO*/
