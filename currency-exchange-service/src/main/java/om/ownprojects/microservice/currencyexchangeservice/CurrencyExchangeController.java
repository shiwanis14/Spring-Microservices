package om.ownprojects.microservice.currencyexchangeservice;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

  Logger logger= LoggerFactory.getLogger(CurrencyExchangeController.class);
  @Autowired
  private Environment environment;

  @Autowired
  private CurrencyExchangeRepository repository;

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange getValue(
      @PathVariable String from,
      @PathVariable String to) {
    logger.info("retrieve exchange value for {} to {}", from,to);
     CurrencyExchange ce =  repository.findByFromAndTo(from, to);
     if(ce==null)
       throw new RuntimeException("NO DATA!!");
     ce.setEnvironment(environment.getProperty("local.server.port"));
     return ce;
  }

}
