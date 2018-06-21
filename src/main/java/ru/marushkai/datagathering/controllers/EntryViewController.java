package ru.marushkai.datagathering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.marushkai.datagathering.domain.VKUser;
import ru.marushkai.datagathering.repositories.AnalysisRepository;
import ru.marushkai.datagathering.repositories.PersonalRepositroy;
import ru.marushkai.datagathering.repositories.VKUserRepository;
import ru.marushkai.datagathering.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/entrant")
public class EntryViewController {

    @Autowired
    VKUserRepository userRepository;
    @Autowired
    PersonalRepositroy personalRepositroy;
    @Autowired
    AnalysisRepository analysisRepository;

    @GetMapping
    public String index(Model model) {
        return "entrant";
    }

    @GetMapping("/data/")
    public String getData(Model model, String fio) {
        String[] result = fio.split(" ");
        List<VKUser> userList = userRepository.findAllByFirstNameAndLastNameLike(result[0].toLowerCase(), result[1].toLowerCase());
        for (VKUser vkUser : userList) {
            vkUser.setAnalysisResult(analysisRepository.findByVkUser(vkUser));
            vkUser.setPersonal(personalRepositroy.findByVkUser(vkUser));
        }
        model.addAttribute("entrants", userList);
        return "entrant";
    }
}
