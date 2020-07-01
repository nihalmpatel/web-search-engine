import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import textprocessing.In;
import textprocessing.Out;

public class Utils {
	
	public static String[] loadFilenamesFromPath(String path) {
		File[] files = new File(path).listFiles();
		List<String> tmp = new ArrayList<>();
		for (File file : files) {
			if (!file.isDirectory()) {
				tmp.add(file.getName());
			}
		}
		String[] result = new String[tmp.size()];
		return tmp.toArray(result);
	}
	
	public static void writeStringToFile(String filename, String data) {
		Out out = new Out(filename);
		out.print(data);
	}

	public static String readFileToString(String filename) {
		In in = new In(filename);
		return in.readAll();
	}
	
	public static String findByPattern(String pattern, String content) {
        String result = "";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find( )) {
            result += "\n" + m.group() + " at " + m.start();
        }
        return result;
    }
	
	public static String parseHTMLToText(String htmlfilename) {
		String htmlString = Utils.readFileToString(htmlfilename);
		Document doc = Jsoup.parse(htmlString);
		return doc.text();
	}
	
}
