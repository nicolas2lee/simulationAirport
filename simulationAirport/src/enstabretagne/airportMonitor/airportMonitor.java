/**
 * 
 */
package enstabretagne.airportMonitor;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;





import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
/**
 * @author nicolas2lee
 *
 */
import enstabretagne.monitors.MonteCarloMonitor;

public class airportMonitor extends MonteCarloMonitor {

	/**
	 * 
	 */
	public airportMonitor(HashMap<String,HashMap<String,Object>> loggersNames) {
		super(loggersNames);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// It is the main entry for the zhole project
		
		/*
		 * create begin and end time
		 * create context for launch the simulation
		 * create logger
		 * create xlsx(excel) parser
		 * create weather
		 * create airport environment
		 * 
		 * for each simulation context
		 * need to add airplanes*/
		System.out.println("Simulation of airport begins at "+ Instant.now());
		
		//Configuration requise pour le logger
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());
		
		HashMap<String,Object> xlsxParams=new HashMap<String,Object>();		
		xlsxParams.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir") + "\\log");
		xlsxParams.put(LoggerParamsNames.FileName.toString(), "simulationAirport.xlsx");
		//System.out.println(xlsxParams);
		
		loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),xlsxParams);
		//System.out.println(loggersNames);
		//création du moniteur
		airportMonitor sm = new airportMonitor(loggersNames);
		
		//Déclaration des données qui serviront à l'initialisation du scénario
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		int nbDaysOfSimulation = 100;
//		int repliqueNumber = 5;
		String hourOpenAirport="07:00";
		String hourCloseAirport="22:00";
//		String heureDebutArriveeClient="09:00";
//		String heureFinArriveeClient="20:00";
//		int nbClientMaxEnSalle = 10;
//		double vitesseDeCoupe=19;		
//		double periodeArriveeClientsEnMinutes;
//		List<DayOfWeek> joursFermeture = new ArrayList<DayOfWeek>();
//		joursFermeture.add(DayOfWeek.SUNDAY);
//		joursFermeture.add(DayOfWeek.MONDAY);
	}

}
