package com.tadas.XML_reader.services;

import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.repositories.CalculatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {
  @Autowired
  private CalculatorRepository calculatorRepository;

  public List<FxRate> getRates() {
    return calculatorRepository.findAll();
  }
}
