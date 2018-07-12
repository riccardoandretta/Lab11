package it.polito.tdp.bar.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Simulazione {
	enum EventType {
		ARRIVO_GRUPPO_CLIENTI, ASSEGNA_A_TAVOLO, ASSEGNA_A_BANCONE, USCITA
	}

	class Event implements Comparable<Event> {

		private int time;
		private int num_persone;
		private int durata;
		private double tolleranza;
		private EventType tipo;

		public Event(int time, int num_persone, int durata, double tolleranza, EventType tipo) {
			super();
			this.time = time;
			this.num_persone = num_persone;
			this.durata = durata;
			this.tolleranza = tolleranza;
			this.tipo = tipo;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}

		public int getNum_persone() {
			return num_persone;
		}

		public void setNum_persone(int num_persone) {
			this.num_persone = num_persone;
		}

		public int getDurata() {
			return durata;
		}

		public void setDurata(int durata) {
			this.durata = durata;
		}

		public double getTolleranza() {
			return tolleranza;
		}

		public void setTolleranza(double tolleranza) {
			this.tolleranza = tolleranza;
		}

		public EventType getTipo() {
			return tipo;
		}

		public void setTipo(EventType tipo) {
			this.tipo = tipo;
		}

		@Override
		public int compareTo(Event o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	// Coda degli eventi
	private PriorityQueue<Event> coda = new PriorityQueue<>();

	// Parametri di simulazione // Impostati all'inizio // Costanti durante la
	// simulazione

	private Map<Integer, Integer> mappaTavoli = new HashMap<>();

	private int T_ARRIVO = (int) ((Math.random() * 10) + 1);;
	private int N_PERSONE = (int) ((Math.random() * 10) + 1);;
	private int T_DURATA = (int) ((Math.random() * 1000)/61 + 60);
	private double TOLLERANZA;

	// Modello del mondo (stato del sistema, i valori cambiano in continuazione)
	private Map<Integer, Integer> mappaTavoliDisponibili = new HashMap<>();

	// Valori da calcolare
	private int numero_totale_clienti;
	private int numero_clienti_soddisfatti;
	private int numero_clienti_insoddisfatti;

	public void init(int numEventi) {
		coda.clear();
		mappaTavoli.put(4, 5);
		mappaTavoli.put(6, 4);
		mappaTavoli.put(8, 4);
		mappaTavoli.put(10, 2);

		mappaTavoliDisponibili.put(4, 5);
		mappaTavoliDisponibili.put(6, 4);
		mappaTavoliDisponibili.put(8, 4);
		mappaTavoliDisponibili.put(10, 2);

		int cont = 0;
		while (cont < numEventi) {
			Event e = new Event(T_ARRIVO, N_PERSONE, T_DURATA, TOLLERANZA, EventType.ARRIVO_GRUPPO_CLIENTI);
			coda.add(e);
			cont++;
		}
		numero_totale_clienti = 0;
		numero_clienti_soddisfatti = 0;
		numero_clienti_insoddisfatti = 0;
	}

	public void run() {
		Event e;
		while ((e = coda.poll()) != null) {
			processEvent(e);
		}
	}

	private void processEvent(Event e) {

//		int time = (int) ((Math.random() * 10) + 1);
		int num_persone = (int) ((Math.random() * 10) + 1);
		double tolleranza = Math.random();

		switch (e.getTipo()) {
		case ARRIVO_GRUPPO_CLIENTI:
			
			int time = (int) ((Math.random() * 10) + 1);
			int numPersone = e.getNum_persone();
			numero_totale_clienti += numPersone;
			if (tavoloDisponibile(numPersone)) {
				Event atavolo = new Event(time+T_ARRIVO, numPersone, T_DURATA, TOLLERANZA, EventType.ASSEGNA_A_TAVOLO);
				coda.add(atavolo);
				mappaTavoliDisponibili.replace(numPersone, mappaTavoliDisponibili.get(numPersone)-1);
				numero_clienti_soddisfatti += numPersone;
			}else if(tolleranza > 0) {
				Event abancone = new Event(time+T_ARRIVO, numPersone, T_DURATA, TOLLERANZA, EventType.ASSEGNA_A_BANCONE);
				coda.add(abancone);
				numero_clienti_soddisfatti += numPersone;
			}else {
				new Event(time+T_ARRIVO, numPersone, T_DURATA, TOLLERANZA, EventType.USCITA);
				numero_clienti_insoddisfatti += numPersone;
			}		

			break;
		case ASSEGNA_A_BANCONE:
			
			break;
		case ASSEGNA_A_TAVOLO:
			break;
		case USCITA:
			break;
		}

	}

	private boolean tavoloDisponibile(int numPersone) {
		for (Integer i : mappaTavoli.keySet()) {
			if (numPersone == i) {
				if (mappaTavoliDisponibili.get(i) > 0) {
					return true;
				}
			}
			else if (numPersone < i  && numPersone >= mappaTavoliDisponibili.get(i)/2) {
				return true;
			}
		}
		return false;
	}

	public int getNumero_totale_clienti() {
		return numero_totale_clienti;
	}

	public int getNumero_clienti_soddisfatti() {
		return numero_clienti_soddisfatti;
	}

	public int getNumero_clienti_insoddisfatti() {
		return numero_clienti_insoddisfatti;
	}
}
