package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.Response;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@RequestMapping
@CrossOrigin(origins="*")
public class NewsController {

	@Autowired
	private NewsService newsSvc;

	// TODO: Task 1
	@PostMapping(path="/postNews", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postNews(@RequestPart MultipartFile image, @RequestPart String title, @RequestPart String description, @RequestPart String photo, @RequestPart String tags) {

		try {
			String newsId = newsSvc.postNews(image, title, description, photo, tags);
			JsonObject resp = Json.createObjectBuilder().add("newsId", newsId).build();
			return ResponseEntity.ok(resp.toString());
		} catch (Exception e) {
			return ResponseEntity.status(400).body("Unable to post");
		}
	}

	// TODO: Task 2
	@GetMapping(path="/getTags", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTrendingTags(@RequestParam Integer interval) {
		try {List<TagCount> tagsInInterval = newsSvc.getTags(interval);
			JsonArrayBuilder tagsArrayBuilder = Json.createArrayBuilder();
			for(TagCount tagCount : tagsInInterval) {
			JsonObject obj = Json.createObjectBuilder().add("tag", tagCount.tag()).add("count", tagCount.count()).build();
			tagsArrayBuilder.add(obj);
		}
			JsonArray tagsArray = tagsArrayBuilder.build();
			return ResponseEntity.ok(tagsArray.toString());}
		catch (Exception e){
			return ResponseEntity.status(404).body("Not Found");
		}
	}

	// TODO: Task 3
	@GetMapping(path="/getNews", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getNews(@RequestParam("interval") Integer interval, @RequestParam("tag") String tag) {
		try {
			List<News> newsResults = newsSvc.getNewsByTag(interval, tag);
			JsonArrayBuilder newsArrayBuilder = Json.createArrayBuilder();
			for(News news: newsResults) {
				JsonObject obj = Json.createObjectBuilder().add("title", news.getTitle()).add("description", news.getDescription()).add("date", Long.toString(news.getPostDate())).add("imageUrl", news.getImage()).add("tags", news.getTags().toString()).build();
				newsArrayBuilder.add(obj);
			}
			JsonArray newsArray = newsArrayBuilder.build();
			return ResponseEntity.ok(newsArray.toString());
		} catch (Exception e) {
			return ResponseEntity.status(404).body("Not Found");
		}
	}

}
