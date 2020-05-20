#include <stdio.h>
#define nmax 50
void leer(int vect[], int num);
void mostrar(int vect[], int num);
int find(int vect[], int num, int busc);
void ordena(int vect[], int num);
void main(){
	int vect[nmax], num, n=-1, i, busc, res, valor, cont;
	while(n!=0){
		printf("M E N U\n");
		printf("---------\n");
		printf("0. Sortir\n");
		printf("1. Introdueix un vector\n");
		printf("2. Mostra el vector per pantalla\n");
		printf("3. Busca un element en el vector\n");
		printf("4. Cuantas veces sale un elemento en el vector\n");
		printf("5. Di el numero mas alto del vector\n");
		printf("6. Ordenar un vector amb el mètode de la bombolla\n");
		printf("7. Búsqueda dicotomica en un vector\n");
		printf("8. Inserta un numero en un vector i retorna la posició\n");

		printf("---------\n");
		scanf("%d", &n);
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf("Abans introdueix la quantitat de numeros que tindra el vector\n");
				scanf("%d", &num);
				printf("Escribe el vector\n");
				leer(vect, num);
				break; 
			case 2:printf("\n");
				mostrar(vect, num);
				break;
			case 3:printf("Introdueix el número que vols buscar\n");
				scanf("%d", &busc);
				res=find(vect, num, busc);
				if(res==-1){
					printf("El numero no esta en el vector");
				}
				else{ 
				printf("Posicion: %d\n", res);
				}
				break;
			case 4:printf("\nIntroduce el número que quieras buscar\n");
				scanf("%d", &valor);
				res=veces(valor, vect, num);	
				printf("El numero esta %d veces\n", res);
				break;
			case 5:res=buscar(vect, num);
				printf("El numero mes alt es a la posició %d\n", res);
				break;
			case 6:ordena(vect, num);
				printf("\nVector ordernado:\n");
				mostrar(vect, num);
				break;
			case 7:ordena(vect, num);
				printf("\nIntroduce el número que quieras buscar\n");
				scanf("%d", &busc);
				res=buscDicotomica(vect, num, busc);
				if(res==-1){
					printf("El numero NO esta en el vector\n");				
				}
				else{
					printf("El numero esta en la posición %d\n", res);				
				}
				break;
			case 8:printf("Introduce el numero que quieres introducir\n");
				scanf("%d", &elem);
				insertarOrden(elem, vec, *num);


				break;
		}
	}
}
void leer(int vect[], int num){
	int i;
	for(i=0;i<num;i++){
		scanf("%d", &vect[i]);
	}
}
void mostrar(int vect[], int num){
	int i;
	for(i=0;i<num;i++){
		printf("%d\n", vect[i]);
	}
}
int find(int vect[], int num, int busc){
	int i = 0;
	while((i < num)&&(vect[i] != busc)){
		i++;
	}
		if(vect[i] == busc){
			return i+1;
		}
		else{
			return -1;
		}
}
int veces(int valor, int vect[], int num){
	int i;
	int cont = 0;
	for(i=0;i<num;i++){
		if(vect[i] == valor){
			cont++;		
		}
	}
	return cont;	
}
int buscar( int vect[], int num){ 			
	int i;
	int max = 0;
	for(i=0;i<num;i++){
		if(vect[i] > vect[i-1]){
			max = i;
		}
	}
	max = max+1;
	return max;
}
void ordena(int vect[], int num){
	int i,j;
	int aux = 0;
	for(i=0;i<num-2;i++){
		for(j=0;j<=num-i-2;j++){
			if(vect[j] > vect[j+1]){
				aux = vect[j];
				vect[j] = vect[j+1];
				vect[j+1] = aux;	
			}		
		}	
	}
}
int buscDicotomica(int vect[], int num, int busc){	/*Si num esta
								en la primera posicion
								se queda pillado*/
	int izq = 0;
	int dcha = num-1;
	int centro = (izq + dcha) / 2;
	while((vect[centro] != busc)&&(izq <= dcha)){
		if(vect[centro] < busc){
			izq = centro+1;		
		}
		else{
			dcha = centro+1;		
		}
		centro = (izq + dcha) / 2;
	}
	if(vect[centro] == busc){
		return centro;	
	}
	else{
		return -1;	
	}
}
int insertarOrden(int elem, int vec[], int *num){
	int i=0;
	while((vect[i] <= elem )&&(i < num)){
		i++;
	}
	num++;
	if(i < num){
		i++;
		vect[i] = elem;	
	}
	else{
			
	}
}






































