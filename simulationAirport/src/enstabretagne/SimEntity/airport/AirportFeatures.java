/**
 * 
 */
package enstabretagne.SimEntity.airport;

import java.util.List;

import enstabretagne.SimEntity.airplane.AirplaneFeature;
import enstabretagne.simulation.components.SimFeatures;

/**
 * @author zhengta
 *
 */
public class AirportFeatures extends SimFeatures{

	/**
	 * 
	 */
	private int nbTerminal;
	private int nbGate;
	public int getNbGate() {
		return nbGate;
	}

	public void setNbGate(int nbGate) {
		this.nbGate = nbGate;
	}

	private int nbTrack;
	private String openhour;
	
	public String getOpenhour() {
		return openhour;
	}

	public String getClosedhour() {
		return closedhour;
	}

	private String closedhour;
	int frequenceObservationSizeOfFIFO;
	
	private int nbAirplaneMax;
	public int getNbAirplaneMax() {
		return nbAirplaneMax;
	}

	public void setNbAirplaneMax(int nbAirplaneMax) {
		this.nbAirplaneMax = nbAirplaneMax;
	}

	private List<AirplaneFeature> airplaneFeatureslist;
	
	public List<AirplaneFeature> getAirplaneFeatures() {
		return airplaneFeatureslist;
	}



	public void setAirplaneFeatures(List<AirplaneFeature> AirplaneFeatures) {
		this.airplaneFeatureslist = AirplaneFeatures;
	}

	public List<AirplaneFeature> getAirplaneFeatureslist() {
		return airplaneFeatureslist;
	}



	public void setAirplaneFeatureslist(List<AirplaneFeature> airplaneFeatureslist) {
		this.airplaneFeatureslist = airplaneFeatureslist;
	}



	public int getFrequenceObservationSizeOfFIFO() {
		return frequenceObservationSizeOfFIFO;
	}



	public void setFrequenceObservationSizeOfFIFO(int frequenceObservationSizeOfFIFO) {
		this.frequenceObservationSizeOfFIFO = frequenceObservationSizeOfFIFO;
	}

	public AirportFeatures(String id,
			int frequenceObservationSizeOfFIFO,
			int nbAirplaneMax,
			int nbTerminal,
			int nbGate,
			int nbTrack, // piste
			String openhour,
			String closedhour,
			List<AirplaneFeature> airplaneFeatureslist) {
		super(id);
		this.nbTerminal = nbTerminal;
		this.nbGate = nbGate;
		this.nbTrack = nbTrack;
		this.airplaneFeatureslist = airplaneFeatureslist;
		this.openhour=openhour;
		this.closedhour=closedhour;
		this.frequenceObservationSizeOfFIFO = frequenceObservationSizeOfFIFO;
	}

}
