package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import search.service.Result;
import search.service.SearchEngineService;

@RestController
public class SearchEngineController {

	@Autowired
	public SearchEngineService ses;

	@ModelAttribute
	public void setResponseHeader(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	@RequestMapping("/search")
	public ResponsedSearchResult search(@RequestParam Map<String, String> requestParams) {
		String query = requestParams.get("q");
		String page = requestParams.get("page");
		String size = requestParams.get("size");

		if (!query.isEmpty()) {
			Result searchResult = ses.findByQuery(query);
			ResponsedSearchResult responseResult = new ResponsedSearchResult();

			Map<String, Integer> occurences = searchResult.getSortedOccurencesOfAllKeys();
			List<Hit> hits = new ArrayList<>();

			Integer resultRange = Integer.valueOf(size);
			Integer resultPage = Integer.valueOf(page);

			if (occurences.size() <= resultRange) {
				occurences.forEach((k, v) -> hits.add(new Hit(new Metadata(k, "" + v))));
			} else {
				int startIndex = (resultPage - 1) * resultRange;
				int endIndex = ((resultPage * resultRange) <= occurences.size()) ? resultPage * resultRange
						: occurences.size();

				int i = 0;
				for (Map.Entry<String, Integer> entry : occurences.entrySet()) {
					if (startIndex <= i && i < endIndex) {
						hits.add(new Hit(new Metadata(entry.getKey().substring(0, entry.getKey().lastIndexOf('.')) + ".htm", "" + entry.getValue())));
					}
					i++;
				}
			}

			responseResult.setTotal(occurences.size());
			responseResult.setSuggestedSearch(searchResult.getSearchKeys());
			responseResult.setHits(hits);

			return responseResult;

		} else {
			ResponsedSearchResult responseResult = new ResponsedSearchResult();
			responseResult.setTotal(0);
			return responseResult;
		}

	}
}
