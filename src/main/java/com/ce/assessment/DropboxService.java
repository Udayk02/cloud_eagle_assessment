package com.ce.assessment;

import com.ce.assessment.config.DropboxProperties;
import com.ce.assessment.model.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class DropboxService {
    @Autowired
    private DropboxProperties props;

    private final RestTemplate restTemplate = new RestTemplate();

    public String exchangeCodeForAccessToken(String code) {
        String tokenUrl = "https://api.dropboxapi.com/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("grant_type", "authorization_code");
        form.add("client_id", props.getClientId());
        form.add("client_secret", props.getClientSecret());
        form.add("redirect_uri", props.getRedirectUri());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("access_token")) {
            return body.get("access_token").toString();
        }
        throw new RuntimeException("Failed to retrieve access token: " + response.getStatusCode());
    }

    public TeamInfo getTeamInfo(String accessToken) {
        String teamInfoUrl = "https://api.dropboxapi.com/2/team/get_info";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("null", headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(teamInfoUrl, request, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null) {
            String name = (String) body.get("name");
            int numLicensed = (int) ((Number) body.get("num_licensed_users")).intValue();
            int numUsed = (int) ((Number) body.get("num_used_licenses")).intValue();
            return new TeamInfo(name, numLicensed, numUsed);
        }
        throw new RuntimeException("Failed to retrieve team info: " + response.getStatusCode());
    }
}