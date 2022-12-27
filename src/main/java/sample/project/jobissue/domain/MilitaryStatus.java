package sample.project.jobissue.domain;

public enum MilitaryStatus {
	군필("군필"), 미필("미필");
	
	private final String description;
	
	MilitaryStatus(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
