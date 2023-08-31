package vttp2023.batch3.csf.assessment.cnserver.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
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

		String imgUrl = imgRepo.uploadImage(title, image);

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
	public List<TagCount> getTags(Integer interval) {
		long intervalInMillis = interval * 60000L;
		return newsRepo.getTags(intervalInMillis);
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(Integer interval, String tag) {
		long intervalInMillis = interval * 60000L;
		List<Document> results = newsRepo.getNews(intervalInMillis, tag);
		List<News> newsResults = new LinkedList<>();
		for(Document doc: results) {
			News news = new News();
			news.setTitle(doc.getString("title"));
			news.setDescription(doc.getString("description"));
			news.setId(doc.get("_id").toString());
			news.setPostDate(doc.getLong("date"));
			news.setTags(doc.getList("tags", String.class));
			news.setImage(imgRepo.getImageUrl(news.getTitle()));
			newsResults.add(news);
		}
		return newsResults;
	}
	
}
