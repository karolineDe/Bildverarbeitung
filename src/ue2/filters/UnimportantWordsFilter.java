package ue2.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ue2.filters.DataEnrichmentFilter;
import interfaces.Readable;
import interfaces.Writeable;
import ue2.entities.Word;

public class UnimportantWordsFilter extends DataEnrichmentFilter<List<List<Word>>, List<List<Word>>> {

	private static Set<String> _dict = new HashSet<>();
	
	public UnimportantWordsFilter(Readable<List<List<Word>>> input, Writeable<List<List<Word>>> output)
		    throws InvalidParameterException {
        
		super(input, output);

       try{
    	   BufferedReader br = Files.newBufferedReader(Paths.get("C:\\WorkDir\\Eclipse_Workspace_JavaEE\\Uebung1_pipe\\src\\unimportantWords.txt"));
    	   
    	   for(String line = br.readLine(); line != null; line = br.readLine())
    	   {
    		   _dict.add(line);
    		   System.out.println("\n\nLine: "+line);
    	   }
    	  
       }catch(IOException e){
    	   e.printStackTrace();
       }
    }

	@Override
	protected boolean fillEntity(List<List<Word>> wordLists, List<List<Word>> entity) {
		
//		if(!wordLists.isEmpty()){
//			
//			for(List<Word> line : wordLists){
//				
//				if(_dict.contains(line.get(0).getValue())){
//					Word temp = line.remove(0);
//					line.add(temp);
//				}
//				
//				if(!line.isEmpty()){
//					entity.add(line);
//				}
//			}
//			return true;
//		}
//		
//		return false;
		
		
		 if (wordLists != null && !wordLists.isEmpty()) {

            for (List<Word> wordList : wordLists) { // loop through word lists, get current list (line)

                // if the first word and following words are present in the dictionary - remove them from line
                wordList = sanitizeWordList(wordList);

                //check if current line is not empty
                if (wordList != null && !wordList.isEmpty()) {
                    entity.add(wordList);
                }
            }

            //check if the whole list is not empty
            if (!entity.isEmpty()) {
                return true;
            }
        }
        return false;
	}

	@Override
	protected List<List<Word>> getNewEntityObject() {
		
		return new LinkedList<>();
	}
	
	private List<Word> sanitizeWordList(List<Word> wordList) {
        List<Word> sanitizedList = new LinkedList<>(wordList);

        for (Word word : wordList) {
            if (_dict.contains(word.getValue())){
                //removing first useless word occurrence in the sanitized list.
                sanitizedList.remove(word);
            } else {
                //word list is sanitized (there are no useless words on the beginning of the line)
                return sanitizedList;
            }
        }
        return null;
    }
	
}
