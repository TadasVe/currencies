package com.tadas.XML_reader.controllers;

import com.tadas.XML_reader.models.Currency;
import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.services.FxRateFromToService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FxRatesFromToController {

  private final FxRateFromToService fxRateFromToService;

  public FxRatesFromToController(FxRateFromToService fxRateFromToService) {
    this.fxRateFromToService = fxRateFromToService;
  }

  @GetMapping("/ratesFromTo")
  public String getCurrencies(Model model) {
    List<Currency> currencies = fxRateFromToService.getCurrencies();
    model.addAttribute("currencies", currencies);
    return "RatesPeriod";
  }

  @PostMapping("/ratesFromTo")
  public String getRates(@RequestParam String currency, @RequestParam String dtFrom, @RequestParam String dtTo, Model model) {
    List<FxRate> rates = fxRateFromToService.getFxRates(currency, dtFrom, dtTo);
    model.addAttribute("fxRates", rates);
    return "RatesPeriod";
  }
}
