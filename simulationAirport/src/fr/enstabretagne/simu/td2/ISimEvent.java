/**
 * 
 */
package fr.enstabretagne.simu.td2;

import enstabretagne.base.time.LogicalDateTime;

/**
 * @author zhengta
 *
 */
public interface ISimEvent extends Comparable<Object> {
	void process();
	LogicalDateTime ScheduleDate();
	void resetProcessDate(LogicalDateTime simulationDate);
}
