#include <stdio.h>
#define DIM 3
void main(){
	void leer(float A[DIM][DIM]);
	void mostrar(float A[DIM][DIM]);
	int n, A[DIM][DIM];
	while(n!=0){
		printf("M E N U\n");
		printf("---------\n");
		printf("0. Sortir\n");
		printf("1. Introdueix una matriu.\n");
		printf("2. Mostra la matriu.\n");	
		printf("3. Torna la trasposada de la matriu.\n");

		printf("---------\n");
		scanf("%d", &n);
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf("Introdueix la matriu (3 x 3)\n");
				leer(A);
				break;
			case 2:mostrar(A);				
				break;
		}
	}
}
void leer(float A[DIM][DIM]){
	int i,j;
	for(i=0;i<DIM;i++){
		for(j=0;j<DIM;j++){
			scanf("%f", &A[i][j]);		
		}	
	}
}
void mostrar(float A[DIM][DIM]){
	int i,j;
	for(i=0;i<DIM;i++){
		for(j=0;j<DIM;j++){
			printf("%.2f ", A[i][j]);	
		}
			printf("\n"); 
	}
}
int traspuesta(float mat[DIM][DIM], float matras[]){
}
