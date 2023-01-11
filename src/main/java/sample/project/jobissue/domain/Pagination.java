package sample.project.jobissue.domain;

import lombok.Data;

@Data
public class Pagination {

	private int pageSize = 20;	/** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
	
	private int blockSize = 10;	/** 2. 페이징된 버튼의 블럭당 최대 개수 **/
	
	private int page = 1;	/** 3. 현재 페이지 **/

	private int block = 1; /** 4. 현재 블럭 **/

	private int totalListCnt; /** 5. 총 게시글 수 **/

	private int totalPageCnt;	/** 6. 총 페이지 수 **/

	private int totalBlockCnt; /** 7. 총 블럭 수 **/

	private int startPage = 1; /** 8. 블럭 시작 페이지 **/
	
	private int endPage = 1; /** 9. 블럭 마지막 페이지 **/

	private int startIndex = 0; /** 10. DB 접근 시작 index **/
	
	private int prevBlock; /** 11. 이전 블럭의 마지막 페이지 **/

	private int nextBlock; /** 12. 다음 블럭의 시작 페이지 **/

	
	// 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.
	public Pagination(int totalListCnt, int page) {
		// 총 게시물 수	- totalListCnt
		// 현재 페이지	- page

		this.page = page; /** 3. 현재 페이지 **/

		this.totalListCnt = totalListCnt; /** 5. 총 게시글 수 **/

		// 한 페이지의 최대 개수를 총 게시물 수 * 1.0로 나누어주고 올림 해준다.
		this.totalPageCnt = (int) Math.ceil(totalListCnt * 1.0 / pageSize); /** 6. 총 페이지 수 **/

		// 한 블럭의 최대 개수를 총  페이지의 수 * 1.0로 나누어주고 올림 해준다.
		this.totalBlockCnt = (int) Math.ceil(totalPageCnt * 1.0 / blockSize); /** 7. 총 블럭 수 **/

		// 현재 페이지 * 1.0을 블록의 최대 개수로 나누어주고 올림한다.
		this.block = (int) Math.ceil((page * 1.0)/blockSize); /** 4. 현재 블럭 **/
		
		this.startPage = (block - 1) * blockSize + 1; /** 8. 블럭 시작 페이지 **/
		
		this.endPage = startPage + blockSize - 1; 	/** 9. 블럭 마지막 페이지 **/
		
		/* === 블럭 마지막 페이지에 대한 validation ===*/
		if(endPage > totalPageCnt){
			this.endPage = totalPageCnt;
		}
		
		this.prevBlock = (block * blockSize) - blockSize;/** 11. 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
		
		/* === 이전 블럭에 대한 validation === */
		if(prevBlock < 1) {
			this.prevBlock = 1;
		}

		this.nextBlock = (block * blockSize) + 1; /** 12. 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
		
		/* === 다음 블럭에 대한 validation ===*/
		if(nextBlock > totalPageCnt) {
			nextBlock = totalPageCnt;
		}

		this.startIndex = (page-1) * pageSize; /** 10. DB 접근 시작 index **/
		
	}
}
