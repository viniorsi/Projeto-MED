package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

   private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String numero;
    private String uf;
    private String complemento;

 public Endereco(DadosEndereco dados) {

  this.logradouro = dados.logradouro();
  this.bairro = dados.bairro();
  this.cep = dados.cidade();
  this.uf = dados.uf();
  this.cidade = dados.cidade();
  this.numero = dados.numero();
  this.complemento = dados.complemento();

 }
}
