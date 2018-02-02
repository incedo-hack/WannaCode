package hackathon.virustotal.beans;

public class FileScanData {
	
	public String system_user_name;
	public String system_file_name;
	public String system_file_path;
	public String system_file_hash;
	
	public FileScanData() {
		super();
	}
	
	public String getSystem_user_name() {
		return system_user_name;
	}
	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}
	public String getSystem_file_name() {
		return system_file_name;
	}
	public void setSystem_file_name(String system_file_name) {
		this.system_file_name = system_file_name;
	}
	public String getSystem_file_path() {
		return system_file_path;
	}
	public void setSystem_file_path(String system_file_path) {
		this.system_file_path = system_file_path;
	}
	public String getSystem_file_hash() {
		return system_file_hash;
	}
	public void setSystem_file_hash(String system_file_hash) {
		this.system_file_hash = system_file_hash;
	}
	
	

}
