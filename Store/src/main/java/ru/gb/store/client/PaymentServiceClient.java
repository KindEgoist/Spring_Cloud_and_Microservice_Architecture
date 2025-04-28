package ru.gb.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.store.dto.ActionResponse;
import ru.gb.store.dto.PaymentRequest;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/pay")
    ActionResponse pay(@RequestBody PaymentRequest request);
}
