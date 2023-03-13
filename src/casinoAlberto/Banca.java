package casinoAlberto;

public class Banca extends Thread{
	//Variables
	int saldo;
	private int numGanador;
	Thread[] jugadores;
	int turno;
	
	//Constructor al que le pasamos el array de hilos de los jugadores
	public Banca(Thread [] jugadores) {
		//Iniciamos el saldo
		this.saldo=50000;
		this.jugadores=jugadores;
		//Iniciamos el turno de jugador a 1
		this.turno=1;
	}
	
	//Metodo synchronized porque hacederan al saldo de la banca para modificarlo todos los hilos jugadores
	public synchronized void moviminetoDinero(int cantidad) {
		//Si el saldo - la cantidad es menor o igual a 0, se termina el programa
		if (this.saldo+cantidad<=0) {
			System.out.println("-----Casino en Bancarrota------");
			System.exit(0);
		}
		else {
			this.saldo+=cantidad;
		}
		
	}

	public int getSaldo() {
		return saldo;
	}
	
	public int getNumGanador() {
		return numGanador;
	}


	//Correponde a la ruleta
	public void run() {
		System.out.println("-----Casino Alberto-----");
		//Metodo anadir jugadores al array de hilos
		anadirJugadores();
		while(true) {
			try {
				//Esperamos 3 segundos a que todos los hilos hagan sus apuestas
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("<<<<<Turno "+turno+">>>>>");	
			System.out.println("---------Apuestas realizadas, girando la ruleta----------");
			try {
				//Esperamos 3 segundos a que todos los hilos hagan sus apuestas
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Genera el numero aleatorio
			numGanador=(int) (Math.random()*36);
			System.out.println("----------El numero ganador es el : "+ numGanador+"----------");
			System.out.println("----------El saldo de la banca es de " +this.saldo+"----------");
			synchronized (this) {
				//Notificamos a todos los hilos que el numero ganador ha sido generado
				this.notifyAll();
				
			}
			//Sumamos + al turno
			turno++;
			
		}
	
	}
	//Metodo en el que vamos metiendo a los jugadores en el array de hilos jugadores y realizamos
	//un start en cada uno de ellos para que comience su ejecucion
	public void anadirJugadores() {
		for (int i =0;i<12;i++) {
			if (i<4){
				jugadores[i]=new JugadorNumConcreto(this);
				//Ponemos un nombre a los hilos para poder identificarlos cuando se impriman por pantalla
				jugadores[i].setName(""+(i+1));
				jugadores[i].start();
			}
			else if (i<8) {
				jugadores[i]=new JugadorParImpar(this);
				jugadores[i].setName(""+(i+1));
				jugadores[i].start();
			}
			else {
				jugadores[i]=new JugadorMartingala(this);
				jugadores[i].setName(""+(i+1));
				jugadores[i].start();
			}
			
		}	
	}
	
}
