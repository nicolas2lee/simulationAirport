/**
 * 
 */
package enstabretagne.Scenario;

import enstabretagne.SimEntity.airport.airportFeatures;
import enstabretagne.SimEntity.airport.airportInit;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;



/**
 * @author zhengta
 *
 */
public class airportScenarioFeatures extends SimFeatures{

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
	private double frequenceArriveAirplanePerHour_normal;
	private double frequenceArriveAirplanePerHour_inBusyHour;
	private double frequenceArriveAirplanePerHour_inWeekEnd;
	private int coefDelayBadWeather;
	
	
	public airportScenarioFeatures(String id, 
			String beginFlightTime,String endFlightTime,
			double frequenceArriveAirplanePerHour_normal,
			double frequenceArriveAirplanePerHour_inBusyHour,
			double frequenceArriveAirplanePerHour_inWeekEnd,
			int coefDelayBadWeather,
			airportFeatures apFeatures,
			airportInit airportinit, CategoriesGenerator arrivalDelayRecordingCatGen) {
		super(id);
		
		this.beginFlightTime = beginFlightTime;
		this.endFlightTime = endFlightTime;
		this.frequenceArriveAirplanePerHour_normal= frequenceArriveAirplanePerHour_normal;
		this.frequenceArriveAirplanePerHour_inBusyHour=frequenceArriveAirplanePerHour_inBusyHour;
		this.frequenceArriveAirplanePerHour_inWeekEnd=frequenceArriveAirplanePerHour_inWeekEnd;
		this.coefDelayBadWeather=coefDelayBadWeather;
		this.apFeatures = apFeatures;
		this.airportinit = airportinit;
		this.arrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
		//this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}


}