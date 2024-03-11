package com.tisoares.oderservice.internal.configuration;

import com.tisoares.oderservice.internal.usecase.EmailProcess;
import com.tisoares.oderservice.internal.usecase.OrderProcess;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Schedulers {

    private final OrderProcess orderProcess;
    private final EmailProcess emailSend;

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void processOrdersPending() {
        orderProcess.execute();
    }

    @Scheduled(fixedRate = 65000, initialDelay = 65000)
    public void sendEmailsPending() {
        emailSend.execute();
    }
}
