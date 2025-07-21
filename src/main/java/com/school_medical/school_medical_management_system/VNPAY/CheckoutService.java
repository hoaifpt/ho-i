package com.school_medical.school_medical_management_system.VNPAY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CheckoutService {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VnPayConfig vnPayConfig;

    public String createPaymentUrl(CreatePaymentRequest request) {
        String txnRef = String.valueOf(System.currentTimeMillis());

        // Lưu order vào database với user ID
        Order order = new Order();
        order.setAmount(request.getAmount());
        order.setOrderInfo(request.getOrderInfo());
        order.setUserId(request.getUserId());
        order.setPackageId(request.getPackageId());
        order.setPackageName(request.getPackageName());
        order.setStatus("PENDING");
        order.setTxnRef(txnRef);

        // Tính toán ngày hết hạn (ví dụ: 1 năm)
        order.setExpiryDate(LocalDateTime.now().plusYears(1));

        orderRepository.save(order);
        logger.info("Created order with txnRef: {}", txnRef);

        // Tạo parameters cho VNPAY
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(request.getAmount() * 100));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", txnRef);
        vnpParams.put("vnp_OrderInfo", request.getOrderInfo());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", "127.0.0.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", createDate);

        cld.add(Calendar.MINUTE, 15);
        String expireDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_ExpireDate", expireDate);

        // Log tất cả parameters gửi đi
        logger.info("=== VNPAY REQUEST PARAMETERS ===");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            logger.info("REQUEST PARAM: {} = [{}]", entry.getKey(), entry.getValue());
        }
        logger.info("=== END REQUEST PARAMETERS ===");

        // Tạo query string và secure hash
        String queryUrl = buildQuery(vnpParams);
        String vnpSecureHash = hmacSHA512(vnPayConfig.getHashSecret(), queryUrl);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String finalUrl = vnPayConfig.getPayUrl() + "?" + queryUrl;
        logger.info("Generated payment URL: {}", finalUrl);
        
        return finalUrl;
    }

    public boolean verifyPayment(Map<String, String> params) {
        String vnpSecureHash = params.get("vnp_SecureHash");
        if (vnpSecureHash == null) {
            logger.error("No vnp_SecureHash in response");
            return false;
        }

        // Tạo bản copy để không modify original map
        Map<String, String> verifyParams = new HashMap<>(params);
        verifyParams.remove("vnp_SecureHash");
        verifyParams.remove("vnp_SecureHashType");

        String queryUrl = buildQuery(verifyParams);
        String calculatedHash = hmacSHA512(vnPayConfig.getHashSecret(), queryUrl);

        logger.info("Received hash: {}", vnpSecureHash);
        logger.info("Calculated hash: {}", calculatedHash);
        logger.info("Hash verification: {}", vnpSecureHash.equals(calculatedHash));

        return vnpSecureHash.equals(calculatedHash);
    }

    private String buildQuery(Map<String, String> params) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }
        
        String result = hashData.toString();
        logger.debug("Built query string: {}", result);
        return result;
    }

    private String hmacSHA512(String key, String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException("Key or data is null");
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            logger.error("Error generating HMAC SHA512: ", ex);
            return "";
        }
    }

    // Method để update order status
    public Order updateOrderStatus(String txnRef, String status) {
        Optional<Order> orderOpt = orderRepository.findByTxnRef(txnRef);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
}