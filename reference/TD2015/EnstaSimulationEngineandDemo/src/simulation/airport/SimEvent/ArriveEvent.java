package simulation.airport.SimEvent;

public class ArriveEvent {

	public class NotifBeginOfArrive extends SimEvent{

		@Override
		public void  process() {
			// TODO Auto-generated method stub
			System.out.println("test notif");
			
		}
		
	}
	
	public class WaitTrackAndTW extends SimEvent{
		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("test wait");
			
		}
	}
	
	public class NearToAirport extends SimEvent{
		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("nearToAirport");
			
		}
	}
	
	public class landing extends SimEvent{
		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("landing");
			
		}
	}
	
	public class Rolling extends SimEvent{
		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("rolling");
			
		}
	}
	
	public class NotifEndOfArrive extends SimEvent{
		@Override
		public void process() {
			// TODO Auto-generated method stub
			System.out.println("nearToAirport");
			
		}
	}

}