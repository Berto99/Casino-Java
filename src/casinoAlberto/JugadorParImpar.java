package casinoAlberto;

public class JugadorParImpar extends Thread {
	int saldo;
	int numApuesta;
	Banca banca;
	boolean juego;
	int apuesta;

	public JugadorParImpar(Banca banca) {
		this.banca = banca;
		this.saldo = 1000;
		this.apuesta = 10;
		this.juego = true;

	}
	//Comentado en la clase JugadorNumConcreto , realiza lo mismo, solo cambia el metodo del tipo de juego
	public void run() {
		while (juego) {
			this.saldo -= apuesta;
			banca.moviminetoDinero(apuesta);
			numApuesta = (int) (Math.random() * 36 + 1);
			synchronized (banca) {
				try {
					banca.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			juego = parImpar();

		}

	}

	public boolean parImpar() {
		//Si el saldo - la apuesta del jugador es menor o igual a 0 , retorna falso 
		if (this.saldo - apuesta <= 0) {
			System.out.println("El jugadorMartingala<"+this.getName()+"> se ha quedado sin pasta");
			juego = false;
			return juego;
		} else {
			//Igualamos la variable juego a true
			juego = true;
			//Si el numero de la banca = 0 , el jugador pierde independietemente de su numero
			if (banca.getNumGanador() == 0) {
				System.out.println("JugadorParImpar<"+ this.getName()+"> Ha salido un 0!!!! has perdido. Tu saldo es de "+this.saldo);
			}
			//Si el numero del jugador es par
			else if (numApuesta % 2 == 0) {
				//Si el numero de la banca es par
				if (banca.getNumGanador() % 2 == 0) {
					//El jugador gana , se le restan 20 al saldo de la banca y se ingrsan 20 al jugador
					banca.moviminetoDinero(-20);
					this.saldo += 20;
					System.out.println("El numero del jugadorParImpar<"+this.getName()+"> es " + numApuesta
							+ ". Has Ganado!!!!! Tu saldo es de " + this.saldo);
				} else {
					//El jugador pierde
					System.out.println("El numero del jugadorParImpar<" +this.getName()+"> es " + numApuesta
							+ ". Has Perdido!!!!! Tu saldo es de " + this.saldo);
				}
			} 
			
			else {
				//Si el numero del jugador es impar y el numero de la banca tambien
				if (banca.getNumGanador() % 2 != 0) {
					//Si gana sumamos saldo al jugador y quitamos saldo 
					banca.moviminetoDinero(-20);
					this.saldo += 20;
					System.out.println("El numero del jugadorParImpar<" +this.getName()+"> es " + numApuesta
							+ ". Has Ganado!!!!! Tu saldo es de " + this.saldo);
				} else {
					System.out.println("El numero del jugadorParImpar<" +this.getName()+"> es " + numApuesta
							+ ". Has Perdido!!!!! Tu saldo es de " + this.saldo);
				}

			}
			//Devolvemos true
			return juego;

		}

	}

}
