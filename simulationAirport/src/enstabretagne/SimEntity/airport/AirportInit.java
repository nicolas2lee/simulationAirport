/**
 * 
 */
package enstabretagne.SimEntity.airport;

import java.util.HashMap;

import enstabretagne.SimEntity.airplane.AirplaneInit;
import enstabretagne.simulation.components.SimInitParameters;


/**
 * @author zhengta
 *
 */
public class AirportInit extends SimInitParameters {

	/**
	 * 
	 */
	HashMap<String, AirplaneInit> initialAirplanesinit;
	
	public HashMap<String, AirplaneInit> getAirplanesinit() {
		return initialAirplanesinit;
	}
	
	public AirportInit(HashMap<String, AirplaneInit> airplanesinit) {
		this.initialAirplanesinit = airplanesinit;
	}

}
