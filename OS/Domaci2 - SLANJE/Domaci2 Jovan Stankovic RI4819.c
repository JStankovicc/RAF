//SIMULATION OF COMPUTER NETWORK USING THREADS
//10 COMPUTERS

#include <pthread.h>
#include <semaphore.h>
#include <sys/time.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

sem_t semafor;

int end=0;//END FLAG
int sum=0;//SUM OF PACKAGES

struct racunar_t{
    int state; // 0 – TRANSMITING, 1 – WAITING FOR NEW TRANSMISSION, 2 – WAITING FOR RETRANSMISSION
    int k; // NOUMBER OF CONSECUTIVE COLLISIONS
}racunar_t;

struct computer{
    struct racunar_t comp;
    int collision;
    int id;
}computer;

struct magistrala_t{
    int start; // STARTED TRANSMISSION
    int computer_id; // ID OF THE COMPUTER USING IT
    int brojac; // NOUMBER OF CONSECUTIVE PACKAGES SUCCESFULLY TRANSMITED
}magistrala_t;

void pauza(int vrm){              //PAUSE FUNCTION
    int trVreme=clock()*1000/CLOCKS_PER_SEC;
    int kraj=trVreme+vrm;
    do{
        trVreme=clock()*1000/CLOCKS_PER_SEC;
    } while(trVreme<=kraj);
}

void* racunarThread(void *arg,struct computer racunari[10]){

    struct computer *pc=(struct computer*) arg;
    while(end==0){
        int time=getVreme();
        if((time-magistrala_t.start)<2000&&magistrala_t.computer_id!=-1){//CHECKS IF IT IS A COLLISION
            racunari[magistrala_t.computer_id].collision=1;
            pc->comp.k++;
            pc->comp.state=2;
            int n=pc->comp.k;
            int del=1000*delay(n);//EXPONENTIAL DELAY
            usleep(del);
        }else{
            pc->comp.state=0;
            sem_wait(&semafor);
            magistrala_t.start=getVreme();
            magistrala_t.computer_id=pc->id;
            usleep(1999);
            if(pc->collision==1){
                magistrala_t.computer_id=-1;
                sem_post(&semafor);
                pc->collision=0;
                pc->comp.state=2;
                pc->comp.k++;
            }else{
                usleep(8001);
                magistrala_t.computer_id=-1;
                magistrala_t.brojac++;
                sem_post(&semafor);
                pc->comp.state=1;
                if(pc->comp.k!=0)pc->comp.k=0;
                int del=1000*(50+rand()%100);//RANDOM DELAY
                usleep(del);
            }
        }
    }
}

void* magistralaThread(void *arg){
    int num=0;
    while(num<60){//60s SIMULATION
        pauza(1000);
        sem_wait(&semafor);
        sum+=magistrala_t.brojac;
        printf("Broj prenetih paketa u %d. sekundi je: %d.\n",num+1,magistrala_t.brojac);//NOUMBER OF TRANSMITED PACKAGES FOR EVERY SECOND
        magistrala_t.brojac=0;
        sem_post(&semafor);
        num++;
    }
    double temp=sum;
    double stepenIsk=temp/6000;
    printf("\n\n\nTotal noumber of transmited packages: %d.\nStepen iskoriscenje mreze je: %.4f.\nProcenat iskoriscenja mreze je: %.2f%c.\n\n", sum,stepenIsk,stepenIsk*100,'%');
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
