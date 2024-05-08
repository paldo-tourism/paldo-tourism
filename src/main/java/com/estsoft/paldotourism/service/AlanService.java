package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.AlanDto;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AlanService {
    private static final String AI_API_URL = "https://kdt-api-function.azurewebsites.net/api/v1/question";
    private static final String CLIENT_ID = "a98e068e-aa3d-4b61-838f-ff25d2ce66a1"; // 클라이언트 아이디

    public String askAi(AlanDto alanDto) {
        RestTemplate restTemplate = new RestTemplate();

        String questionDummy = alanDto.makeDummy();
        String url = getUrl(questionDummy);
        String response = restTemplate.getForObject(url, String.class);
        String contentPart = getContentPart(response);

        return removeSource(contentPart);
    }

    @NotNull
    private static String getUrl(String questionDummy) {
        String url = UriComponentsBuilder.fromHttpUrl(AI_API_URL)
                .queryParam("content", questionDummy)
                .queryParam("client_id", CLIENT_ID)
                .toUriString();
        return url;
    }

    private String getContentPart(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("content");
    }

    private String removeSource(String requestJson) {
        try {
            // 출처 및 링크 제거
            return requestJson.replaceAll("출처\\d*\\s*\\(.*?\\)", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while processing the request";
        }
    }



}
