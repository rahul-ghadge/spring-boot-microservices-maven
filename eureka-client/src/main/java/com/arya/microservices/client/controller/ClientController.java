package com.arya.microservices.client.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
public class ClientController {


    @Autowired
    private RestTemplate template;

    private static final String COUNTRY_API = "http://country-client/";


    @Autowired
    private EurekaClient client;

    @Value("${country.client:}")
    private String countryClientService;


    // Circuit breaker
//    @HystrixCommand(fallbackMethod = "getCountryDataFallback",
//            ignoreExceptions = { RuntimeException.class },
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
//            })
    @GetMapping({"/", "/{country}"})
    public Object getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "bharat";

        System.out.println("Getting country details for " + country);

        // Call country service by hardcoding API endpoint
        Object response = getServiceUsingRestTemplate(COUNTRY_API, country);


        System.out.println();
        System.out.println("**************************************");
        System.out.println("Application Name :: " + countryClientService);
        System.out.println("**************************************");
        System.out.println();


//        Call country service by by getting API endpoint from EurekaClient
//        InstanceInfo instanceInfo = client.getNextServerFromEureka("country-client", false);
//        String apiUrl = instanceInfo.getHomePageUrl();
//        Object response = getServiceUsingRestTemplate(apiUrl, country);

        System.out.println("Response Received from country client " + response);

        return response;
    }


    private Object getServiceUsingRestTemplate(String url, String country) {

        return template
                .getForObject(url + country, Object.class);
//                .exchange(url, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<String>() {}, country)
//                .getBody();
    }


    public String getCountryDataFallback(String country) {
        return "Country details API for " + country + " is currently unavailable. Please try after some time...!";
    }
}
