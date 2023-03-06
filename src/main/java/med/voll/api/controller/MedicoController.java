package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
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

        /*
        Há um padrão de nomenclatura do Spring Data, que se criarmos um método com um padrão correto, ele consegue
        montar a query automaticamente.
        Find all + By + Atributo
         */
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    //O Parâmetro do DeleteMapping entre chaves mostra que ele é dinâmico e adicionado ao fim da url '/medicos'.
    // PathVariable informa que o id do parâmetro é o mesmo informado na URL.
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        // repository.deleteById(id); -> Essa exclusão é PERMANENTE, apagando o registro do banco de dados.
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

}
