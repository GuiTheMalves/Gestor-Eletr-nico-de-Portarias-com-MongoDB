package br.edu.ifpr.gep.model.utils.comparators;

import java.util.Comparator;
import br.edu.ifpr.gep.model.Portaria;

//Comparator que ordena Portarias com base apenas no número
public class NúmeroComparator implements Comparator<Portaria> {
	@Override
	//Compara duas Portarias pelo valor numérico retornando -1, 0 ou 1
	public int compare(Portaria o1, Portaria o2) {
		long o1Número = o1.getNúmero().intValue();
		long o2Número = o2.getNúmero().intValue();

		if (o1Número < o2Número) return -1;
		else
			if (o1Número > o2Número) return 1;

		return 0;
	}
}