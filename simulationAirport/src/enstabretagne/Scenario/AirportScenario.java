package enstabretagne.Scenario;

import java.time.DayOfWeek;

import enstabretagne.SimEntity.airplane.AirplaneInit;
import enstabretagne.SimEntity.airplane.StatusAirplane;
import enstabretagne.SimEntity.airplane.Airplane;
import enstabretagne.SimEntity.airplane.AirplaneFeature;
import enstabretagne.SimEntity.airport.Airport;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.messages.Messages;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;


public class AirportScenario extends SimScenario {
	
	
	private MoreRandom random;
	private LogicalDuration beginFlightTime;
	private LogicalDuration endFlightTime;
	private AirportScenarioFeatures asf;
	private LogicalDateTime currentDateTime;
	
	public LogicalDateTime getCurrentDateTime() {
		return currentDateTime;
	}

	public void setCurrentDateTime(LogicalDateTime currentDateTime) {
		this.currentDateTime = currentDateTime;
	}

	public AirportScenario(SimEngine engine,
			ScenarioId scenarioId, 
			SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(engine, scenarioId,  features, start, end);
		//System.out.println("====================the simulation data is "+engine.SimulationDate()+"===========");
		asf = (AirportScenarioFeatures) features;
		random = new MoreRandom(MoreRandom.globalSeed);
		
		//lamda_arrive_airplane_normal = 
		beginFlightTime = LogicalDuration.fromString(asf.getBeginFlightTime());
		endFlightTime = LogicalDuration.fromString(asf.getEndFlightTime());
		
		
		Add(new Action_EntityCreation(Airport.class, asf.getApFeatures().getId(), asf.getApFeatures(), asf.getAirportinit()));
		
	}
	
	int nbAirplanes;
	class beginArriveAirplane extends SimEvent{
		@Override
		public void Process() {
			LogicalDateTime d = getNextTimeAirplane();
			if (d!= null) Post(new NewAirplaneEvent(),d);			
			Post(new endArriveAirplane(), getCurrentLogicalDate().truncateToDays().add(endFlightTime));
			Logger.Information(this.Owner(), "beginArriveAirplane", Messages.BeginPeriodNewAirplane);
		}
	}
	
	class NewAirplaneEvent extends SimEvent {
		int lasttimeNextAirplane;
		@Override
		public void Process() {
			AirplaneFeature af ;
			af=new AirplaneFeature("A"+nbAirplanes++,StatusAirplane.Arrive);
			
			Airplane air=(Airplane)createChild(getEngine(),Airplane.class, af.getId(), af);
			
			air.Initialize(new AirplaneInit());
			Logger.Information(this.Owner(), "NewAirplane", Messages.NewAirplane);
			LogicalDateTime d = getNextTimeAirplane();
			if(d!=null) Post(new NewAirplaneEvent(),d);
			//System.out.println(air.getStatus());
			air.activate();		
		}

	}
	
	class endArriveAirplane extends SimEvent {
		@Override
		public void Process() {			
			System.out.println("==========="+getCurrentLogicalDate().truncateToDays()+"==================");
			LogicalDateTime d=getCurrentLogicalDate().truncateToDays().add(LogicalDuration.ofDay(1).add(beginFlightTime));
			setCurrentDateTime(d);
			Post(new beginArriveAirplane(),d);			
			Logger.Information(this.Owner(), "endArriveAirplane", Messages.EndPeriodNewAirplane);
		}		
	}
	
	
	private class VerifDistribRecord implements IRecordable{
		double d;
		public VerifDistribRecord(double d){
			this.d = d;
		}
		@Override
		public String[] getTitles() {
			return new String[]{"delta","Categorie"};
			
		}

		@Override
		public String[] getRecords() {
			//arrivalDelayRecordingCatGen.getSegmentOf(d).toString();
			return new String[]{Double.toString(d/60), arrivalDelayRecordingCatGen.getSegmentOf(d).toString()};
		}

		@Override
		public String getClassement() {
		
			return "NextAirplane";
		}
		
	}
	
	public double getFrequenceArriveAiplanePerHour(LogicalDateTime t){
		DayOfWeek d=t.getDayOfWeek();
		LogicalDuration hour = t.truncateToHours().soustract(t.truncateToDays());
		if ((d.toString()!= DayOfWeek.SATURDAY.toString()) && (d.toString()!= DayOfWeek.SUNDAY.toString())){
			return asf.getFrequenceArriveAirplanePerHour_inWeekEnd();
		}else{
			if ((hour.getHours()>6 && hour.getHours()<10) || (hour.getHours()>16 && hour.getHours()<19)){
				return asf.getFrequenceArriveAirplanePerHour_inBusyHour();
			}
			return asf.getFrequenceArriveAirplanePerHour_normal();
		}
	}
	
	LogicalDateTime getNextTimeAirplane() {
		double lamda = getFrequenceArriveAiplanePerHour(getCurrentDateTime())/3600;
		double d=random.nextExp(lamda);
		Logger.Data(new VerifDistribRecord(d));
		
		LogicalDuration t= LogicalDuration.ofSeconds(d);
		LogicalDateTime nextEndOfAirplaneArrival = getCurrentLogicalDate().truncateToDays().add(endFlightTime);
		
		LogicalDateTime possibleAirplaneArrival = getCurrentLogicalDate().add(t);
		if(possibleAirplaneArrival.compareTo(nextEndOfAirplaneArrival)<0)
			return possibleAirplaneArrival;
		else
			return null;
	}
	
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		super.AfterActivate(sender, starting);
		this.Post(new endArriveAirplane());
	}

}
