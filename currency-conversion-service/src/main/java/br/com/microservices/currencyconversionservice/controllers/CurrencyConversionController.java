package br.com.microservices.currencyconversionservice.controllers;

import br.com.microservices.currencyconversionservice.CurrencyExchangeServiceProxy;
import br.com.microservices.currencyconversionservice.bean.CurrencyConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversion responseEntity = proxy.retrieveExchangeValue(from, to);

        CurrencyConversion currencyConversion = new CurrencyConversion(
                responseEntity.getId(),
                from,
                to,
                responseEntity.getConversionMultiple(),
                quantity,
                quantity.multiply(
                        responseEntity.getConversionMultiple()
                ),
                responseEntity.getPort());
        logger.info("{}", currencyConversion);
        return currencyConversion;
    }

}
