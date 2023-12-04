package med.voll.api.domain.Paciente;

public record DadosListagemPaciente(long id, String nome, String email, String cpf, boolean ativo ) {

    public DadosListagemPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.isAtivo());
    }
}
