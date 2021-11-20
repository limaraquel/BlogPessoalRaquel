package br.org.generation.blogpessoal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A anotação @Entity indica que a classe é uma entidade, ou seja, ele será
 * utilizada para gerar uma tabela no Banco de Dados.
 * 
 * A anotação @Table indica o nome da tabela no Banco de dados. Caso ela não
 * seja declarada, o Banco criará a tabela com o mesmo nome da classe.
 */

@Entity
@Table(name = "postagens")
public class Postagem {

	/**
	 * A anotação @ID inidica que o atributo é a chave primária da tabela
	 * 
	 * A anotação @GeneratedValue indica que a chave primária será do tipo
	 * auto-incremento.
	 * 
	 * O parâmetro strategy indica como será gerada a chave.
	 * 
	 * GenerationType.IDENTITY indica que será uma sequência numérica iniciando em
	 * 1.
	 * 
	 * Não confundir o auto-incremento do Banco de Dados que inicia em 1 com o
	 * indice de um Array (Vetor ou Matriz) que inicia em 0.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * A anotação @NotNull indica que um atributo não pode ser nulo
	 * 
	 * O parâmtero message inidica a mensagem que será exibida caso o atributo seja
	 * nulo
	 * 
	 * Não confundir nulo com branco (você não obriga que o atributo seja
	 * preenchido, podendo ficar em branco).
	 * 
	 * Para evitar que o atributo fique em branco utilize a anotação @NotBlank
	 * 
	 * A anotação @Size tem a função de definir o tamanho minimo e máximo de
	 * caracteres de um atributo.
	 * 
	 * ***IMPORTANTE***
	 * 
	 * Para utilizar estas anotações, não se esqueça de inserir a dependência
	 * Validation na criação do projeto ou insira diretamente no arquivo pom.xml
	 */

	@NotNull(message = "Amigx, esse campo não pode ficar vazio! Coloca um título aí por favor <3")
	@Size(min = 5, max = 100, message = "O título deve conter no mínimo 5 e no máximo 100 caracteres migx!")
	private String titulo;
	@NotNull(message = "Gatx, não esquece de colocar um textinho aí por favor! ;)")
	@Size(min = 10, max = 500, message = "Seu textro deve conter no mínimo 10 e no máximo 500 caracteres migx! ;)")
	private String texto;
	/**
	 * A anotação @Temporal: Indica se o atributo receberá uma data ou um Timestamp
	 * (Data e hora do sistema)
	 * 
	 * System.currentTimeMillis(): insere os milisegundos na hora
	 * 
	 */

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new java.sql.Date(System.currentTimeMillis());

	/**
	 * 
	 * Métodos Get e Set
	 * 
	 */
	@ManyToOne
	@JsonIgnoreProperties ("postagem")
	private Tema tema;

	@ManyToOne
	@JsonIgnoreProperties ("postagem")
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;

	}
}
