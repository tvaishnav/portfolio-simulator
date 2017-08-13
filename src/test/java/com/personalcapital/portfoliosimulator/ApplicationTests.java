package com.personalcapital.portfoliosimulator;

import static com.personalcapital.portfoliosimulator.service.PortfolioSimulatorService.getPercentile;
import static com.personalcapital.portfoliosimulator.service.PortfolioSimulatorService.runSimulation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.personalcapital.portfoliosimulator.model.AppConfig;
import com.personalcapital.portfoliosimulator.model.Portfolio;
import com.personalcapital.portfoliosimulator.model.PortfolioType;
import com.personalcapital.portfoliosimulator.model.SimulationResult;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
public class ApplicationTests {
	@MockBean
    private Random randomInstance;
	
	@Autowired
	private AppConfig appConfig;
		
	@Test
	public void runSimulationTest() {
		//setup expectations
		given(this.randomInstance.nextGaussian()).willReturn(0.1);
		final Map<Portfolio, SimulationResult> expectedResult = new HashMap<>(appConfig.getPortfolios().size());
		for (final Portfolio portfolio : appConfig.getPortfolios()) {
			if (portfolio.getPortfolioType() == PortfolioType.AGGRESSIVE) {
				expectedResult.put(portfolio, new SimulationResult(portfolio, BigDecimal.valueOf(405_176.96), BigDecimal.valueOf(405_176.96), BigDecimal.valueOf(405_176.96)));
			} else {
				expectedResult.put(portfolio, new SimulationResult(portfolio, BigDecimal.valueOf(188_156.58), BigDecimal.valueOf(188_156.58), BigDecimal.valueOf(188_156.58)));
			}
		}
		//test
		final int simulationCount = appConfig.getSimulationCount();

		for (final Portfolio portfolio : appConfig.getPortfolios()) {
			final BigDecimal[] simulationReturns = new BigDecimal[simulationCount];
			for (int i = 0; i < simulationCount; i++) {
				simulationReturns[i] = runSimulation(appConfig.getInitialCapital(), portfolio,
						appConfig.getSimulationPeriodInYears(), appConfig.getInflationRate(), randomInstance);
			}
			Arrays.parallelSort(simulationReturns);
			final SimulationResult simulationResult = new SimulationResult(portfolio, getPercentile(simulationReturns, 50), getPercentile(simulationReturns, 90), getPercentile(simulationReturns, 10));
			assertThat(simulationResult).isEqualTo(expectedResult.get(portfolio));
		}
	}

}
