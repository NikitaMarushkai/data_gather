package ru.marushkai.datagathering.controllers;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.wall.WallGetFilter;
import org.codehaus.groovy.classgen.Verifier;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/vk")
public class VKAuthController {
    private String clientId = "6470626";
    private String clientSecret = "RCPywHMJmIHwcdGn9wfS";
    private String redirectCallBackUri = "http://localhost:8080/vk/callback";
    private String scope = "friends,photos,audio,video,stories,pages,status,notes,wall,offline,docs,groups,stats";
    //    private String userProfileUri = "https://oauth.vk.com/blank.html";
    TransportClient transportClient = HttpTransportClient.getInstance();
    @Autowired
    VkApiClient vk;
    public static UserActor actor;


    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void vkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String secretState = "secret" + new Random().nextInt(999_999);
        request.getSession().setAttribute("SECRET_STATE", secretState);

        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .callback(redirectCallBackUri)
                .scope(scope)
                .state(secretState)
                .build(VkontakteApi.instance());

        final String redirectURL = service.getAuthorizationUrl();

        response.sendRedirect(redirectURL);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           HttpServletRequest request, ModelMap model) throws IOException {


        try {
            UserAuthResponse authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(Integer.parseInt(clientId), clientSecret,
                            redirectCallBackUri, code)
                    .execute();
            actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "index";
    }
}