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
					if (a.getCurrentState() == StateAirplane.CloseToAirport){
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

	public StateAirplane getCurrentState() {
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
		String[] titles={"tw2full"};
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
			System.out.println("-------------------------"+a.isTW2Full()+"---------------------------");
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
			Logger.Information(this, "WaitForTW1AndTrack", Messages.WaitForTW1AndTrack, this.getName());	
		}else{
			closeToAirport(a);
		}
	}

	@Override
	public void closeToAirport(Airport a) {
		setAirplaneState(StateAirplane.CloseToAirport);
		a.setTrackFull(true);
		//how to add 2~5min 120~300s
		double d=random.nextDouble()*180+120;
		LogicalDuration t = LogicalDuration.ofSeconds(d);
		Post(new Landing(),getCurrentLogicalDate().add(t));
		Logger.Information(this, "CloseToAirport", Messages.CloseToAirport, this.getName());
	}
	
	@Override
	public void WaitForTW1(Airport a) {
		setAirplaneState(StateAirplane.WaitForTW1);
		a.getWaitTw1List().add(this);
		Logger.Information(this, "WaitForTW1", Messages.WaitForTW1, this.getName());
	}

	
	class Landing extends SimEvent{
		//last 2 min
		@Override
		public void Process() {
			//getMyAirport().setTrackFull(true);
			setAirplaneState(StateAirplane.Landing);
			// track need to be setted true(done in closeToAirport)
			LogicalDuration t = LogicalDuration.ofSeconds(120);
			Post(new RollingToGate(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "Landing", Messages.Landing);
		}
		
	}

	class RollingToGate extends SimEvent{
		//last 2~6 mins
		@Override
		public void Process() {
			getMyAirport().setTrackFull(false);
			if (getMyAirport().getWaitTrackList().size()>0){
				Airplane air=getMyAirport().getWaitTrackList().getFirst();
				getMyAirport().getWaitTrackList().remove(air);
				System.out.println("=============== it is in rolling to gate " + air.getCurrentState()+"the air is "+air+"============");
				if (air.getCurrentState()==StateAirplane.WaitForTrackToDepart){
					air.Post(air.new Takeoff(), getCurrentLogicalDate());
				}else{
					if (air.getCurrentState() == StateAirplane.WaitForTW1AndTrack){
						air.closeToAirport(getMyAirport());
					}
				}	
			}
			getMyAirport().setTW1Full(true);
			setAirplaneState(StateAirplane.RollingToGate);
			double d=random.nextDouble()*240+120;
			LogicalDuration t = LogicalDuration.ofSeconds(d);
			Post(new UnloadingPassagersAndPreparing(),getCurrentLogicalDate().add(t));
			Logger.Information(this.Owner(), "RollingToGate", Messages.RollingToGate);
		}
	}

	class UnloadingPassagersAndPreparing extends SimEvent{
		//last 10+30 min
		@Override
		public void Process() {
			getMyAirport().setTW1Full(false);
			if (getMyAirport().getWaitTw1List().size()>0){
				Airplane air=getMyAirport().getWaitTw1List().getFirst();
				System.out.println("========== the first air in Tw1list is "+air+"====================");
				getMyAirport().getWaitTw1List().remove(air);
				System.out.println("=================It is in unloading passagers"+getMyAirport().getWaitTw1List()+"============");
				air.Post(air.new RollingToGate(), getCurrentLogicalDate());	
			}

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
			Post(new RollingToTrack(),getCurrentLogicalDate().add(t));
			System.out.println("getWaitTrackList in loading passagers"+getMyAirport().getWaitTrackList());
			Logger.Information(this.Owner(), "LoadingPassagers", Messages.LoadingPassagers);
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	@Override
	public void WaitForTW2(Airport a) {
		setAirplaneState(StateAirplane.WaitForTW2);
		a.getWaitTw2List().add(this);
		Logger.Information(this, "WaitForTW2", Messages.WaitForTW2, this.getName());

	}
	
	class RollingToTrack extends SimEvent{
		//rolling last 2 mins
		@Override
		public void Process() {
			System.out.println("++++++++++++++++++++ enter rolling to track +++++++++++++++++++++++");
			if (getCurrentState()!= StateAirplane.WaitForTW2){
				setAirplaneState(StateAirplane.NotifyBeginDepart);
				Logger.Information(this.Owner(), "NotifyBeginDepart", Messages.NotifyBeginDepart);
			}
			System.out.println(getMyAirport().isTW2Full());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if (getMyAirport().isTW2Full()== false){
				setAirplaneState(StateAirplane.RollingToTrack);
				getMyAirport().setTW2Full(true);
				LogicalDuration t = LogicalDuration.ofSeconds(120);
				Post(new Takeoff(),getCurrentLogicalDate().add(t));
				Logger.Information(this.Owner(), "RollingToTrack", Messages.RollingToTrack);
			}else{
				WaitForTW2(getMyAirport());
			}
		}
	}
	
	public void WaitForTrack(Airport a){
		a.getWaitTrackList().add(this);
		setAirplaneState(StateAirplane.WaitForTrackToDepart);
		Logger.Information(this, "WaitForTrack", Messages.WaitForTrackToDepart, this.getName());
	}
	
	class Takeoff extends SimEvent{
		//last 3 mins
		@Override
		public void Process() {
			System.out.println("###################### Taking off#############################");
			if (getMyAirport().isTrackFull()){
				WaitForTrack(getMyAirport());
			}else{
				getMyAirport().setTW2Full(false);
				if (getMyAirport().getWaitTw2List().size()>0){
					Airplane air=getMyAirport().getWaitTw2List().getFirst();
					getMyAirport().getWaitTw2List().remove(air);
					//air.getMyAirport().setTW2Full(false);
					air.Post(air.new RollingToTrack(), getCurrentLogicalDate());
				}
				setAirplaneState(StateAirplane.Takeoff);
				getMyAirport().setTrackFull(true);
				LogicalDuration t = LogicalDuration.ofSeconds(3*60);
				Post(new Departing(),getCurrentLogicalDate().add(t));
				Logger.Information(this.Owner(), "Takeoff", Messages.Takeoff);
			}
		}
	}
	
	@Override
	public void notifyEndDepart(Airport a) {
		setAirplaneState(StateAirplane.Takeoff);
		Logger.Information(this, "Takeoff", Messages.Takeoff, this.getName());
		//departing(a);
	}

	class Departing extends SimEvent{
		@Override
		public void Process() {
			notifyEndDepart(getMyAirport());
			getMyAirport().setTrackFull(false);
			if (getMyAirport().getWaitTrackList().size()>0){
				Airplane air= getMyAirport().getWaitTrackList().getFirst();
				if (air.getCurrentState()==StateAirplane.WaitForTrackToDepart){
					air.Post(air.new Takeoff(), getCurrentLogicalDate());
				}else{
					if (air.getCurrentState() == StateAirplane.WaitForTW1AndTrack){
						air.closeToAirport(getMyAirport());
					}
				}
			}
			setAirplaneState(StateAirplane.Departing);
			Logger.Information(this.Owner(), "Departing", Messages.Departing);
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
