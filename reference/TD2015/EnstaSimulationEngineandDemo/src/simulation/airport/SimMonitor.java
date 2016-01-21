package simulation.airport;

import simulation.airport.SEntity.Airplane.Airplane;
import simulation.airport.SimEvent.SimEvent;
import enstabretagne.base.time.LogicalDateTime;

public class SimMonitor {
		
	private static LogicalDateTime t(int n) {
		String year = Integer.toString(n);
		while (year.length() < 4)
			year = "0" + year;
		return new LogicalDateTime("01/01/" + year + " 00:00:00.0000");
	}
	
	public static void main(String [] args) {
		SimEngine engine = new SimEngine(t(9999));
		/*
		Airplane alice = new Airplane(engine);
		alice.setName("alice");
		Airplane bob = new Airplane(engine);
		bob.setName("Bob");
		SimEvent e1 = new SimEvent() {
			@Override
			public void process() {
				alice.sayHelloTo(bob);
			}
		};
		e1.resetProcessDate(t(2));
		SimEvent e2 = new SimEvent() {
			@Override
			public void process() {
				bob.sayHelloTo(alice);
			}
		};
		e2.resetProcessDate(t(1));
		alice.addEvent(e1);
		bob.addEvent(e2);
		engine.initialize();
		engine.resume();
		while (engine.triggerNextEvent()) {}*/
	}
	
}