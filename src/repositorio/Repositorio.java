package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;

public class Repositorio {
	private ArrayList<Conta> contas = new ArrayList<>();
	private ArrayList<Correntista> correntistas = new ArrayList<>(); 

	public Repositorio() {
		carregarObjetos();
	}
	public void adicionar(Conta c)	{
		contas.add(c);
	}

	public void remover(Conta c)	{
		contas.remove(c);
	}

	public Conta localizarConta(int id)	{
		for(Conta c : contas)
			if(c.getId() == id)
				return c;
		return null;
	}

	public void adicionar(Correntista cr)	{
		correntistas.add(cr);
	}
	public void remover(Correntista cr)	{
		correntistas.remove(cr);
	}

	public Correntista localizarCorrentista(String cpf)	{
		for(Correntista cr : correntistas)
			if(cr.getCpf().equals(cpf))
				return cr;
		return null;
	}

	public ArrayList<Conta> getContas() {
		return contas;
	}
	
	public ArrayList<Correntista> getCorrentistas() {
		return correntistas;
	}

	public int getTotalConta()	{
		return contas.size();
	}

	public int getTotalcorrentistas()	{
		return correntistas.size();
	}

	public int gerarIdConta() {
		if (contas.isEmpty())
			return 1;
		else {
			Conta ultimo = contas.get(contas.size()-1);
			return ultimo.getId() + 1;
		}
	}
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\correntistas.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\contas.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	
		Correntista cr;
		Conta c;

		try	{
			String cpf, nome, senha ;
			int contaTitular;
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				cpf = partes[0];
				nome = partes[1];
				senha = partes[2];
				contaTitular = Integer.parseInt(partes[3]);
				cr = new Correntista(cpf, nome, senha);
				cr.setContaTitular(contaTitular);
				this.adicionar(cr);
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
		}

		try	{
			String tipo, data, cpfs, titular;
			int id;
			double saldo,limite;

			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				tipo = partes[0];
				id = Integer.parseInt(partes[1]);
				data = partes[2];
				saldo = Double.parseDouble(partes[3]);
				cpfs="";
				if(tipo.equals("Conta")) {
					c = new Conta(id,data);
					this.adicionar(c);
					titular = partes[5];
					c.setTitular(titular);
					if(partes.length>4)
						cpfs = partes[4];		//cpfs dos correntistas separados por ","
				}
				else {
					limite = Double.parseDouble(partes[4]);
					c = new ContaEspecial(id,data, limite);
					this.adicionar(c);
					titular = partes[6];
					c.setTitular(titular);
					if(partes.length>5)
						cpfs = partes[5];		//cpfs dos correntistas separados por ","
				}

				//relacionar Conta com os seus correntistas
				if(!cpfs.isEmpty()) {	
					for(String idCorrentista : cpfs.split(",")){	//converter string em array
						cr = this.localizarCorrentista(idCorrentista);
						cr.adicionar(c);
						c.adicionar(cr);
						c.setSaldo(saldo); //Atualiza o saldo com o valor salvo.
					}
				}
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de contas:"+ex.getMessage());
		}
	}

	//--------------------------------------------------------------------
	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que est�o no reposit�rio
		try	{
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Correntista cr : correntistas) 	{
				arquivo1.write(cr.getCpf()+";"+cr.getNome()+";"+cr.getSenha()+";"+cr.getContaTitular()+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na cria��o do arquivo  correntistas "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			ArrayList<String> lista ;
			String listaId;
			for(Conta c : contas) {
				//montar uma lista com os id dos correntistas do Conta
				lista = new ArrayList<>();
				for(Correntista cr : c.getCorrentistas()) {
					lista.add(cr.getCpf()+"");
				}
				listaId = String.join(",", lista);

				if(c instanceof ContaEspecial ce )
					arquivo2.write("ContaEspecial" + ";" +ce.getId() +";" + ce.getData() +";" 
							+ ce.getSaldo() +";"+ ce.getLimite() +";"+ listaId +";"+ ce.getTitular() + "\n");	
				else
					arquivo2.write("Conta;" +c.getId() +";" + c.getData() +";" 
							+ c.getSaldo() +";"+ listaId +";"+c.getTitular()+"\n");	

			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo  contas "+e.getMessage());
		}

	}
}

