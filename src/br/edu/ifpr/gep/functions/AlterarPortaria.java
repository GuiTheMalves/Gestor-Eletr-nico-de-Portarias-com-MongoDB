package br.edu.ifpr.gep.functions;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
import br.edu.ifpr.gep.model.utils.EmissorConverter;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;

//O objetivo dessa classe é realizar as operações relacionadas a alterar uma portaria
public class AlterarPortaria {
	ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
	EmissorConverter eval = new EmissorConverter();
	MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
	Scanner scanner = new Scanner(System.in);

	
	
	
	
	//Exibe o subMenu alterar
	private void exibirMenuAlterar() {
		System.out.println("Qual atributo você deseja alterar?");
		System.out.println("1- Alterar membro");
		System.out.println("2- Alterar dia");
		System.out.println("3- Alterar mês");
		System.out.println("4- Alterar dia e mês");
		System.out.println("5- Alterar tudo");
		System.out.println("6- Voltar");
	}

	
	
	
	
	//Faz as operações necessárias para alterar o(s) membro(s) da portaria
	private void alterarMembros(Portaria port) {
	    List<String> membros = new ArrayList<>(port.getMembro());

	    while(true) {
	        System.out.println("\n==> ALTERAR MEMBROS <==");
	        System.out.println("Membros atuais:");
	        for (int i = 0; i < membros.size(); i++) {
	            System.out.println((i + 1) + " - " + membros.get(i));
	        }
	        System.out.println("\nA - Adicionar membro");
	        System.out.println("R - Remover membro");
	        System.out.println("E - Editar membro existente");
	        System.out.println("X - Voltar");
	        System.out.print("Escolha: ");

	        String opcao = scanner.nextLine().trim().toLowerCase();

	        if (opcao.equals("a")) {
	            System.out.println("Digite o novo membro:");
	            String novo = scanner.nextLine().trim();
	            if (novo.matches("^[\\p{L} ]+$")) {
	                membros.add(novo);
	                System.out.println("Membro adicionado!");
	            } else vali.exibirValorInvalido();
	        }

	        else if (opcao.equals("r")) {
	            System.out.print("Digite o número do membro a remover: ");
	            try {
	                int idx = Integer.parseInt(scanner.nextLine()) - 1;
	                if (idx >= 0 && idx < membros.size()) {
	                    membros.remove(idx);
	                    System.out.println("Membro removido!");
	                } else vali.exibirValorInvalido();
	            } catch (NumberFormatException e) { vali.exibirValorInvalido(); }
	        }

	        else if (opcao.equals("e")) {
	            System.out.print("Digite o número do membro a editar: ");
	            try {
	                int idx = Integer.parseInt(scanner.nextLine()) - 1;
	                if (idx >= 0 && idx < membros.size()) {
	                    System.out.println("Digite o novo nome:");
	                    String novoNome = scanner.nextLine().trim();
	                    if (novoNome.matches("^[\\p{L} ]+$")) {
	                        membros.set(idx, novoNome);
	                        System.out.println("Membro atualizado!");
	                    } else vali.exibirValorInvalido();
	                }
	            } catch (NumberFormatException e) {
	                vali.exibirValorInvalido();
	            }
	        }

	        else if (opcao.equals("x")) break;

	        else vali.exibirValorInvalido();
	    }

	    System.out.println("Confirmar alterações nos membros? (S/N)");
	    if (scanner.nextLine().equalsIgnoreCase("s")) {
	        port.setMembro(membros);
	        if (repo.update(port))
	            System.out.println("Membros atualizados com sucesso!");
	        else
	            System.out.println("Erro ao atualizar membros!");
	    } else {
	        System.out.println("Alterações canceladas!");
	    }
	}


	
	
	
	
	
	
	
	//Faz as operações necessárias para alterar o dia da portaria
	private void alterarDia(Portaria port) {
		String entrada;
		while(true) {
			System.out.println("\nDigite o novo dia (1-31): ");
			System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			entrada = scanner.nextLine().trim();
			if(entrada.isEmpty()) {
				System.out.println("Valor atual mantido.");
				return;
			}
			//aceita apenas dígitos
			if(!entrada.matches("^\\d{1,2}$")) {
				vali.exibirValorInvalido();
				continue;
			}

			try {
				int novoDia = Integer.parseInt(entrada);
				if(novoDia < 1 || novoDia > 31) {
					vali.exibirValorInvalido();
					continue;
				}
				LocalDate dataAtual = port.getPublicação();
				LocalDate novaData = LocalDate.of(dataAtual.getYear(), dataAtual.getMonthValue(), novoDia);

				System.out.printf("Confirmar alteração para %02d/%02d/%d? (S/N)%n",
						novaData.getDayOfMonth(), novaData.getMonthValue(), novaData.getYear());
				String confirmar = scanner.nextLine().trim().toLowerCase();
				if(confirmar.equalsIgnoreCase("s")) {
					port.setPublicação(dataAtual);
					if(repo.update(port)) {
						System.out.println("Dia alterado com sucesso!");
					} else {
						System.out.println("Dia não alterado com sucesso!");
					}
				}
				else if(confirmar.equalsIgnoreCase("n")) {
					System.out.println("Alteração cancelada!");
				}
				else {
					vali.exibirValorInvalido();
				}
				return; // operação finalizada (sucesso, cancelamento ou inválido)
			} catch (NumberFormatException | DateTimeException e) {
	            vali.exibirValorInvalido();
			}
		}
	}

	
	
	
	
	
	
	
	//Faz as operações necessárias para alterar o mês de uma portaria
	private void alterarMes(Portaria port) {
		String entrada;
		while(true) {
			System.out.println("\nDigite o novo mês (1-12): ");
			System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			entrada = scanner.nextLine().trim();
			if(entrada.isEmpty()) {
				System.out.println("Valor atual mantido.");
				return;
			}
			//aceita apenas dígitos
			if(!entrada.matches("^\\d{1,2}$")) {
				vali.exibirValorInvalido();
				continue;
			}

			try {
				int novoMes = Integer.parseInt(entrada);
				if(novoMes < 1 || novoMes > 12) {
					vali.exibirValorInvalido();
					continue;
				}
				LocalDate dataAtual = port.getPublicação();
				LocalDate novaData = LocalDate.of(dataAtual.getYear(), dataAtual.getMonthValue(), novoMes);

				System.out.printf("Confirmar alteração para %02d/%02d/%d (S/N)%n",
						novaData.getDayOfMonth(), novaData.getMonthValue(), novaData.getYear());
				String confirmar = scanner.nextLine().trim().toLowerCase();
				if(confirmar.equalsIgnoreCase("s")) {
					port.setPublicação(dataAtual);
					if(repo.update(port)) {
						System.out.println("Mês alterado com sucesso!");
					} else {
						System.out.println("Mês não alterado com sucesso!");
					}
				}
				else if(confirmar.equalsIgnoreCase("n")) {
					System.out.println("Alteração cancelada!");
				}
				else {
					vali.exibirValorInvalido();
				}
				return;
			} catch(NumberFormatException | DateTimeException e) {
				vali.exibirValorInvalido();
			}
		}
	}

	
	
	
	
	
	
	
	
	//Faz as operações necessárias para alterar o dia e o mês de uma portaria
	private void alterarDiaMes(Portaria port) {
		String entrada1;
		String entrada2;
		while(true) {
			System.out.println("\nDigite o novo dia (1-31): ");
			System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			entrada1 = scanner.nextLine().trim();
			if(entrada1.isEmpty()) {
				System.out.println("Valor atual mantido.");
				return;
			}
			System.out.println("\nDigite o novo mês (1-12): ");
			System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
			entrada2 = scanner.nextLine().trim();
			if(entrada2.isEmpty()) {
				System.out.println("Valor atual mantido.");
				return;
			}

			//aceita apenas dígitos
			if(!entrada1.matches("^\\d{1,2}$") || !entrada2.matches("^\\d{1,2}$")) {
				vali.exibirValorInvalido();
				continue;
			}

			try {
				int novoDia = Integer.parseInt(entrada1);
				int novoMes = Integer.parseInt(entrada2);
				//valida intervalos básicos
				if(novoDia < 1 || novoDia > 31 || novoMes < 1 || novoMes > 12) {
					vali.exibirValorInvalido();
					continue;
				}
				//Tenta criar a data para validar dias de cada mês (inclui ano bissexto)
				LocalDate dataAtual = port.getPublicação();
				LocalDate novaData = LocalDate.of(dataAtual.getYear(), novoMes, novoDia);

				System.out.printf("Confirmar alteração para %02d/%02d/%d (S/N)%n",
				novaData.getDayOfMonth(), novaData.getMonthValue(), novaData.getYear());
				String confirmar = scanner.nextLine().trim().toLowerCase();
				if(confirmar.equalsIgnoreCase("s")) {
					port.setPublicação(dataAtual);
					if(repo.update(port)) {
						System.out.println("Dia e mês alterado com sucesso!");
					} else {
						System.out.println("Dia e mês não alterado com sucesso!");
					}
				}
				else if(confirmar.equalsIgnoreCase("n")) {
					System.out.println("Alteração cancelada!");
				}
				else {
					vali.exibirValorInvalido();
				}
				return;
			} catch(NumberFormatException | DateTimeException e) {
				vali.exibirValorInvalido();
			}
		}
	}

	//Chama os métodos necessários para alterar todos os 3 atributos disponíveis
	private void alterarTudo(Portaria port) {
		//Altera data (dia/mês) e depois membro
		alterarDiaMes(port);
		alterarMembros(port);
	}

	//Recebe as entrada de dados necessárias para identificar a portaria desejada e
	//chamar os métodos de alteração de acordo com a entrada recebida
	public void alterar() {
		int emissorAlt;
		String emissorStr;
		while(true) {
			System.out.print("Digite o emissor da portaria: ");
		 	System.out.println("\nNOTA: Acentue se necessário.");
		 	System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
		 	emissorStr = scanner.nextLine().trim();
			 if (emissorStr.isEmpty()) {
				 return;
			 }
			 Integer conv = eval.converterEmissorParaInteger(emissorStr);
			 if(conv != null) {
					emissorAlt = conv;
					break;
			 } else {
				 vali.exibirValorInvalido();
			 }
		}

        long numeroAlt;
        //validação para o número
        while(true) {
            System.out.println("Digite o número: ");
    	 	System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
            String numeroStr = scanner.nextLine().trim();
            if(numeroStr.isEmpty()) {
           	 	return;
            }
            if(!numeroStr.matches("^\\d+$")) {
            	vali.exibirValorInvalido();
            	continue;
            }
            try {
                numeroAlt = Long.parseLong(numeroStr);
                break;
            } catch (NumberFormatException e) {
            	vali.exibirValorInvalido();
            }
        }
        //validação para a data
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova;
            System.out.println("Digite a data (DD/MM/AAAA): ");
    	 	System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
            String dataAlt = scanner.nextLine().trim();
            dataNova = repo.strParaLocalDate(dataAlt);
        

        int anoAlt = dataNova.getYear();
        String mostrarEmissor = eval.exibirEmissor(emissorAlt);
		System.out.println("\n==> ALTERAR PORTARIA (" + mostrarEmissor + ", " + numeroAlt + " e " + anoAlt + ") <==");
		Optional<Portaria> portaria = repo.findPortaria(emissorAlt,numeroAlt,anoAlt);
		if (portaria.isPresent()) {
			Portaria port = portaria.get();
		    System.out.println("Dados atuais:");
		    //Mostra o nome legível do emissor
		    System.out.println("Emissor: " + eval.exibirEmissor(port.getEmissor()));
		    System.out.println("Número: " + port.getNúmero());
		    System.out.println("Ano: " + dataNova.getYear());
		    System.out.println("Membro responsável: " + port.getMembro());
		    exibirMenuAlterar();

		    //ler escolha como linha 
		    String escolhaStr = scanner.nextLine().trim();
		    if(escolhaStr.isEmpty()) return;
		    if(!escolhaStr.matches("^\\d$")) {
		    	vali.exibirValorInvalido();
		    	return;
		    }
		    int escolha = Integer.parseInt(escolhaStr);
		    
			if(escolha == 1) {
				alterarMembros(port);
		    }
			else if(escolha == 2) {
				alterarDia(port);
		    }
			else if(escolha == 3) {
				alterarMes(port);
		    }
			else if(escolha == 4) {
				alterarDiaMes(port);
		    }
			else if(escolha == 5) {
			    alterarTudo(port);
		    }
			else if(escolha == 6) {
				System.out.println("Valores atuais mantidos.");
				//voltar
			} else {
				vali.exibirValorInvalido();
			}
	    } else {
	    	System.out.println("Portaria não encontrada.");
	    }
	}
}
