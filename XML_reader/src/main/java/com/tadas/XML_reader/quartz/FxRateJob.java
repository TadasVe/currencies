package com.tadas.XML_reader.quartz;

import com.tadas.XML_reader.services.FxRateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FxRateJob implements Job {

  @Autowired
  private FxRateService fxRateService;

  @Value("${fxRates.data.url}")
  private String fxRatesDataUrl;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("Executing currency update job at: " + java.time.LocalTime.now());
    fxRateService.fetchAndSaveFxRates(fxRatesDataUrl);
  }
}

