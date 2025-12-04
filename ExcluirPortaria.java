package br.edu.ifpr.gep.functions;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
import br.edu.ifpr.gep.model.utils.EmissorConverter;
import br.edu.ifpr.gep.model.utils.ParseValores;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;
//Classe responsável por excluir a portaria desejada
public class ExcluirPortaria {
	Scanner scanner = new Scanner(System.in);
	private MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
	ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
	EmissorConverter eval = new EmissorConverter();
	
	//Exibe o subMenu de exclusão de portaria
	public void exibirMenuExcluir() {
		System.out.println("==> EXCLUIR <==");
		System.out.println("1 - Excluir Apenas uma Portaria");
		System.out.println("2 - Excluir Todas as Portarias");
		System.out.println("3 - Excluir Por um Determinado Parâmetro da Portaria");
		System.out.println("4 - Voltar");
		System.out.println("Qual sua opção? ");
	}
	
	private void exibirMenuPorParametro() {
		System.out.println("==> EXCLUSÃO POR PARÂMETRO <==");
		System.out.println("1 - Emissor");
		System.out.println("2 - Número");
		System.out.println("3 - Ano");
		System.out.println("4 - Data");
		System.out.println("5 - Voltar");
	}
	public void chamarExcluir() {
		exibirMenuExcluir();
		String opcao = scanner.nextLine().trim();
		int entrada;
		try {
		    entrada = Integer.parseInt(opcao);
		} catch(NumberFormatException e) {
		    vali.exibirValorInvalido();
		    return;
		}
		if(entrada == 1) {
			excluirUnico();
		}
		else if(entrada == 2) {
			excluirTudo();
		}
		else if(entrada == 3) {
			exibirMenuPorParametro();
			int entradaParametro = scanner.nextInt();
			if(entradaParametro == 1) {
				excluirPorEmissor();
			}
			else if(entradaParametro == 2) {
				excluirPorNumero();
			}
			else if(entradaParametro == 3) {
				excluirPorAno();
			}
			else if(entradaParametro == 4) {
				excluirPorData();
			}
			else if(entradaParametro == 5) {
				
			}
		}
	}
	//Responsável por realizar as operações relacionadas a exclusão de todas as portarias
	public void excluirTudo() {
			while(true) {
			System.out.println("Excluir todas as portarias? (S/n)");
		    System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			String confirmar = scanner.nextLine();
			if (confirmar.equalsIgnoreCase("s")) {
			int registrs = repo.delete();
			System.out.println("\n==> EXCLUIR TUDO <==");
			System.out.println("*** Registros Excluídos [" + registrs + "]***");
			break;
			} else if(confirmar.equalsIgnoreCase("n")){
				System.out.println("Portarias não excluídas.");
				break;
			} else {
				System.out.println("Digite (S) ou (n).");
			}
			}
			}
	//Responsável por realizar as operações relacionadas a exclusão de uma única portaria
	public void excluirUnico() {
			int emissorExc;
			String emissorStr;
			System.out.println("");
			while(true) {
			System.out.print("Digite o emissor da portaria: ");
		 	System.out.println("\nNOTA: Acentue se necessário.");
		 	System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
		 	emissorStr = scanner.nextLine().trim();
			 if (emissorStr.isEmpty()) {
				 return;
			 }
			 if(!(eval.converterEmissorParaInteger(emissorStr) == null) ) {
					emissorExc = eval.converterEmissorParaInteger(emissorStr);
					break;
			 }
			 }
			
            long numeroExc;
            //validação para o número
            while(true) {
                System.out.println("Digite o número: ");
    		 	System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
                String numeroStr = scanner.nextLine();
                if(numeroStr.isEmpty()) {
               	  return;
                }
                try {
                    numeroExc = Integer.parseInt(numeroStr);
                    break;
                } catch (NumberFormatException e) {
                	vali.exibirValorInvalido();                }
            }

            int anoExc;
            //validação para a data
            while(true) {
                System.out.println("Digite o ano de publicação: ");
    		 	System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
                String anoStr = scanner.nextLine();
                if(anoStr.isEmpty()) {
               	 return;
                }
                try {
                    anoExc = Integer.parseInt(anoStr);
                    break;
                } catch (NumberFormatException e) {
                	vali.exibirValorInvalido();                }
            }
		    String mostrarEmissor = eval.exibirEmissor(emissorExc);
		    Optional<Portaria> portaria = repo.findPortaria(emissorExc,numeroExc,anoExc);
			if (portaria.isPresent()) {
			System.out.println("\n==> EXCLUIR PORTARIA (" + mostrarEmissor + ", " + numeroExc + " e " + anoExc + ") <== (S/n)");
			String confirmar = scanner.nextLine();
			if (confirmar.equalsIgnoreCase("s")) {
				if (repo.delete(emissorExc,numeroExc,anoExc)) {
				System.out.println("Portaria excluída com sucesso");
				} else {
				// este caso JAMAIS vai acontecer, dados os valores de busca
				System.out.println("Erro na exclusão da Portaria");
				}
			} else if(confirmar.equalsIgnoreCase("n")) {
				System.out.println("Portaria não excluída.");
			} else {
				vali.exibirValorInvalido();			
			}
			} else {
				System.out.println("Portaria não encontrada.");
			}
		}
	private void excluirPorEmissor() {
	    String emissorStr;
	    Integer emissor;
	    while (true) {
	        System.out.println("Digite o emissor da portaria:");
	        System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
	        emissorStr = scanner.nextLine().trim();
	        if (emissorStr.isEmpty()) return;
	        emissor = eval.converterEmissorParaInteger(emissorStr);
	        if (emissor != null) break;
	        vali.exibirValorInvalido();
	    }
	    List<Portaria> lista = repo.findByEmissor(emissor);
	    if (lista.isEmpty()) {
	        System.out.println("Nenhuma portaria encontrada para esse emissor.");
	        return;
	    }
	    System.out.println("Foram encontradas " + lista.size() + " portarias.");
	    System.out.print("Deseja excluir todas? (S/n): ");
	    String confirmar = scanner.nextLine();
	    if (!confirmar.equalsIgnoreCase("s")) {
	        System.out.println("Exclusão cancelada.");
	        return;
	    }
	    int contador = 0;
	    for (Portaria p : lista) {
	    	ParseValores pava = new ParseValores();
			LocalDate data = pava.ParseData(p.getPublicação());
			if (data == null) {  
		        System.out.println("Data inválida na portaria: " + p.getPublicação());
		        continue;  // pula essa portaria ao invés de quebrar o programa
		    }
	        boolean ok = repo.delete(p.getEmissor(), p.getNúmero(), data.getYear());
	        if (ok) contador++;
	    }
	    System.out.println("Total excluído: " + contador);
	}
	private void excluirPorNumero() {
		String numeroString;
		Long numero;
			System.out.println("Digite o número da portaria: ");
			System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			numeroString = scanner.nextLine().trim();
			if(numeroString.isEmpty()) return;
			try {
				numero = Long.parseLong(numeroString);
			} catch(NumberFormatException e){
				vali.exibirValorInvalido();
				return;
			}	
		
		List<Portaria> lista = repo.findByNumero(numero);
		if(lista.isEmpty()) {
			System.out.println("Nenhuma portaria encontrada com esse número");
			return;
		}
		
		System.out.println("Encontradas: " + lista.size() + " portarias");
		System.out.println("Deseja excluir todas? (s/n)");
		if(!scanner.nextLine().equalsIgnoreCase("s")) return;
		int contador = 0;
		for(Portaria p : lista) {
			ParseValores pava = new ParseValores();
			LocalDate data = pava.ParseData(p.getPublicação());
			if (data == null) {  
		        System.out.println("Data inválida na portaria: " + p.getPublicação());
		        continue;  // pula essa portaria ao invés de quebrar o programa
		    }
			boolean ok = repo.delete(p.getEmissor(), p.getNúmero(),data.getYear());
			if(ok) contador++;
		}
		System.out.println("Total excluído: " + contador);
	}
	private void excluirPorAno() {
		String anoString;
		Integer ano;
		System.out.println("Digite o ano da portaria: ");
		System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
		anoString = scanner.nextLine().trim();
		if(anoString.isEmpty()) return;
		try {
			ano = Integer.parseInt(anoString);
		} catch(NumberFormatException e) {
			vali.exibirValorInvalido();
			return;
		}
		ParseValores pava = new ParseValores();
		List<Portaria> lista = repo.findAll()
		            .stream()
		            .filter(p -> { LocalDate data = pava.ParseData(p.getPublicação());
		            return data != null && data.getYear() == ano; })
		            .collect(Collectors.toList());
		if(lista.isEmpty()) {
			System.out.println("Nenhuma portaria encontrada com esse ano");
			return;
		}
		System.out.println("Encontradas: " + lista.size() + " portarias");
		System.out.println("Deseja excluir todas? (s/n)");
		if(!scanner.nextLine().equalsIgnoreCase("s")) return;
		int contador = 0;
		for(Portaria p : lista) {
			LocalDate data = pava.ParseData(p.getPublicação());
			if (data == null) {  
		        System.out.println("Data inválida na portaria: " + p.getPublicação());
		        continue;  // pula essa portaria ao invés de quebrar o programa
		    }
			if(repo.delete(p.getEmissor(), p.getNúmero(), data.getYear()))
				contador++;
		}
		System.out.println("Total excluiído: " + contador);
	}
	private void excluirPorData() {
		String dataString;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data;
		System.out.println("Digite a data completa da portaria (DD/MM/AAAA)");
		System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
		dataString = scanner.nextLine().trim();
		if(dataString.isEmpty()) return;
		try {
			data = LocalDate.parse(dataString, formato);
		} catch(DateTimeException e) {
			vali.exibirValorInvalido();
			return;
		}
		List<Portaria> lista = repo.findByPublicacao(data);
		if(lista.isEmpty()) {
			System.out.println("Nenhuma portaria encontrada com esse ano");
			return;
		}
		System.out.println("Encontradas: " + lista.size() + " portarias");
		System.out.println("Deseja excluir todas? (s/n)");
		if(!scanner.nextLine().equalsIgnoreCase("s")) return;
		int contador = 0;
		for(Portaria p: lista) {
			ParseValores pava = new ParseValores();
			LocalDate data1 = pava.ParseData(p.getPublicação());
			if (data == null) {  
		        System.out.println("Data inválida na portaria: " + p.getPublicação());
		        continue;  // pula essa portaria ao invés de quebrar o programa
		    }
			if(repo.delete(p.getEmissor(), p.getNúmero(), data1.getYear()))
				contador ++;
		}
		System.out.println("Total excluído: " + contador);
	}
}