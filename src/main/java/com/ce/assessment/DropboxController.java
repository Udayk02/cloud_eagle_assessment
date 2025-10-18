package com.ce.assessment;

import com.ce.assessment.config.DropboxProperties;
import com.ce.assessment.model.TeamInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class DropboxController {
    private DropboxService dropboxService;
    private DropboxProperties props;

    @GetMapping("/connect")
    public void connect(HttpServletResponse response) throws IOException {
        String authorizeUrl = "https://www.dropbox.com/oauth2/authorize"
                + "?client_id=" + props.getClientId()
                + "&response_type=code"
                + "&redirect_uri=" + props.getRedirectUri()
                + "&scope=" + props.getScopes();
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping("/oauth2/callback")
    public TeamInfo oauthCallback(@RequestParam("code") String code) {
        String accessToken = dropboxService.exchangeCodeForAccessToken(code);
        return dropboxService.getTeamInfo(accessToken);
    }
}