package com.tadas.XML_reader.services;

import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.repositories.FxRateRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class FxRateService {

  private final FxRateRepository fxRateRepository;

  public FxRateService(FxRateRepository fxRateRepository) {
    this.fxRateRepository = fxRateRepository;
  }

  public void fetchAndSaveFxRates(String urlString) {
    try {

      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      Document doc = factory.newDocumentBuilder().parse(connection.getInputStream());
      doc.getDocumentElement().normalize();

      XPathFactory xPathFactory = XPathFactory.newInstance();
      XPath xPath = xPathFactory.newXPath();
      xPath.setNamespaceContext(new NamespaceContext() {
        @Override
        public String getNamespaceURI(String prefix) {
          if ("ns".equals(prefix)) {
            return "http://www.lb.lt/WebServices/FxRates";
          }
          return null;
        }

        @Override
        public String getPrefix(String namespaceURI) {
          return null;
        }

        @Override
        public Iterator getPrefixes(String namespaceURI) {
          return null;
        }
      });

      XPathExpression fxRateExpr = xPath.compile("//ns:FxRate");
      NodeList fxRateList = (NodeList) fxRateExpr.evaluate(doc, XPathConstants.NODESET);

      List<FxRate> fxRates = new ArrayList<>();

      for (int i = 0; i < fxRateList.getLength(); i++) {
        Node node = fxRateList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;

          String type = xPath.compile("./ns:Tp").evaluate(element);
          LocalDate date = LocalDate.parse(xPath.compile("./ns:Dt").evaluate(element));

          NodeList ccyAmtList = (NodeList) xPath.compile("./ns:CcyAmt").evaluate(element, XPathConstants.NODESET);
          if (ccyAmtList.getLength() == 2) {
            Element fromElement = (Element) ccyAmtList.item(0);
            Element toElement = (Element) ccyAmtList.item(1);

            String fromCurrency = xPath.compile("./ns:Ccy").evaluate(fromElement);
            double fromAmount = Double.parseDouble(xPath.compile("./ns:Amt").evaluate(fromElement));
            String toCurrency = xPath.compile("./ns:Ccy").evaluate(toElement);
            double toAmount = Double.parseDouble(xPath.compile("./ns:Amt").evaluate(toElement));

            Optional<FxRate> existingFxRate = fxRateRepository.findByToCurrency(toCurrency);

            if (existingFxRate.isPresent()) {
              FxRate fxRate = existingFxRate.get();
              fxRate.setDate(date);
              fxRate.setToAmount(toAmount);
              fxRateRepository.save(fxRate);
            } else {
              FxRate fxRate = new FxRate();
              fxRate.setType(type);
              fxRate.setDate(date);
              fxRate.setFromCurrency(fromCurrency);
              fxRate.setFromAmount(fromAmount);
              fxRate.setToCurrency(toCurrency);
              fxRate.setToAmount(toAmount);
              fxRates.add(fxRate);
            }
          }
        }
      }

      if (!fxRates.isEmpty()) {
        fxRateRepository.saveAll(fxRates);
      }

    } catch (Exception e) {
    }
  }

  public List<FxRate> showRates(String keyword) {
    if (keyword != null) {
      return fxRateRepository.search(keyword);
    }
    return fxRateRepository.findAll();
  }
}
