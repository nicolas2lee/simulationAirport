/**
 * 
 */
package fr.enstabretagne.simu.td2;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimulationDateProvider;
import fr.enstabretagne.simu.td2.demo.SimEvent;

/**
 * @author zhengta
 *
 */
public class SimEngine implements ISimulationDateProvider {

	LogicalDateTime currentDateTime = new LogicalDateTime("18/11/2015 10:27:30.1234") ;
	private int aleanum = 0;
	private SimEvent evt = new SimEvent();
	
	/**
	 * 
	 */
	public SimEngine(int num) {
		// TODO Auto-generated constructor stub
		aleanum=num;
		
		if (evt.getEntityState() == EntityState.NONE){
			evt.setEntityState(EntityState.BORN);
			
		}else{
			System.out.println("You are trying to do from "+evt.getEntityState()+" BORNE");
		}
	}
	
	
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoreRandom alea = new MoreRandom();
		SimEngine sim = new SimEngine(alea.nextInt(6));
		
	
		
		/* test for random nuumber
		for (int i=0;i<10;i++)
			System.out.println(alea.nextInt(5));
		*/
	}

	@Override
	public LogicalDateTime SimulationDate() {
		return currentDateTime;
	}

}
