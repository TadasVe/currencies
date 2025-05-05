package com.tadas.XML_reader.services;

import com.tadas.XML_reader.models.Currency;
import com.tadas.XML_reader.models.FxRate;
import com.tadas.XML_reader.repositories.CurrencyRepository;
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

@Service
public class FxRateFromToService {

  private final CurrencyRepository currencyRepository;

  public FxRateFromToService(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  public List<FxRate> getFxRates(String currency, String dtFrom, String dtTo) {
    try {
      String urlString = String.format(
        "https://www.lb.lt/webservices/FxRates/FxRates.asmx/getFxRatesForCurrency?tp=EU&ccy=%s&dtFrom=%s&dtTo=%s",
        currency, dtFrom, dtTo
      );

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

            FxRate fxRate = new FxRate();
            fxRate.setType(type);
            fxRate.setDate(date);
            fxRate.setFromCurrency(xPath.compile("./ns:Ccy").evaluate(fromElement));
            fxRate.setFromAmount(Double.parseDouble(xPath.compile("./ns:Amt").evaluate(fromElement)));
            fxRate.setToCurrency(xPath.compile("./ns:Ccy").evaluate(toElement));
            fxRate.setToAmount(Double.parseDouble(xPath.compile("./ns:Amt").evaluate(toElement)));

            fxRates.add(fxRate);
          }
        }
      }

      return fxRates;

    } catch (Exception e) {
    }

    return null;
  }

  public List<Currency> getCurrencies() {
    return currencyRepository.findAll();
  }

}
