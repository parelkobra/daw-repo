#include <stdio.h>
#include <string.h>
#define nmax 10

typedef struct STOCK {
	char codi[6];
	int preu;
	int quant;
	char desc[25];
} reg;

void mostrar(reg r[], int num);
void cargar(reg r[], char nom_fitxer[]);
void mostrarMayor15(reg r[], int num);
void buscar(reg r[], int num, char codigoBuscar[]);
void guardarSTOCK5(reg r[], int num);
void guardarProductes(reg r[], int num);
void main(){
	int n=-1, num;
	char nom_fitxer[nmax], codigoBuscar[nmax];
	reg r[nmax];
	
	while(n!=0){
		printf("\n-- Practica Ficheros Refuerzo --\n");
		printf("\n0. Salir\n");	
		printf("\n1. Carga los registros del fichero en un vector.\n");
		printf("\n2. Muestra los productos con un precio superior a 15€.\n");
		printf("\n3. Buscar producto por codigo.\n");
		printf("\n4. Guardar en el fichero STOCK5.txt los productos con un stock inferior a 5.\n");
		printf("\n5. Guardar en el fichero Productes.txt los productos con un stock superior a 100 y precio superior a 10€.\n");	
		printf("\n------------------------\n");
		scanf("%d", &n);
		getchar();
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf("Nombre del fichero >> ");
				scanf("%s", nom_fitxer);
				printf("Cantidad de registros en el fichero >> ");
				scanf("%d", &num);
				printf("\n");
				cargar(r, nom_fitxer);
				mostrar(r, num);
				break;
			case 2:mostrarMayor15(r, num);
				break;
			case 3:printf("Codigo del producto >> ");
				scanf("%s", codigoBuscar);
				buscar(r, num, codigoBuscar);
				break;
			case 4:guardarSTOCK5(r, num);
				printf("\nGuardado\n");
				break;
			case 5:guardarProductes(r, num);
				printf("\nGuardado\n");
				break;
		}
	}
}

void mostrar(reg r[], int num){
	int i;
	for(i=0; i<num; i++){
		printf("%s -", r[i].codi);
		printf(" %d€ -", r[i].preu);
		printf(" %d -", r[i].quant);
		printf(" %s", r[i].desc);
		printf("\n");
	}
}

void cargar(reg r[], char nom_fitxer[]){
	int i=0;	
	FILE *f1;
	f1 = fopen(nom_fitxer, "rw");
	while(fscanf(f1, "%s %d %d %s", r[i].codi, &r[i].preu, &r[i].quant, r[i].desc) > 0){
		i++;
	}
	fclose(f1);
}

void mostrarMayor15(reg r[], int num){
	int i;
	for(i=0; i<num; i++){
		if(r[i].preu > 15){
			printf("%s -", r[i].codi);
			printf(" %d€ -", r[i].preu);
			printf(" %d -", r[i].quant);
			printf(" %s", r[i].desc);
			printf("\n");
		}
	}
}

void buscar(reg r[], int num, char codigoBuscar[]){
	int i=0, comp;
	comp = strcmp(r[i].codi, codigoBuscar);	

	while((i<num)&&(comp != 0)){
		i++;
		comp = strcmp(r[i].codi, codigoBuscar);
	}
	if(comp == 0){			
		printf("%s -", r[i].codi);
		printf(" %d€ -", r[i].preu);
		printf(" %d -", r[i].quant);
		printf(" %s", r[i].desc);
		printf("\n");
	}
	else{
		printf("No s'ha trobat el producte.\n");	
	}
}

void guardarSTOCK5(reg r[], int num){
	int i;
	FILE *f2;
	f2 = fopen("STOCK5.txt", "w");
	for(i=0; i<num; i++){
		if(r[i].quant < 5){
			fprintf(f2, "%s %d€ %d %s\n", r[i].codi, r[i].preu, r[i].quant, r[i].desc);
		}
	}
	fclose(f2);
}

void guardarProductes(reg r[], int num){
	int i;
	FILE *f3;
	f3 = fopen("Productes.txt", "w");
	for(i=0; i<num; i++){
		if((r[i].quant > 100)&&(r[i].preu > 10)){
			fprintf(f3, "%s %d€ %d %s\n", r[i].codi, r[i].preu, r[i].quant, r[i].desc);
		}
	}
	fclose(f3);
}
