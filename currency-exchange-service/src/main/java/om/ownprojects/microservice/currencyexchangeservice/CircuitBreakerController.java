package om.ownprojects.microservice.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

  private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

  @GetMapping("/sample")
  //@Retry(name= "sample",fallbackMethod = "hardcodedresponse")
  //@CircuitBreaker(name= "default",fallbackMethod = "hardcodedresponse")
  @Bulkhead(name= "default")
  //@RateLimiter(name="default") //1000 calls to this api in 10s
  public String sample(){
      logger.info("Sample call");
     // return (new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class).getBody());
    return "sample";
  }

  public String hardcodedresponse(Exception ex){
    logger.info("Fallback call");
    return "fallback" ;
  }
}
