package enstabretagne.SimEntity.airplane;

import java.util.List;

import enstabretagne.SimEntity.airport.Airport;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.messages.Messages;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimObject;
import enstabretagne.simulation.core.SimObjectRequest;

public class Airplane extends SimEntity implements IAirplane, IRecordable{

	String airplaneId ;
	StateAirplane currnetState;
	SimObjectRequest openedAirportRequest;
	SimObjectRequest airplaneClosedToAirportRequest;
	
	public Airplane(SimEngine engine, String name, SimFeatures features) {
		super(engine, name , features);
		
		setAirplaneState(StateAirplane.Arriving);
		openedAirportRequest = new SimObjectRequest(){

			@Override
			public boolean filter(SimObject o) {
				if (Airport.class.isAssignableFrom(o.getClass())){
					Airport a = (Airport) o;
					if (a.isOpened()){
						return true;
					}
				}
				return false;	
			}
		};
		
		airplaneClosedToAirportRequest = new SimObjectRequest(){

			@Override
			public boolean filter(SimObject o) {
				if (Airplane.class.isAssignableFrom(o.getClass())){
					Airplane a = (Airplane) o;
					if (a.getCurrnetState() == StateAirplane.CloseToAirport){
						return true;
					}
				}
				return false;	
			}
			
		};
	}
	
	private void setAirplaneState(StateAirplane state) {
		currnetState = state;
	}

	public StateAirplane getCurrnetState() {
		return currnetState;
	}

	public String getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(String airplaneId) {
		this.airplaneId = airplaneId;
	}

	

	@Override
	public String[] getTitles() {
		String[] titles={"Wait time"};
		return titles;
	}

	@Override
	public String[] getRecords() {
		String[] rec;
		rec= new String[]{""};
		return null;
	}

	@Override
	public String getClassement() {
		
		return "Airplane";
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		arriveAirport();
		
	}


	private Airport findOpendAirport(){
		List<SimObject> a = getEngine().requestSimObject(openedAirportRequest);
		if (a.size()> 0){
			return (Airport) a.get(0);
		}else
			return null;
	}
	
	@Override
	public Airport arriveAirport() {
		Airport a = findOpendAirport();
		if (a!=null){
			setAirplaneState(StateAirplane.NotifyBeginArrive);
			Logger.Information(this, "NotifyBeginArrive", Messages.NotifyBeginArrive, this.getName());
			
			waitTrackAndTW1(a);
			return a;
			
		}else{
			return null;
		}
	}
	
	@Override
	public void waitTrackAndTW1(Airport a) {
		
		while (a.isTrackFull() && a.isTW1Full()){
			setAirplaneState(StateAirplane.WaitForTW1AndTrack);
			Logger.Information(this, "WaitForTW1AndTrack", Messages.WaitForTW1AndTrack, this.getName());
			//return true;
			//need to add wait time 
			//waitAPeriod();
		}
		closeToAirport(a);
	}

	@Override
	public void closeToAirport(Airport a) {
		setAirplaneState(StateAirplane.CloseToAirport);
		Logger.Information(this, "CloseToAirport", Messages.CloseToAirport, this.getName());
		landing(a);
	}
	
	@Override
	public void landing(Airport a) {
		a.setTrackFull(true);
		setAirplaneState(StateAirplane.Landing);
		Logger.Information(this, "Landing", Messages.Landing, this.getName());
		rollingToGate(a);
	}
	
	@Override
	public void rollingToGate(Airport a) {
		//need to check if gate is full
		a.setTW1Full(true);
		setAirplaneState(StateAirplane.RollingToGate);
		Logger.Information(this, "RollingToGate", Messages.RollingToGate, this.getName());
		notifyEndArrive(a);
	}

	@Override
	public void notifyEndArrive(Airport a) {
		// TODO Auto-generated method stub
		a.setTW1Full(false);
		setAirplaneState(StateAirplane.NotifyEndArrive);
		Logger.Information(this, "NotifyEndArrive", Messages.NotifyEndArrive, this.getName());
		UnloadingPassagersAndPreparing(a);
	}
	
	@Override
	public void UnloadingPassagersAndPreparing(Airport a) {
		setAirplaneState(StateAirplane.UnloadingPassagersAndPreparing);
		Logger.Information(this, "UnloadingPassagersAndPreparing", Messages.UnloadingPassagersAndPreparing, this.getName());
		loadingPassagers(a);
	}
	
	@Override
	public void loadingPassagers(Airport a) {
		setAirplaneState(StateAirplane.LoadingPassagers);
		Logger.Information(this, "LoadingPassagers", Messages.LoadingPassagers, this.getName());
		notifyBeginDepart(a);
	}

	@Override
	public void notifyBeginDepart(Airport a) {
		setAirplaneState(StateAirplane.NotifyBeginDepart);
		Logger.Information(this, "LoadingPassagers", Messages.LoadingPassagers, this.getName());
		
	}

	@Override
	public void WaitForTW2(Airport a) {
		while (a.isTW2Full()){
			setAirplaneState(StateAirplane.WaitForTW2);
			Logger.Information(this, "WaitForTW2", Messages.WaitForTW2, this.getName());
		}
		rollingToTrack(a);
		
	}

	public boolean findClosedAirplanes(){
		List<SimObject> a = getEngine().requestSimObject(openedAirportRequest);
		if (a.size()> 0){
			return true;
		}else
			return false;
	}
	
	@Override
	public void rollingToTrack(Airport a) {
		a.setTW2Full(true);
		setAirplaneState(StateAirplane.RollingToTrack);
		Logger.Information(this, "RollingToTrack", Messages.RollingToTrack, this.getName());
		while(a.isTrackFull() && findClosedAirplanes()){
			waitForTrackToDepart(a);
		}
		takeoff(a);
	}
	
	@Override
	public void waitForTrackToDepart(Airport a) {
		setAirplaneState(StateAirplane.WaitForTrackToDepart);
		Logger.Information(this, "WaitForTrackToDepart", Messages.WaitForTrackToDepart, this.getName());
	}

	@Override
	public void takeoff(Airport a) {
		a.setTrackFull(true);
		setAirplaneState(StateAirplane.Takeoff);
		Logger.Information(this, "Takeoff", Messages.Takeoff, this.getName());
		notifyEndDepart(a);
	}
	
	@Override
	public void notifyEndDepart(Airport a) {
		setAirplaneState(StateAirplane.Takeoff);
		Logger.Information(this, "Takeoff", Messages.Takeoff, this.getName());
		
		setAirplaneState(StateAirplane.Departing);
		Logger.Information(this, "Departing", Messages.Departing, this.getName());
		
	}




	
	@Override
	public StatusAirplane getStatusAirplane() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}











	





	

}
