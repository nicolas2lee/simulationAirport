package simulation.airport.SEntity.Airplane;

public enum AirplaneStatus {
	Depart("Depart"),
	Arrive("Arrive");
	private final String name;
	private AirplaneStatus(String name){
		this.name=name;
	}
}
