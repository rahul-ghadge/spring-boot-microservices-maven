package com.arya.microservices.ribbon.client.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="country-client")
@RibbonClient(name="country-client")
public interface CountryClientProxy {

    @GetMapping("/{country}")
    public Object getCountryData(@PathVariable("country") String country);

    }
