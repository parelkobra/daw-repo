#include <stdio.h>
#define cmax 55
int strlen2(char s[]);
void strcat2(char destino[], char origen[]);
void main()
{
	char s[cmax], destino[cmax], origen[cmax], s1[cmax], s2[cmax];
	int n=-1, i, res, comp;
	while(n!=0)
	{
		printf("M E N U\n");
		printf("---------\n");
		printf("0. Sortir\n");
		printf("1. Retorna la quantitat de caracters del string\n");	
		printf("2. Copia los caracteres d'un string 'origen' a destino\n");
		printf("3. Compara 2 strings\n");
		printf("4. Concatena la cadena origen i destino\n");		
		printf("---------\n");
		scanf("%d", &n);
		switch(n)
		{
			case 0:printf("\nAdeu\n");
				break;	
			case 1:printf("Introdueix una frase\n");
				scanf("%s", s);
				res = strlen2(s);
				printf("%d\n", res);
				break;
			case 2:printf("Introdueix una frase\n");
				scanf("%s", origen);
				strcpy2(destino, origen);
				printf("%s\n", destino);
				break;	
			case 3:printf("Introdueix la primera cadena\n");
				scanf("%s", s1);
				printf("Introdueix la segona cadena\n");
				scanf("%s", s2);
				comp = strcmp2(s1, s2);
				if(comp < 0){
					printf("La primera string es MENOR que la segona string\n");
				}
				if(comp == 0){
					printf("La primera string es IGUAL que la segona string\n");
				}
				if(comp > 0){
					printf("La primera string es MAJOR que la segona string\n");
				}
				break;	
			case 4:printf("Introdueix la cadena 'origen'\n");
				scanf("%s", origen);
				printf("Introdueix la cadena 'destino'\n");
				scanf("%s", destino);
				strcat2(destino, origen);
				printf("%s\n", destino);
				break;	
		}	
	}	
}
int strlen2(char s[]){
	int res=0, i=0;
	while(s[i]!='\0'){
		res=res+1;
		i++;
	}
	return res;
}
int strcpy2(char destino[], char origen[]){
	int i;
	while(origen[i]!='\0'){
		destino[i]=origen[i];
		i++;
	}
	destino[i]='\0';
}
int strcmp2(char s1[], char s2[]){
	int i=0, res=0;
	while((s1[i]!='\0')&&(s1[i]==s2[i])&&(s2[i]!='\0')){
		i++;
	}
	if(s1[i]!=s2[i]){
		res=s1[i]-s2[i];
		return res;	
	}
	else{
		return res;	
	}
}
void strcat2(char destino[], char origen[]){
	int i=0, j=0;
	while(destino[j]!='\0'){
		j++;	
	}
	while(origen[i]!='\0'){
		destino[j] = origen[i]; 		
		i++;
		j++;	
	}
	destino[j]='\0';
}
