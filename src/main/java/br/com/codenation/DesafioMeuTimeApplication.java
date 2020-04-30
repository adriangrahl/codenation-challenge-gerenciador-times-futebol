package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

import static java.util.Comparator.comparing;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	
	private List<Time> meusTimes = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		
		boolean idJaUtilizado = this.buscarTimes().stream()
			.anyMatch(idTime -> idTime.equals(id));
		
		if (idJaUtilizado)
			throw new IdentificadorUtilizadoException();
		
		Time time = Time.builder()
				.withId(id)
				.withNome(nome)
				.withDataCriacao(dataCriacao)
				.withCorUniformePrincipal(corUniformePrincipal)
				.withCorUniformeSecundario(corUniformeSecundario)
				.build();
		meusTimes.add(time);
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		
		Jogador jogador = Jogador.builder()
				.withId(id)
				.withIdTime(idTime)
				.withNome(nome)
				.withDataNascimento(dataNascimento)
				.withNivelHabilidade(nivelHabilidade)
				.withSalario(salario)
				.build();
		
		Time time = meusTimes.stream()
				.filter(t -> t.getId().equals(idTime))
				.findFirst()
				.orElseThrow(TimeNaoEncontradoException::new);
		
		
		boolean idJaUtilizado = meusTimes.stream()
			.flatMap(t -> t.getJogadores().stream())
			.map(Jogador::getId)
			.anyMatch(idExistente -> id.equals(idExistente));
		
		if (idJaUtilizado)
			throw new IdentificadorUtilizadoException();
		
		time.addJogador(jogador);
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		
		Jogador jogador = meusTimes.stream()
				.map(Time::getJogadores)
				.flatMap(List::stream)
				.filter(j -> j.getId().equals(idJogador))
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
		
		meusTimes.stream()
			.filter(t -> t.getId().equals(jogador.getIdTime()))
			.findFirst()
			.ifPresent(time -> time.setCapitao(jogador));
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Time time = buscarTime(idTime);
		Jogador capitao = Optional.ofNullable(time.getCapitao()).orElseThrow(CapitaoNaoInformadoException::new);
		return capitao.getId();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return meusTimes.stream()
			.flatMap(time -> time.getJogadores().stream())
			.filter(jogador -> jogador.getId().equals(idJogador))
			.map(Jogador::getNome)
			.findFirst()
			.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		Time time = buscarTime(idTime);
		return time.getNome();
	}
	
	private Time buscarTime(Long id) {
		return meusTimes.stream()
				.filter(time -> time.getId().equals(id))
				.findFirst()
				.orElseThrow(TimeNaoEncontradoException::new);
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		Time time = buscarTime(idTime);
		return time.getJogadores().stream()
				.map(Jogador::getId)
				.sorted()
				.collect(Collectors.toList());
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Comparator<Jogador> comparator = comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId);
		Time time = buscarTime(idTime);
		return time.getJogadores().stream()
			.sorted(comparator)
			.findFirst()
			.orElseThrow(JogadorNaoEncontradoException::new)
			.getId();
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Comparator<Jogador> comparator = comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId);
		Time time = buscarTime(idTime);
		return time.getJogadores().stream()
				.sorted(comparator)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new)
				.getId();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return meusTimes.stream()
			.map(Time::getId)
			.sorted()
			.collect(Collectors.toList());
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Comparator<Jogador> comparator = comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId);
		Time time = buscarTime(idTime);
		return time.getJogadores().stream()
				.sorted(comparator)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new)
				.getId();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return meusTimes.stream()
				.flatMap(time -> time.getJogadores().stream())
				.filter(jogador -> jogador.getId().equals(idJogador))
				.map(Jogador::getSalario)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		Comparator<Jogador> comparator = comparing(Jogador::getNivelHabilidade)
				.reversed().thenComparing(Jogador::getId);
		return meusTimes.stream()
				.flatMap(time -> time.getJogadores().stream())
				.sorted(comparator)
				.limit(top)
				.map(Jogador::getId)
				.collect(Collectors.toList());
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		Time daCasa = buscarTime(timeDaCasa);
		Time deFora = buscarTime(timeDeFora);

		String corUniformePrincipalDeFora = deFora.getCorUniformePrincipal();
		String corUniformeSecundarioDeFora = deFora.getCorUniformeSecundario();
		
		if (daCasa.getCorUniformePrincipal().equalsIgnoreCase(corUniformePrincipalDeFora))
			return corUniformeSecundarioDeFora;
		else
			return corUniformePrincipalDeFora;
	}

}
