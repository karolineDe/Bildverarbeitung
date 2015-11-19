package ue2.filters;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import ue2.filters.DataEnrichmentFilter;
import interfaces.Readable;
import interfaces.Writeable;
import ue2.entities.Char;
import ue2.entities.Word;

public class CharToWordBuilderFilter extends DataEnrichmentFilter<Char, Word> {

	private static final String END_WORD_INDICATORS = "  \r\n\t";
    private static final Set<Character> END_WORD_INDICATORS_SET = new HashSet<>();

    private StringBuilder _sb;

    public CharToWordBuilderFilter(Readable<Char> input, Writeable<Word> output)
    throws InvalidParameterException {
        super(input, output);

        prepareEndWordIndicatorsSet(END_WORD_INDICATORS);
    }

    @Override
    protected boolean fillEntity(Char character, Word entity) {
        if (isBufferEmpty() && isCharEmpty(character)) return true; //nothing to do here

        if (isBufferEmpty() && !isCharEmpty(character) && !isEndWordIndicator(character)) {
            createNewBuffer(character);
            return false;
        }

        if (!isBufferEmpty() && !isCharEmpty(character)) {

            if (isEndWordIndicator(character)) {

                flushBuffer(entity);
                return true;

            } else {
                appendCharToBuffer(character);
                return false;
            }
        }

        if (!isBufferEmpty() && isCharEmpty(character)) {
            flushBuffer(entity);
            return true;
        }

        return false;
    }

    @Override
    protected Word getNewEntityObject() {
        return new Word();
    }

    private boolean isBufferEmpty() {
        return _sb == null || _sb.length() == 0;
    }

    private boolean isCharEmpty(Char character) {
        return character == null || character.getValue() == null;
    }

    private void createNewBuffer(Char character) {
        _sb = new StringBuilder();

        appendCharToBuffer(character);
    }

    private void appendCharToBuffer(Char character) {
        _sb.append(character.getValue());
    }

    private void flushBuffer(Word entity) {
        entity.setValue(_sb.toString());

        _sb = null;
    }

    private boolean isEndWordIndicator(Char character) {
        return END_WORD_INDICATORS_SET.contains(character.getValue());
    }

    private static void prepareEndWordIndicatorsSet(String separators) {
        separators.chars().forEach(c -> END_WORD_INDICATORS_SET.add((char) c));
    }
	

}
