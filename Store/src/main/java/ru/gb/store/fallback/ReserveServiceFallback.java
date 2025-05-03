package ru.gb.store.fallback;

import org.springframework.stereotype.Component;
import ru.gb.store.client.ReserveServiceClient;
import ru.gb.store.dto.ActionResponse;
import ru.gb.store.dto.PurchaseRequest;
import ru.gb.store.dto.ReserveResponse;

@Component
public class ReserveServiceFallback implements ReserveServiceClient {

    @Override
    public ReserveResponse reserve(PurchaseRequest request) {
        return new ReserveResponse(false, "Резерв-сервис временно недоступен (fallback)", 0);
    }

    @Override
    public void cancel(PurchaseRequest request) {
        System.out.println("Отмена резерва не выполнена (fallback)");
    }

    @Override
    public ActionResponse commit(PurchaseRequest request) {
        return new ActionResponse(false, "Резерв-сервис временно недоступен (fallback)");
    }
}
