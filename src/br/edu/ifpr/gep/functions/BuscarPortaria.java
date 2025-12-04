package br.edu.ifpr.gep.functions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.StringSearch;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
import br.edu.ifpr.gep.model.utils.EmissorConverter;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;

//Classe responsável por realizar todas as operações relacionadas a buscar uma portaria
public class BuscarPortaria {
    Scanner scanner = new Scanner(System.in);
    MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
    EmissorConverter eval = new EmissorConverter();
    ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
    MostrarOrdenarPortaria most = new MostrarOrdenarPortaria();

    //Exibe o subMenu de consultar uma portaria
    public void exibirMenuConsultas() {
        System.out.println("==> CONSULTAS <==");
        System.out.println("A- Todos");
        System.out.println("B- Portaria");
        System.out.println("C- Emissor");
        System.out.println("D- Número");
        System.out.println("E- Publicação");
        System.out.println("F- Período");
        System.out.println("G- Nome");
        System.out.println("X- Voltar");
        System.out.println("Qual sua opção? ");
    }

    //Lê o emissor (aceita nome ou parte) e converte para Integer; retorna null se cancelar
    private Integer lerEmissorOuCancelar() {
        while(true) {
            System.out.print("Digite o emissor da portaria: ");
            System.out.println("\nNOTA: Acentue se necessário.");
            System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
            String emissorStr = scanner.nextLine().trim();
            if (emissorStr.isEmpty()) return null;
            Integer conv = eval.converterEmissorParaInteger(emissorStr);
            if (conv != null) return conv;
            vali.exibirValorInvalido();
        }
    }

    //Lê um número (Long), retorna null se cancelar
    private Long lerNumeroOuCancelar() {
        while(true) {
            System.out.print("Digite o número: ");
            System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
            String numeroStr = scanner.nextLine().trim();
            if (numeroStr.isEmpty()) return null;
            if (!numeroStr.matches("^\\d+$")) {
                vali.exibirValorInvalido();
                continue;
            }
            try {
                return Long.parseLong(numeroStr);
            } catch (NumberFormatException e) {
                vali.exibirValorInvalido();
            }
        }
    }

    //Lê uma data no formato dd/MM/yyyy, retorna null se cancelar
    private LocalDate lerDataOuCancelar(DateTimeFormatter formato, String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");
            String s = scanner.nextLine().trim();
            if (s.isEmpty()) return null;
            try {
                return LocalDate.parse(s, formato);
            } catch (DateTimeParseException e) {
                vali.exibirValorInvalido();
            }
        }
    }

    //Imprime mensagem de quantidade (0 / 1 / >1)
    private void imprimirResumoQuantidade(List<Portaria> lista, String descricao) {
        int tamanho = lista.size();
        if (tamanho == 0) {
            System.out.println(">>> NÃO EXISTE (" + descricao + ") <<<");
        } else if (tamanho == 1) {
            System.out.println(">>> EXISTE SOMENTE UM (" + descricao + ") <<<");
        } else {
            System.out.println(">>> EXISTE MAIS DE UM (" + descricao + ") <<<");
        }
    }


    //BUSCA POR PRIMARY KEY (PK)
    public void buscarPortariaPorPK(List<Portaria> portarias) {
        System.out.println("\n==> BUSCAR PORTARIA (EMISSOR, NÚMERO e ANO) <==");
        Integer emissor = lerEmissorOuCancelar();
        if (emissor == null) return;

        Long numero = lerNumeroOuCancelar();
        if (numero == null) return;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = lerDataOuCancelar(formato, "Digite a data (DD/MM/AAAA):");
        if (data == null) return;
        int ano = data.getYear();

        var opt = repo.findPortaria(emissor, numero, ano);
        String mostrarEmissor = eval.exibirEmissor(emissor);
        if (opt.isPresent()) {
            System.out.println(">>> EXISTE SOMENTE UM (" + mostrarEmissor + ", " + numero + ", " + ano + ") <<<");
            most.mostrar(List.of(opt.get()));
        } else {
            System.out.println(">>> NÃO EXISTE (" + mostrarEmissor + ", " + numero + ", " + ano + ") <<<");
        }
    }

    //BUSCA POR EMISSOR
    public void buscarPortariaPorEmissor(List<Portaria> portarias) {
        Integer emissor = lerEmissorOuCancelar();
        if (emissor == null) return;

        List<Portaria> resultados = repo.findByEmissor(emissor);
        String mostrarEmissor = eval.exibirEmissor(emissor);
        imprimirResumoQuantidade(resultados, mostrarEmissor);
        most.mostrar(resultados);
    }

    //BUSCA POR NUMERO
    public void buscarPortariaPorNumero(List<Portaria> portarias) {
        System.out.println("\n==> BUSCAR POR NÚMERO <==");
        Long numero = lerNumeroOuCancelar();
        if (numero == null) return;

        List<Portaria> resultados = repo.findByNumero(numero);
        imprimirResumoQuantidade(resultados, String.valueOf(numero));
        most.mostrar(resultados);
    }

    //BUSCA POR DATA DE PUBLICAÇÃO
    public void buscarPortariaPorPublicacao(List<Portaria> portarias) {
        System.out.println("\n==> BUSCAR POR PUBLICAÇÃO <==");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate publicacao = lerDataOuCancelar(formato, "Digite a data (DD/MM/AAAA):");
        if (publicacao == null) return;

        List<Portaria> resultados = repo.findByPublicacao(publicacao);
        imprimirResumoQuantidade(resultados, publicacao.toString());
        most.mostrar(resultados);
    }

    //BUSCA POR PERÍODO (SUBTRAÇÃO DE DUAS DATAS)
    public void buscarPortariaPorPeriodo(List<Portaria> portarias) {
        System.out.println("\n==> BUSCAR POR PERÍODO <==");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate d1 = lerDataOuCancelar(formato, "Digite a data inicial (DD/MM/AAAA):");
        if (d1 == null) return;
        LocalDate d2 = lerDataOuCancelar(formato, "Digite a data final (DD/MM/AAAA):");
        if (d2 == null) return;

        if (d1.isAfter(d2) || d1.isEqual(d2)) {
            System.out.println("A data inicial precisa ser menor que a data final. Tente novamente.");
            return;
        }

        List<Portaria> resultados = repo.findByPeriodo(d1, d2);
        imprimirResumoQuantidade(resultados, d1 + " a " + d2);
        most.mostrar(resultados);
    }

    //BUSCA POR MEMBRO DA PORTARIA
    public void buscarPortariaPorMembro(List<Portaria> portarias) {
        System.out.println("\n==> BUSCAR POR MEMBRO <==");
        String nome;

        while (true) {
            System.out.print("Digite o nome completo (com acento, caso tenha) do membro: ");
            System.out.println("\n>>> Para cancelar, pressione [ENTER] <<<");

            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) return;

            if (nome.matches("^[\\p{L} ]+$")) break;

            vali.exibirValorInvalido();
        }

        List<Portaria> resultados = repo.findByMembro(nome);
        imprimirResumoQuantidade(resultados, nome);
        most.mostrar(resultados);
    }

}
