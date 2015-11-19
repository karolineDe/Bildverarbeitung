package ue2.filters;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import ue2.filters.DataEnrichmentFilter;
import interfaces.Readable;
import interfaces.Writeable;
import ue2.entities.Line;
import ue2.entities.Word;

public class LineToWordListFilter extends DataEnrichmentFilter<Line, List<Word>> { 
	
    private static final String TEXT_SEPARATORS = "[ \r\n\t.,;:'\"()?!]";
	
	public LineToWordListFilter(Readable<Line> input, Writeable<List<Word>> output) throws InvalidParameterException {
		super(input, output);
	}

	 @Override
	    protected boolean fillEntity(Line stringLine, List<Word> entity)  {
		 
	        if (stringLine != null && stringLine.getValue() != null) {

	            for (String word : stringLine.getValue().split(TEXT_SEPARATORS)) {
	                String trimmedWord = word.trim();

	                if (!trimmedWord.isEmpty()) {
	                	
	                    entity.add(new Word(stringLine.getLineIndex(), trimmedWord));
	                }
	            }

	            if (!entity.isEmpty()) return true;
	        }

	        return false;
	    }

	@Override
	protected List<Word> getNewEntityObject() {
		
		return new LinkedList<>();
	}
}
