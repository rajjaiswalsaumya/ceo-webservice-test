package org.cdac.gist.hello;

import java.text.SimpleDateFormat;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import org.cdac.gist.hello.wsdl.Forecast;
import org.cdac.gist.hello.wsdl.ForecastReturn;
import org.cdac.gist.hello.wsdl.GetCityForecastByZIP;
import org.cdac.gist.hello.wsdl.GetCityForecastByZIPResponse;
import org.cdac.gist.hello.wsdl.Temp;

public class WeatherClient extends WebServiceGatewaySupport {

    public GetCityForecastByZIPResponse getCityForecastByZip(String zipCode) {
        GetCityForecastByZIP request = new GetCityForecastByZIP();
        request.setZIP(zipCode);

        System.out.println();
        System.out.println("Requesting forecast for " + zipCode);

        GetCityForecastByZIPResponse response = (GetCityForecastByZIPResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));

        return response;
    }

    public void printResponse(GetCityForecastByZIPResponse response) {
        ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();

        if (forecastReturn.isSuccess()) {
            System.out.println();
            System.out.println("Forecast for " + forecastReturn.getCity() + ", "
                    + forecastReturn.getState());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
                System.out.print(format.format(forecast.getDate().toGregorianCalendar().getTime()));
                System.out.print(" ");
                System.out.print(forecast.getDesciption());
                System.out.print(" ");
                Temp temperature = forecast.getTemperatures();
                System.out.print(temperature.getMorningLow() + "\u00b0-"
                        + temperature.getDaytimeHigh() + "\u00b0 ");
                System.out.println();
            }
        } else {
            System.out.println("No forecast received");
        }
    }

}