package com.microservice.a.route.a;

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
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;
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
                .bean(simpleLoggingProcessingComponent)
                .log("${body}")
                .bean(getCurrentTimeBean)
                .log("${body}")
                .to("log:first-timer");
    }
}


@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time Now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
    public void process(String message) {
logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

