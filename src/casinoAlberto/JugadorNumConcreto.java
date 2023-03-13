package casinoAlberto;

public class JugadorNumConcreto extends Thread {
	// Variables
	int saldo;
	int numApuesta;
	Banca banca;
	boolean juego;
	int apuesta;

	// Constructor
	public JugadorNumConcreto(Banca banca) {
		this.banca = banca;
		//Iniciamos el saldo del jugador a 1000 
		this.saldo = 1000;
		this.juego = true;
		//La apuesta del jugador la iniciamos en 10
		this.apuesta = 10;
	}

	public void run() {
		//Mientras que el boolean juego sea true
		while (juego) {
			// La apuesta que realiza el jugador para poder jugar
			this.saldo -= apuesta;
			//Llamamos al metodo de la banca para sumar la apuesta del jugador al saldo de la banca
			banca.moviminetoDinero(apuesta);
			//Generamos el numero aleatorio del jugador
			numApuesta = (int) (Math.random() * 36 + 1);
			//Bloque syncronized al que le pasamos el objeto banca y le decimos al hilo Jugadr que espera hasta
			//Que reciba un notify por parte de la clase Banca(espera hasta que el numGanador sea generado)
			synchronized (banca) {
				try {
					banca.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Igualamos el boolean juego al metodo  numConcreto porque devuelve un boolean
			juego = numConcreto();
			//Si el juego es igual a false, se sale del bucle y termina la ejecucion del hilo
		}

	}

	public boolean numConcreto() {
		//Si el saldo - la apuesta del jugador es menor o igual a 0 , retorna falso 
		if (this.saldo - apuesta <= 0) {
			System.out.println("El jugadorNumConcreto<"+this.getName()+"> esta sin pasta :(");
			juego = false;
			return juego;
		} else {
			//Si el numero de la banca = 0 , el jugador pierde independietemente de su numero
			if (banca.getNumGanador() == 0) {
				System.out.println("JugadorNumConcreto<"+this.getName() +"> Ha salido un 0!!!! has perdido Tu saldo es de "+this.saldo);
			}
			//Si el numero del jugador es igual al numero de la banca la banca pierde 360 y se ingresan al saldo del jugador
			else if (this.numApuesta == banca.getNumGanador()) {
				banca.moviminetoDinero(-360);
				this.saldo += 360;
				System.out.println("El numero del jugadorNumConcreto<"+ this.getName()  +"> es "+numApuesta
						+ ". Has Ganado!!!!! Tu saldo es de " + this.saldo);
			} else {
				//El jugador pierde
				System.out.println("El numero del jugadorNumConcreto<"+ this.getName() +"> es "+ numApuesta + " Has Perdido !!! Tu saldo es de "
						+ this.saldo);
			}
			// retorna true
			return juego;
		}

	}

}
