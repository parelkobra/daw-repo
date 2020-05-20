#include <stdio.h>
#include <string.h>

typedef struct jugadores {
	char nombre[50];
	char equipo[25];
	int goles;
	int minutos;
	float altura;
} jugador;

void mostrar(jugador p[]);
void ordenaAltura(jugador p[]);
void club(jugador p[]);
void main(){
	jugador p[5] = {
			"Cristiano Ronaldo","Real Madrid",28,2470,1.88,
			"Leo Messi","Barcelona",27,2150,1.68,
			"David Villa","Barcelona",17,2240,1.76,
			"Fernando Llorente","Athletic Club",15,2512,1.92,
			"Giussepe Rossi","Villareal",15,1956,1.74
	};
	int n=-1, res;
	while(n!=0){
		printf("\n-- Ejercicio Registros --\n");
		printf("0. Salir\n");
		printf("1. Quí ha jugat més minuts?\n");
		printf("2. Muestra una llista de les alçades dels jugadors de més petit a més gran.\n");
		printf("3. Quants gols han fet en total els 5 jugadors?\n");
		printf("4. Muestra una llista dels golejadors ordenats per club alfabèticament\n");
		printf("----------\n");
		scanf("%d", &n);
		getchar();
		switch(n){
			case 0:printf("\nAdeu\n");
				break;
			case 1:res = maxMinuts(p);
				printf("%s amb %d minuts\n", p[res].nombre, p[res].minutos);
				break;
			case 2:ordenaAltura(p);
				mostrar(p);
				break;
			case 3:res = sumaGols(p);
				printf("%d\n", res);
				break;
			case 4:club(p);
				mostrar(p);
				break;
		}
	}
}

void mostrar(jugador p[]){
	int i;
	for(i=0; i<5; i++){
		printf("%s -", p[i].nombre);
		printf(" %s -", p[i].equipo);
		printf(" %d -", p[i].goles);
		printf(" %d -", p[i].minutos);
		printf(" %.2f", p[i].altura);
		printf("\n");
	}
}

int maxMinuts(jugador p[]){
	int i, max = p[0].minutos, pos;
	for(i=0; i<4 ;i++){
		if(p[i].minutos > max){
			max = p[i].minutos;
			pos = i; 			
		}
	}
	return pos;
}

void ordenaAltura(jugador p[]){
	int i, j;
	jugador aux;
	for(i=0; i<5-2; i++){
		for(j=0; j<=5-i-2; j++){
			if(p[j].altura > p[j+1].altura){
				aux = p[j];
				p[j] = p[j+1];
				p[j+1] = aux;			
			}
		}	
	}
}

int sumaGols(jugador p[]){
	int i, sum = 0;
	for(i=0; i<5; i++){
		sum = sum + p[i].goles;
	}
	return sum;
}

void club(jugador p[]){
	int i, j, comp;
	jugador aux;
	for(i=0; i<5-2; i++){
		for(j=0; j<=5-i-2; j++){
			comp = strcmp(p[j].equipo, p[j+1].equipo);
			if(comp > 0){
				aux = p[j];
				p[j] = p[j+1];
				p[j+1] = aux;
			}
		}	
	}
}
