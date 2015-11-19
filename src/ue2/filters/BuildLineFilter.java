package ue2.filters;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import ue2.filters.DataEnrichmentFilter;
import interfaces.Writeable;
import ue2.entities.Line;
import ue2.entities.Word;
import interfaces.Readable;

public class BuildLineFilter extends DataEnrichmentFilter<List<List<Word>>, List<Line>> {

	
	public BuildLineFilter(Readable<List<List<Word>>> input, Writeable<List<Line>> output)
		    throws InvalidParameterException {
		        super(input, output);
		    }

	@Override
	protected boolean fillEntity(List<List<Word>> wordLists, List<Line> entity) {
		
		if(wordLists != null){
			
			if(wordLists.isEmpty()){
				return true;
			}
			
			for(List<Word> list : wordLists){
				int size = list.size();
				int lineNr = list.get(0).getLineIndex();
				StringBuilder sb = new StringBuilder((2*size)+1);
				
				for(Word word : list){
					sb.append(word.getValue());
					sb.append(" ");
				}
				
				sb.append(" - ");
				sb.append(lineNr);
				
				entity.add(new Line(lineNr, sb.toString()));
			}
			return true;
		}
		
		return false;
	}

	@Override
	protected List<Line> getNewEntityObject() {
		
		return new LinkedList<>();
	}
}
