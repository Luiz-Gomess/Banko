package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 *
 */

import regras_negocio.Fachada;

public class TestarExcecao {

	public static void main (String[] args) {

		System.out.println("\n-------TESTE DE EXCE��ES LAN�ADAS PELOS METODOS DA FACHADA--------");
		try {
			Fachada.criarCorrentista("123","linguica", "1234");
			Fachada.criarCorrentista("234","tomas", "1265");
			Fachada.criarCorrentista("345","jerry", "1285");
			System.out.println("*************1--->Nao lan�ou exce��o para: criar correntista existente "); 
		}catch (Exception e) {System.out.println("1ok--->"+e.getMessage());}

		try {
			Fachada.criarConta("123");
			Fachada.criarConta("234");
			System.out.println("*************2--->Nao lan�ou exce��o para: criar Conta existente ");		 
		}catch (Exception e) {System.out.println("2ok--->"+e.getMessage());}

		try {
			Fachada.criarContaEspecial("345", -10);
			Fachada.criarContaEspecial("542", -5);
			System.out.println("*************3--->Nao lan�ou exce��o para: criar ContaEspecial existente "); 
		}catch (Exception e) {System.out.println("3ok--->"+e.getMessage());}

		// try {
		// 	Fachada.criarCorrentista("123456","c2", "1236");
		// 	System.out.println("*************4--->Nao lan�ou exce��o para: criar evento preco negativo "); 
		// }catch (Exception e) {System.out.println("4ok--->"+e.getMessage());}

		try 
		{
			Fachada.inserirCorrentistaConta("234", 1);	
			Fachada.inserirCorrentistaConta("345", 2);	
			System.out.println("*************6--->Nao lan�ou exce��o: adicionar correntista conta que participa"); 
		}catch (Exception e) {System.out.println("6ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerCorrentistaConta("234", 1);	
			Fachada.removerCorrentistaConta("345", 2);	
			System.out.println("*************7--->Nao lan�ou exce��o: remover participante evento que nao participa"); 
		}catch (Exception e) {System.out.println("7ok--->"+e.getMessage());}

		try 
		{
			Fachada.inserirCorrentistaConta("p2", 1);	
			System.out.println("*************8--->Nao lan�ou exce��o: adicionar participante inexistente"); 
		}catch (Exception e) {System.out.println("8ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerCorrentistaConta("p2", 1);	
			System.out.println("*************9--->Nao lan�ou exce��o: remover participante inexistente "); 
		}catch (Exception e) {System.out.println("9ok--->"+e.getMessage());}

		// try 
		// {
		// 	Fachada.removerCorrentista("03/01/2030");	
		// 	Fachada.apagarEvento("03/01/2030");	
		// 	System.out.println("*************10--->Nao lan�ou exce��o: apagar evento inexistente"); 
		// }catch (Exception e) {System.out.println("10ok--->"+e.getMessage());}

		// try 
		// {
		// 	Fachada.apagarParticipante("p2");	
		// 	System.out.println("*************11--->Nao lan�ou exce��o: apagar participante inexistente"); 
		// }catch (Exception e) {System.out.println("11ok--->"+e.getMessage());}

		// //apagar dados usados no teste
		// try {Fachada.apagarEvento("01/01/2030");}	catch (Exception e){}		
		// try {Fachada.apagarEvento("02/01/2030");}	catch (Exception e){}		
		// try {Fachada.apagarEvento("03/01/2030");}	catch (Exception e) {}		
		// try {Fachada.apagarParticipante("p1");} catch (Exception e){}
		// try {Fachada.apagarParticipante("p2");} catch (Exception e){}
		// try {Fachada.apagarParticipante("c1");} catch (Exception e){}
	}
}


