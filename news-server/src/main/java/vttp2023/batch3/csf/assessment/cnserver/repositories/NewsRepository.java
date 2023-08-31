package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;

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
	public static final String F_ID="_id";

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
		return newDoc.getObjectId(F_ID);
	}
	

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method
	/*db.news.aggregate([
        {$match : {"date": {$gte: currentDateMillis - TimeIntervalMillis}}},
        {$unwind: "$tags"},
        {$group: { _id: "$tags",
            count: {$sum: 1}
        }},
        {$project: {
            count: 1
        }},
        {$sort: {
            count: -1,
            _id: 1
        }},
        {$limit: 10}
		]) */
	public List<TagCount> getTags(long interval) {
		MatchOperation matchOp = Aggregation.match(new Criteria(F_DATE).gte((System.currentTimeMillis()-interval)));
		UnwindOperation unwindOp = Aggregation.unwind(F_TAGS);
		GroupOperation groupOp = Aggregation.group(F_TAGS).count().as("count");
		ProjectionOperation projectionOp = Aggregation.project().andExpression(F_ID).as("tag").andInclude("count");
		SortOperation sortOp = Aggregation.sort(Direction.DESC, "count").and(Direction.ASC, "tag");
		LimitOperation limitOp = Aggregation.limit(10);

		Aggregation aggregation = Aggregation.newAggregation(matchOp, unwindOp, groupOp, projectionOp, sortOp, limitOp);

		List<TagCount> results = mongoTemplate.aggregate(aggregation, C_NEWS, TagCount.class).getMappedResults();

		return results;
	}

	// TODO: Task 3
	// Write the native Mongo query in the comment above the method
	/*db.news.aggregate([
        {$match : {"date": {$gte: 5},
					"tags": {$in:["123"]}}},
        {$sort: 
            {date: -1}
        }
	]) */
	public List<Document> getNews(long interval, String tag) {
		MatchOperation matchOp = Aggregation.match(Criteria.where(F_DATE).gte((System.currentTimeMillis()-interval)).and(F_TAGS).in(tag));
		SortOperation sortOp = Aggregation.sort(Direction.DESC, F_DATE);

		Aggregation aggregation = Aggregation.newAggregation(matchOp, sortOp);
		List<Document> results = mongoTemplate.aggregate(aggregation, C_NEWS, Document.class).getMappedResults();

		return results;
	}

}
