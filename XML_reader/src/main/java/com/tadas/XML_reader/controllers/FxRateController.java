package com.tadas.XML_reader.controllers;

import com.tadas.XML_reader.models.Currency;
import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.services.CurrencyService;
import com.tadas.XML_reader.services.FxRateService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FxRateController {
  private final FxRateService fxRateService;
  private final CurrencyService currencyService;

  public FxRateController(FxRateService fxRateService, CurrencyService currencyService) {
    this.fxRateService = fxRateService;
    this.currencyService = currencyService;
  }

  @GetMapping("/rates")
  public String showRates(Model model, @Param("keyword") String keyword) {
    List<FxRate> ratesList = fxRateService.showRates(keyword);
    List<Currency> currencyList = currencyService.showCurrencies(keyword);
    Map<String, Currency> currencyMap = new HashMap<>();
    for (Currency currency : currencyList) {
      currencyMap.put(currency.getCode(), currency);
    }
    model.addAttribute("rateList", ratesList);
    model.addAttribute("currencyMap", currencyMap);
    model.addAttribute("keyword");
    return "Rates";
  }

}
