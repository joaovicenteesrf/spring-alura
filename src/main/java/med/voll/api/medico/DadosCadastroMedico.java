package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

/*
Essa é uma classe do tipo Record. O java criará uma classe imutável, quando necessário, onde cada um dos campos será
um atributo, os métodos get, set e construtores serão gerados sem a necessidade de digitar tudo manualmente.
Dessa maneira o código fica muito mais enxuto e menos verboso.
 */
public record DadosCadastroMedico(
        //'NotBlank' verifica se está vazio e se não é nulo, mas apenas para campos do tipo string
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        // 'Pattern' é uma expressão regular que identifica o CRM como tendo de 4 a 6 dígitos
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull
        // 'Valid' serve para validar quando há outro objeto do tipo record, que também terá validações internas
        @Valid
        DadosEndereco endereco) {
}
