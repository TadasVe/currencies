package com.tadas.XML_reader.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class QuartzConfig {

  @Bean
  public JobDetail currencyJobDetail() {
    return JobBuilder.newJob(CurrencyJob.class)
      .withIdentity("currencyJob")
      .storeDurably()
      .build();
  }

  @Bean
  public Trigger currencyJobTrigger(JobDetail currencyJobDetail) {
    return TriggerBuilder.newTrigger()
      .forJob(currencyJobDetail)
      .withIdentity("currencyJobTrigger")
      .withSchedule(CronScheduleBuilder
        .dailyAtHourAndMinute(18, 5)
        .inTimeZone(TimeZone.getTimeZone("Europe/Vilnius"))
      )
      .build();
  }

  @Bean
  public JobDetail fxRateJobDetail() {
    return JobBuilder.newJob(FxRateJob.class)
      .withIdentity("fxRateJob")
      .storeDurably()
      .build();
  }

  @Bean
  public Trigger fxRateJobTrigger(JobDetail fxRateJobDetail) {
    return TriggerBuilder.newTrigger()
      .forJob(fxRateJobDetail)
      .withIdentity("fxRateJobTrigger")
      .withSchedule(CronScheduleBuilder
        .dailyAtHourAndMinute(18, 5)
        .inTimeZone(TimeZone.getTimeZone("Europe/Vilnius"))
      )
      .build();
  }
}
