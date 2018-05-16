package ru.marushkai.datagathering.controllers;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Controller
@RequestMapping("/connect")
public class ConnectController {

    TransportClient transportClient = HttpTransportClient.getInstance();
    VkApiClient vk = new VkApiClient(transportClient);

    private static final String AUTH_URL = "https://oauth.vk.com/authorize"
            + "?client_id={APP_ID}"
            + "&scope={PERMISSIONS}"
            + "&redirect_uri={REDIRECT_URI}"
            + "&display={DISPLAY}"
            + "&v={API_VERSION}"
            + "&response_type=code";

    private static final String API_VERSION = "5.21";

    @RequestMapping("/auth")
    private void auth(String appId) throws IOException {
        String reqUrl = AUTH_URL
                .replace("{APP_ID}", appId)
                .replace("{PERMISSIONS}", "photos,messages")
                .replace("{REDIRECT_URI}", "https://oauth.vk.com/blank.html")
                .replace("{DISPLAY}", "page")
                .replace("{API_VERSION}", API_VERSION);
        try {
            RestTemplate template = new RestTemplate();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URL(reqUrl).toURI());
            }
            System.out.println(template.getForEntity(new URL(reqUrl).toURI(), String.class));
//            System.out.println(new URL(reqUrl).toURI());;
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
    }
}
