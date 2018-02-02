package hackathon.virustotal.rest;

import java.util.Map;

import hackathon.virustotal.APIDetails;
import hackathon.virustotal.exception.VTException;

public class VTController implements IVTController {
	
	SSLRestTemplate restTemplate = new SSLRestTemplate();

	@Override
	public Map<String, String> uploadHash(String listOfHashes) throws VTException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReport(String listOfHashes) throws VTException {
		SSLRestTemplate restTemplate =  new SSLRestTemplate();
		String response = restTemplate.get(APIDetails.VIRUS_TOTAL_URL_PREFIX + "?apikey=" + APIDetails.API_KEY + "&resource="+listOfHashes);
		System.out.println(response);
		return response;
	}

}
