package org.cdac.gist;

import org.cdac.gist.hello.WeatherClient;
import org.cdac.gist.hello.wsdl.GetCityForecastByZIPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by root on 2/11/15.
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    ConfigurableApplicationContext ctx;

    @Override
    public void run(String... args) throws Exception {
        WeatherClient weatherClient = ctx.getBean(WeatherClient.class);

        String zipCode = "94304";
        if (args.length > 0) {
            zipCode = args[0];
        }
        GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZip(zipCode);
        weatherClient.printResponse(response);
    }
}
