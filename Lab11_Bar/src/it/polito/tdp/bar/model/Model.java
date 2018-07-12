package it.polito.tdp.bar.model;

public class Model {

	Simulazione simulazione;
	
	public Model(){
		simulazione = new Simulazione();
	}
	
	public void simula() {
		simulazione.init(2000);
		simulazione.run();
	}

	public Simulazione getSimulazione() {
		return simulazione;
	}
	
}
