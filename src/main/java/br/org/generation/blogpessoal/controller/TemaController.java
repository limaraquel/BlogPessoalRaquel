package br.org.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Tema;
import br.org.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/tema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	@Autowired
	private TemaRepository repository;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id) {
		return repository.findById(id)
		.map(resp -> ResponseEntity.ok(resp))
		.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String nome) {
		return ResponseEntity.ok(repository.findByDescricaoContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@Valid @RequestBody Tema tema) {
		return repository.findById(tema.getId())
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(tema)))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTema(@PathVariable long id) {
		return repository.findById(id)
			.map(resposta -> {
				repository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}

