package it.polito.tdp.bar.model;

public class TestModel {

	public static void main(String[] args) {

		Simulazione sim = new Simulazione();
		sim.init(2000);
		sim.run();

		System.out.format(
				"Numero clienti arrivati: %d\nNumero clienti soddisfatti: %d\nNumero clienti insoddisfatti: %d",
				sim.getNumero_totale_clienti(), sim.getNumero_clienti_soddisfatti(),
				sim.getNumero_clienti_insoddisfatti());
	}

}
