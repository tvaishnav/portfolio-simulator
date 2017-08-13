package com.personalcapital.portfoliosimulator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import com.personalcapital.portfoliosimulator.model.Portfolio;

/**
 * Class {@code PortfolioSimulatorService} is a stateless service which provides methods for portfolio simulation.
 * 
 * It's utility methods are kept static for the purpose of ease of import.
 * 
 * @author Tapasvi Vaishnav
 */
public class PortfolioSimulatorService {

	/**
	 * This method takes the initial investment amount, portfolio strategy (with historical risk and return percents),
	 * and runs a simulation for given number of years, adjusting for effect of inflation after every year.
	 * 
	 * @param initialCapital inital investment amount
	 * @param portfolio portfolio strategy (with historical risk and return percents)
	 * @param simulationPeriodInYears number of years to run forecast for
	 * @param inflationRate rate of inflation in decimal representation (not percent)
	 * @param randomInstance instance of Random
	 * @return inflation adjusted projected value at the end of given number of years
	 */
	public static BigDecimal runSimulation (final BigDecimal initialCapital, final Portfolio portfolio, final int simulationPeriodInYears, final BigDecimal inflationRate, final Random randomInstance) {
		BigDecimal inflationAdjustedProjectedValue = initialCapital;
		for (int year=0; year<simulationPeriodInYears; year++) {
			/*
			 * As Random.nextGaussian() returns a Gaussian ("normally") distributed pseudorandom value with mean 0.0 and standard deviation 1.0,
			 * we need to derive our own pseudoRandomSample with historical mean and standard deviation values. 
			 */
			final BigDecimal pseudoRandomSample = BigDecimal.valueOf(randomInstance.nextGaussian()).multiply(portfolio.getPastRisk()).add(portfolio.getPastReturn()).add(BigDecimal.ONE);
			//adjust for inflation
			inflationAdjustedProjectedValue = inflationAdjustedProjectedValue.multiply(pseudoRandomSample).divide(inflationRate.add(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
		}
		return inflationAdjustedProjectedValue;
	}
	
	public static BigDecimal getPercentile(final BigDecimal[] simulationReturns, final int percentile) {
		final int index = (int) Math.ceil((percentile/100.0) * simulationReturns.length) - 1;
		return simulationReturns[index];
	}
	
}
