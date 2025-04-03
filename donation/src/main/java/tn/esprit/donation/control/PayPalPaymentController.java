package tn.esprit.donation.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.donation.entity.Donation;
import tn.esprit.donation.service.IDonationService;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/payment")
public class PayPalPaymentController {

    private final IDonationService donationService;
    private final String clientId;
    private final String clientSecret;
    private final String mode;

    @Autowired
    public PayPalPaymentController(IDonationService donationService,
                                   @Value("${paypal.client-id}") String clientId,
                                   @Value("${paypal.client-secret}") String clientSecret,
                                   @Value("${paypal.mode}") String mode) {
        this.donationService = donationService;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderDetails) {
        try {
            // Validation
            if (orderDetails == null || orderDetails.get("amount") == null || orderDetails.get("eventId") == null) {
                return ResponseEntity.badRequest().body(errorResponse("Amount and eventId are required"));
            }

            float amount;
            Long eventId;

            try {
                amount = Float.parseFloat(orderDetails.get("amount").toString());
                eventId = Long.parseLong(orderDetails.get("eventId").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(errorResponse("Invalid amount or eventId format"));
            }

            // Validate amount
            if (amount <= 0) {
                return ResponseEntity.badRequest().body(errorResponse("Amount must be greater than 0"));
            }

            // Validate eventId
            if (eventId <= 0) {
                return ResponseEntity.badRequest().body(errorResponse("Invalid event ID"));
            }

            // Create PayPal order
            Map<String, Object> orderRequest = buildOrderRequest(amount);
            String accessToken = getPayPalAccessToken();

            if (accessToken == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(errorResponse("Failed to authenticate with PayPal"));
            }

            // Call PayPal API
            ResponseEntity<Map> paypalResponse = createPayPalOrder(accessToken, orderRequest);
            if (!paypalResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(errorResponse("Failed to create PayPal order"));
            }

            // Create donation record
            Donation donation = createDonation(amount, eventId);
            Donation savedDonation = donationService.addDonation(donation);

            // Return success response
            return ResponseEntity.ok(successResponse(
                    paypalResponse.getBody().get("id").toString(),
                    savedDonation.getId()
            ));

        } catch (Exception e) {
            return handleException(e, "Error creating order");
        }
    }

    @PostMapping("/capture-order")
    public ResponseEntity<?> captureOrder(@RequestBody Map<String, String> data) {
        try {
            // Validation des données
            if (data == null || !data.containsKey("orderId") || !data.containsKey("donationId")) {
                return ResponseEntity.badRequest().body(errorResponse("orderId and donationId are required"));
            }

            String orderId = data.get("orderId");
            Long donationId;

            try {
                donationId = Long.parseLong(data.get("donationId"));
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(errorResponse("Invalid donationId format"));
            }

            // Capture du paiement avec PayPal
            String accessToken = getPayPalAccessToken();
            if (accessToken == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(errorResponse("Failed to authenticate with PayPal"));
            }

            ResponseEntity<Map> paypalResponse = capturePayPalPayment(accessToken, orderId);
            if (!paypalResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse("Payment capture failed"));
            }

            // Mise à jour de la donation
            Donation donation = donationService.retrieveDonation(donationId);
            if (donation == null) {
                return ResponseEntity.badRequest().body(errorResponse("Donation not found"));
            }

            updateDonationStatus(donation, orderId);
            donationService.modifyDonation(donation);

            return ResponseEntity.ok(successResponse(donation));

        } catch (Exception e) {
            return handleException(e, "Error capturing order");
        }
    }

    // Méthodes utilitaires

    private Map<String, Object> buildOrderRequest(float amount) {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("intent", "CAPTURE");

        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", Map.of(
                "currency_code", "USD",
                "value", amount
        ));

        orderRequest.put("purchase_units", new Map[] { purchaseUnit });

        if ("sandbox".equals(mode)) {
            orderRequest.put("application_context", Map.of(
                    "user_action", "PAY_NOW",
                    "payment_method", Map.of(
                            "payer_selected", "PAYPAL",
                            "payee_preferred", "IMMEDIATE_PAYMENT_REQUIRED"
                    )
            ));
        }
        return orderRequest;
    }

    private ResponseEntity<Map> createPayPalOrder(String accessToken, Map<String, Object> orderRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(orderRequest, headers);
        return new RestTemplate().postForEntity(
                getPayPalBaseUrl() + "/v2/checkout/orders",
                request,
                Map.class
        );
    }

    private ResponseEntity<Map> capturePayPalPayment(String accessToken, String orderId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(headers);
        return new RestTemplate().postForEntity(
                getPayPalBaseUrl() + "/v2/checkout/orders/" + orderId + "/capture",
                request,
                Map.class
        );
    }

    private Donation createDonation(float amount, Long eventId) {
        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setEventId(eventId);
        donation.setDate(LocalDateTime.now());
        donation.setPaymentMethod("paypal");
        donation.setStatus("PENDING");
        return donation;
    }

    private void updateDonationStatus(Donation donation, String transactionId) {
        donation.setTransactionId(transactionId);
        donation.setStatus("COMPLETED");
    }

    private String getPayPalBaseUrl() {
        return "sandbox".equals(mode)
                ? "https://api-m.sandbox.paypal.com"
                : "https://api-m.paypal.com";
    }

    private String getPayPalAccessToken() {
        try {
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

            ResponseEntity<Map> response = new RestTemplate().postForEntity(
                    getPayPalBaseUrl() + "/v1/oauth2/token",
                    request,
                    Map.class
            );

            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthodes de réponse standardisées
    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }

    private Map<String, Object> successResponse(String orderId, Long donationId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("orderId", orderId);
        response.put("donationId", donationId);
        return response;
    }

    private Map<String, Object> successResponse(Donation donation) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("donation", donation);
        return response;
    }

    private ResponseEntity<?> handleException(Exception e, String context) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse(context + ": " + e.getMessage()));
    }
}