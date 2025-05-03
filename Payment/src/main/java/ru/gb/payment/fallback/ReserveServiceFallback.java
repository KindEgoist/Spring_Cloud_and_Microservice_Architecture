package ru.gb.payment.fallback;

import org.springframework.stereotype.Component;
import ru.gb.payment.client.ReserveServiceClient;
import ru.gb.payment.dto.ActionResponse;
import ru.gb.payment.dto.PaymentRequest;

@Component
public class ReserveServiceFallback implements ReserveServiceClient {

    @Override
    public ActionResponse commitReserve(PaymentRequest request) {
        return new ActionResponse(false, "Резерв-сервис временно недоступен (fallback)");
    }

    @Override
    public void cancelReserve(PaymentRequest request) {
        System.out.println("Отмена резерва не выполнена (fallback)");
    }

}
