package enstabretagne.SimEntity.airplane;

public enum StateAirplane {
	Arriving("Arriving"),
	NotifyBeginArrive("NotifyBeginArrive"),
	WaitForTW1AndTrack("WaitForTW1AndTrack"),
	CloseToAirport("CloseToAirport"),
	Landing("Landing"),
	RollingToGate("RollingToGate"),
	NotifyEndArrive("NotifyEndArrive"),
	UnloadingPassagersAndPreparing("UnloadingPassagersAndPreparing"),
	LoadingPassagers("LoadingPassagers"),
	NotifyBeginDepart("NotifyBeginDepart"),
	WaitForTW2("WaitForTW2"),
	RollingToTrack("RollingToTrack"),
	WaitForTrackToDepart("WaitForTrackToDepart"),
	Takeoff("Takeoff"),
	NotifyEndDepart("NotifyEndDepart"),
	Departing("Departing");
	
	
	private String StateAirplane;
	private StateAirplane(String state){
		this.StateAirplane=state;
	}

}