#include <stdio.h>
#define size 255

void main(){
  int leng, vA[size], vB[size], res;

	printf("PROBLEMA 2 APARTADO [A]\n");
	printf("Introdueix la cantitat de numeros que tindra el vector\n");
	scanf("%d", &leng);
	printf("Intorudce el vector:\n");
	intrVect(vA, leng);

	printf("\nPROBLEMA 2 APARTADO [B]\n");
	printf("Introduce un vector de 10 elementos enteros\n");
	intrVect2(vB);
	res = capicua(vB);
	if(res == -1){
		printf("El vector no es capicua.\n");
	}
	else{
		printf("El vector es capicua.\n");	
	}
}

//Funciones A
int intrVect(int vA[], int leng){
	int i;
	for(i=0;i<leng;i++){
		scanf("%d", &vA[i]);
	}
}

//Funciones B
int intrVect2(int vB[]){
	int i;
	for(i=0;i<=9;i++){
		scanf("%d", &vB[i]);
	}
}
int capicua(int vB[]){
	int izq=0, der=9;
	while((izq < der)&&(vB[izq] != vB[der])){
		izq++;
		der--;
	}
	if(vB[izq] != vB[der]){
		return -1;	
	}
	else{
		return 1;	
	}
}
