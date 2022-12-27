package sample.project.jobissue.domain;

public enum ReadingCode {
	공개("공개"), 비공개("비공개");
	
	private final String description;
	
	ReadingCode(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
