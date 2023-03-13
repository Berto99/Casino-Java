package casinoAlberto;

public class JugadorMartingala extends Thread {
	//Variables
	int saldo;
	int numApuesta;
	Banca banca;
	int apuesta;
	boolean juego;
	
	//Constructor
	public JugadorMartingala(Banca banca) {
		this.banca = banca;
		this.saldo = 1000;
		this.apuesta = 10;
		this.juego = true;
	}
	//Comentado en la clase JugadorNumConcreto
	public void run() {
		while (juego) {
			this.saldo -= this.apuesta;
			banca.moviminetoDinero(this.apuesta);
			numApuesta = (int) (Math.random() * 36 + 1);
			synchronized (banca) {
				try {
					banca.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			juego = martingala();
		}
	}

	public boolean martingala() {
		//Si el saldo - la apuesta del jugador es menor o igual a 0 , retorna falso 
		if (this.saldo - apuesta <= 0) {
			System.out.println("El jugadorMartingala<"+this.getName()+"> se ha quedado sin pasta :(");
			juego = false;
			return juego;
		}

		else {
			//boolean igual a true
			juego = true;
			//Si el numero de la banca es igual a 0 , todos los jugadores pierden
			if (banca.getNumGanador() == 0) {
				System.out.println("Jugador Martingala<"+ this.getName() +"> Ha salido un 0!!!! has perdido. Tu saldo es de "+this.saldo);
			} else if (numApuesta == banca.getNumGanador()) {
				//El jugador gana 360 euros y la banca los pierde
				banca.moviminetoDinero(-360);
				this.saldo += 360;
				System.out.println("El numero del jugadorMartingala<" +this.getName()+ "> es "+numApuesta
						+ ". Has Ganado!!!!! Tu saldo es de " + this.saldo);
				// Reiniciamos la cantidad con la que apuesta el jugador
				this.apuesta = 10;

			} else {
				// Si el jugador pierde, doblamos la apuesta
				this.apuesta *= 2;
				System.out.println("El numero del jugadorMartingala<"+this.getName()+"> es " + numApuesta
						+ ". Has Perdido!!!!! Tu saldo es de " + this.saldo + " Se dobla la apuesta a " + this.apuesta);
			}
			//devuelve true
			return juego;
		}

	}

}
