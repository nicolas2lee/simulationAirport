/**
 * 
 */
package enstabretagne.airportMonitor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enstabretagne.Scenario.AirportScenario;
import enstabretagne.Scenario.AirportScenarioFeatures;
import enstabretagne.SimEntity.airplane.StatusAirplane;
import enstabretagne.SimEntity.airplane.AirplaneFeature;

import enstabretagne.SimEntity.airplane.AirplaneInit;
import enstabretagne.SimEntity.airport.AirportFeatures;
import enstabretagne.SimEntity.airport.AirportInit;
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


public class AirportMonitor extends MonteCarloMonitor {

	/**
	 * 
	 */
	public AirportMonitor(HashMap<String,HashMap<String,Object>> loggersNames) {
		super(loggersNames);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// It is the main entry for the zhole project
		
	
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
		AirportMonitor sm = new AirportMonitor(loggersNames);
		
		//D�claration des donn�es qui serviront � l'initialisation du sc�nario
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		int nbDaysOfSimulation = 90;

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
		AirportScenarioFeatures asf; 
	
		List<AirplaneFeature> initialAirplanes;
		
		HashMap<String,AirplaneInit> i;
	
		//Cr�ation des Sc�narios
		//periodeArriveeClientsEnMinutes=12;
		
		//The first context
		initialAirplanes = new ArrayList<AirplaneFeature>();
		String flght_0 = "A00";
		String flght_1 = "A01";
		String flght_2 = "A02";
		
		String flght_3 = "A03";
		String flght_4 = "A04";
		String flght_5 = "A05";
		/*
		initialAirplanes.add(new AirplaneFeature(flght_0,StatutAirplane.Depart));
		initialAirplanes.add(new AirplaneFeature(flght_1,StatutAirplane.Depart));
		initialAirplanes.add(new AirplaneFeature(flght_2,StatutAirplane.Arrive));
		
		*/
		i = new HashMap<String,AirplaneInit>();
		/*
		i.put(flght_3,new AirplaneInit());
		i.put(flght_4,new AirplaneInit());
		i.put(flght_5,new AirplaneInit());
		*/
		nbTerminal=1;
		nbGate=4;
		nbTrack=1;
		
		int max_airplane=nbGate+nbTrack+1;
		asf = new AirportScenarioFeatures(
				"ScenarioAirport1", 
				beginFlightTime, 
				endFlightTime,
				60.0/20,60.0/10,60.0/40,2,//normal_time(Mon-Fri 10-17), busy_time(Mon-Fri 7-10,17-19), weekend_time(Sat,sun), coef of bad weather
				//int frequenceObservationSizeOfFIFO,int nbAirplaneMax
				
				new AirportFeatures("Aiport alpha",20,max_airplane, nbTerminal, nbGate, nbTrack,beginFlightTime,endFlightTime, initialAirplanes),
				new AirportInit(i),
				//7H~10H
				new CategoriesGenerator(0, 10*18, 18, 3, 2),
				//10H~17H
				new CategoriesGenerator(10*18, 20*21+10*18, 21, 3, 2),
				//17H~19H
				new CategoriesGenerator(20*21+10*18, 20*21+10*18+10*12, 12, 3, 2),
				//19H~22H
				new CategoriesGenerator(20*21+10*18+10*12, 20*21+10*18+10*12+20*9, 9, 3, 2),
				//weekend
				new CategoriesGenerator(0, 40*22, 22, 3, 2)
				);
		
		listeScenario.add(new AirportScenario(
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
