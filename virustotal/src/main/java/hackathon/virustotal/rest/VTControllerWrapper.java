package hackathon.virustotal.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.dto.VirusScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.InvalidArguentsException;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;*/

import hackathon.virustotal.APIDetails;
import hackathon.virustotal.exception.VTException;

/*public class VTControllerWrapper implements IVTController{

	private VirustotalPublicV2 virusTotalRef;

	public VTControllerWrapper() {
		try {
			VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(APIDetails.API_KEY);
			virusTotalRef = new VirustotalPublicV2Impl();
		} catch (APIKeyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<String, String> uploadHash(String listOfHashesToBeScanned) throws VTException {
		try {
			Map<String, String> mapOfScanIds = new HashMap<>();
			ScanInfo[] result = virusTotalRef.reScanFiles(listOfHashesToBeScanned.split(","));
			for (ScanInfo scanInformation : result) {
				System.out.println("___SCAN INFORMATION___");
				System.out.println("MD5 :\t" + scanInformation.getMd5());
				System.out.println("Perma Link :\t" + scanInformation.getPermalink());
				System.out.println("Resource :\t" + scanInformation.getResource());
				System.out.println("Scan Date :\t" + scanInformation.getScanDate());
				System.out.println("Scan Id :\t" + scanInformation.getScanId());
				System.out.println("SHA1 :\t" + scanInformation.getSha1());
				System.out.println("SHA256 :\t" + scanInformation.getSha256());
				System.out.println("Verbose Msg :\t" + scanInformation.getVerboseMessage());
				System.out.println("Response Code :\t" + scanInformation.getResponseCode());
				System.out.println("done.");
				
				mapOfScanIds.put(scanInformation.getSha256(),scanInformation.getScanId());
			}
			return mapOfScanIds;
			
		} catch (IOException | UnauthorizedAccessException | InvalidArguentsException | QuotaExceededException e) {
			throw new VTException(e);
		}
	}
	
	public String getReport(String listOfScanIds) throws VTException {
		try {
			FileScanReport[] result = virusTotalRef.getScanReports(listOfScanIds.split(","));
			for(FileScanReport scanreport : result) {
				System.out.println("Scan report for : " +scanreport.getSha256());
				for(String scanner : scanreport.getScans().keySet()) {
					VirusScanInfo scanInfo = scanreport.getScans().get(scanner);
					System.out.println("Scan result of : " +scanner);
					System.out.println("Detected : "+scanInfo.isDetected());
					System.out.println("Result : "+scanInfo.getResult());
					System.out.println("Update :" +scanInfo.getUpdate());
					System.out.println("Version : "+scanInfo.getVersion());
				}
			}
			
			return result.toString();
		} catch (IOException | UnauthorizedAccessException | QuotaExceededException | InvalidArguentsException e) {
			e.printStackTrace();
			throw new VTException(e);
		}
	}

}*/
