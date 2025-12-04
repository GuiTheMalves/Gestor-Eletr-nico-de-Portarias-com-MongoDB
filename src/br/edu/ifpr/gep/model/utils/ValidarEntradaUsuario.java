package br.edu.ifpr.gep.model.utils;

import java.util.List;

import br.edu.ifpr.gep.model.Portaria;
//Classe com o objetico de validação das operações com portarias
public class ValidarEntradaUsuario {
	
	//Checa se o a lista de portarias está vazia ou não
	public boolean checarQtdePortarias(List<Portaria> portarias) {
		if(portarias.size() >= 1) {
			return true;
       	 } else {
       		 System.out.println("Portarias vazias. Por favor, simule dados ou insira uma portaria.");
       		 return false;
       	 }
	}
	//Checa se o usuário digitou uma entrada de um único dígito
	//a fim de evitar dados como "67" ou "6azb9" serem lidos como "6" normalmente
	//pois o mesmo se encontra na primeira posição da entrada
	public boolean checarTamanhoEntrada(String opcao) {
		if(opcao.length() == 1) {
			return true;
		} else {
			return false;
		}
	}
	//Exibe a mensagem de valor invalido, utilizado caso o usuário 
	//de fato digite um valor invalido
	public void exibirValorInvalido() {
 		System.out.println("Por favor, insira um valor válido.");
		
	}
}
