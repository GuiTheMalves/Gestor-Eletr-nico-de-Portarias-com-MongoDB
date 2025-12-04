package br.edu.ifpr.gep.aplicacao;

import br.edu.ifpr.gep.aplicacao.App;
	//Classe que chama a interface de texto cuja qual chama todos os respectivos métodos de operações de acordo
	//com a entrada do usuário
public class App {
	
    	TUI inter = new TUI();
    	//Instancia o objeto da classe App, chamando a classe TUI
    	public static void main(String[] args) {
    			App app = new App();
    			app.inter.interface_tui();
    		}   	
}
