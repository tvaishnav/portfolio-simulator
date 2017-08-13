package com.personalcapital.portfoliosimulator;

import static com.personalcapital.portfoliosimulator.service.PortfolioSimulatorService.getPercentile;
import static com.personalcapital.portfoliosimulator.service.PortfolioSimulatorService.runSimulation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.personalcapital.portfoliosimulator.model.AppConfig;
import com.personalcapital.portfoliosimulator.model.Portfolio;
import com.personalcapital.portfoliosimulator.model.SimulationResult;

/**
 * Class {@code PortfolioSimulatorApplication} serves an entry point to this command line application and is a consumer to {@code PortfolioSimulatorService} 
 * which is a stateless service that provides methods for portfolio simulation.
 * 
 * @author Tapasvi Vaishnav
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private static final Random RAND = new Random();

	@Autowired
	private AppConfig appConfig;

	@Override
	public void run(final String... args) {
		final int simulationCount = appConfig.getSimulationCount();

		for (final Portfolio portfolio : appConfig.getPortfolios()) {
			final BigDecimal[] simulationReturns = new BigDecimal[simulationCount];
			for (int i = 0; i < simulationCount; i++) {
				simulationReturns[i] = runSimulation(appConfig.getInitialCapital(), portfolio,
						appConfig.getSimulationPeriodInYears(), appConfig.getInflationRate(), RAND);
			}
			Arrays.parallelSort(simulationReturns);
			final SimulationResult simulationResult = new SimulationResult(portfolio, getPercentile(simulationReturns, 50), getPercentile(simulationReturns, 90), getPercentile(simulationReturns, 10));
			LOGGER.info("\n\n{}\n\n",simulationResult.toString());
		}
	}
	
	public static void main(final String... args) {
		SpringApplication.run(Application.class, args);
	}
}
