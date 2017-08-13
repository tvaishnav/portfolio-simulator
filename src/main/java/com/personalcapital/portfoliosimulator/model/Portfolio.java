package com.personalcapital.portfoliosimulator.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * Class {@code Portfolio} is a regular POJO class to hold portfolio configuration.
 * 
 * @author Tapasvi Vaishnav
 */
@Component
public class Portfolio {
	private PortfolioType portfolioType;
	private BigDecimal pastReturnPercent;
	private BigDecimal pastRiskPercent;
	
	public PortfolioType getPortfolioType() {
		return portfolioType;
	}
	
	public void setPortfolioType(final PortfolioType portfolioType) {
		this.portfolioType = portfolioType;
	}
	
	public BigDecimal getPastReturnPercent() {
		return pastReturnPercent;
	}
	
	public BigDecimal getPastReturn() {
		return pastReturnPercent.divide(BigDecimal.valueOf(100));
	}

	public void setPastReturnPercent(final BigDecimal pastReturnPercent) {
		this.pastReturnPercent = pastReturnPercent;
	}
	
	public BigDecimal getPastRiskPercent() {
		return pastRiskPercent;
	}
	
	public BigDecimal getPastRisk() {
		return pastRiskPercent.divide(BigDecimal.valueOf(100));
	}

	public void setPastRiskPercent(final BigDecimal pastRiskPercent) {
		this.pastRiskPercent = pastRiskPercent;
	}

	@Override
	public String toString() {
		return String.format("Portfolio [portfolioType=%s, pastReturnPercent=%s%%, pastRiskPercent=%s%%]", portfolioType, pastReturnPercent, pastRiskPercent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(portfolioType, pastReturnPercent, pastRiskPercent);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Portfolio other = (Portfolio) obj;
		return Objects.equals(this.portfolioType, other.portfolioType)
				&& Objects.equals(this.pastReturnPercent, other.pastReturnPercent)
				&& Objects.equals(this.pastRiskPercent, other.pastRiskPercent);
	}
}
