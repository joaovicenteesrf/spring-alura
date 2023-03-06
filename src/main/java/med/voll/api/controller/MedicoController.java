package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    /*
    O atributo 'Autowired' serve para que o próprio Spring instancie o objeto e passe ele para a controller através da
    injeção de dependências.
     */
    @Autowired
    private MedicoRepository repository;

    /*
    Nessa classe está sendo utilizado o padrão DTO - Data Transfer Object, um padrão muito utilizado em API's para
    representar os dados que chegam e são devolvidos à API. Sempre que for necessário receber ou devolver uma informação
    da API, esse padrão será utilizado.
     */
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        /*
        A anotação 'RequestBody' mapeia que o parâmetro json vem do corpo da requisição, enviada pelo postman.
         */

        /*
        O método save é herdado pelo repository e ele é o responsável por salvar os dados no banco de dados.
         */
        repository.save(new Medico(dados));
    }

}
