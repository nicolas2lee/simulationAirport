package fr.enstabretagne.simu.td2;

public enum EntityState {
	NONE,
	BORN,
	IDLE,
	HELD,
	ACTIVE,
	DEAD;
	
	public boolean isBusy() {
		if (this == HELD || this == ACTIVE)
			return true;
		return false;
	}
	
	public boolean isAlive(){
		if (this == IDLE || isBusy()){
			return true;
		}
		return false;
	}
	
	private void demo() {
		EntityState state = null;
		// do something
		if (state.isBusy()) {
			NONE.isBusy();
			BORN.isBusy();
			HELD.isBusy();
		}
	}
	

}
