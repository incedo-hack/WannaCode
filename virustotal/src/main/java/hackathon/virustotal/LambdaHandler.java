package hackathon.virustotal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hackathon.virustotal.beans.FileScanData;
import hackathon.virustotal.rest.IVTController;
import hackathon.virustotal.rest.VTController;

public class LambdaHandler implements RequestHandler<S3Event, String> {
	
	AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJF3PYEGE5IH375JA", "/bOBALqurLw4aJqSJ8Qx0njquvW2Y648lWMNEFeG");
	private AmazonS3 s3 = new AmazonS3Client(awsCredentials);

    public LambdaHandler() {
    	
    }

	@Override
	public String handleRequest(S3Event event, Context context) {
		context.getLogger().log("Received event: " + event);
		/*System.setProperty("aws.accessKeyId", "AKIAJF3PYEGE5IH375JA");
		System.setProperty("aws.secretKey", "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");
*/
        // Get the object from the event and show its content type
        String bucket = "wannacode";
        String key = "collected/C.json";
        try {
        	for (Bucket s3bucket : s3.listBuckets()) {
        		context.getLogger().log(s3bucket.getName());
        	}
            S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
            context.getLogger().log(response.toString());
            String contentType = response.getObjectMetadata().getContentType();
            context.getLogger().log("Object Content :" +response.getObjectContent().toString());
            context.getLogger().log("CONTENT TYPE: " + contentType);
            context.getLogger().log("Object Metadata :" +response.getObjectMetadata().toString());
            
            System.out.println(response.getObjectMetadata().getContentType());
            System.out.println(response.getObjectMetadata().getContentLength());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getObjectContent()));
            
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
            	jsonContent.append(line);
            	context.getLogger().log(line);
            }
            
            Gson gsonDeserialiser = new GsonBuilder().create();
            FileScanData[] fileScanData = gsonDeserialiser.fromJson(jsonContent.toString(), FileScanData[].class);
            
            StringBuilder stringOfHashes = new StringBuilder();
            for(int i=0; i< fileScanData.length; i++) {
            	stringOfHashes.append(fileScanData[i].getSystem_file_hash());
            	
            	if(i != fileScanData.length - 1) {
            		stringOfHashes.append(",");
            	}
            }
            		
            IVTController restController = new VTController();
            String result = restController.getReport(stringOfHashes.toString());
            
            Calendar cal = Calendar.getInstance();
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBytes());
            s3.putObject(bucket, "VT_Output/" +"vtresult.json", inputStream, new ObjectMetadata());
            
        } catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log(String.format(
                "Error getting object %s from bucket %s. Make sure they exist and"
                + " your bucket is in the same region as this function.", key, bucket));
            
            return "failure";
        }
        return "Success";
	}

}
