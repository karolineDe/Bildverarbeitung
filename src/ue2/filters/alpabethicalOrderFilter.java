package ue2.filters;

import java.security.InvalidParameterException;
import java.util.List;

import ue2.filters.DataTransformationFilter;
import interfaces.Writeable;
import ue2.entities.Line;
import interfaces.Readable;

public class alpabethicalOrderFilter extends DataTransformationFilter<List<Line>> {
	
	public alpabethicalOrderFilter(Readable<List<Line>> input, Writeable<List<Line>> output)
		    throws InvalidParameterException {
		        super(input, output);
		    }

	@Override
	protected void process(List<Line> listOfLines) {	
		
		if (listOfLines != null && !listOfLines.isEmpty()) {
			listOfLines.sort(
                (line_1, line_2) -> String.CASE_INSENSITIVE_ORDER.compare(line_1.getValue(), line_2.getValue())
            );
        }
	}
}

