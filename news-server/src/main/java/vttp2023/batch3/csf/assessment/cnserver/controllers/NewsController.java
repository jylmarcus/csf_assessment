package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

		String newsId = newsSvc.postNews(image, title, description, photo, tags);

		return ResponseEntity.ok(newsId);
	}

	// TODO: Task 2


	// TODO: Task 3

}
