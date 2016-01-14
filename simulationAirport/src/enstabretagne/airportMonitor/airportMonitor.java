/**
 * 
 */
package enstabretagne.airportMonitor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enstabretagne.Scenario.airportScenario;
import enstabretagne.Scenario.airportScenarioFeatures;
import enstabretagne.SimEntity.airplane.StatutAirplane;
import enstabretagne.SimEntity.airplane.airplaneFeature;
import enstabretagne.SimEntity.airplane.airplaneIds;
import enstabretagne.SimEntity.airplane.airplaneInit;
import enstabretagne.SimEntity.airport.airportFeatures;
import enstabretagne.SimEntity.airport.airportInit;
//import enstabretagne.SimEntity.airplane.airplaneInit;
//import enstabretagne.SimEntity.airplane.airplaneNames;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
//import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
/**
 * @author nicolas2lee
 *
 */
import enstabretagne.monitors.MonteCarloMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimScenario;


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
		//cr�ation du moniteur
		airportMonitor sm = new airportMonitor(loggersNames);
		
		//D�claration des donn�es qui serviront � l'initialisation du sc�nario
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		int nbDaysOfSimulation = 90;
//		int repliqueNumber = 5;
		String beginFlightTime="07:00";
		String endFlightTime="22:00";
		int  repliqueNumber=1;
//		String heureDebutArriveeClient="09:00";
//		String heureFinArriveeClient="20:00";
//		int nbClientMaxEnSalle = 10;
//		double vitesseDeCoupe=19;		
//		double periodeArriveeClientsEnMinutes;
//		List<DayOfWeek> joursFermeture = new ArrayList<DayOfWeek>();
//		joursFermeture.add(DayOfWeek.SUNDAY);
//		joursFermeture.add(DayOfWeek.MONDAY);
		int nbTerminal;
		int nbGate;
		int nbTrack;
		List<SimScenario> listeScenario = new ArrayList<SimScenario>();
		
		
		//d�claration des variables qui nous servirons � chaque run
		airportScenarioFeatures asf; 
	
		List<airplaneFeature> l;
		
		HashMap<airplaneIds,airplaneInit> i;
	
		//Cr�ation des Sc�narios
		//periodeArriveeClientsEnMinutes=12;
		
		//The first context
		l = new ArrayList<airplaneFeature>();
		airplaneIds flght_0 = new airplaneIds("A00");
		airplaneIds flght_1 = new airplaneIds("A01");
		airplaneIds flght_2 = new airplaneIds("A02");
		
		l.add(new airplaneFeature(flght_0,StatutAirplane.Depart));
		l.add(new airplaneFeature(flght_1,StatutAirplane.Depart));
		l.add(new airplaneFeature(flght_2,StatutAirplane.Arrive));
		
		
		i = new HashMap<airplaneIds,airplaneInit>();
		i.put(flght_0,new airplaneInit());
		i.put(flght_1,new airplaneInit());
		i.put(flght_2,new airplaneInit());
		
		nbTerminal=1;
		nbGate=4;
		nbTrack=1;
		int periodArrivePlaneInMins = 20;
		asf = new airportScenarioFeatures(
				"ScenarioAirport1", 
				beginFlightTime, 
				endFlightTime,
				20,10,40,2,//normal_time(Mon-Fri 10-17), busy_time(Mon-Fri 7-10,17-19), weekend_time(Sat,sun), coef of bad weather
				new airportFeatures("Aiport alpha", nbTerminal, nbGate, nbTrack, l),
				new airportInit(i),
				new CategoriesGenerator(0, periodArrivePlaneInMins*10, 10, 3, 2)
				//new CategoriesGenerator(0, vitesseDeCoupe*5, 50, 3, 2)
				);
		
		listeScenario.add(new airportScenario(
				sm.getEngine(),
				new ScenarioId("Scenario1"),
				asf,
				start,
				start.add(LogicalDuration.ofDay(nbDaysOfSimulation))
		));
		
		sm.run(listeScenario, repliqueNumber);
		sm.terminate(false);
		System.out.println("End :"+Instant.now());
	}

}
