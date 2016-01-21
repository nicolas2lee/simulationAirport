package simulation.airport.SEntity.Airplane;



import simulation.airport.SimEngine;
import simulation.airport.SimEntity;
import simulation.airport.SimEvent.SimEvent;


public class Airplane extends SimEntity {

	private String id;
	private AirplaneStatus status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Airplane(SimEngine engine,String id, AirplaneStatus status) {
		super(engine);
		this.id=id;
		this.status=status;
	}
	
	public static class NotifDepart extends SimEvent{

		

		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("Notif");
		}
		
	
}


}