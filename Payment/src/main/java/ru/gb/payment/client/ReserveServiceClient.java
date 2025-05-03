package ru.gb.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.payment.dto.ActionResponse;
import ru.gb.payment.dto.PaymentRequest;
import ru.gb.payment.fallback.ReserveServiceFallback;

@FeignClient(
        name = "reserve-service",
        fallback = ReserveServiceFallback.class
)
//@FeignClient(name = "reserve-service")
public interface ReserveServiceClient {

    @PostMapping("/reserve/commit")
    ActionResponse commitReserve(@RequestBody PaymentRequest request);

    @PostMapping("/reserve/cancel")
    void cancelReserve(@RequestBody PaymentRequest request);
}
