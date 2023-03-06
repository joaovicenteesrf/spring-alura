package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    /*
    Nessa classe está sendo utilizado o padrão DTO - Data Transfer Object, um padrão muito utilizado em API's para
    representar os dados que chegam e são devolvidos à API. Sempre que for necessário receber ou devolver uma informação
    da API, esse padrão será utilizado.
     */
    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        /*
        A anotação 'RequestBody' mapeia que o parâmetro json vem do corpo da requisição, enviada pelo postman.
        Quando a requisição do tipo POST é feita, o body dela é retornado no system.out.
         */
        System.out.println(dados);
    }

}
