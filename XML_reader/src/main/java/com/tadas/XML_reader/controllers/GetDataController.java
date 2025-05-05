package com.tadas.XML_reader.controllers;

import com.tadas.XML_reader.services.CurrencyService;
import com.tadas.XML_reader.services.FxRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetDataController {
  private final CurrencyService currencyService;
  private final FxRateService fxRateService;

  @Value("${currency.data.url}")
  private String currencyDataUrl;

  @Value("${fxRates.data.url}")
  private String fxRatesDataUrl;

  public GetDataController(CurrencyService currencyService, FxRateService fxRateService) {
    this.currencyService = currencyService;
    this.fxRateService = fxRateService;
  }

  @GetMapping({"/", "/home"})
  public String GetData() {
    currencyService.fetchAndSaveCurrencies(currencyDataUrl);
    fxRateService.fetchAndSaveFxRates(fxRatesDataUrl);
    return "index";
  }
}
