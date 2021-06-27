package om.ownprojects.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

  @Autowired
  CurrencyExchangeProxy proxy;

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculate(
      @PathVariable String from,
      @PathVariable String to,
      @PathVariable BigDecimal quantity) {
    HashMap<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from); //map uri variables
    uriVariables.put("to", to);
    ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
        .getForEntity
            ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

    CurrencyConversion ce = responseEntity.getBody();
    return new CurrencyConversion(ce.getId(), from, to, ce.getConversionMultiple(), quantity,
        quantity.multiply(ce.getConversionMultiple()), ce.getEnvironment());
  }

  @GetMapping("/currency-conversion/feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateUsingFeign(
      @PathVariable String from,
      @PathVariable String to,
      @PathVariable BigDecimal quantity) {
    CurrencyConversion ce=proxy.calculate(from, to);
    return new CurrencyConversion(ce.getId(), from, to, ce.getConversionMultiple(), quantity,
        quantity.multiply(ce.getConversionMultiple()), ce.getEnvironment());
  }

}
