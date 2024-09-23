package regras_negocio;
/**********************************
 * POO - Fausto Ayres
 **********************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.ContaEspecial;
import modelo.Correntista;
import modelo.Conta;
import repositorio.Repositorio;

public class Fachada {
	private Fachada() {}		
	private static Repositorio repositorio = new Repositorio();	
	
	public static ArrayList<Conta> listarContas() {
		return repositorio.getContas();
	}
	public static ArrayList<Correntista> listarCorrentista() {
		ArrayList<Correntista> lista_correntistas = repositorio.getCorrentistas();
		Collections.sort(lista_correntistas, (c1, c2) -> c1.getCpf().compareTo(c2.getCpf()));
		return lista_correntistas;
		
	}
	public static Conta localizarConta(int id) {
		return repositorio.localizarConta(id);
	}
	public static Correntista localizarCorrentista(String cpf) 	{
		return repositorio.localizarCorrentista(cpf);
	}
	
	public static void criarConta(String cpf) throws Exception {

		// localizar Conta no repositorio, usando o nome 
		// Conta c = repositorio.localizarConta(id);
		// if (c!=null)
		// 	throw new Exception("N�o criou Conta: " + id + " ja cadastrado(a)");
		// criar objeto Conta 
			Correntista cr = repositorio.localizarCorrentista(cpf);

			if(cr == null){
				throw new Exception("Correntista " + cpf + "não está cadastrado.");
			}
			if(cr.getContaTitular() != 0){
				throw new Exception("Correntista " + cpf + "já é titular de outra conta");
			}

			LocalDate data = LocalDate.now();
			DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataFormatada = data.format(formater);
			Conta c = new Conta (repositorio.gerarIdConta(),dataFormatada);

			cr.adicionar(c);
			c.adicionar(cr);
			c.setTitular(cpf);
			cr.setContaTitular(c.getId());
			

			//adicionar Conta no reposit�rio
			repositorio.adicionar(c);
			// // //gravar reposit�rio
			repositorio.salvarObjetos();
	}	

	public static void criarContaEspecial(String cpf,  double limite) throws Exception {
		
		//localizar Conta no repositorio, usando o nome 
		// Conta p = repositorio.localizarConta(nome);
		// if (p!=null)
		// 	throw new Exception("criar convidado: " + nome + " ja cadastrado(a)");
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null){
			throw new Exception("Correntista " + cpf + "não está cadastrado.");
		}
		if(cr.getContaTitular() != 0){
			throw new Exception("Correntista " + cpf + "já é titular de outra conta");
		}

		if(limite < 50){
			throw new Exception("Limite não pode ser abaixo de R$50.00");
		}
		//criar objeto ContaEspecial 
		LocalDate data = LocalDate.now();
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = data.format(formater);
		ContaEspecial ce = new ContaEspecial (repositorio.gerarIdConta(), dataFormatada, limite);

		cr.adicionar(ce);
		ce.adicionar(cr);
		ce.setTitular(cpf);
		cr.setContaTitular(ce.getId());

		//adicionar convidado no reposit�rio
		repositorio.adicionar(ce);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}
	
	public static void criarCorrentista (String cpf, String nome, String senha) throws Exception {
		cpf.trim();
		//localizar Correntista no repositorio, usando a data 
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if (cr!=null)
			throw new Exception("criar Correntista: " + cpf + " : " + nome + " já existe.");
		
		if (!Pattern.matches( "^\\d{4}$", senha))
			throw new Exception("criar Correntista: senha deve conter 4 dígitos numéricos. ");

		//gerar id no repositorio
		cr = new Correntista(cpf, nome, senha);	
		
		//adicionar Correntista no reposit�rio
		repositorio.adicionar(cr);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void 	inserirCorrentistaConta( String cpf, int id) throws Exception {

		//localizar Conta no repositorio, usando o nome 
		Conta c = repositorio.localizarConta(id);
		if(c == null) 
			throw new Exception("adicionar Conta:  " + id + " inexistente");

		//localizar Correntista no repositorio, usando id 
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null) 
			throw new Exception("adicionar Conta: Correntista " + cpf + " inexistente");

		//localizar o Conta no Correntista, usando o nome
		Correntista paux = c.localizar(cpf);
		if(paux != null) 
			throw new Exception("N�o adicionou Correntista: " + cpf + " j� participa de conta " + id);

		//adicionar o Conta ao Correntista
		c.adicionar(cr);
		//adicionar o Correntista ao Conta
		cr.adicionar(c);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void 	removerCorrentistaConta(String cpf, int id) throws Exception {

		//localizar Conta no repositorio, usando o nome 
		Conta c = repositorio.localizarConta(id);
		if(c == null) 
			throw new Exception("remover Correntista de Conta:  " + id + " inexistente");

		//localizar Correntista no repositorio, usando id 
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null) 
			throw new Exception("remover Correntista de Conta: Correntista " + cpf + " inexistente");

		if(cr.getContaTitular() == c.getId()){
			throw new Exception("Correntista " + cpf + " é titular de " + id + ". Não pode ser removido");
		}

		//localizar o Conta no Correntista, usando o nome
		Correntista paux = c.localizar(cpf);
		if(paux == null) 
			throw new Exception("remover Correntista: " + cpf + " não participa de conta " + id);

		//adicionar o Conta ao Correntista
		c.remover(cr);
		//adicionar o Correntista ao Conta
		cr.remover(c);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void apagarConta(int id) throws Exception	{
		//localizar Correntista no repositorio, usando cpf 
		Conta c = repositorio.localizarConta(id);
		if (c == null)
			throw new Exception("apagar Conta: id " + id + " inexistente");
		
		if(c.getSaldo() != 0){
			throw new Exception("apagar Conta: conta " + id + " possui saldo diferente de 0.");
		}

		//Remover todos os Correntistas desta Conta
		for(Correntista cr : c.getCorrentistas()) {
			if(cr.getContaTitular() == id){
				cr.setContaTitular(0);
			}
			cr.remover(c);
		}
		c.getCorrentistas().clear();
		c.setTitular(null);
		
		//remover Correntista do reposit�rio
		repositorio.remover(c);
		//gravar reposit�rio
		repositorio.salvarObjetos();
	}

	public static void creditarValor(int id, String cpf, String senha, double valor) throws Exception{
		//localizar Correntista no repositorio, usando id 
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null) 
			throw new Exception("creditar valor: Correntista " + cpf + " inexistente");
		
		if(!cr.getSenha().equals(senha))
			throw new Exception("creditar valor: Senha incorreta");
		
		Conta c = cr.localizar(id);
		if(c == null)
			throw new Exception("creditar valor: Conta " + id + "não associada a Correntista " + cpf);
		
		c.creditar(valor);

	}
	public static void debitarValor(int id, String cpf, String senha, double valor) throws Exception{
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null) 
			throw new Exception("debitar valor: Correntista " + cpf + " inexistente");
		
		if(!cr.getSenha().equals(senha))
			throw new Exception("debitar valor: Senha incorreta");
		
		Conta c = cr.localizar(id);
		if(c == null)
			throw new Exception("debitar valor: Conta " + id + "não associada a Correntista " + cpf);

		if(c instanceof ContaEspecial == false){
			if(c.getSaldo() - valor < 0){
				throw new Exception("debitar valor: Conta " + id + "não pode ficar com saldo negativo.");
			}else{
				c.debitar(valor);
			}
		}else{
			c.debitar(valor);
		}
	}
	public static void tranferirValor(int id1, String cpf, String senha, double valor, int id2) throws Exception{
		Correntista cr = repositorio.localizarCorrentista(cpf);
		if(cr == null) 
			throw new Exception("debitar valor: Correntista " + cpf + " inexistente");
		
		if(!cr.getSenha().equals(senha))
			throw new Exception("debitar valor: Senha incorreta");
		
		Conta c1 = cr.localizar(id1);
		if(c1 == null)
			throw new Exception("debitar valor: Conta " + id1 + "não associada a Correntista " + cpf);

		Conta c2 = repositorio.localizarConta(id2);
		if(c2 == null)
			throw new Exception("transferir valor: Conta " + id2 + "não encontrada.");

		c1.transferir(valor, c2);
	}

}
