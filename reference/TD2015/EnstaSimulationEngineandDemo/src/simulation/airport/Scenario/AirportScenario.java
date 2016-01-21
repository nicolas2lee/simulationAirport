package simulation.airport.Scenario;

import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import simulation.airport.SimEngine;
import simulation.airport.SEntity.Airplane.Airplane;
import simulation.airport.SimEvent.ArriveEvent;


public class AirportScenario {
	
	private SimEngine engine;
	private String scenarioId;
	private LogicalDateTime start;
	private LogicalDateTime end;
	private List<Airplane> initialAirplanes;
	
	
	public AirportScenario(SimEngine engine,
			String scenarioId, 
			LogicalDateTime start, LogicalDateTime end,
			List<Airplane> initialAirplanes) {
		this.engine=engine;
		this.scenarioId=scenarioId;
		this.start=start;
		this.end=end;
		this.initialAirplanes=initialAirplanes;
		for ( Airplane airplane : initialAirplanes){
			//airplane.addEvent(airplane.new NotifDepart());
			ArriveEvent arriveEvent=new ArriveEvent();
			airplane.addEvent(arriveEvent.new NotifBeginOfArrive());
		}
	}
}
	