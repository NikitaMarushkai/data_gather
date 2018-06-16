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
            GetResponse getResponse = vk.wall().get(actor)
                    .ownerId(actor.getId())
                    .count(100)
                    .offset(5)
                    .filter(WallGetFilter.OWNER)
                    .execute();
            getResponse.getItems().forEach(it -> System.out.println(it.getComments().toString()));
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
//        final OAuth20Service service = new ServiceBuilder(clientId)
//                .apiKey(clientId)
//                .apiSecret(clientSecret)
//                .callback(redirectCallBackUri)
//                .build(VkontakteApi.instance());
//
//
//        final Verifier verifier = new Verifier();
//        OAuth2AccessToken accessToken = null;
//        try {
//            accessToken = service.getAccessToken(code);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, userProfileUri);
//        service.signRequest(accessToken, oauthRequest);
//
//        final Response resourceResponse;
//        try {
//            resourceResponse = service.execute(oauthRequest);
//            final JSONObject obj = new JSONObject(resourceResponse.getBody());
//
//            final String userId = obj.getString("uid");
//            final String first_name = obj.getString("first_name");
//            final String last_name = obj.getString("last_name");
//
//            System.out.println(userId);
//            System.out.println(first_name);
//            System.out.println(last_name);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        return "index";
    }
}

//        if (userService.findOne(Long.parseLong(userId)) != null) {
//            request.getSession().setAttribute("VK_ACCESS_TOKEN", accessToken);
//            model.addAttribute("user", userService.findOne(Long.parseLong(userId)));
//            final Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (user instanceof User) {
//                return "account";
//            } else {
//                return "/personalarea";
//            }
//        } else {
//            final User user = new User();
//            user.setFirst_name(first_name);
//            user.setLast_name(last_name);
//            model.addAttribute("user", user);
//            return "/registration";
//        }