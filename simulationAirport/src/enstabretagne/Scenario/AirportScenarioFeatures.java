/**
 * 
 */
package enstabretagne.Scenario;

import enstabretagne.SimEntity.airport.AirportFeatures;
import enstabretagne.SimEntity.airport.AirportInit;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;



/**
 * @author zhengta
 *
 */
public class AirportScenarioFeatures extends SimFeatures{

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
	public AirportFeatures getApFeatures() {
		return apFeatures;
	}
	public void setApFeatures(AirportFeatures apFeatures) {
		this.apFeatures = apFeatures;
	}
	public AirportInit getAirportinit() {
		return airportinit;
	}
	public void setAirportinit(AirportInit airportinit) {
		this.airportinit = airportinit;
	}


	private String beginFlightTime;
	private String endFlightTime;
	private AirportFeatures apFeatures;
	private AirportInit airportinit;
	
	public CategoriesGenerator getArrivalDelayRecordingCatGen7_10() {
		return arrivalDelayRecordingCatGen7_10;
	}
	public void setArrivalDelayRecordingCatGen7_10(
			CategoriesGenerator arrivalDelayRecordingCatGen7_10) {
		this.arrivalDelayRecordingCatGen7_10 = arrivalDelayRecordingCatGen7_10;
	}
	public CategoriesGenerator getArrivalDelayRecordingCatGen10_17() {
		return arrivalDelayRecordingCatGen10_17;
	}
	public void setArrivalDelayRecordingCatGen10_17(
			CategoriesGenerator arrivalDelayRecordingCatGen10_17) {
		this.arrivalDelayRecordingCatGen10_17 = arrivalDelayRecordingCatGen10_17;
	}
	public CategoriesGenerator getArrivalDelayRecordingCatGen17_19() {
		return arrivalDelayRecordingCatGen17_19;
	}
	public void setArrivalDelayRecordingCatGen17_19(
			CategoriesGenerator arrivalDelayRecordingCatGen17_19) {
		this.arrivalDelayRecordingCatGen17_19 = arrivalDelayRecordingCatGen17_19;
	}
	public CategoriesGenerator getArrivalDelayRecordingCatGen19_22() {
		return arrivalDelayRecordingCatGen19_22;
	}
	public void setArrivalDelayRecordingCatGen19_22(
			CategoriesGenerator arrivalDelayRecordingCatGen19_22) {
		this.arrivalDelayRecordingCatGen19_22 = arrivalDelayRecordingCatGen19_22;
	}
	public CategoriesGenerator getArrivalDelayRecordingCatGenWeekend() {
		return arrivalDelayRecordingCatGenWeekend;
	}
	public void setArrivalDelayRecordingCatGenWeekend(
			CategoriesGenerator arrivalDelayRecordingCatGenWeekend) {
		this.arrivalDelayRecordingCatGenWeekend = arrivalDelayRecordingCatGenWeekend;
	}


	private CategoriesGenerator arrivalDelayRecordingCatGen7_10;
	private CategoriesGenerator arrivalDelayRecordingCatGen10_17;
	private CategoriesGenerator arrivalDelayRecordingCatGen17_19;
	private CategoriesGenerator arrivalDelayRecordingCatGen19_22;
	private CategoriesGenerator arrivalDelayRecordingCatGenWeekend;
	
	
	private double frequenceArriveAirplanePerHour_normal;
	private double frequenceArriveAirplanePerHour_inBusyHour;
	private double frequenceArriveAirplanePerHour_inWeekEnd;
	private int coefDelayBadWeather;
	
	public double getFrequenceArriveAirplanePerHour_normal() {
		return frequenceArriveAirplanePerHour_normal;
	}
	public void setFrequenceArriveAirplanePerHour_normal(
			double frequenceArriveAirplanePerHour_normal) {
		this.frequenceArriveAirplanePerHour_normal = frequenceArriveAirplanePerHour_normal;
	}
	public double getFrequenceArriveAirplanePerHour_inBusyHour() {
		return frequenceArriveAirplanePerHour_inBusyHour;
	}
	public void setFrequenceArriveAirplanePerHour_inBusyHour(
			double frequenceArriveAirplanePerHour_inBusyHour) {
		this.frequenceArriveAirplanePerHour_inBusyHour = frequenceArriveAirplanePerHour_inBusyHour;
	}
	public double getFrequenceArriveAirplanePerHour_inWeekEnd() {
		return frequenceArriveAirplanePerHour_inWeekEnd;
	}
	public void setFrequenceArriveAirplanePerHour_inWeekEnd(
			double frequenceArriveAirplanePerHour_inWeekEnd) {
		this.frequenceArriveAirplanePerHour_inWeekEnd = frequenceArriveAirplanePerHour_inWeekEnd;
	}
	public int getCoefDelayBadWeather() {
		return coefDelayBadWeather;
	}
	public void setCoefDelayBadWeather(int coefDelayBadWeather) {
		this.coefDelayBadWeather = coefDelayBadWeather;
	}

	
	
	public AirportScenarioFeatures(String id, 
			String beginFlightTime,String endFlightTime,
			double frequenceArriveAirplanePerHour_normal,
			double frequenceArriveAirplanePerHour_inBusyHour,
			double frequenceArriveAirplanePerHour_inWeekEnd,
			int coefDelayBadWeather,
			AirportFeatures apFeatures,
			AirportInit airportinit, 
			CategoriesGenerator arrivalDelayRecordingCatGen7_10,
			CategoriesGenerator arrivalDelayRecordingCatGen10_17,
			CategoriesGenerator arrivalDelayRecordingCatGen17_19,
			CategoriesGenerator arrivalDelayRecordingCatGen19_22,
			CategoriesGenerator arrivalDelayRecordingCatGenWeekend) {
		super(id);
		
		this.beginFlightTime = beginFlightTime;
		this.endFlightTime = endFlightTime;
		this.frequenceArriveAirplanePerHour_normal= frequenceArriveAirplanePerHour_normal;
		this.frequenceArriveAirplanePerHour_inBusyHour=frequenceArriveAirplanePerHour_inBusyHour;
		this.frequenceArriveAirplanePerHour_inWeekEnd=frequenceArriveAirplanePerHour_inWeekEnd;
		this.coefDelayBadWeather=coefDelayBadWeather;
		this.apFeatures = apFeatures;
		this.airportinit = airportinit;
		this.arrivalDelayRecordingCatGen7_10 = arrivalDelayRecordingCatGen7_10;
		this.arrivalDelayRecordingCatGen10_17 = arrivalDelayRecordingCatGen10_17;
		this.arrivalDelayRecordingCatGen17_19 = arrivalDelayRecordingCatGen17_19;
		this.arrivalDelayRecordingCatGen19_22 = arrivalDelayRecordingCatGen19_22;
		this.arrivalDelayRecordingCatGenWeekend = arrivalDelayRecordingCatGenWeekend;
		//this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}


}
