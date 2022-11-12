#include <pthread.h>
#include <semaphore.h>
#include <sys/time.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

sem_t semafor;

int end=0;//flag za kraj
int sum=0;//suma paketa

struct racunar_t{
    int state; // 0 – transmituje, 1 – čeka na novu transmisiju, 2 – čeka na retransmisiju
    int k; // broj uzastopnih kolizija
}racunar_t;

struct computer{
    struct racunar_t comp;
    int collision;//kolizija
    int id; //id kompjutera
}computer;

struct magistrala_t{
    int start; // početak transmisije
    int computer_id; // id računara koji je počeo transmisiju
    int brojac; // brojač paketa prenetih bez prekida
}magistrala_t;

void pauza(int vrm){
    int trVreme=clock()*1000/CLOCKS_PER_SEC;//trenutno vreme
    int kraj=trVreme+vrm;//vreme kada ce se pauza zavrsiti
    do{
        trVreme=clock()*1000/CLOCKS_PER_SEC;
    } while(trVreme<=kraj);
}

void* racunarThread(void *arg,struct computer racunari[10]){

    struct computer *pc=(struct computer*) arg;
    while(end==0){
        int time=getVreme();//trenutno vreme
        if((time-magistrala_t.start)<2000&&magistrala_t.computer_id!=-1){//da li je doslo do kolizije
            racunari[magistrala_t.computer_id].collision=1;//kolizija
            pc->comp.k++;//povecava broj uzastopnih kolizija kompjutera
            pc->comp.state=2;//menja stanje kompjutera na cekanje na retransmisiju
            int n=pc->comp.k;//uzima br uzastopnih kolizija
            int del=1000*delay(n);//dolazi do eksponencijalnog kasnjenja
            usleep(del);//cekanje na retransmisiju
        }else{
            pc->comp.state=0;//menja stanje kompjutera na transmituje
            sem_wait(&semafor);//dekrementira brojac semafora
            magistrala_t.start=getVreme();//pocetak transmisije je trenutno vreme
            magistrala_t.computer_id=pc->id;//id kompjutera koji koristi magistralu je id trenutnog kompjutera
            usleep(1999);//pauza 1999ms
            if(pc->collision==1){//ako je kolizija
                magistrala_t.computer_id=-1;//menja se kompjuter koji koristi magistralu na id -1
                sem_post(&semafor);//Inkrementira brojac semafora
                pc->collision=0;//kolizija prestaje
                pc->comp.state=2;//menja stanje kompjutera na cekanje na retransmisiju
                pc->comp.k++;//povecava broj uzastopnih kolizija kompjutera
            }else{
                usleep(8001);//ceka 8ms (vec je cekao 2)
                magistrala_t.computer_id=-1;//menja se kompjuter koji koristi magistralu na id -1
                magistrala_t.brojac++;//dodaje jedan uspesno preneti okvir kroz mrezu
                sem_post(&semafor);//Inkrementira brojac semafora
                pc->comp.state=1;//menja stanje kompjutera na cekanje na novu transmisiju
                if(pc->comp.k!=0)pc->comp.k=0;//ako je broj uzastopnih kolizija veci od 0 vraca ga na 0
                int del=1000*(50+rand()%100);//random broj izmedju 50 i 150
                usleep(del);//novi okvir se transmituje 50-150ms nakon prethodnog
            }
        }
    }
}

void* magistralaThread(void *arg){
    int num=0;//brojac
    while(num<60){//60s
        pauza(1000);//pauza 1s
        sem_wait(&semafor);//dekrementira brojac semafora
        sum+=magistrala_t.brojac;//na sumu prenetih se dodaje broj prenetih paketa svake sekunde
        printf("Broj prenetih paketa u %d. sekundi je: %d.\n",num+1,magistrala_t.brojac);//Broj prenetih paketa svake sekunde
        magistrala_t.brojac=0;//broj prenetih paketa trenutne sekunde se vraca na 0
        sem_post(&semafor);//inkrementira brojac semafora
        num++;//brojac se povecava
    }
    double temp=sum;
    double stepenIsk=temp/6000;
    printf("\n\n\nUkupan broj prenetih paketa je: %d.\nStepen iskoriscenje mreze je: %.4f.\nProcenat iskoriscenja mreze je: %.2f%c.\n\n", sum,stepenIsk,stepenIsk*100,'%');
    end = 1;//kraj
}

int delay(int x){
    if (x>=10)x=10;//Ako se desi vise od 10 prekida, tretirati kao da je bilo 10
    int y=rand()%(int)(pow(2,x)-1);//slucajan odabir iz skupa od 2^n,n-broj prekida
    return y*2;//broj se mnozi sa 2 da bi se dobilo kasnjenje u ms
}

int getVreme(){ //vraca trenutno vreme
    struct timeval tv;
    gettimeofday(&tv,NULL);
    int time=tv.tv_usec; // vreme u mikrosekundama
    return time;
}

int main(){
    pthread_t niti[10],mag; //inicijalizacija niti
    struct computer kompjuteri[10];//niz od 10 kompjutera
    sem_init(&semafor,0,1);//kreiranje semafora
    for(int i=0;i<10;i++){  //dodeljivanje vrednosti strukturama kompjutera
        kompjuteri[i].comp.state=1;
        kompjuteri[i].comp.k=0;
        kompjuteri[i].id=i;
        kompjuteri[i].collision=0;
    }
    pthread_create(&mag,NULL,magistralaThread,NULL);//kreiranje niti magistrale
    for(int i=0;i<10;i++){    //kreiranje niti kompjutera
        pthread_create(&niti[i],NULL,racunarThread,(void*)&kompjuteri[i]);
    }
    pthread_join(mag,NULL);//pozivanje niti magistrale
    for(int i=0;i<10;i++){
        pthread_join(niti[i],NULL);//pozivanje niti kompjutera
    }
    sem_post(&semafor);//inkrementacija brojaca semafora
}
