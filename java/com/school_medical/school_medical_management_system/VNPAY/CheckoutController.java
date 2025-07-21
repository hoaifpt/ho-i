package com.school_medical.school_medical_management_system.VNPAY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        try {
            logger.info("Creating payment for user: {} with amount: {}", request.getUserId(), request.getAmount());
            String paymentUrl = checkoutService.createPaymentUrl(request);
            return ResponseEntity.ok(new CreatePaymentResponse(paymentUrl));
        } catch (Exception e) {
            logger.error("Error creating payment: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/vnpay-return")
    public RedirectView vnpayReturn(@RequestParam Map<String, String> params) {
        logger.info("VNPAY return with parameters: {}", params);
        try {
            boolean isValid = checkoutService.verifyPayment(params);
            logger.info("Verification result: {}", isValid);

            String txnRef = params.get("vnp_TxnRef");
            String responseCode = params.get("vnp_ResponseCode");
            String vnpTransactionNo = params.get("vnp_TransactionNo");

            logger.info("VNPAY response:");
            logger.info("  txnRef: {}", txnRef);
            logger.info("  responseCode: {}", responseCode);
            logger.info("  vnpTransactionNo: {}", vnpTransactionNo);

            if (isValid) {
                Optional<Order> orderOpt = orderRepository.findByTxnRef(txnRef);
                if (orderOpt.isPresent()) {
                    Order order = orderOpt.get();
                    if ("00".equals(responseCode)) {
                        order.setStatus("SUCCESS");
                        order.setVnpTransactionNo(vnpTransactionNo);
                    } else {
                        order.setStatus("FAILED");
                    }
                    orderRepository.save(order);
                    logger.info("Order updated: status={}, vnpTransactionNo={}", order.getStatus(),
                            order.getVnpTransactionNo());
                    return new RedirectView("http://localhost:3000/payment/result?success=true");
                } else {
                    logger.error("Order not found for txnRef: {}", txnRef);
                    return new RedirectView("http://localhost:3000/payment/result?success=false&error=order_not_found");
                }
            } else {
                logger.error("Payment verification failed");
                return new RedirectView("http://localhost:3000/payment/result?success=false&error=verification_failed");
            }
        } catch (Exception e) {
            logger.error("Error processing VNPAY return: ", e);
            return new RedirectView("http://localhost:3000/payment/result?success=false&error=exception");
        }
    }

    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<Map<String, Object>> getUserOrders(@PathVariable Long userId) {
        try {
            logger.info("Getting orders for user: {}", userId);

            List<Order> orders = orderRepository.findByUserIdAndStatus(userId, "SUCCESS");
            logger.info("Found {} orders for user {}", orders.size(), userId);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "packages", orders,
                    "total", orders.size()));
        } catch (Exception e) {
            logger.error("Error getting user orders: ", e);
            return ResponseEntity.ok(Map.of("success", false, "message", "Error: " + e.getMessage()));
        }
    }

}