package com.arya.microservices.ribbon.client.controller;

import com.arya.microservices.ribbon.client.service.CountryClientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RibbonClientController {

    @Autowired
    private CountryClientProxy countryClientProxy;

    @GetMapping({"/", "/{country}"})
    public Object getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "bharat";

        System.out.println("Getting country details for " + country);
        // Call country service by API endpoint
        Object response = countryClientProxy.getCountryData(country);
        System.out.println("Response Received from country client " + response);

        return response;
    }
}
