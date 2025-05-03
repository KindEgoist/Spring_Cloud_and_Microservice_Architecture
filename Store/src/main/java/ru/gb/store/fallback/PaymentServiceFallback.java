package ru.gb.store.fallback;

import org.springframework.stereotype.Component;
import ru.gb.store.client.PaymentServiceClient;
import ru.gb.store.dto.ActionResponse;
import ru.gb.store.dto.PaymentRequest;

@Component
public class PaymentServiceFallback implements PaymentServiceClient {
    @Override
    public ActionResponse pay(PaymentRequest request) {
        return new ActionResponse(false, "Оплата не доступна (fallback)");
    }
}
