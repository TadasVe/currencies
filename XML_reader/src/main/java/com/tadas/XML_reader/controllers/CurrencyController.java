package com.tadas.XML_reader.controllers;

import com.tadas.XML_reader.models.Currency;
import com.tadas.XML_reader.services.CurrencyService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CurrencyController {

  private final CurrencyService currencyService;

  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  @GetMapping("/currencies")
  public String showCurrencies(Model model, @Param("keyword") String keyword) {
    List<Currency> currencyList = currencyService.showCurrencies(keyword);
    model.addAttribute("ccyList", currencyList);
    model.addAttribute("keyword");
    return "Currencies";
  }
}
