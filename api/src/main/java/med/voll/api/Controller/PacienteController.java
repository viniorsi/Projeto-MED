package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.domain.Paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping( "/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity CadastrarPacientes(@RequestBody DadosPaciente dados, UriComponentsBuilder uriBuilder){
            var paciente = new Paciente(dados);
            var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));


    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPaciente(Pageable paginacao){

        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente :: new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizaPaciente(@RequestBody @Valid DadosAtualizaPaciente dados){
      var paciente =  repository.getReferenceById(dados.id());
      paciente.atualizaInfos(dados);

      return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletaPaciente(@PathVariable long id){

        var paciente =  repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/{id}")
    public ResponseEntity detalharPaciente (@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok( new DadosDetalhamentoPaciente(paciente));
    }


}
