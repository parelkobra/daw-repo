#include <stdio.h>
void leer(int v[]);
void main(){
	int v[10],i,n,res;
	printf("Introdueix 10 numeros enters possitius\n");
	leer(v);
	n=-1;
	while(n!=0){
		printf("MENU\n");
		printf("0. Sortir\n");	
		printf("1. ¿Quants d’aquests nombres son parells?\n");
		printf("2. ¿Quin és el valor del màxim?\n");
		printf("3. ¿Quin és el valor del mínim?\n");
		scanf("%d", &n);
		switch(n){
			case 0:	printf("Adeu\n");
				break;
			case 1:	res = pares(v);
				printf("%d numeros son parells\n",res);
				break;
			case 2:	res = max(v);
				printf("El valor màxim és %d\n", res);
				break;
			case 3:	res = min(v);
				printf("El valor mínim és %d\n", res);
				break;
		}
	}	
}	
void leer(int v[]){
	int i;
	for(i=0;i<10;i++){
		scanf("%d", &v[i]);
	}
}
int pares(int v[]){
	int cont, i;
	cont=0;
	for(i=0;i<10;i++){
		if(v[i]%2==0){
			cont++;
		}
	}
	return cont;
}
int max(int v[]){
	int max, i;
	max=v[0];
	for(i=1;i<10;i++){
		if(v[i]>max){
			max=v[i];
		}		
	}
	return max;
}
int min(int v[]){
	int min, i;
	min=v[0];
	for(i=1;i<10;i++){
		if(v[i]<min){
			min=v[i];
		}		
	}
	return min;
}

