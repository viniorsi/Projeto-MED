package med.voll.api.Paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosPaciente(String nome, String email, String telefone, String cpf, DadosEndereco endereco) {

}
