package enstabretagne.SimEntity.airplane;

import enstabretagne.base.utility.IRecordable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;

public class airplane extends SimEntity implements IRecordable{

	airplaneIds airplaneId ;
	public airplaneIds getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(airplaneIds airplaneId) {
		this.airplaneId = airplaneId;
	}

	public airplane(SimEngine engine, String name, SimFeatures features) {
		super(engine, name , features);
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
