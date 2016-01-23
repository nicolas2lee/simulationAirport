package enstabretagne.SimEntity.airplane;

import java.util.List;

import enstabretagne.SimEntity.airport.Airport;
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
import enstabretagne.simulation.core.SimObject;
import enstabretagne.simulation.core.SimObjectRequest;

public class Airplane extends SimEntity implements IAirplane, IRecordable{

	MoreRandom random;
	String airplaneId ;
	StateAirplane currnetState;
	Airport myAirport;
	public Airport getMyAirport() {
		return myAirport;
	}

	public void setMyAirport(Airport myAirport) {
		this.myAirport = myAirport;
	}

	SimObjectRequest openedAirportRequest;
	SimObjectRequest airplaneClosedToAirportRequest;
	
	public Airplane(SimEngine engine, String name, SimFeatures features) {
		super(engine, name , features);
		
		random = new MoreRandom(MoreRandom.globalSeed);
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
	
	LogicalDateTime timeEntryWaitTrackList;
	LogicalDateTime timeOutWaitTrackList;
	private void setAirplaneState(StateAirplane state) {
		if (state.equals(StateAirplane.WaitForTW1AndTrack) ){
			timeEntryWaitTrackList=(LogicalDateTime) getCurrentLogicalDate().getCopy();
		}
		if (state.equals(StateAirplane.CloseToAirport)){
			timeOutWaitTrackList = (LogicalDateTime) getCurrentLogicalDate().getCopy();
		}
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
		setMyAirport(a);
		//System.out.println("==========================="+a+"======================");
		if (a!=null){
			setAirplaneState(StateAirplane.NotifyBeginArrive);
			Logger.Information(this, "NotifyBeginArrive", Messages.NotifyBeginArrive, this.getName());
			waitTrackAndTW1(a);
			return a;
			
		}else{
			Post(new Departing(),getCurrentLogicalDate());
			return null;
		}
	}
	
	@Override
	public void waitTrackAndTW1(Airport a) {
		if (a.isTrackFull() && a.isTW1Full()){
			setAirplaneState(StateAirplane.WaitForTW1AndTrack);
			a.getWaitTrackList().add(this);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(a.getWaitTrackList());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
			Logger.Information(this, "WaitForTW1AndTrack", Messages.WaitForTW1AndTrack, this.getName());
		
		}else{
			//a.getWaitTrackList().remove(this);
			closeToAirport(a);
		}
	}

	@Override
	public void closeToAirport(Airport a) {
		setAirplaneState(StateAirplane.CloseToAirport);
		a.setTrackFull(true);
		System.out.println("====================================");
		System.out.println("aiport's track is full "+a.isTrackFull());
		System.out.println("aiport's TW1 is full "+a.isTW1Full());
		System.out.println(a);
		System.out.println(this);
		System.out.println(a.getWaitTrackList());
		a.getWaitTrackList().remove(this);
		System.out.println(a.getWaitTrackList());
		System.out.println("====================================");
		Logger.Information(this, "CloseToAirport", Messages.CloseToAirport, this.getName());
		//how to add 2~5min 120~300s
		double d=random.nextDouble()*180+120;
		LogicalDuration t = LogicalDuration.ofSeconds(d);
		Post(new Landing(),getCurrentLogicalDate().add(t));

		//Logger.Information(this, function, message, args);
	}
	
	/*
	@Override
	public void landing(Airport a) {
		a.setTrackFull(true);
		setAirplaneState(StateAirplane.Landing);
		Logger.Information(this, "Landing", Messages.Landing, this.getName());
		rollingToGate(a);
	}*/
	class Landing extends SimEvent{
		//last 2 min
		@Override
		public void Process() {
			//getMyAirport().setTrackFull(true);
			setAirplaneState(StateAirplane.Landing);
			LogicalDuration t = LogicalDuration.ofSeconds(120);
			Post(new RollingToGate(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "Landing", Messages.Landing);
			getMyAirport().setTrackFull(true);
			if (getMyAirport().getWaitTrackList().size()>0){
				Airplane air=getMyAirport().getWaitTrackList().getFirst();
				air.closeToAirport(getMyAirport());
			}
		}
		
	}
	/*
	@Override
	public void rollingToGate(Airport a) {
		//need to check if gate is full
		a.setTW1Full(true);
		setAirplaneState(StateAirplane.RollingToGate);
		Logger.Information(this, "RollingToGate", Messages.RollingToGate, this.getName());
		notifyEndArrive(a);
	}*/
	class RollingToGate extends SimEvent{
		//last 2~6 mins
		@Override
		public void Process() {
			getMyAirport().setTW1Full(true);
			setAirplaneState(StateAirplane.RollingToGate);
			double d=random.nextDouble()*240+120;
			LogicalDuration t = LogicalDuration.ofSeconds(d);
			Post(new UnloadingPassagersAndPreparing(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "RollingToGate", Messages.RollingToGate);
			
		}
		
	}
	/*
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
	}*/
	class UnloadingPassagersAndPreparing extends SimEvent{
		//last 10+30 min
		@Override
		public void Process() {
			getMyAirport().setTW1Full(false);;
			setAirplaneState(StateAirplane.NotifyEndArrive);
			Logger.Information(this.Owner(), "NotifyEndArrive", Messages.NotifyEndArrive);
			setAirplaneState(StateAirplane.UnloadingPassagersAndPreparing);
			LogicalDuration t = LogicalDuration.ofSeconds(40*60);
			Post(new loadingPassagers(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "UnloadingPassagersAndPreparing", Messages.UnloadingPassagersAndPreparing);
			
		}
		
	}
	/*
	@Override
	public void loadingPassagers(Airport a) {
		setAirplaneState(StateAirplane.LoadingPassagers);
		Logger.Information(this, "LoadingPassagers", Messages.LoadingPassagers, this.getName());
		notifyBeginDepart(a);
	} 
	*/
	class loadingPassagers extends SimEvent{
		//last 20 mins
		@Override
		public void Process() {
			setAirplaneState(StateAirplane.LoadingPassagers);
			LogicalDuration t = LogicalDuration.ofSeconds(20*60);
			//Post(new Landing(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "LoadingPassagers", Messages.LoadingPassagers);
		}
	}
	/*
	@Override
	public void notifyBeginDepart(Airport a) {
		setAirplaneState(StateAirplane.NotifyBeginDepart);
		Logger.Information(this, "LoadingPassagers", Messages.LoadingPassagers, this.getName());
		
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
	}*/
	@Override
	public boolean WaitForTW2(Airport a) {
		if (a.isTW2Full()){
			setAirplaneState(StateAirplane.WaitForTW2);
			a.getWaitTw2List().add(this);
			Logger.Information(this, "WaitForTW2", Messages.WaitForTW2, this.getName());
			return true;
		}else{
			return false;
		}
		
	}
	/*
	@Override
	public void waitForTrackToDepart(Airport a) {
		setAirplaneState(StateAirplane.WaitForTrackToDepart);
		Logger.Information(this, "WaitForTrackToDepart", Messages.WaitForTrackToDepart, this.getName());
	}*/
	
	class RollingToTrack extends SimEvent{
		//rolling last 2 mins
		@Override
		public void Process() {
			setAirplaneState(StateAirplane.NotifyBeginDepart);
			Logger.Information(this, "NotifyBeginDepart", Messages.NotifyBeginDepart);
			if (!WaitForTW2(getMyAirport())){
				if (getMyAirport().isTrackFull()){
					setAirplaneState(StateAirplane.WaitForTrackToDepart);
					Logger.Information(this, "WaitForTrackToDepart", Messages.WaitForTrackToDepart);
				}else{
					LogicalDuration t = LogicalDuration.ofSeconds(120);
					Post(new Takeoff(),getCurrentLogicalDate().add(t));
				}
			}
		}
	}
	
	/*
	@Override
	public void takeoff(Airport a) {
		a.setTrackFull(true);
		setAirplaneState(StateAirplane.Takeoff);
		Logger.Information(this, "Takeoff", Messages.Takeoff, this.getName());
		notifyEndDepart(a);
	}*/
	class Takeoff extends SimEvent{
		//last 3 mins
		@Override
		public void Process() {
			setAirplaneState(StateAirplane.Takeoff);
			getMyAirport().setTrackFull(true);
			LogicalDuration t = LogicalDuration.ofSeconds(3*60);
			Post(new Departing(),getCurrentLogicalDate().add(t));
			Logger.Information(this, "Takeoff", Messages.Takeoff);
		}
	}
	
	@Override
	public void notifyEndDepart(Airport a) {
		setAirplaneState(StateAirplane.Takeoff);
		Logger.Information(this, "Takeoff", Messages.Takeoff, this.getName());
		//departing(a);
	}
/*
	@Override
	public void departing(Airport a) {
		setAirplaneState(StateAirplane.Departing);
		Logger.Information(this, "Departing", Messages.Departing, this.getName());
		deactivate();
		
	}*/
	class Departing extends SimEvent{

		@Override
		public void Process() {
			setAirplaneState(StateAirplane.Departing);
			Logger.Information(this, "Departing", Messages.Departing);
			deactivate();
		}
		
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
