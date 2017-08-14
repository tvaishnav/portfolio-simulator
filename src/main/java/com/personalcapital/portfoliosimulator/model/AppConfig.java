package com.personalcapital.portfoliosimulator.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class {@code AppConfig} is a convenience class to hold application configuration
 * in one place.
 * 
 * It is usually preferred to inject an instance of this class instead of injecting
 * numerous property values separately.
 *
 * @author Tapasvi Vaishnav
 */
@Configuration
@ConfigurationProperties(prefix = "config.personalcapital")
public class AppConfig {
	private int simulationCount;
	private int simulationPeriodInYears;
	private BigDecimal initialCapital;
	private BigDecimal inflationRatePercent;
	private Boolean tobeAdjustedForInflation;
	private List<Portfolio> portfolios;
	
	public int getSimulationCount() {
		return simulationCount;
	}
	
	public void setSimulationCount(final int simulationCount) {
		this.simulationCount = simulationCount;
	}
	
	public int getSimulationPeriodInYears() {
		return simulationPeriodInYears;
	}
	
	public void setSimulationPeriodInYears(final int simulationPeriodInYears) {
		this.simulationPeriodInYears = simulationPeriodInYears;
	}
	
	public BigDecimal getInitialCapital() {
		return initialCapital;
	}
	
	public void setInitialCapital(final BigDecimal initialCapital) {
		this.initialCapital = initialCapital;
	}
	
	public BigDecimal getInflationRatePercent() {
		return inflationRatePercent;
	}
	
	public BigDecimal getInflationRate() {
		return inflationRatePercent.divide(BigDecimal.valueOf(100));
	}

	public void setInflationRatePercent(final BigDecimal inflationRatePercent) {
		this.inflationRatePercent = inflationRatePercent;
	}
	
	public Boolean isTobeAdjustedForInflation() {
		return tobeAdjustedForInflation;
	}

	public void setTobeAdjustedForInflation(final Boolean tobeAdjustedForInflation) {
		this.tobeAdjustedForInflation = tobeAdjustedForInflation;
	}

	public List<Portfolio> getPortfolios() {
		return portfolios;
	}
	
	public void setPortfolios(final List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}	
}
