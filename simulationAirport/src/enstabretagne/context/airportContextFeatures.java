/**
 * 
 */
package enstabretagne.context;

import enstabretagne.SimEntity.airport.airportFeatures;
import enstabretagne.SimEntity.airport.airportInit;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;



/**
 * @author zhengta
 *
 */
public class airportContextFeatures extends SimFeatures{

	public String getBeginFlightTime() {
		return beginFlightTime;
	}
	public void setBeginFlightTime(String beginFlightTime) {
		this.beginFlightTime = beginFlightTime;
	}
	public String getEndFlightTime() {
		return endFlightTime;
	}
	public void setEndFlightTime(String endFlightTime) {
		this.endFlightTime = endFlightTime;
	}
	public airportFeatures getApFeatures() {
		return apFeatures;
	}
	public void setApFeatures(airportFeatures apFeatures) {
		this.apFeatures = apFeatures;
	}
	public airportInit getAirportinit() {
		return airportinit;
	}
	public void setAirportinit(airportInit airportinit) {
		this.airportinit = airportinit;
	}
	public CategoriesGenerator getArrivalDelayRecordingCatGen() {
		return arrivalDelayRecordingCatGen;
	}
	public void setArrivalDelayRecordingCatGen(
			CategoriesGenerator arrivalDelayRecordingCatGen) {
		this.arrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
	}
	public CategoriesGenerator getDelaiAttenteRecordingCatGen() {
		return delaiAttenteRecordingCatGen;
	}
	public void setDelaiAttenteRecordingCatGen(
			CategoriesGenerator delaiAttenteRecordingCatGen) {
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}
	private String beginFlightTime;
	private String endFlightTime;
	private airportFeatures apFeatures;
	private airportInit airportinit;
	private CategoriesGenerator arrivalDelayRecordingCatGen;
	private CategoriesGenerator delaiAttenteRecordingCatGen;
	
	
	public airportContextFeatures(String id, 
			String beginFlightTime,String endFlightTime, 
			airportFeatures apFeatures,
			airportInit airportinit, CategoriesGenerator arrivalDelayRecordingCatGen,CategoriesGenerator delaiAttenteRecordingCatGen) {
		super(id);
		
		this.beginFlightTime = beginFlightTime;
		this.endFlightTime = endFlightTime;
		this.apFeatures = apFeatures;
		this.airportinit = airportinit;
		this.arrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}


}