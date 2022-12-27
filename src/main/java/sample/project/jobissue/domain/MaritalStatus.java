package sample.project.jobissue.domain;

public enum MaritalStatus {
	기혼("기혼"), 미혼("미혼");
	
	private final String description;
	
	MaritalStatus(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
