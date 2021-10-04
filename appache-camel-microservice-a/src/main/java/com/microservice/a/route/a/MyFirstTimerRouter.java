package com.microservice.a.route.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;
    @Autowired
    private simpleLoggingProcessingComponent simpleLoggingProcessingComponent;
    @Override
    public void configure() throws Exception {
        //timer
        // transformation
        // log

        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        //  Exchange[ExchangePattern: InOnly, BodyType: String, Body: My Constant Message]
        //  Exchange[ExchangePattern: InOnly, BodyType: String, Body: Time Is 2021-10-04T14:31:05.610]
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("MyConstant Message")
                .log("${body}")
//                .transform().constant ("My Constant Message")
//                .transform().constant("Time Is " + LocalDateTime.now())

                //Processing
                //Transformation
                .bean(getCurrentTimeBean)
                .log("${body}")
                .bean(simpleLoggingProcessingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer"); // database
    }
}


@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time Now is " + LocalDateTime.now();
    }
}

@Component
class simpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(simpleLoggingProcessingComponent.class);
    public void process() {

    }
}

class SimpleLoggingProcessor implements Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {} ", exchange.getMessage().getBody());
    }
}

