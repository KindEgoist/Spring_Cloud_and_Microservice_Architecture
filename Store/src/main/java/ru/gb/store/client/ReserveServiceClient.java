package ru.gb.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.store.dto.ActionResponse;
import ru.gb.store.dto.PurchaseRequest;
import ru.gb.store.dto.ReserveResponse;

@FeignClient(name = "reserve-service", path = "/reserve")
public interface ReserveServiceClient {

    @PostMapping("/reserve")
    ReserveResponse reserve(@RequestBody PurchaseRequest request);

    @PostMapping("/cancel")
    void cancel(@RequestBody PurchaseRequest request);

    @PostMapping("/commit")
    ActionResponse commit(@RequestBody PurchaseRequest request);
}

