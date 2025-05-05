package com.tadas.XML_reader.quartz;

import com.tadas.XML_reader.services.CurrencyService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyJob implements Job {

  @Autowired
  private CurrencyService currencyService;

  @Value("${currency.data.url}")
  private String currencyDataUrl;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("Executing currency update job at: " + java.time.LocalTime.now());
    currencyService.fetchAndSaveCurrencies(currencyDataUrl);
  }
}
