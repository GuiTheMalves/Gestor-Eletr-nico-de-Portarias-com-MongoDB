package br.edu.ifpr.gep.model.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.StringSearch;
import br.edu.ifpr.gep.model.utils.ParseValores;
import br.edu.ifpr.gep.model.utils.ValidarEntradaUsuario;

/**
 * Repositório MongoDB que simula o comportamento do PortariaRepository original,
 * permitindo salvar, alterar, buscar e excluir Portarias no banco Mongo.
 *
 * Implementado como Singleton (enum INSTANCE), igual ao repositório original.
 */
public enum MongoPortariaRepository {
    INSTANCE;
	ValidarEntradaUsuario vali = new ValidarEntradaUsuario();
    // -------------------------------------------------------------------------
    // CONFIGURAÇÕES DO MONGO
    // -------------------------------------------------------------------------

    /** String de conexão com o servidor MongoDB */
    private final String MONGODB_CONN = "mongodb://localhost:28017";

    /** Nome do banco de dados */
    private final String MONGODB_DB   = "GEP_database";

    /** Nome da coleção (equivalente a tabela) */
    private final String MONGODB_COLL = "GEP_db";

    /** Cliente Mongo (conexão principal) */
    private MongoClient mongoClient;

    /** Objeto do banco */
    private MongoDatabase database;

    /** Coleção onde os documentos serão salvos */
    private MongoCollection<Document> coll;

    // -------------------------------------------------------------------------
    // CONSTRUTOR DO SINGLETON
    // -------------------------------------------------------------------------

    /**
     * Construtor do enum (executa apenas uma vez).
     * Abre a conexão com o MongoDB e pega a coleção.
     */
    MongoPortariaRepository() {
        mongoClient = MongoClients.create(MONGODB_CONN);
        database    = mongoClient.getDatabase(MONGODB_DB);
        coll        = database.getCollection(MONGODB_COLL);
    }

    /** Fecha conexão com MongoDB (caso queira limpar corretamente) */
    public void close() {
        mongoClient.close();
    }

    // -------------------------------------------------------------------------
    // MÉTODOS PRINCIPAIS DO CRUD
    // -------------------------------------------------------------------------

    /**
     * Insere uma nova Portaria no MongoDB.
     * O método converte o objeto Portaria em um Document BSON.
     */
    public LocalDate strParaLocalDate(String dataNova) {
        if (dataNova == null || dataNova.isBlank()) {
            return null; // ou lançar exceção, se preferir
        }

        /*
         * Caso a data chegue no formato correto ISO (yyyy-MM-dd), como o Mongo salva,
         * o LocalDate.parse já entende automaticamente.
         */
        try {
            return LocalDate.parse(dataNova);
        } catch (Exception e) {
            // Caso a data venha em outro formato, use um formatter:
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            vali.exibirValorInvalido();
            return LocalDate.parse(dataNova, fmt);
        }
    }
    public boolean insert(Portaria portaria) {
        Document doc = portariaToDoc(portaria);

        // insertOne retorna um objeto contendo o ID gerado → se não for nulo → inseriu
        return coll.insertOne(doc).getInsertedId() != null;
    }

    /**
     * Atualiza campos de uma portaria existente.
     * A chave única continua sendo:
     *  emissor + numero + anoPublicacao
     */
    public boolean update(Portaria portaria) {
    	ParseValores pava = new ParseValores();
    	LocalDate data = portaria.getPublicação();
        // Filtro para encontrar a portaria correta (chave composta)
        Bson filter = Filters.and(
                Filters.eq("emissor", portaria.getEmissor()),
                Filters.eq("numero", portaria.getNúmero()),
                Filters.eq("anoPublicacao", data.getYear())
        );

        // Lista de atualizações possíveis
        Bson updates = Updates.combine(
                Updates.set("membros", portaria.getMembro())
        );

        // updateOne retorna quantos documentos foram alterados
        return coll.updateOne(filter, updates).getModifiedCount() > 0;
    }

    /**
     * Exclui uma portaria usando a chave composta.
     */
    public boolean delete(Integer emissor, Long numero, Integer ano) {
        Bson filter = Filters.and(
                Filters.eq("emissor", emissor),
                Filters.eq("numero", numero),
                Filters.eq("anoPublicacao", ano)
        );

        return coll.deleteOne(filter).getDeletedCount() > 0;
    }

    /**
     * Exclui TODAS as portarias da coleção.
     * drop() remove a coleção inteira.
     */
    public int delete() {
        long total = coll.countDocuments();
        if (total > 0) {
            coll.drop();
        }
        return (int) total;
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE BUSCA
    // -------------------------------------------------------------------------

    /**
     * Busca portarias por emissor.
     */
    public List<Portaria> findByEmissor(Integer emissor) {
        Bson filter = Filters.eq("emissor", emissor);
        FindIterable<Document> docs = coll.find(filter);

        List<Portaria> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(docToPortaria(doc));
        }
        return lista;
    }

    /**
     * Busca portarias por número.
     */
    public List<Portaria> findByNumero(Long numero) {
        Bson filter = Filters.eq("numero", numero);
        FindIterable<Document> docs = coll.find(filter);

        List<Portaria> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(docToPortaria(doc));
        }
        return lista;
    }

    /**
     * Busca por intervalo de datas de publicação.
     */
    public List<Portaria> findByPeriodo(LocalDate inicio, LocalDate fim) {

        Bson filter = Filters.and(
                Filters.gte("publicacao", inicio.toString()),
                Filters.lte("publicacao", fim.toString())
        );

        FindIterable<Document> docs = coll.find(filter);

        List<Portaria> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(docToPortaria(doc));
        }
        return lista;
    }

    /**
     * Busca portárias por data exata de publicação.
     */
    public List<Portaria> findByPublicacao(LocalDate data) {
        Bson filter = Filters.eq("publicacao", data.toString());
        FindIterable<Document> docs = coll.find(filter);

        List<Portaria> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(docToPortaria(doc));
        }
        return lista;
    }

    /**
     * Busca portaria pela chave composta.
     */
    public Optional<Portaria> findPortaria(Integer emissor, Long numero, Integer ano) {
        Bson filter = Filters.and(
                Filters.eq("emissor", emissor),
                Filters.eq("numero", numero),
                Filters.eq("anoPublicacao", ano)
        );

        Document doc = coll.find(filter).first();

        if (doc != null)
            return Optional.of(docToPortaria(doc));

        return Optional.empty();
    }

    /**
     * Retorna todas as portarias.
     */
    public List<Portaria> findAll() {
        List<Portaria> lista = new ArrayList<>();

        for (Document doc : coll.find()) {
            lista.add(docToPortaria(doc));
        }

        return lista;
    }

    // -------------------------------------------------------------------------
    // CONVERSORES ENTRE OBJETO JAVA E DOCUMENT MONGO
    // -------------------------------------------------------------------------

    /**
     * Converte um Document BSON em objeto Portaria.
     */
    private Portaria docToPortaria(Document doc) {
        Portaria p = new Portaria();

        p.setEmissor(doc.getInteger("emissor"));          // OK
        p.setNúmero(doc.getLong("numero"));               // OK
        // pegar string ISO do banco
        String dataStr = doc.getString("publicacao");

        // converter para LocalDate
        LocalDate data = LocalDate.parse(dataStr); // ISO já funciona direto

        p.setPublicação(data); // OK

        List<String> membros = doc.getList("membros", String.class);
        p.setMembro(membros != null ? membros : new ArrayList<>());

        return p;
    }

    
    private Portaria docToGep(Document doc) {
	      Portaria port = new Portaria();
	      
	      port.setEmissor(doc.getInteger("Emissor"));
	      port.setNúmero(doc.getLong("Numero"));
	      String dataStr = doc.getString("publicacao");
	      LocalDate data = LocalDate.parse(dataStr); // ISO já funciona direto

	      port.setPublicação(data); // OK
	      List<String> mem = doc.getList("Membro", String.class);
	      port.setMembro(mem);
	    

	      return port;
	   }
    
    /*public List<Portaria> findByMembro(List<String> nomes, StringSearch mode, boolean requireAll) {
		if (nomes == null || nomes.isEmpty()) return Collections.emptyList();
		
			List<Bson> conds = new ArrayList<>();
			for (String n : nomes) {
				if (n == null) continue;
				n = n.trim();
				if (n.isEmpty()) continue;
		
		String q = Pattern.quote(n);
		boolean exact = (mode == StringSearch.EXACT || mode == StringSearch.EXACT_CASE_INSENSITIVE);
		
		
		String regex = exact
			    ? "(^|,\\s*)" + q + "(\\s*,|$)"
			    : q;		
		int flags = (mode == StringSearch.EXACT_CASE_INSENSITIVE || mode == StringSearch.PARTIAL_CASE_INSENSITIVE)
		? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
		: 0;
		
		conds.add(Filters.regex("Membro", Pattern.compile(regex, flags)));
		}
		
		if (conds.isEmpty()) return Collections.emptyList();
		Bson filtro;
		if (requireAll) {
		    filtro = Filters.and(conds);
		} else {
		    filtro = Filters.or(conds);
		}
		List<Portaria> ports = new ArrayList<>();
		 for (Document d : coll.find(filtro)) {
		        ports.add(docToGep(d));
		    }
		return ports;
   }*/
    /*public List<Portaria> findByMembro(List<String> nomes, StringSearch mode, boolean requireAll) {
        if (nomes == null || nomes.isEmpty()) return Collections.emptyList();

        List<Bson> conds = new ArrayList<>();
        int flags = (mode == StringSearch.EXACT_CASE_INSENSITIVE || mode == StringSearch.PARTIAL_CASE_INSENSITIVE)
                ? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                : 0;

        for (String n : nomes) {
            if (n == null) continue;
            n = n.trim();
            if (n.isEmpty()) continue;

            Pattern p = Pattern.compile(Pattern.quote(n), flags);
            System.out.println("DEBUG: adicionando regex para '" + n + "' -> " + p.pattern());
            conds.add(Filters.regex("Membro", p)); // troque "Membro" se necessário
        }

        if (conds.isEmpty()) return Collections.emptyList();
        Bson filtro = requireAll ? Filters.and(conds) : Filters.or(conds);

        List<Portaria> ports = new ArrayList<>();
        for (Document d : coll.find(filtro)) {
            ports.add(docToGep(d));
        }
        return ports;
    }*/
    
    public List<Portaria> findByMembro(String parteDoNome) {
        List<Portaria> lista = new ArrayList<>();

        // REGEX case-insensitive
        Document filtroRegex = new Document("$regex", parteDoNome)
                .append("$options", "i");

        // Campo correto: "membros"
        Document query = new Document("membros", filtroRegex);

        try (MongoCursor<Document> cursor = coll.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                lista.add(docToPortaria(doc));  // <- método correto
            }
        }

        return lista;
    }
    

    /**
     * Converte um objeto Portaria para Document BSON.
     */
    private Document portariaToDoc(Portaria p) {
        LocalDate data = p.getPublicação();

        return new Document()
                .append("emissor", p.getEmissor())
                .append("numero", p.getNúmero())
                .append("publicacao", p.getPublicação()) // salva como String
                .append("anoPublicacao", data.getYear())
                .append("membros", p.getMembro()); // lista de Strings
    }

}
