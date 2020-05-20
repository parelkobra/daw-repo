#include <stdio.h>
#include <string.h>
#define nmax 10

typedef struct AGENDA {
	char nom[30];
	char telefon[15];
	int edad;
} amics;

void introduir(amics a[], int num);
void mostrar(amics a[], int num);
void ordenarNom(amics a[], int num, int option);
void ordenarEdad(amics a[], int num, int option);
void insertar(amics a[], int *num, int option);
void insertarOrdenado(amics a[], amics b, int *num, int option);
void guardar(amics a[], int num, char nom_fitxer[]);
void cargar(amics a[], int *num, char nom_fitxer[]);
void main(){
	int n=-1, res, num, option, numero, numero_buscar;
	char cadena_buscar[nmax], nom_fitxer[nmax];
	amics a[nmax], b;

	while(n!=0){
		printf("\n-- Ejercicio Registros 2 --\n");
		printf("0. Salir\n");	
		printf("1. Nombre d'elements inicials de l'agenda?\n");			
		printf("2. Introdueix l'agenda\n");
		printf("3. Imprimir l'agenda\n");
		printf("4. Buscar un registre per nom o edad\n");
		printf("5. Ordena l'agenda per nom o edad\n");
		printf("6. Afegir nou contacte\n");
		printf("7. Guardar\n");
		printf("8. Cargar\n");
		printf("------------------------\n");
		scanf("%d", &n);
		getchar();
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:printf("\n>> ");
				scanf("%d", &num);
				break;
			case 2:introduir(a, num);
				break;
			case 3:mostrar(a, num);
				break;
			case 4:printf("1.Nom / 2.Edad ? ");
				scanf("%d", &option);
				if(option == 1){
					printf("Nombre a buscar >> ");
					scanf("%s", cadena_buscar);
					res = buscar1(a, num, cadena_buscar);
				}
				else if(option == 2){
					printf("Edad a buscar >> ");
					scanf("%d", &numero_buscar);
					res = buscar2(a, num, numero_buscar);
				}
				if(res == -1){
					printf("\nNo s'ha trobat l'element\n");
				}
				else{
					printf("\nEstà en la posició %d\n", res);				
				}
				break;
			case 5:printf("1.Nom / 2.Edad ? ");
				scanf("%d", &option);
				if(option == 1){
					ordenarNom(a, num, option);
					mostrar(a, num);
				}
				else{
					ordenarEdad(a, num, option);
					mostrar(a, num);
				}
				break;
			case 6:printf("1.Vector desordenat / 2.Vector ordenat per nom ? ");
				scanf("%d", &option);
				if(option == 1){
					insertar(a, &num, option);
					mostrar(a, num);
				}
				else{
					printf("\nNom: ");
					scanf("%s", b.nom);
					printf("\nTelefon: ");
					scanf("%s", b.telefon);
					printf("\nEdad: ");
					scanf("%d", &b.edad);
					printf("\n");
					insertarOrdenado(a, b, &num, option);
					mostrar(a, num);
				}
				break;
			case 7:printf("Introdueix el nombre del fitxer >> ");
				scanf("%s", nom_fitxer);
				guardar(a, num, nom_fitxer);
				printf("\nGuardado\n");
				break;
			case 8:printf("Cual es el nombre del fixero que quieres cargar? ");
				scanf("%s", nom_fitxer);
				cargar(a, &num, nom_fitxer);				
				printf("\nCargado\n");
				mostrar(a, num);				
				break;
		}
	}
}

void introduir(amics a[], int num){
	int i;
	for(i=0; i<num; i++){
		printf("\nNom: ");
		scanf("%s", a[i].nom);
		printf("\nTelefon: ");
		scanf("%s", a[i].telefon);
		printf("\nEdad: ");
		scanf("%d", &a[i].edad);
		printf("\n");
		printf("----------\n");
	}
}

void mostrar(amics a[], int num){
	int i;
	for(i=0; i<num; i++){
		printf("%s -", a[i].nom);
		printf(" %s -", a[i].telefon);
		printf(" %d", a[i].edad);
		printf("\n");
	}
}

int buscar1(amics a[], int num, char cadena_buscar[]){
	int i=0, comp;
	comp = strcmp(a[i].nom, cadena_buscar);
	while((i<num)&&(comp != 0)){
		i++;
		comp = strcmp(a[i].nom, cadena_buscar);
	}
	if(comp == 0){
		return i+1;			
	}
	else{
		return -1;
	}
}

int buscar2(amics a[], int num, int numero_buscar){
	int i=0;
	while((numero_buscar != a[i].edad)&&(i<num)){
		i++;
	}
	if(numero_buscar == a[i].edad){
		return i+1;			
	}
	else{
		return -1;
	}
}

void ordenarNom(amics a[], int num, int option){
	int i, j, comp;
	amics aux;
	for(i=0; i<num-2; i++){
		for(j=0; j<=num-i-2; j++){
			comp = strcmp(a[j].nom, a[j+1].nom);
			if(comp > 0){
				aux = a[j];
				a[j] = a[j+1];
				a[j+1] = aux;
			}
		}	
	}
}

void ordenarEdad(amics a[], int num, int option){
	int i, j;
	amics aux;
	for(i=0; i<num-2; i++){
		for(j=0; j<=num-i-2; j++){
			if(a[j].edad > a[j+1].edad){
				aux = a[j];
				a[j] = a[j+1];
				a[j+1] = aux;			
			}
		}	
	}
}

void insertar(amics a[], int *num, int option){
	int i=*num;
	printf("\nNom: ");
	scanf("%s", a[i].nom);
	printf("\nTelefon: ");
	scanf("%s", a[i].telefon);
	printf("\nEdad: ");
	scanf("%d", &a[i].edad);
	printf("\n");	
	*num = *num +1;
}

void insertarOrdenado(amics a[], amics b, int *num, int option){ 
	int i=0, comp, pos;
	comp = strcmp(b.nom, a[i].nom);
	while((comp > 0 )&&(i < *num)){
		i++;
		comp = strcmp(b.nom, a[i].nom);
	}
	pos = i;
	for(i=*num; i>pos; i--){
		a[i] = a[i-1];
	}
	a[i] = b;
	*num = *num +1;
}

void guardar(amics a[], int num, char nom_fitxer[]){
	int i;
	FILE *f;
	f = fopen(nom_fitxer, "w");
	for(i=0; i<num; i++){
		fprintf(f, "%s %s %d\n", a[i].nom, a[i].telefon, a[i].edad);
	}
	fclose(f);
}

void cargar(amics a[], int *num, char nom_fitxer[]){
  int i=0;	
	FILE *f;
	f = fopen(nom_fitxer, "rw");
	while(fscanf(f, "%s %s %d", a[i].nom, a[i].telefon, &a[i].edad) > 0){
		i++;
	}
	*num=i;
	fclose(f);
}
