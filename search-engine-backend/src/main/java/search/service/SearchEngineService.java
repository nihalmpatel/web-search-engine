package search.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import algs4.cs.princeton.edu.algorithmDesign.Sequences;
import algs4.cs.princeton.edu.textprocessing.TST;

@Service
public class SearchEngineService {

	private TST<Word> tst;
	
	public SearchEngineService() {
		this.init();
	}

	public void init() {
		String source = "src/files/W3C Web Pages/";
		String destination = "src/files/W3C Web Pages/ConvertedTextFiles/";
		String[] filenames = Utils.loadFilenamesFromPath(source);

		for (String filename : filenames) {
			String content = Utils.parseHTMLToText(source + filename);
			String outputFilename = filename.substring(0, filename.lastIndexOf('.')) + ".txt";
			Utils.writeStringToFile(destination + outputFilename, content);
		}

		source = "src/files/W3C Web Pages/ConvertedTextFiles/";
		filenames = Utils.loadFilenamesFromPath(source);

		// Create a tries using TST
		tst = new TST<Word>();

		for (String filename : filenames) {
			String content = Utils.readFileToString(source + filename);

			// Extract words from file content
			// A word character \w is equivalent to [a-zA-Z_0-9].
			// Replace all non-word character in file content by \n for tokenizing purpose
			String regEx = "[^a-zA-Z_0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(content);
			String tmp = m.replaceAll("\n").trim();

			// Tokenize by a list of delimiters
			StringTokenizer st = new StringTokenizer(tmp.toString(), "\n");

			String words[] = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				words[i] = st.nextToken();
				i++;
			}

			for (int l = 0; l < words.length; l++) {
				String word = words[l].toLowerCase();
				if (tst.contains(word)) {
					// Increase occurrences in TST
					Word w = tst.get(word);
					Map<String, Integer> occur = w.getOccurences();
					if (occur.containsKey(filename)) {
						occur.put(filename, occur.get(filename) + 1);
					} else {
						occur.put(filename, 1);
					}
					tst.put(word, w);
				} else {
					// First occurrence in TST
					Map<String, Integer> occur = new HashMap<String, Integer>();
					occur.put(filename, 1);
					Word w = new Word(word, occur);
					tst.put(word, w);
				}
			}
		}
	}
	
	public Result findByQuery(String query) {
		Result rs = new Result();
		
		String[] keys = query.split(" ");
		for (String key : keys) {
			key = key.toLowerCase();
			if (tst.get(key) != null) {
				rs.setSearchKeys(key);
				rs.setOccurencesOfEachKey(tst.get(key).getSortedOccurences());
				
			} else {
				// If TST does not content the keyword, use edit distance algorithm
				// to suggest the search key with smallest distance
				Iterator<String> tstKeysIter = tst.keys().iterator();
				
				int min_distance = 1_000_000;
				String min_distance_key = "";
				while (tstKeysIter.hasNext()) {
					String tstKey = tstKeysIter.next();
					int ed = Sequences.editDistance(key, tstKey);
					if (ed <= min_distance) {
						min_distance = ed;
						min_distance_key = tstKey;
					}
				}
				
				rs.setSearchKeys(min_distance_key);
				
				if (tst.get(min_distance_key) != null) {
					rs.setOccurencesOfEachKey(tst.get(min_distance_key).getSortedOccurences());
				}
			}
		}
		
		return rs;
	}
	
	public TST<Word> getTst() {
		return this.tst;
	}
}