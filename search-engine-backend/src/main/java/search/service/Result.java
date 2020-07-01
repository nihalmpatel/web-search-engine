package search.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Result {

	private String searchKeys = "";
	private Map<String, Integer> occurencesOfAllKeys = new HashMap<String, Integer>();

	public String getSearchKeys() {
		return searchKeys;
	}

	public void setSearchKeys(String searchKeys) {
		if (this.searchKeys.isEmpty()) {
			this.searchKeys = searchKeys;
		} else {
			this.searchKeys = this.searchKeys + " " + searchKeys;
		}
	}

	public Map<String, Integer> getOccurencesOfAllKeys() {
		return occurencesOfAllKeys;
	}

	public Map<String, Integer> getSortedOccurencesOfAllKeys() {
		return this.occurencesOfAllKeys.entrySet().stream()
				.sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public void setOccurencesOfAllKeys(Map<String, Integer> occurencesOfAllKeys) {
		this.occurencesOfAllKeys = occurencesOfAllKeys;
	}

	public void setOccurencesOfEachKey(Map<String, Integer> occurencesOfEachKeys) {

		if (this.getOccurencesOfAllKeys().isEmpty()) {
			this.getOccurencesOfAllKeys().putAll(occurencesOfEachKeys);
		} else {
			occurencesOfEachKeys.entrySet().stream().forEach(e -> {
				if (this.getOccurencesOfAllKeys().containsKey(e.getKey())) {
					this.getOccurencesOfAllKeys().put(e.getKey(),
							(e.getValue() + this.getOccurencesOfAllKeys().get(e.getKey())));
				} else {
					this.getOccurencesOfAllKeys().put(e.getKey(), e.getValue());
				}
			});
		}
	}
}
