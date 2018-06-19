package ru.marushkai.datagathering.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entrant")
public class EntryViewController {

    @GetMapping
    public String index(Model model) {
        return "entrant";
    }
}
