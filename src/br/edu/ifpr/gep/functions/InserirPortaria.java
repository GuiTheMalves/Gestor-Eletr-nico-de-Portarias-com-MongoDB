package br.edu.ifpr.gep.functions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
import br.edu.ifpr.gep.model.utils.EmissorConverter;
import br.edu.ifpr.gep.model.utils.ParseValores;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;

 //Classe responsável por realizar a inserção de novas Portarias no sistema.
 
public class InserirPortaria {
	Scanner scanner = new Scanner(System.in);
    private final MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
    private final EmissorConverter eval = new EmissorConverter();
    private final ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
    private final ParseValores pars = new ParseValores();

    //Realiza o procedimento completo de inserção de uma nova portaria.
    public void inserirPortaria() {

        System.out.println("\n==> INSERIR PORTARIA <==");

        //Emissor
        Integer emissor;
        while (true) {
            System.out.println("Digite o emissor:");
            System.out.println("NOTA: Acentue se necessário.");
            System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
            String emissorStr = scanner.nextLine().trim();

            if (emissorStr.isEmpty()) return;

            emissor = eval.converterEmissorParaInteger(emissorStr);
            if (emissor != null) break;

            vali.exibirValorInvalido();
        }

        //Numero
        long numero;
        while (true) {
            System.out.println("Digite o número:");
            System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
            String numeroStr = scanner.nextLine().trim();

            if (numeroStr.isEmpty()) return;
            
            numero = pars.ParseNumero(numeroStr);
            if(numero !=0L) break;

        }

        //Data
        LocalDate data;
        String dataStr;
        while (true) {
            System.out.println("Digite a data (DD/MM/AAAA):");
            System.out.println(">>> Para cancelar, pressione [ENTER] <<<");
            dataStr = scanner.nextLine().trim();

            if (dataStr.isEmpty()) return;

           data = pars.ParseData(dataStr);
           if(data != null) break;
        }

        //Membros
        List<String> membros = new ArrayList<>();
        System.out.println("\nDigite os membros responsáveis:");

        while (true) {
            System.out.println("Nome do membro:");
            System.out.println(">>> Para parar de adicionar membros, pressione [ENTER] <<<");
            String nome = scanner.nextLine().trim();

            if (nome.isEmpty()) break; //encerra inserção de membros

            if (!nome.matches("^[\\p{L} ]+$")) {
                vali.exibirValorInvalido();
                continue;
            }

            membros.add(nome);
            System.out.println("Membro adicionado com sucesso!\n");
        }

        if (membros.isEmpty()) {
            System.out.println("ERRO: A portaria deve possuir ao menos 1 membro responsável.");
            return;
        }

        //Cria a portaria inserida pelo usuário
        Portaria nova = new Portaria(emissor, numero, data, membros);

        if (repo.insert(nova)) {
            System.out.println("Portaria inserida com sucesso.");
        } else {
            System.out.println("Erro: já existe uma portaria com essa chave.");
        }

        System.out.println("\n====================================\n");
    }
}
