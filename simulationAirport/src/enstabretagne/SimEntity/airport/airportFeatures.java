/**
 * 
 */
package enstabretagne.SimEntity.airport;

import java.util.List;

import enstabretagne.SimEntity.airplane.airplaneFeature;
import enstabretagne.simulation.components.SimFeatures;

/**
 * @author zhengta
 *
 */
public class airportFeatures extends SimFeatures{

	/**
	 * 
	 */
	private int nbTerminal;
	private int nbGate;
	private int nbTrack;
	private List<airplaneFeature> airplaneFeatureslist;
	
	public airportFeatures(String id,
			int nbTerminal,
			int nbGate,
			int nbTrack, // piste
			List<airplaneFeature> airplaneFeatureslist) {
		super(id);
		this.nbTerminal = nbTerminal;
		this.nbGate = nbGate;
		this.nbTrack = nbTrack;
		this.airplaneFeatureslist = airplaneFeatureslist;
	}

}