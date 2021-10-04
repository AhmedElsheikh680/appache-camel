package com.microservice.a.route.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
        // transformation
        // log

        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        //  Exchange[ExchangePattern: InOnly, BodyType: String, Body: My Constant Message]
        //  Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time Is 2021-10-04T14:31:05.610]
        from("timer:first-timer")
//                .transform().constant("My Constant Message")
                .transform().constant("Time Is " + LocalDateTime.now())
                .to("log:first-timer");
    }
}
