package main.java.com.example.sqlsolver;

import com.example.sqlsolver.dto.WebhookRequest;
import com.example.sqlsolver.dto.WebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void process() {
        // 1. Send POST request to generate webhook
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        WebhookRequest request = new WebhookRequest(
                "Swati Sharan",
                "22BIT0573",
                "swati.sharan2022@vitstudent.ac.in"
        );

        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(url, request, WebhookResponse.class);
        WebhookResponse webhookResponse = response.getBody();

        if (webhookResponse == null) {
            System.out.println("❌ No response from server");
            return;
        }

        String webhook = webhookResponse.getWebhook();
        String token = webhookResponse.getAccessToken();

        System.out.println("✅ Got Webhook: " + webhook);
        System.out.println("✅ Got Token: " + token);

        // 2. Your final SQL query
        String finalQuery =
                "SELECT p.AMOUNT AS SALARY, " +
                "       CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                "       TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
                "       d.DEPARTMENT_NAME " +
                "FROM PAYMENTS p " +
                "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                "WHERE DAY(p.PAYMENT_TIME) <> 1 " +
                "ORDER BY p.AMOUNT DESC " +
                "LIMIT 1;";

        // 🔹 Step 5: Explain SQL query
        explainSQL(finalQuery);

        // 3. Send solution to webhook
        String submitUrl = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String body = "{ \"finalQuery\": \"" + finalQuery.replace("\"", "\\\"") + "\" }";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> submitResponse = restTemplate.postForEntity(submitUrl, entity, String.class);

        System.out.println("📩 Submission Response: " + submitResponse.getBody());
    }

    // ✅ Step 5: Explain SQL query
    private void explainSQL(String sqlQuery) {
        System.out.println("🔹 SQL Query Explained:");
        System.out.println("Query: " + sqlQuery);
        System.out.println("Explanation: ");
        System.out.println("- Selects SALARY from PAYMENTS table");
        System.out.println("- Concatenates employee FIRST_NAME and LAST_NAME as NAME");
        System.out.println("- Calculates AGE using TIMESTAMPDIFF from DOB");
        System.out.println("- Includes DEPARTMENT_NAME from DEPARTMENT table");
        System.out.println("- Excludes payments made on the 1st day of any month");
        System.out.println("- Orders results by SALARY descending");
        System.out.println("- Limits output to the top 1 result");
    }
}
