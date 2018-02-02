package hackathon.virustotal.rest;

import java.util.Map;

import hackathon.virustotal.exception.VTException;

public interface IVTController {
	
	public Map<String, String> uploadHash(String listOfHashes) throws VTException;
	
	public String getReport (String listOfHashes) throws VTException;

}
