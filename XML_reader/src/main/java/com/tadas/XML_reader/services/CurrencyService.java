package com.tadas.XML_reader.services;

import com.tadas.XML_reader.models.Currency;
import com.tadas.XML_reader.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
  private final CurrencyRepository currencyRepository;

  public CurrencyService(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  public void fetchAndSaveCurrencies(String urlString) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      Document doc = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(connection.getInputStream());
      doc.getDocumentElement().normalize();

      NodeList nodeList = doc.getElementsByTagName("CcyNtry");
      List<Currency> currencies = new ArrayList<>();

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String code = element.getElementsByTagName("Ccy").item(0).getTextContent();
          String nameLT = getCurrencyName(element, "LT");
          String nameEN = getCurrencyName(element, "EN");
          String number = element.getElementsByTagName("CcyNbr").item(0).getTextContent();
          int minorUnits = Integer.parseInt(element.getElementsByTagName("CcyMnrUnts").item(0).getTextContent());

          Optional<Currency> existingCurrency = currencyRepository.findByCode(code);
          if (existingCurrency.isPresent()) {
            Currency currency = existingCurrency.get();
            currency.setCode(code);
            currency.setNameLT(nameLT);
            currency.setNameEN(nameEN);
            currency.setNumber(number);
            currency.setMinorUnits(minorUnits);
            currencyRepository.save(currency);
          } else {
            Currency currency = new Currency();
            currency.setCode(code);
            currency.setNameLT(nameLT);
            currency.setNameEN(nameEN);
            currency.setNumber(number);
            currency.setMinorUnits(minorUnits);
            currencies.add(currency);
          }
        }
      }

      if (!currencies.isEmpty()) {
        currencyRepository.saveAll(currencies);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getCurrencyName(Element element, String lang) {
    NodeList names = element.getElementsByTagName("CcyNm");
    for (int i = 0; i < names.getLength(); i++) {
      Element nameElement = (Element) names.item(i);
      if (lang.equals(nameElement.getAttribute("lang"))) {
        return nameElement.getTextContent();
      }
    }
    return null;
  }

  public List<Currency> showCurrencies(String keyword) {

    if (keyword != null) {
      return currencyRepository.search(keyword);
    }
    return currencyRepository.findAll();
  }
}
