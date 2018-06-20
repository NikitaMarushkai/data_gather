package ru.marushkai.datagathering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.marushkai.datagathering.repositories.VKUserRepository;
import ru.marushkai.datagathering.services.UserService;

@Controller
@RequestMapping("/entrant")
public class EntryViewController {

    @Autowired
    VKUserRepository userRepository;

    @GetMapping
    public String index(Model model) {
        return "entrant";
    }

    @GetMapping("/data/")
    public String getData(Model model, String fio) {
        String[] result = fio.split(" ");
        model.addAttribute("entrants", userRepository.findAllByFirstNameAndLastNameLike(result[0].toLowerCase(), result[1].toLowerCase()));
        return "entrant";
    }
}
