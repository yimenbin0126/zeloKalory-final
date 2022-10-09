package eunbin.DTO;

// 회원정보 테이블
public class e_SvPagingViewDTO {
	// 게시물에서 가져올 값
	private String sv_type;
	
	// 현재 페이지
	private int page_NowBno;
	// 현재 페이지의 시작 번호
	private int page_StartBno;
	// 현재 페이지의 끝 번호
	private int page_EndBno;
	// 현재 페이지 수의 범위 개수
	private int page_RangeSize = 5;
	// 전체 페이지 수
	private int page_AllCount;
	// 이전페이지, 다음 페이지 여부
	private boolean page_prev = true;
	private boolean page_next = true;
	
	// 현재 페이지의 게시물 리스트 갯수
	private int board_NowBnoSize = 5;
	// 전체 페이지의 총 게시물 리스트 갯수
	private int board_AllCount;
	// 현재 페이지의 게시물 목록 시작번호
	private int board_NowStartBno;
	// 현재 페이지의 게시물 목록 끝번호
	private int board_NowEndBno;
	
	public e_SvPagingViewDTO () {
	}
	// 전체 게시물 갯수, 현재 페이지
	public e_SvPagingViewDTO(int board_AllCount, int page_NowBno) {
		// 값 초기화
		// 총 게시물 수
		setBoard_AllCount(board_AllCount);
		// 전체 페이지 수
		if (board_AllCount % this.board_NowBnoSize > 0) {
			setPage_AllCount(board_AllCount / this.board_NowBnoSize + 1);
		} else {
			setPage_AllCount(board_AllCount / this.board_NowBnoSize);
		}
		// 현재 페이지 번호
		setPage_NowBno(page_NowBno);
		// 1~5, 6~10. 현재 번호의 맨 뒤 숫자 파악.
		String bno_all = String.valueOf(page_NowBno);
		int bno_end = Integer.parseInt(String.valueOf(bno_all.charAt(bno_all.length()-1)));
		// 페이지 맨 뒤 숫자로 앞뒤 번호 저장하기
		int bno_end_first = 0;
		int bno_end_last = 0;
		if(1 <= bno_end && bno_end <= 5) {
			bno_end_first = 1;
			if(5 >=  page_AllCount && page_AllCount > 0) {
				bno_end_last = page_AllCount;
			} else if (page_AllCount == 0) {
				bno_end_last = 1;
			} else {
				bno_end_last = 5;
			}
		} else if (6 <= bno_end && bno_end <= 9) {
			bno_end_first = 6;
			if(6 <=  page_AllCount && page_AllCount <= 9) {
				bno_end_last = page_AllCount;
			} else {
				bno_end_last = 9;
			}
		} else if (bno_end == 0) {
			bno_end_last = 0;
		}
		// 두자리수 이상인지 확인 
		// 시작번호, 끝번호 초기화
		if(bno_all.length()>1) {
			String bno_start = bno_all.substring(0, bno_all.length()-1);
			setPage_StartBno(Integer.valueOf(bno_start)*10+bno_end_first);
			setPage_EndBno(Integer.valueOf(bno_start)*10+bno_end_last);
		} else {
			setPage_StartBno(bno_end_first);
			if (bno_end_last == 0) {
				setPage_EndBno(10);
			} else {
				setPage_EndBno(bno_end_last);
			}
		}
		
		// 이전페이지, 다음 페이지 여부
		if (this.page_RangeSize < this.page_AllCount
				&& this.page_EndBno < this.page_AllCount) {
			setPage_next(false);
		}
		
		if (this.page_AllCount < this.page_RangeSize) {
			setPage_prev(false);
			setPage_next(false);
		}
		
		// 게시물 번호 설정
		// 현재 페이지의 게시물 목록 시작번호
		setBoard_NowStartBno((this.page_NowBno)*(board_NowBnoSize)-4);
		// 현재 페이지의 게시물 목록 끝번호
		if((this.page_NowBno)*(board_NowBnoSize)>board_AllCount) {
			setBoard_NowEndBno(board_AllCount);
		} else {
			setBoard_NowEndBno((this.page_NowBno)*(board_NowBnoSize));
		}
	}
	
	public String getSv_type() {
		return sv_type;
	}
	public void setSv_type(String sv_type) {
		this.sv_type = sv_type;
	}
	public int getPage_NowBno() {
		return page_NowBno;
	}
	public void setPage_NowBno(int page_NowBno) {
		this.page_NowBno = page_NowBno;
	}
	public int getPage_StartBno() {
		return page_StartBno;
	}
	public void setPage_StartBno(int page_StartBno) {
		this.page_StartBno = page_StartBno;
	}
	public int getPage_EndBno() {
		return page_EndBno;
	}
	public void setPage_EndBno(int page_EndBno) {
		this.page_EndBno = page_EndBno;
	}
	public int getPage_RangeSize() {
		return page_RangeSize;
	}
	public void setPage_RangeSize(int page_RangeSize) {
		this.page_RangeSize = page_RangeSize;
	}
	public int getPage_AllCount() {
		return page_AllCount;
	}
	public void setPage_AllCount(int page_AllCount) {
		this.page_AllCount = page_AllCount;
	}
	public boolean isPage_prev() {
		return page_prev;
	}
	public void setPage_prev(boolean page_prev) {
		this.page_prev = page_prev;
	}
	public boolean isPage_next() {
		return page_next;
	}
	public void setPage_next(boolean page_next) {
		this.page_next = page_next;
	}
	public int getBoard_NowBnoSize() {
		return board_NowBnoSize;
	}
	public void setBoard_NowBnoSize(int board_NowBnoSize) {
		this.board_NowBnoSize = board_NowBnoSize;
	}
	public int getBoard_AllCount() {
		return board_AllCount;
	}
	public void setBoard_AllCount(int board_AllCount) {
		this.board_AllCount = board_AllCount;
	}
	public int getBoard_NowStartBno() {
		return board_NowStartBno;
	}
	public void setBoard_NowStartBno(int board_NowStartBno) {
		this.board_NowStartBno = board_NowStartBno;
	}
	public int getBoard_NowEndBno() {
		return board_NowEndBno;
	}
	public void setBoard_NowEndBno(int board_NowEndBno) {
		this.board_NowEndBno = board_NowEndBno;
	}
	
	@Override
	public String toString() {
		return "e_SvPagingView [sv_type=" + sv_type + ", page_NowBno=" + page_NowBno + ", page_StartBno="
				+ page_StartBno + ", page_EndBno=" + page_EndBno + ", page_RangeSize=" + page_RangeSize
				+ ", page_AllCount=" + page_AllCount + ", page_prev=" + page_prev + ", page_next=" + page_next
				+ ", board_NowBnoSize=" + board_NowBnoSize + ", board_AllCount=" + board_AllCount
				+ ", board_NowStartBno=" + board_NowStartBno + ", board_NowEndBno=" + board_NowEndBno + "]";
	}
}
