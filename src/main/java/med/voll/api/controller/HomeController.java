package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @GetMapping
    public String welcomeMessage() {
        return "Seja bem vindo ao Medicina Lang. O site está em construção!";
    }
}
