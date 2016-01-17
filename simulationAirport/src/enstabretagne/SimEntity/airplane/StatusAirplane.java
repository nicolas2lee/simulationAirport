/**
 * 
 */
package enstabretagne.SimEntity.airplane;

/**
 * @author zhengta
 *
 */
public enum StatusAirplane {
	Depart("Depart"),
	Arrive("Arrive");
	
	private final String statut;
	private StatusAirplane(String s) {
		statut=s;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return statut;
	}

}
