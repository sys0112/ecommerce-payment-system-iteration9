package com.simple.StES.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class IamportService {

	 @Value("${iamport.api_key}")
	    private String apiKey;

	    @Value("${iamport.api_secret}")
	    private String apiSecret;
	    

	    private static final String API_URL = "https://api.iamport.kr";

	    @Autowired
	    private RestTemplate restTemplate;

	    // Iamport API 토큰 가져오기
	    public String getToken() {
	        String tokenUrl = API_URL + "/users/getToken";
	        
	        Map<String, String> body = new HashMap<>();
	        body.put("imp_key", apiKey);
	        body.put("imp_secret", apiSecret);

	        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, body, Map.class);
	        Map<String, Object> responseBody = response.getBody();
	        
	        if (responseBody != null && "success".equals(responseBody.get("code").toString())) {
	            Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
	            return (String) responseData.get("access_token");
	        }
	        
	        throw new RuntimeException("Failed to get Iamport API token");
	    }

	    // 결제 검증 메소드 예시
	    public Map<String, Object> getPaymentByImpUid(String impUid) {
	        String token = getToken();
	        String paymentUrl = API_URL + "/payments/" + impUid;

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", token);

	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        ResponseEntity<Map> response = restTemplate.exchange(paymentUrl, HttpMethod.GET, entity, Map.class);
	        
	        return response.getBody();
	    }
}
