package ru.marushkai.datagathering.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marushkai.datagathering.controllers.VKAuthController;

import java.util.List;

@Service
public class UserService {

    @Autowired
    VkApiClient vk;

    public List<UserXtrCounters> getUserInfoById(List<String> userIds) {
        List<UserXtrCounters> users = null;
        try {
            users = vk.users().get(VKAuthController.actor)
                    .userIds(userIds)
                    .fields(UserField.ABOUT, UserField.ACTIVITIES, UserField.CAREER, UserField.CITY)
                    .execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return users;
    }
}
