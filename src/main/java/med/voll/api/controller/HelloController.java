package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 A anotação 'RestController' serve para definir que a classe é uma controller, em uma aplicação do tipo Rest.
 A anotação 'RequestMapping' define a qual URL esse mapeamento irá responder.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /*
     Anotação 'GetMapping' significa que, quando chegar uma requisição do tipo GET para a url '/hello',
     o método abaixo será chamado.
     */
    @GetMapping
    public String helloWorld() {
        return "Hello World Spring!";
    }
}
