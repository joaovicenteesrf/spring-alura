package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    Nessa classe é utilizado o padrão DTO — Data Transfer Object, um padrão muito utilizado em API's para
    representar os dados que chegam e são devolvidos à API. Sempre que for necessário receber ou devolver uma informação
    da API, esse padrão será utilizado.
     */
    @PostMapping
    // Anotação 'Transactional' é necessária, pois o método realiza alterações no banco de dados
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        /*
        A anotação 'RequestBody' mapeia que o parâmetro json vem do corpo da requisição, enviada pelo postman.
         */

        /*
        O método save é herdado pelo repository e ele é o responsável por salvar os dados no banco de dados.
         */
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        /* O método findAll retorna um objeto do tipo Medico, mas é necessário que retorne um objeto do tipo
        DadosListagemMedico, por isso há a conversão entre o tipo de retorno.

            O 'Pageable' realiza a paginação automaticamente.
         */

        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

}
