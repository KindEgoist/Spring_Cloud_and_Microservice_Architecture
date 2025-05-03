package ru.gb.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.store.dto.ActionResponse;
import ru.gb.store.dto.PaymentRequest;
import ru.gb.store.fallback.PaymentServiceFallback;
import ru.gb.store.fallback.ReserveServiceFallback;

@FeignClient(
        name = "payment-service",
        path = "/payment",
        fallback = PaymentServiceFallback.class
)

//@FeignClient(name = "payment-service", path = "/payment")
public interface PaymentServiceClient {

    @PostMapping("/pay")
    ActionResponse pay(@RequestBody PaymentRequest request);
}

