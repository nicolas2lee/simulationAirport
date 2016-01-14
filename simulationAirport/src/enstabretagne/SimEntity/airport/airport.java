/**
 * 
 */
package enstabretagne.SimEntity.airport;
import java.util.HashMap;

import enstabretagne.SimEntity.airplane.airplane;
import enstabretagne.SimEntity.airplane.airplaneFeature;
import enstabretagne.SimEntity.airplane.airplaneIds;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
/**
 * @author nicolas2lee
 *
 */
public class airport extends SimEntity implements IRecordable {

	/**
	 * 
	 */
	HashMap<airplaneIds, airplane> listAirplanes;
	public airport(SimEngine engine, String name, SimFeatures features) {

		super(engine, name, features);
		airportFeatures af = (airportFeatures) features;
		//open time and close time need to be added after
		listAirplanes = new HashMap<>();
		for (airplaneFeature apf : af.getAirplaneFeatures() ){
			airplane air = (airplane) createChild(engine, airplane.class, apf.getAirplaneId().toString(), apf);
			listAirplanes.put(air.getAirplaneId(), air);
		}
		
	}


	@Override
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

}
