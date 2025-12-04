package br.edu.ifpr.gep.aplicacao;

import java.util.List;
import java.util.Scanner;

import br.edu.ifpr.gep.functions.AlterarPortaria;
import br.edu.ifpr.gep.functions.BuscarPortaria;
import br.edu.ifpr.gep.functions.ExcluirPortaria;
import br.edu.ifpr.gep.functions.InserirPortaria;
import br.edu.ifpr.gep.functions.MostrarOrdenarPortaria;
import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
import br.edu.ifpr.gep.model.utils.EmissorConverter;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;
//O objetivo da classe (TUI) é fazer um Text User Interface ou TUI, onde ela será instânciada na classe aplicação
//contendo o menu principal do programa (gep) e chamando os respectivos métodos de acordo com a entrada do usuário
public class TUI {
	Scanner scanner = new Scanner(System.in);
	private MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
	ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
	EmissorConverter eval = new EmissorConverter();
	PopularPortaria pplr = new PopularPortaria();
	BuscarPortaria bspo = new BuscarPortaria();
	ExcluirPortaria excl = new ExcluirPortaria();
	InserirPortaria insr = new InserirPortaria();
	MostrarOrdenarPortaria most = new MostrarOrdenarPortaria();
	AlterarPortaria altr = new AlterarPortaria();
	
	//Exibe o menu principal
	private void exibirMenuPrincipal() {
		System.out.println(">>>> Gestor Eletrônico de Portarias <<<<");
        System.out.println("1- Simular Dados");
        System.out.println("2- Inserir");
        System.out.println("3- Alterar");
        System.out.println("4- Excluir");
        System.out.println("5- Mostrar");
        System.out.println("6- Buscar");
        System.out.println("7- Sair");
		System.out.println("Qual sua opção? ");
	}
	//Cria a estrutura do TUI e chama os respectivos métodos de acordo com a entrada do usuário
	public void interface_tui() {
	List<Portaria> portarias;
	while(true){
	exibirMenuPrincipal(); 
    String opcao = scanner.nextLine().toLowerCase();
       if(vali.checarTamanhoEntrada(opcao)) {
    	   
    	 //SIMULAR DADOS
         if(opcao.charAt(0) == '1') {
        	 pplr.popular();
	         System.out.println("### Registros incluídos [" + repo.findAll().size() + "] ###\n");
         }
         //INSERIR
         else if(opcao.charAt(0) == '2') {
        	 insr.inserirPortaria();
         }
         //ALTERAR
         else if(opcao.charAt(0) == '3') { 
		     portarias = repo.findAll();
		     if(vali.checarQtdePortarias(portarias)) {
        	 altr.alterar();
		     }
         }
         //EXCLUIR
         else if(opcao.charAt(0) == '4') {
		     portarias = repo.findAll();
		     while(true) {
        	 if(vali.checarQtdePortarias(portarias)) {
        	 excl.exibirMenuExcluir();
 		     String opcaoExc = scanner.nextLine().toLowerCase();
 		     if(vali.checarTamanhoEntrada(opcaoExc)) {
 		    	 
 		   ///EXCLUIR UMA PORTARIA
        	 if(opcaoExc.charAt(0) == '1') {
			     excl.excluirUnico();
			     break;
        	 } 
        	 ///EXCLUIR TODAS AS PORTARIAS
        	 else if(opcaoExc.charAt(0) == '2') {
        		 excl.excluirTudo();
        		 break;
        	 }
        	 else if(opcaoExc.charAt(0) == '3') {
        		 excl.chamarExcluirPorParametro();
        	 }
        	 ///VOLTAR AO MENU PRINCIPAL
        	 else if(opcaoExc.charAt(0) == '4')    {
        		 break;
        	 } else {
        		 vali.exibirValorInvalido();		        	 }
 		     } else {
 		    	vali.exibirValorInvalido();		 		     }
        	 } else { 
        	 break;
        	 }
         } 
         }
         //MOSTRAR
         else if(opcao.charAt(0) == '5') {
        	 portarias = repo.findAll();
	        	if(vali.checarQtdePortarias(portarias)) {
		     	most.mostrarOrdenações(portarias);
	        	 } 
	         }
        //BUSCAR
         else if(opcao.charAt(0) == '6') {
            portarias = repo.findAll();
            while(true) {
        	if(vali.checarQtdePortarias(portarias)) {
        	bspo.exibirMenuConsultas();
 		    String opcaoCon = scanner.nextLine().toLowerCase();
		    if(vali.checarTamanhoEntrada(opcaoCon)) {
		    
		    ///BUSCAR TODOS
        	 if(opcaoCon.charAt(0) == 'a') {
		        	portarias = repo.findAll();
		        	if(vali.checarQtdePortarias(portarias)) {
		     		most.mostrar(portarias);
		        	} 
		        	break;

	}		///BUSCAR POR PORTARIAS (PK ou Emissor, Numero e Ano)
		         else if(opcaoCon.charAt(0) == 'b') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        	 bspo.buscarPortariaPorPK(portarias);
		        	 } 
		        	 break;
		        	 
	}		///BUSCAR POR EMISSOR
		         else if(opcaoCon.charAt(0) == 'c') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        	 bspo.buscarPortariaPorEmissor(portarias);
		        	 } 
		        	 break;
		        	 
	}		///BUSCAR POR NUMERO
		         else if(opcaoCon.charAt(0) == 'd') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        	 bspo.buscarPortariaPorNumero(portarias);
		        	 } 
		        	 break;

	
	}		///BUSCAR POR DATA DE PUBLICACAO
		         else if(opcaoCon.charAt(0) == 'e') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        	 bspo.buscarPortariaPorPublicacao(portarias);
		        	 } 
		        	 break;
		        	 
	}		///BUSCAR POR PERIODO
		         else if(opcaoCon.charAt(0) == 'f') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        		 bspo.buscarPortariaPorPeriodo(portarias);
		        	 }
		        	 break;
		        	 
	}		///BUSCAR POR MEMBRO
		         else if(opcaoCon.charAt(0) == 'g') {
		        	 portarias = repo.findAll();
		        	 if(vali.checarQtdePortarias(portarias)) {
		        	 bspo.buscarPortariaPorMembro(portarias);
		        	 }
		        	 break;
		         }
        	 
        	 ///VOLTAR AO MENU PRINCIPAL
		         else if(opcaoCon.charAt(0) == 'x') {
		        	 break;
		         } else {
		        	 vali.exibirValorInvalido();				         }
		         
         } else {
        	 vali.exibirValorInvalido();		         }
        	} else {
	         break;
        	}
        	} 
         }
         //SAIR DO SISTEMA
         else if(opcao.charAt(0) == '7') {
        	System.out.println("Encerrando o sistema...");
        	break;
         	} else {
         		vali.exibirValorInvalido();		         	}
         } else {
        	 vali.exibirValorInvalido();
         }
	 }
}
}
