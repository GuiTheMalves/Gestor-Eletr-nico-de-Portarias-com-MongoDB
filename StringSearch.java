package br.edu.ifpr.gep.model;

//Enumeração que define os tipos possíveis de busca por Strings
//Cada constante representa uma maneira diferente de comparar ou localizar textos
//Útil para checar 
public enum StringSearch {
   //Busca exata: o texto deve ser idêntico ao alvo
   EXACT,

   //Busca parcial: o texto pode conter apenas parte do alvo
   PARTIAL,

   //Busca exata, mas agora ignorando diferenças entre maiúsculas e minúsculas
   EXACT_CASE_INSENSITIVE,

   //Busca parcial, mas agora ignorando diferenças entre maiúsculas e minúsculas
   PARTIAL_CASE_INSENSITIVE;
}
