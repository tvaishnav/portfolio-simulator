package com.personalcapital.portfoliosimulator.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class {@code SimulationResult} is a regular POJO class to hold the output values 
 * resulting from simulation runs.
 * 
 * @author Tapasvi Vaishnav
 */
public class SimulationResult {
	private final Portfolio portfolio;
	private final BigDecimal medianValue;
	private final BigDecimal tenPcBestCaseValue;
	private final BigDecimal tenPcWorstCaseValue;

	public SimulationResult(final Portfolio portfolio, final BigDecimal medianValue, final BigDecimal tenPcBestCaseValue, final BigDecimal tenPcWorstCaseValue) {
		this.portfolio = portfolio;
		this.medianValue = medianValue;
		this.tenPcBestCaseValue = tenPcBestCaseValue;
		this.tenPcWorstCaseValue = tenPcWorstCaseValue;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public BigDecimal getMedianValue() {
		return medianValue;
	}

	public BigDecimal getTenPcBestCaseValue() {
		return tenPcBestCaseValue;
	}

	public BigDecimal getTenPcWorstCaseValue() {
		return tenPcWorstCaseValue;
	}

	@Override
	public String toString() {
		return String.format(new StringBuilder("%s\n").append("Median=%(,.2f\n").append("10%% Best Case=%(,.2f\n").append("10%% Worst Case=%(,.2f\n").toString(),portfolio, medianValue, tenPcBestCaseValue, tenPcWorstCaseValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(portfolio, medianValue, tenPcBestCaseValue, tenPcWorstCaseValue);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimulationResult other = (SimulationResult) obj;
		return Objects.equals(this.portfolio, other.portfolio)
				&& Objects.equals(this.medianValue, other.medianValue)
				&& Objects.equals(this.tenPcBestCaseValue, other.tenPcBestCaseValue)
				&& Objects.equals(this.tenPcWorstCaseValue, other.tenPcWorstCaseValue);
	}
}
