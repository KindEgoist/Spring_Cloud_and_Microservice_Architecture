package ru.gb.reserve.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.reserve.dto.ActionResponse;
import ru.gb.reserve.dto.ProductActionRequest;
import ru.gb.reserve.dto.ReserveResponse;
import ru.gb.reserve.model.Product;
import ru.gb.reserve.service.ReserveService;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping
    public ResponseEntity<ReserveResponse> reserveProduct(@RequestBody ProductActionRequest request) {
        boolean reserved = reserveService.reserve(request.getProductId(), request.getQuantity());
        if (reserved) {
            Product product = reserveService.getProduct(request.getProductId());
            return ResponseEntity.ok(new ReserveResponse(true, "Продукт зарезервирован", product.getPrice()));
        } else {
            return ResponseEntity.ok(new ReserveResponse(false, "Продукт не зарезервирован", 0));
        }
    }

    @PostMapping("/commit")
    public ResponseEntity<ActionResponse> commitReserve(@RequestBody ProductActionRequest request) {
        boolean committed = reserveService.commitReserve(request.getProductId(), request.getQuantity());
        String msg = committed ? "Продукт продан" : "Недостаточно товара в резерве";
        return ResponseEntity.ok(new ActionResponse(committed, msg));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ActionResponse> cancelReserve(@RequestBody ProductActionRequest request) {
        try {
            reserveService.cancelReserve(request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(new ActionResponse(true, "Резерв отменён"));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new ActionResponse(false, "Ошибка при отмене: " + e.getMessage()));
        }
    }


}
