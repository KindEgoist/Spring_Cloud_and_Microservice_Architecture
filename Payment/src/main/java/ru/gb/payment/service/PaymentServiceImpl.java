package ru.gb.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.payment.client.ReserveServiceClient;
import ru.gb.payment.dto.ActionResponse;
import ru.gb.payment.dto.PaymentRequest;
import ru.gb.payment.model.Account;
import ru.gb.payment.repository.AccountRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final AccountRepository accountRepository;
    private final ReserveServiceClient reserveServiceClient;

    public PaymentServiceImpl(AccountRepository accountRepository,
                              ReserveServiceClient reserveServiceClient) {
        this.accountRepository = accountRepository;
        this.reserveServiceClient = reserveServiceClient;
    }

    @Override
    @Transactional
    public ActionResponse processPayment(PaymentRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Аккаунт не найден"));

        if (account.getBalance() < request.getAmount()) {
            // Отменить резерв
            reserveServiceClient.cancelReserve(request);
            return new ActionResponse(false, "Недостаточно средств");
        }

        account.setBalance(account.getBalance() - request.getAmount());

        // Подтверждаем резерв
        ActionResponse commitResponse = reserveServiceClient.commitReserve(request);

        if (commitResponse == null || !commitResponse.isSuccess()) {
            return new ActionResponse(false, "Не удалось подтвердить резерв на складе");
        }

        return new ActionResponse(true, "Оплата прошла успешно");
    }
}
