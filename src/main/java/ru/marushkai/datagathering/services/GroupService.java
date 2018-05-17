package ru.marushkai.datagathering.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.objects.newsfeed.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marushkai.datagathering.controllers.VKAuthController;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    // MDK, Лентач, Мемы про машинное обучение, КИномания, Science|Наука
    @Autowired
    VkApiClient vk;

    //    GetResponse getResponse = vk.wall().get(actor)
//            .ownerId(actor.getId())
//            .count(100)
//            .offset(5)
//            .filter(WallGetFilter.OWNER)
//            .execute();
//            getResponse.getItems().forEach(it -> System.out.println(it.getComments().toString()));
    public List<String> getUserIdsFromGroup(String groupId) {
        List<String> members = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            try {
                GetMembersResponse getResponse = vk.groups().getMembers(VKAuthController.actor)
                        .groupId(groupId)
                        .offset(1000 * i)
                        .execute();
                getResponse.getItems().forEach(item -> members.add(item.toString()));
                Thread.sleep(300);
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return members;
    }
}