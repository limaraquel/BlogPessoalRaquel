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

import br.org.generation.blogpessoal.model.Postagem;
import br.org.generation.blogpessoal.repository.PostagemRepository;

/**
 * 
 * Annotation @RestController: indica que a Classe é uma RestController, ou seja, 
 * é responsável por responder às requisições http enviadas para um endpoint 
 * (endereço) definido na anotação @RequestMapping
 * 
 * Annotation @RequestMapping("/postagens"): indica o endpoint (endereço) que a 
 * controladora responderá as requisições 
 * 
 * Annotation @CrossOrigin("*"): indica que a classe controladora permitirá o 
 * recebimento de requisições realizadas de fora do domínio (localhost, em nosso caso) ao qual 
 * ela pertence. Essa anotação é essencial para que o front-end (Angular em nosso caso), tenha
 * acesso à nossa API (O termo técnico é consumir a API)
 * 
 * Para as versões mais recentes do Angular, recomenda-se alterar esta anotação para: 
 * a annotation @CrossOrigin(origins = "*", allowedHeaders = "*") 
 * Esta anotação, além de liberar as origens, libera também os cabeçalhos das requisições
 * 
 */ 

@RestController
@RequestMapping ("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	/*
	 * 
	 * Injeção de Dependência (@Autowired): Consiste  na  maneira,  ou  seja,  na  implementação 
	 * utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for 
	 * necessário.
	 * 
	 * A Injeção de Dependência define quais classes serão instanciadas e em quais lugares serão 
	 * injetadas quando houver necessidade. 
	 * 
	 * Em nosso exemplo a classe controladora cria um ponto de injeção da interface PostagemRepository, 
	 * e quando houver a necessidade o Spring Framework irá criar uma instância (objeto) desta interface
	 * permitindo o uso de todos os métodos (padrão ou personalizados em PostagemRepository)
	 *  
	 * */
	
	@Autowired
	private PostagemRepository repository; 
	
	/**
	 * Listar todas as Postagens
	 *  
	 * Annotation @GetMapping: indica que o método abaixo responderá todaas as 
	 * requisições do tipo GET que forem enviadas no endpoint /postagens
	 * 
	 * O Método getAll() será do tipo ResponseEntity porque ele responderá a requisição (Request),
	 * com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo List (Collection) preenchido com objetos do tipo Postagem,
	 * que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAll());: Executa o método findAll(), que é um
	 * método padrão da interface JpaRepository e retorna o status OK = 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 */
	
	  @GetMapping
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll()); 
	
	  }
	  
	  @GetMapping("/{id}")
		public ResponseEntity<Postagem> getById(@PathVariable long id) {
			return repository.findById(id)
					.map(resp -> ResponseEntity.ok(resp))
					.orElse(ResponseEntity.notFound().build());

	  }
	  
	  @GetMapping("/titulo/{titulo}")
		public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
			return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	  }


      @PostMapping
       public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
	     return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
}
      
      @PutMapping
  	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
  		return repository.findById(postagem.getId())
  			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)))
  			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  	}

  	@DeleteMapping("/{id}")
  	public ResponseEntity<?> deletePostagem(@PathVariable long id) {
  		return repository.findById(id)
  			.map(resposta -> {
  				repository.deleteById(id);
  				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  			})
  			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}
}

	  
		