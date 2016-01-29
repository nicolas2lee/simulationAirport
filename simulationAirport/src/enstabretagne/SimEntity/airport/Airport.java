/**
 * 
 */
package enstabretagne.SimEntity.airport;
import java.util.HashMap;
import java.util.LinkedList;

import enstabretagne.SimEntity.airplane.Airplane;
import enstabretagne.SimEntity.airplane.AirplaneFeature;
import enstabretagne.base.math.MoreRandom;
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
	public LinkedList<Airplane> getWaitTrackList() {
		return waitTrackList;
	}


	public void setWaitTrackList(LinkedList<Airplane> waitTrackList) {
		this.waitTrackList = waitTrackList;
	}


	public LinkedList<Airplane> getWaitTw1List() {
		return waitTw1List;
	}


	public void setWaitTw1List(LinkedList<Airplane> waitTw1List) {
		this.waitTw1List = waitTw1List;
	}


	public LinkedList<Airplane> getWaitTw2List() {
		return waitTw2List;
	}


	public void setWaitTw2List(LinkedList<Airplane> waitTw2List) {
		this.waitTw2List = waitTw2List;
	}




	LinkedList<Airplane> waitTrackList;
	LinkedList<Airplane> waitTw1List;
	LinkedList<Airplane> waitTw2List;
	LinkedList<Airplane> waitGateList;
	LinkedList<Airplane> airInGateList;
	
	public LinkedList<Airplane> getAirInGateList() {
		return airInGateList;
	}


	public void setAirInGateList(LinkedList<Airplane> airInGateList) {
		this.airInGateList = airInGateList;
	}


	public LinkedList<Airplane> getWaitGateList() {
		return waitGateList;
	}


	public void setWaitGateList(LinkedList<Airplane> waitGateList) {
		this.waitGateList = waitGateList;
	}




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
	
	private boolean goodweather;

	
	public boolean isGoodweather() {
		return goodweather;
	}


	public void setGoodweather(boolean goodweather) {
		this.goodweather = goodweather;
	}


	private MoreRandom random;
	private int maxnumAir;
	private int nbgate;
	public int getNbgate() {
		return nbgate;
	}


	public void setNbgate(int nbgate) {
		this.nbgate = nbgate;
	}


	public Airport(SimEngine engine, String name, SimFeatures features) {

		super(engine, name, features);
		AirportFeatures af = (AirportFeatures) features;
		//open time and close time need to be added after
		initialAirplanes = new HashMap<>();
		random = new MoreRandom(MoreRandom.globalSeed);
		maxnumAir = af.getNbAirplaneMax();
		nbgate = af.getNbGate();
		waitTrackList = new LinkedList<>();
		waitTw1List = new LinkedList<>();
		waitTw2List = new LinkedList<>();
		waitGateList = new LinkedList<>();
		
		airInGateList = new LinkedList<>();
		
		TW1Full=false;
		TW2Full=false;
		trackFull=false; 
		
		goodweather=true;
		
		airportOpened = LogicalDuration.fromString(af.getOpenhour());
		airportClosed = LogicalDuration.fromString(af.getClosedhour());
		
		for (AirplaneFeature apf : af.getAirplaneFeatures() ){
			if (apf != null){
				Airplane air = (Airplane) createChild(engine, Airplane.class, apf.getAirplaneId().toString(), (SimFeatures)apf);
				initialAirplanes.put(air.getAirplaneId(), air);
			}
		}
		
	}
	
	public int getMaxnumAir() {
		return maxnumAir;
	}


	public void setMaxnumAir(int maxnumAir) {
		this.maxnumAir = maxnumAir;
	}


	public boolean isOpened(){
		return isOpened;
	}


	@Override
	public String[] getTitles() {
		String[] titles = {"weather",
				"Size of waiting Track list",
				"size of waiting Tw1 list",
				"size of waiting Gate list",
				"size of waiting Tw2 list"};
		return titles;
	}

	public String translateWeather(boolean isgoodweather){
		if (isgoodweather){
			return "good weather";
		}else
			return "bad weather";
	}
	
	@Override
	public String[] getRecords() {
		String[] records={translateWeather(isGoodweather()),
				Integer.toString(getWaitTrackList().size()),
				Integer.toString(getWaitTw1List().size()),
				Integer.toString(getWaitGateList().size()),
				Integer.toString(getWaitTw2List().size())};
		return records;
		//return null;
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
		Post(new CloseAirport());
		Post (new ObservationSizeOfFIFO());
		for (SimEntity a : getChildren()){
			//System.out.println(a.getStatus());
			a.activate();
		}
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean initWeather(){
		if (random.nextDouble() < 0.125){
			return false;
		}else{
			return true;
		}
	}
	
	class OpenAirport extends SimEvent{

		@Override
		public void Process() {
			setGoodweather(initWeather());
			isOpened=true;
			Post(new CloseAirport(), getCurrentLogicalDate().truncateToDays().add(airportClosed));
			Logger.Information(this.Owner(), "OpenAirport", Messages.OpenAirport);
		}
	}
	
	class CloseAirport extends SimEvent{

		@Override
		public void Process() {
			isOpened=false;
			LogicalDateTime tomorrow=getCurrentLogicalDate().truncateToDays().add(
					LogicalDuration.ofDay(1).add(airportOpened)
					);
			
			Post(new OpenAirport(), tomorrow);
			Logger.Information(this.Owner(), "ClosAirport", Messages.CloseAirport);
			
		}
		
	}
	
	class ObservationSizeOfFIFO extends SimEvent{
		@Override
		public void Process() {
			if (isOpened)
			Logger.Data((IRecordable) this.Owner());
			Post(this, getCurrentLogicalDate().add(LogicalDuration.ofMinutes(
					((AirportFeatures) getFeatures()).getFrequenceObservationSizeOfFIFO()
					)));
			
		}
		
	}
	
}
