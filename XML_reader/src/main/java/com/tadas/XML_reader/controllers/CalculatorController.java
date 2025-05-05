package com.tadas.XML_reader.controllers;

import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CalculatorController {
  @Autowired
  private CalculatorService calculatorService;

  @GetMapping("/calculator")
  public String calculator(Model model) {
    List<FxRate> currencies = calculatorService.getRates();
    model.addAttribute("currencies", currencies);
    return "Calculator";
  }
}
