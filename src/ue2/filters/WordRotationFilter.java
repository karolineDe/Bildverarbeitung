package ue2.filters;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import ue2.filters.DataEnrichmentFilter;
import interfaces.Readable;
import interfaces.Writeable;
import ue2.entities.Word;

public class WordRotationFilter extends DataEnrichmentFilter<List<Word>, List<List<Word>>>{

	public WordRotationFilter(Readable<List<Word>> input, Writeable<List<List<Word>>> output)
		    throws InvalidParameterException {
		        super(input, output);
		    }

	@Override
	protected boolean fillEntity(List<Word> words, List<List<Word>> entity) {
		
		if (words != null) {
            LinkedList<Word> tempList = (LinkedList<Word>) words;

            if (tempList.isEmpty()) {
                return true;
            }

            //saving one iteration
            entity.add(cloneList(tempList));

            //shifting iterations
            for (int shift = 0; shift < tempList.size() - 1; ++shift) {
                Word tempWord = tempList.removeFirst();
                tempList.addLast(tempWord);

                entity.add(cloneList(tempList));
            }

            return true;
        }

        return false;
	}

	@Override
	protected List<List<Word>> getNewEntityObject() {
		
		return new LinkedList<>();
	}	
	
	 private List<Word> cloneList(List<Word> list) {
	        return new LinkedList<>(list);
	    }
}
