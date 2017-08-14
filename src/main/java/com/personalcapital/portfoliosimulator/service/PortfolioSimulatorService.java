package com.personalcapital.portfoliosimulator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personalcapital.portfoliosimulator.model.AppConfig;
import com.personalcapital.portfoliosimulator.model.Portfolio;
import com.personalcapital.portfoliosimulator.model.SimulationResult;

/**
 * Class {@code PortfolioSimulatorService} is a stateless service which provides methods for portfolio simulation.
 * 
 * @author Tapasvi Vaishnav
 */
@Service
public class PortfolioSimulatorService {
	@Autowired
	private AppConfig appConfig;

	/**
	 * This method takes portfolio strategy (with historical risk and return percents),
	 * and runs a simulation for given number of years.
	 * 
	 * @param portfolio portfolio strategy (with historical risk and return percents)
	 * @param randomInstance instance of Random
	 * @return Projected value at the end of given number of years
	 */
	public BigDecimal runSimulation (final Portfolio portfolio, final Random randomInstance) {
		BigDecimal projectedValue = appConfig.getInitialCapital();
		for (int year=0; year<appConfig.getSimulationPeriodInYears(); year++) {
			/*
			 * As Random.nextGaussian() returns a Gaussian ("normally") distributed pseudorandom value with mean 0.0 and standard deviation 1.0,
			 * we need to derive our own pseudoRandomSample with historical mean and standard deviation values. 
			 */
			final BigDecimal pseudoRandomSample = BigDecimal.valueOf(randomInstance.nextGaussian()).multiply(portfolio.getPastRisk()).add(portfolio.getPastReturn()).add(BigDecimal.ONE);
			projectedValue = projectedValue.multiply(pseudoRandomSample);
		}
		return projectedValue;
	}
	
	/**
	 * This method expects a <strong>sorted</strong> array as an input to avoid incurring the cost of sorting 
	 * again and again for each percentile calculation.
	 * 
	 * @param simulationReturns sorted array of BigDecimal values
	 * @param percentile percentile value 
	 * @return a value from the array representative of the passed percentile value (i.e. value below 
	 * 			which p% of the observations may be found where p is the passed percentile value).
	 */
	public BigDecimal getPercentile(final BigDecimal[] simulationReturns, final int percentile) {
		final int index = (int) Math.ceil((percentile/100.0) * simulationReturns.length) - 1;
		return simulationReturns[index];
	}
	
	/**
	 * This method takes an unadjusted {@code SimulationResult} instance an input 
	 * and returns the inflation adjusted {@code SimulationResult} instance.
	 *  
	 * @param unadjustedSimulationResult unadjusted {@code SimulationResult} instance 
	 * @return inflation adjusted {@code SimulationResult} instance
	 */
	public SimulationResult adjustForInflation(final SimulationResult unadjustedSimulationResult) {
		final Function<BigDecimal, BigDecimal> inflationAdjuster = percentileValue -> percentileValue.divide(appConfig.getInflationRate().add(BigDecimal.ONE).pow(appConfig.getSimulationPeriodInYears()), 2, RoundingMode.HALF_UP);
		return new SimulationResult(unadjustedSimulationResult.getPortfolio(), 
				inflationAdjuster.apply(unadjustedSimulationResult.getMedianValue()),
				inflationAdjuster.apply(unadjustedSimulationResult.getTenPcBestCaseValue()),
				inflationAdjuster.apply(unadjustedSimulationResult.getTenPcWorstCaseValue()));
	}
}
