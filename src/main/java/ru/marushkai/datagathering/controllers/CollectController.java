package ru.marushkai.datagathering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.marushkai.datagathering.repositories.GroupRepository;
import ru.marushkai.datagathering.services.GroupService;
import ru.marushkai.datagathering.services.UserService;


@Controller
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;
    @Autowired
    GroupRepository groupRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("groups", groupRepository.findAll());
        return "choose_collect";
    }

    @RequestMapping(value = "/parse", method = RequestMethod.GET)
    public String parseGroup(String[] group) {
        userService.saveUsers(groupService.getUserIdsFromGroup(group));
        return "index";
    }
}
