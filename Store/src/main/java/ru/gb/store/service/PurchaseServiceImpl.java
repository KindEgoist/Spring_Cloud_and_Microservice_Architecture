package ru.gb.store.service;

import org.springframework.stereotype.Service;
import ru.gb.store.client.PaymentServiceClient;
import ru.gb.store.client.ReserveServiceClient;
import ru.gb.store.dto.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ReserveServiceClient reserveServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    public PurchaseServiceImpl(ReserveServiceClient reserveServiceClient,
                               PaymentServiceClient paymentServiceClient) {
        this.reserveServiceClient = reserveServiceClient;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public PurchaseResponse processPurchase(PurchaseRequest request) {
        ReserveResponse reserveResponse = reserveServiceClient.reserve(request);

        if (reserveResponse == null || !reserveResponse.isSuccess()) {
            return new PurchaseResponse(false, "Резервирование не удалось: " +
                    (reserveResponse != null ? reserveResponse.getMessage() : "Нет ответа"));
        }

        int totalAmount = reserveResponse.getPrice() * request.getQuantity();

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAccountId(request.getAccountId());
        paymentRequest.setAmount(totalAmount);
        paymentRequest.setProductId(request.getProductId());
        paymentRequest.setQuantity(request.getQuantity());

        ActionResponse paymentResponse = paymentServiceClient.pay(paymentRequest);

        if (paymentResponse == null || !paymentResponse.isSuccess()) {
            reserveServiceClient.cancel(request);
            return new PurchaseResponse(false, "Оплата не удалась: " +
                    (paymentResponse != null ? paymentResponse.getMessage() : "Нет ответа"));
        }

        ActionResponse commitResponse = reserveServiceClient.commit(request);
        if (commitResponse == null || !commitResponse.isSuccess()) {
            return new PurchaseResponse(false, "Ошибка при подтверждении резерва: " +
                    (commitResponse != null ? commitResponse.getMessage() : "Нет ответа"));
        }

        return new PurchaseResponse(true, "Покупка успешно завершена!");
    }
}
