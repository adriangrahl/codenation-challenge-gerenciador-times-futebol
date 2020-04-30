package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Jogador {
	
	private Long id;
	
	private Long idTime;
	
	private String nome;
	
	private LocalDate dataNascimento;
	
	private Integer nivelHabilidade;
	
	private BigDecimal salario;
	
	private Jogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		this.id = id;
		this.idTime = idTime;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.nivelHabilidade = nivelHabilidade;
		this.salario = salario;
	}
	
	public Long getId() {
		return id;
	}

	public Long getIdTime() {
		return idTime;
	}

	public String getNome() {
		return nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Integer getNivelHabilidade() {
		return nivelHabilidade;
	}

	public BigDecimal getSalario() {
		return salario;
	}
	
	public static JogadorBuilder builder() {
		return new JogadorBuilder();
	}

	public static class JogadorBuilder {
		
		private Long id;
		
		private Long idTime;
		
		private String nome;
		
		private LocalDate dataNascimento;
		
		private Integer nivelHabilidade;
		
		private BigDecimal salario;
		
		public JogadorBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public JogadorBuilder withIdTime(Long idTime) {
			this.idTime = idTime;
			return this;
		}
		
		public JogadorBuilder withNome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public JogadorBuilder withDataNascimento(LocalDate dataNascimento) {
			this.dataNascimento = dataNascimento;
			return this;
		}
		
		public JogadorBuilder withNivelHabilidade(Integer nivelHabilidade) {
			if (nivelHabilidade != null && (nivelHabilidade < 0 || nivelHabilidade > 100))
				throw new IllegalArgumentException("Nível de habilidade do jogador deve estar entre 0 e 100");
			this.nivelHabilidade = nivelHabilidade;
			return this;
		}
		
		public JogadorBuilder withSalario(BigDecimal salario) {
			this.salario = salario;
			return this;
		}
		
		public Jogador build() {
			validaCamposObrigatorios();
			return new Jogador(this.id, this.idTime, this.nome, this.dataNascimento, this.nivelHabilidade, this.salario);
		}

		private void validaCamposObrigatorios() {
			if (this.id == null || this.idTime == null || this.nome == null || this.nome.equals("") || this.dataNascimento == null || this.nivelHabilidade == null || this.salario == null)
				throw new IllegalArgumentException();
		}
		
	}

}
