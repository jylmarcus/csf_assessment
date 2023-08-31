package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {
	
	@Autowired
	private AmazonS3 s3;
	// TODO: Task 1
	public String uploadImage(String imageName, MultipartFile uploadFile) {
		Map<String, String> userData = new HashMap<>();
		userData.put("fileName", imageName);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(uploadFile.getContentType());
		metadata.setContentLength(uploadFile.getSize());
		metadata.setUserMetadata(userData);

		try {
			PutObjectRequest putReq = new PutObjectRequest("jylmarcus", "newsImages/%s".formatted(imageName), uploadFile.getInputStream(), metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3.putObject(putReq);
            System.out.printf("result: %s\n", result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

		return getImageUrl(imageName);

	}

	public String getImageUrl(String imageName) {
        String key = "newsImages/%s".formatted(imageName);
        return s3.getUrl("jylmarcus", key).toExternalForm();
    }

}
