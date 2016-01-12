/**
 * 
 */
package fr.enstabretagne.simu.td2.demo;

import fr.enstabretagne.simu.td2.EntityState;

/**
 * @author zhengta
 *
 */
public class SimEvent {

	private EntityState currentState = EntityState.NONE;
	
	/**
	 * 
	 */
	public SimEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public void setEntityState(EntityState state){
		currentState = state ;
	} 
	
	public EntityState getEntityState(){
		return currentState ;
	} 

	
	public void Init(){
		if (currentState == EntityState.BORN){
			currentState=EntityState.IDLE;
		}else{
			System.out.println("You are trying to do from "+currentState+" IDLE");
		}
		
	}
	
	public void Active(){
		if (currentState == EntityState.IDLE){
			//To do to check in which state
			currentState=EntityState.ACTIVE;
			currentState=EntityState.HELD;
		}else{
			System.out.println("You are trying to do from "+currentState+" Busy");
		}
		
	}
	
	public void Pause(){
		if (currentState.isBusy()){
			//To do to check in which state
			currentState=EntityState.IDLE;
		}else{
			System.out.println("You are trying to do from "+currentState+" IDLE");
		}
		
	}
	
	public void Deactivate(){
		if (currentState.isAlive()){
			//To do to check in which state
			currentState=EntityState.BORN;
		}else{
			System.out.println("You are trying to do from "+currentState+" BORN");
		}
		
	}
	
	public void Terminate(){
		if (currentState == EntityState.BORN){
			//To do to check in which state
			currentState=EntityState.DEAD;
		}else{
			System.out.println("You are trying to do from "+currentState+" DEAD");
		}
		
	}


}
