/**
 * 
 */
package enstabretagne.SimEntity.airplane;

import enstabretagne.simulation.components.SimFeatures;



/**
 * @author zhengta
 *
 */
public class airplaneFeature extends SimFeatures{

	airplaneIds airplaneId;
	StatutAirplane statutAirplane;
	
	
	
	public airplaneIds getAirplaneId() {
		return airplaneId;
	}

	public StatutAirplane getCoiffeurStatut(){
		return statutAirplane;
	}
	

	
	public airplaneFeature(airplaneIds airplaneId, StatutAirplane statut) {
		super(airplaneId.toString());
		this.airplaneId=airplaneId;
		
		this.statutAirplane=statut;
		
	}
}