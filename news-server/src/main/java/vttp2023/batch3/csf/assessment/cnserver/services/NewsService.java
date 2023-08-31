package vttp2023.batch3.csf.assessment.cnserver.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {
	
	@Autowired
	private ImageRepository imgRepo;

	@Autowired
	private NewsRepository newsRepo;

	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(MultipartFile image, String title, String description, String photo, String tags) {

		String imgUrl = imgRepo.uploadImage(photo, image);

		News news = new News();
		news.setTitle(title);
		news.setDescription(description);
		news.setImage(imgUrl);
		news.setTags(Arrays.asList(tags.split(",")));
		news.setPostDate(System.currentTimeMillis());
		news.setId(newsRepo.insertNews(news).toString());

		return news.getId();
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public JsonArray getTags(Integer interval) {
		long intervalInMillis = interval * 60000L;
		List<TagCount> tagsInInterval = newsRepo.getTags(intervalInMillis);
		JsonArrayBuilder tagsArrayBuilder = Json.createArrayBuilder();
		for(TagCount tagCount : tagsInInterval) {
			JsonObject obj = Json.createObjectBuilder().add("tag", tagCount.tag()).add("count", tagCount.count()).build();
			tagsArrayBuilder.add(obj);
		}
		JsonArray tagsArray = tagsArrayBuilder.build();
		return tagsArray;
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(/* Any number of parameters */) {
		return new LinkedList<>();
	}
	
}
