package enstabretagne.Scenario;

import enstabretagne.SimEntity.airport.airport;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.Logger;
import enstabretagne.messages.Messages;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;

public class airportScenario extends SimScenario {
	
	private CategoriesGenerator arrivalDelayRecordingCatGen;
	private MoreRandom random;
	private LogicalDuration beginFlightTime;
	private LogicalDuration endFlightTime;
	
	public airportScenario(SimEngine engine,
			ScenarioId scenarioId, 
			SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(engine, scenarioId,  features, start, end);
		System.out.println("====================the simulation data is "+engine.SimulationDate()+"===========");
		airportScenarioFeatures asf = (airportScenarioFeatures) features;
		arrivalDelayRecordingCatGen = asf.getArrivalDelayRecordingCatGen();
		
		random = new MoreRandom(MoreRandom.globalSeed);
		
		//lamda_arrive_airplane_normal = 
		beginFlightTime = LogicalDuration.fromString(asf.getBeginFlightTime());
		endFlightTime = LogicalDuration.fromString(asf.getEndFlightTime());
		
		
		Add(new Action_EntityCreation(airport.class, asf.getApFeatures().getId(), asf.getApFeatures(), asf.getAirportinit()));
		
	}
	
	class beginArriveAirplane extends SimEvent{

		@Override
		public void Process() {
			LogicalDateTime d = getNextTimeForAirplane();
			if (d!= null) Post(new NewAirplaneEvent(),d);
			
			Post(new endArriveAirplane(), getCurrentLogicalDate().truncateToDays().add(endFlightTime));
			Logger.Information(this.Owner(), "beginArriveAirplane", Messages.BeginPeriodNewAirplane);
			
		}

		LogicalDateTime getNextTimeForAirplane() {
			double d=random.nextExp(lambda)
			return null;
		}
		
	}

}