package search.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Word {
	
	private static AtomicInteger atomicIndex = new AtomicInteger();
	private Integer index;
	private String word;
	private  Map<String, Integer> occurences;
	
	public Word (String word, Map<String, Integer> occurences) {
		this.index = atomicIndex.incrementAndGet();
		this.word = word;
		this.occurences = occurences;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	public Map<String, Integer> getOccurences() {
		return occurences;
	}

	public void setOccurences(Map<String, Integer> occurences) {
		this.occurences = occurences;
	}
	
	public Map<String, Integer> getSortedOccurences() {
		return this.occurences.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	

}
