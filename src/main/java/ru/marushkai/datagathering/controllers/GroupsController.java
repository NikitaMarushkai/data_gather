package ru.marushkai.datagathering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.marushkai.datagathering.services.GroupService;
import ru.marushkai.datagathering.services.UserService;

@Controller
@RequestMapping("/groups")
public class GroupsController {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String parseGroup(String groupId) {
        userService.getUserInfoById(groupService.getUserIdsFromGroup(groupId)).forEach(item -> System.out.println(item.toString()));
        return "";
    }
}
