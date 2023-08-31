package vttp2023.batch3.csf.assessment.cnserver.repositories;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final String C_NEWS="news";
	public static final String F_TITLE="title";
	public static final String F_DATE="date";
	public static final String F_DESC="description";
	public static final String F_PHOTO="imageName";
	public static final String F_TAGS="tags";

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	/*db.news.insert({
    date: 123,
    title: "title",
    description: "desc",
    imageName: "imagename",
    tags: ["123", "456"]
	}) */
	public ObjectId insertNews(News news) {
		Document doc = new Document();
		doc.put(F_DATE, news.getPostDate());
		doc.put(F_TITLE, news.getTitle());
		doc.put(F_DESC, news.getDescription());
		doc.put(F_PHOTO, news.getImage());
		doc.put(F_TAGS, news.getTags());

		Document newDoc = mongoTemplate.insert(doc, C_NEWS);
		return newDoc.getObjectId("_id");
	}
	

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method


}
