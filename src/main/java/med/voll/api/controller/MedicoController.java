package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        /*
        A anotação 'RequestBody' mapeia que o parâmetro json vem do corpo da requisição, enviada pelo postman.
         */

        var medico = new Medico(dados);

        /*
        O método save é herdado pelo repository e ele é o responsável por salvar os dados no banco de dados.
         */
        repository.save(medico);

        /*
        uriBuilder encapsula a URL, pois após o deploy não será mais localhost. A url passará o id do médico
        dinamicamente e o método buildAndExpand serve para resgatar esse id no banco de dados.
         */
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        var dto = new DadosDetalhamentoMedico(medico);

        // Devolverá o código 201 (Created). Devolve no corpo da resposta os dados do novo recurso/registro criado
        // Devolve também um cabeçalho do protocolo HTTP (Location)
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public  ResponseEntity<Page<DadosListagemMedico>> listar(Pageable paginacao) {
        /* O método findAll retorna um objeto do tipo Medico, mas é necessário que retorne um objeto do tipo
        DadosListagemMedico, por isso há a conversão entre o tipo de retorno.

            O 'Pageable' realiza a paginação automaticamente.
         */

        /*
        Há um padrão de nomenclatura do Spring Data, que se criarmos um método com um padrão correto, ele consegue
        montar a query automaticamente.
        Find all + By + Atributo
         */
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    //O Parâmetro do DeleteMapping entre chaves mostra que ele é dinâmico e adicionado ao fim da url '/medicos'.
    // PathVariable informa que o id do parâmetro é o mesmo informado na URL.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        // repository.deleteById(id); -> Essa exclusão é PERMANENTE, apagando o registro do banco de dados.
        var medico = repository.getReferenceById(id);
        medico.excluir();

        // ResponseEntity é uma classe do Spring que controla a resposta devolvida pela Framework.
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
