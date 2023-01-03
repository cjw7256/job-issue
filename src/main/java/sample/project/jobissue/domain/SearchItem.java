package sample.project.jobissue.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class SearchItem {
	private int page;
	private int perPageNum;
	//속성 searchType, keyword 추가
	private String searchType;
	private String keyword;
	
	public SearchItem() {
		this.page = 1;
		this.perPageNum = 10;
        this.searchType = null;
		this.keyword = null;
		
	}
	public int getPageStart() {
		return (this.page - 1)*perPageNum;
	}
    
	//searchType, keyword 추가
	public String makeQuery() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", this.perPageNum);
				
		if (searchType!=null) {
			uriComponentsBuilder
					.queryParam("searchType", this.searchType)
					.queryParam("keyword", this.keyword);
		}
		return uriComponentsBuilder.build().encode().toString();
	}
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", searchType=" + searchType + ", keyword="
				+ keyword + "]";
	}
}
