package casinoAlberto;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Nos creamos un array de hilos y un objeto banca
		Thread hilos[] = new Thread[12];
		Banca banca = new Banca(hilos);
		//Comenzamos al ejecucion del hilo banca
		banca.start();
		
	}

}
