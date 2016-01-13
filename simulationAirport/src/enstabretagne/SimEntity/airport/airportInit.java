/**
 * 
 */
package enstabretagne.SimEntity.airport;

import java.util.HashMap;

import enstabretagne.SimEntity.airplane.airplaneIds;
import enstabretagne.SimEntity.airplane.airplaneInit;
import enstabretagne.simulation.components.SimInitParameters;


/**
 * @author zhengta
 *
 */
public class airportInit extends SimInitParameters {

	/**
	 * 
	 */
	HashMap<airplaneIds, airplaneInit> airplanesinit;
	
	public HashMap<airplaneIds, airplaneInit> getAirplanesinit() {
		return airplanesinit;
	}
	
	public airportInit(HashMap<airplaneIds, airplaneInit> airplanesinit) {
		this.airplanesinit = airplanesinit;
	}

}