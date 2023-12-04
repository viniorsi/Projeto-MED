package med.voll.api.domain.Paciente;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "Pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Paciente(DadosPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizaInfos(DadosAtualizaPaciente dados) {

        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }

        if(dados.email() != null) {
            this.email = dados.email();
        }

        if(dados.endereco() != null) {
            this.endereco.atualizarInfos(dados.endereco());
        }



    }

    public void excluir() {
            this.ativo = false;
    }
}
