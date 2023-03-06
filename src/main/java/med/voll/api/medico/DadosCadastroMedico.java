package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

/*
Essa é uma classe do tipo Record. O java criará uma classe imutável, quando necessário, onde cada um dos campos será
um atributo, os métodos get, set e construtores serão gerados sem a necessidade de digitar tudo manualmente.
Dessa maneira o código fica muito mais enxuto e menos verboso.
 */
public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, DadosEndereco endereco) {
}
