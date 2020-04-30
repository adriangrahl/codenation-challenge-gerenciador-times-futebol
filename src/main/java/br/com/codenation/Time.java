package br.com.codenation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Time {
	
	private final Long id;
	
	private final String nome;
	
	private final LocalDate dataCriacao;
	
	private final String corUniformePrincipal;
	
	private final String corUniformeSecundario;
	
	private Jogador capitao;
	
	private List<Jogador> jogadores = new ArrayList<>();
	
	private Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario, Jogador capitao) {
		this.id = id;
		this.nome = nome; 
		this.dataCriacao = dataCriacao;
		this.corUniformePrincipal = corUniformePrincipal; 
		this.corUniformeSecundario = corUniformeSecundario;
		this.capitao = capitao;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public String getCorUniformePrincipal() {
		return corUniformePrincipal;
	}

	public String getCorUniformeSecundario() {
		return corUniformeSecundario;
	}
	
	public Jogador getCapitao() {
		return capitao;
	}
	
	public boolean addJogador(Jogador jogador) {
		return jogadores.add(jogador);
	}
	
	public List<Jogador> getJogadores() {
		return this.jogadores;
	}
	
	public void setCapitao(Jogador capitao) {
		this.capitao = capitao;
	}
	
	public static TimeBuilder builder() {
		return new TimeBuilder();
	}

	public static class TimeBuilder {
		
		private Long id;
		
		private String nome;
		
		private LocalDate dataCriacao;
		
		private String corUniformePrincipal;
		
		private String corUniformeSecundario;
		
		private Jogador capitao;
		
		public TimeBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public TimeBuilder withNome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public TimeBuilder withDataCriacao(LocalDate dataCriacao) {
			this.dataCriacao = dataCriacao;
			return this;
		}
		
		public TimeBuilder withCorUniformePrincipal(String corUniformePrincipal) {
			this.corUniformePrincipal = corUniformePrincipal;
			return this;
		}
		
		public TimeBuilder withCorUniformeSecundario(String corUniformeSecundario) {
			this.corUniformeSecundario = corUniformeSecundario;
			return this;
		}
		
		public TimeBuilder withCapitao(Jogador capitao) {
			this.capitao = capitao;
			return this;
		}
		
		public Time build() {
			validarCamposObrigatorios();
			return new Time(this.id, this.nome, this.dataCriacao, this.corUniformePrincipal, this.corUniformeSecundario, this.capitao);
		}

		private void validarCamposObrigatorios() {
			if (this.id == null || this.nome == null || this.nome.equals("") || this.dataCriacao == null || 
					this.corUniformePrincipal == null || this.corUniformePrincipal.equals("") || 
					this.corUniformeSecundario == null || this.corUniformeSecundario.equals(""))
				throw new IllegalArgumentException();
		}
	}

}
