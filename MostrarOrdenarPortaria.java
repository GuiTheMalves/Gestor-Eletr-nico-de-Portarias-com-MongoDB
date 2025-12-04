package br.edu.ifpr.gep.functions;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;
import br.edu.ifpr.gep.model.utils.comparators.EmissorComparator;
import br.edu.ifpr.gep.model.utils.comparators.EmissorNúmeroAnoComparator;
import br.edu.ifpr.gep.model.utils.comparators.NúmeroComparator;
import br.edu.ifpr.gep.model.utils.comparators.PublicaçãoComparator;
//Classe responsável por mostrar e ordenar todas as portarias buscadas pelo filtro
public class MostrarOrdenarPortaria {
	Scanner scanner = new Scanner(System.in);
	ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
	
	//Mostra todas as portarias sem filtro algum
	public void mostrar(List<Portaria> portarias) {
		for (Portaria portaria : portarias)
			System.out.println(portaria);
	}
	//Exibe o subMenu de ordenações
	private void exibirMenuOrdenações() {
		System.out.println("Você deseja mostrar:");
		System.out.println("1- Todas as Portarias");
		System.out.println("2- Por ordem de Publicação");
		System.out.println("3- Por ordem de Emissor");
		System.out.println("4- Por ordem de Número");
		System.out.println("5- Por ordem de Emissor, Número e Ano");
		System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
	}
	//Método responsável por ordenar e mostrar as ordenações realizadas nas portarias de acordo com o filtro escolhido pelo usuário
	public void mostrarOrdenações(List<Portaria> portarias) {
		Collections.sort(portarias,new PublicaçãoComparator()); 
		Integer modoOrdenacao = 0;
		while(true) {
		exibirMenuOrdenações();
		String modoOrdenacaoStr = scanner.nextLine().trim();
		if(modoOrdenacaoStr.isEmpty()) {
			return;
		}
		while(true) {
		try {
			modoOrdenacao = Integer.parseInt(modoOrdenacaoStr);
			break;
		} catch (NumberFormatException e) {
			//System.out.println("Valor inválido. Por favor, digite um número inteiro.");
			break;
		}
		}
		if(modoOrdenacao > 5 || modoOrdenacao < 1) {
			vali.exibirValorInvalido();
		} else {
			break;
		}
		}
		//LISTAR TODAS
		if(modoOrdenacao == 1) {
			System.out.println("==> MOSTRAR PORTARIAS <==");
	     	mostrar(portarias);
		}
		//ORDEM POR DATA DE PUBLICACAO
		else if(modoOrdenacao == 2) {
				System.out.println("\n==> ORDENADO (Publicação) <==");
			mostrar(portarias);
		} 
		//ORDEM POR EMISSOR
		else if(modoOrdenacao == 3) {
				Collections.sort(portarias,new EmissorComparator());
				System.out.println("\n==> ORDENADO (Emissor) <==");
				mostrar(portarias);
		} 
		//ORDEM POR NUMERO
		else if(modoOrdenacao == 4) {
				Collections.sort(portarias,new NúmeroComparator());
				System.out.println("\n==> ORDENADO (Número) <==");
				mostrar(portarias);				
		} 
		//ORDEM POR "PRIMARY KEY" (EMISSOR, NUMERO, ANO)
		else if(modoOrdenacao == 5) {
				Collections.sort(portarias,new EmissorNúmeroAnoComparator());
				System.out.println("\n==> ORDENADO (Emissor, Número e Ano) <==");
				mostrar(portarias);
		}

		
		}
}

