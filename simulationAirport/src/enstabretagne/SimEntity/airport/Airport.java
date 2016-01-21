/**
 * 
 */
package enstabretagne.SimEntity.airport;
import java.util.HashMap;
import java.util.LinkedList;

import enstabretagne.SimEntity.airplane.Airplane;
import enstabretagne.SimEntity.airplane.AirplaneFeature;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.messages.Messages;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;
/**
 * @author nicolas2lee
 *
 */
public class Airport extends SimEntity implements IRecordable {

	/**
	 * 
	 */
	HashMap<String, Airplane> initialAirplanes;
	LinkedList<Airplane> FIFO;
	LogicalDuration airportOpened;
	LogicalDuration airportClosed;
	
	boolean TW1Full;
	boolean TW2Full;

	

	public boolean isTW2Full() {
		return TW2Full;
	}


	public void setTW2Full(boolean tW2Full) {
		TW2Full = tW2Full;
	}


	public boolean isTW1Full() {
		return TW1Full;
	}


	public void setTW1Full(boolean tW1Full) {
		TW1Full = tW1Full;
	}




	boolean trackFull;
	
	public boolean isTrackFull() {
		return trackFull;
	}


	public void setTrackFull(boolean trackFull) {
		this.trackFull = trackFull;
	}




	boolean isOpened;
	
	public LinkedList<Airplane> getFIFO() {
		return FIFO;
	}


	public void setFIFO(LinkedList<Airplane> fIFO) {
		FIFO = fIFO;
	}

	
	public Airport(SimEngine engine, String name, SimFeatures features) {

		super(engine, name, features);
		AirportFeatures af = (AirportFeatures) features;
		//open time and close time need to be added after
		initialAirplanes = new HashMap<>();
		FIFO = new LinkedList<>();
		
		TW1Full=false;
		TW2Full=false;
		trackFull=false; 
		
		airportOpened = LogicalDuration.fromString(af.getOpenhour());
		airportClosed = LogicalDuration.fromString(af.getClosedhour());
		
		for (AirplaneFeature apf : af.getAirplaneFeatures() ){
			if (apf != null){
				Airplane air = (Airplane) createChild(engine, Airplane.class, apf.getAirplaneId().toString(), (SimFeatures)apf);
				initialAirplanes.put(air.getAirplaneId(), air);
			}
		}
		
	}
	
	public boolean isOpened(){
		return isOpened;
	}


	@Override
	public String[] getTitles() {
		String[] titles = {"Size FIFO of wait airplanes"};
		return titles;
	}

	@Override
	public String[] getRecords() {
		String[] records={Integer.toString(getFIFO().size())};
		return records;
	}

	@Override
	public String getClassement() {
		return "Airport";
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		for (IEntity s : getChildren()){
			AirportInit si = (AirportInit) init;
		}
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		//Post(new ClosedAirport());
		//Post (new ObservationSizeOfFIFO());
		for (SimEntity a : getChildren()){
			//System.out.println(a.getStatus());
			a.activate();
		}
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}
	
	class OpenAirport extends SimEvent{

		@Override
		public void Process() {
			isOpened=true;
			Post(new CloseAirport(), getCurrentLogicalDate().truncateToDays().add(airportClosed));
			
		}
	}
	
	class CloseAirport extends SimEvent{

		@Override
		public void Process() {
			isOpened=false;
			LogicalDateTime tomorrow=getCurrentLogicalDate().truncateToDays().add(
					LogicalDuration.ofDay(1).add(airportOpened)
					);
			
			Post(new CloseAirport(), getCurrentLogicalDate().truncateToDays().add(airportClosed));
			Logger.Information(this.Owner(), "ClosAirport", Messages.CloseAirport);
			
		}
		
	}
	
	class ObservationSizeOfFIFO extends SimEvent{
		@Override
		public void Process() {
			//if (isOpened)
			Logger.Data((IRecordable) this.Owner());
			Post(this, getCurrentLogicalDate().add(LogicalDuration.ofMinutes(
					((AirportFeatures) getFeatures()).getFrequenceObservationSizeOfFIFO()
					)));
			
		}
		
	}
	
}
