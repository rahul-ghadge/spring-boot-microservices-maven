package com.arya.microservices.countryclient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CountryClientController {

    @Autowired
    private RestTemplate template;
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    

    
    @Value("${server.port}")
    private String port;

    private static final String COUNTRY_API = "https://restcountries.eu/rest/v2/name/";

    @GetMapping("/{country}")
    public Object getCountryData(@PathVariable String country) {

    	Object response = null;
        System.out.println("Fetching data from country : " + country);

        System.out.println("\n\n*********************************************");
        System.out.println("Server port :: " + port);
        System.out.println("*********************************************\n");

        if (country.endsWith("ico"))
            throw new RuntimeException("Don't know how in second call getting country name as : " + country);
        
        
		response = webClientBuilder.build()
			.get().uri(COUNTRY_API + country)
			.retrieve()
			.bodyToMono(Object.class)
			.block();
		
		//response  = template.getForEntity(COUNTRY_API + country, Object.class);
        

        return response;

    }
}
