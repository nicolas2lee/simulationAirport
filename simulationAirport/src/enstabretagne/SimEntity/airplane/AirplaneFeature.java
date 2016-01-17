/**
 * 
 */
package enstabretagne.SimEntity.airplane;

import enstabretagne.simulation.components.SimFeatures;



/**
 * @author zhengta
 *
 */
public class AirplaneFeature extends SimFeatures{

	String airplaneId;
	StatusAirplane statusAirplane;
	
	
	
	public String getAirplaneId() {
		return airplaneId;
	}

	public StatusAirplane getCoiffeurStatut(){
		return statusAirplane;
	}
	

	
	public AirplaneFeature(String airplaneId, StatusAirplane statut) {
		super(airplaneId.toString());
		this.airplaneId=airplaneId;
		
		this.statusAirplane=statut;
		
	}
}
