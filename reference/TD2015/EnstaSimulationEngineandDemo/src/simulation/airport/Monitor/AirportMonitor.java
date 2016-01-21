package simulation.airport.Monitor;

import java.util.ArrayList;
import java.util.List;

import simulation.airport.SimEngine;
import simulation.airport.SEntity.Airplane.Airplane;
import simulation.airport.SEntity.Airplane.AirplaneStatus;
import simulation.airport.Scenario.AirportScenario;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;


public class AirportMonitor extends SimpleSimMonitor{
		
	private static LogicalDateTime t(int n) {
		String year = Integer.toString(n);
		while (year.length() < 4)
			year = "0" + year;
		return new LogicalDateTime("01/01/" + year + " 00:00:00.0000");
	}
	
	public static void main(String [] args) {
		int nbDaysOfSimulation = 90;
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		SimEngine engine = new SimEngine(t(9999));
		List<Airplane> initialAirplanes = new ArrayList<Airplane>();
		initialAirplanes.add(new Airplane(engine,"A01",AirplaneStatus.Depart));
		List<AirportScenario> listeScenario = new ArrayList<AirportScenario>();
		listeScenario.add(new AirportScenario(
				engine,
				"Scenario1",
				start,
				start.add(LogicalDuration.ofDay(nbDaysOfSimulation)),
				initialAirplanes)
		);
		engine.initialize();
		engine.resume();
		while(engine.triggerNextEvent()){}
		//System.out.println("test");
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