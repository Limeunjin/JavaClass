package NAVERID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class NaverSQL {



	//db에 접속하고 입력하는 역할
	//쿼리문을 모아 놓은  클래스
	


	// DB 접속을 위한 변수 선언
	Connection con=null;
	
	//DB로 쿼리문 전송 위한 변수 선언
	Statement stmt = null;
	PreparedStatement pstmt = null;
	//PreparedStatement : ?를 문자로 인식
	
	//조회(sellect)의 결과 저장하기 위한 변수 선언
	ResultSet rs = null;
	
	
	//1. db접속 메소드(앞에 navermain클래스의)
	public void connect() {
		con = DBConnection.DBConnect();
	}
	
	//2. DB접속해제 메소드
	public void conClose() {
		try {
			con.close();
			System.out.println("DB접속해제!");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//3. 회원가입 메소드
	public void memberJoin(NMember nmember) {
	//	System.out.println("3. 회원가입 메소드로 잘 넘어 왔나?");
	//	System.out.println(nmember);
	//	System.out.println("con : "+ con);
		
		String sql = "INSERT INTO NAVER VALUES(?,?,?,TO_DATE(?),?,?,?)";
		//여기선 BIRTH가 STRING이지만, SQL에선 DATE형식이기에
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, nmember.getN_id()); //1번째 ?에 넣을 값 설정
			pstmt.setString(2, nmember.getN_pw());
			pstmt.setString(3, nmember.getN_name());
			pstmt.setString(4, nmember.getN_birth());
			pstmt.setString(5, nmember.getN_gender());
			pstmt.setString(6, nmember.getN_email());
			pstmt.setString(7, nmember.getN_phone());
			
			int result = pstmt.executeUpdate(); //ctrl + enter 실행부분
					
			if(result>0) {
				System.out.println("회원가입 성공");
			} else {
				System.out.println("회원가입 실패");
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException 발생!");
			e.printStackTrace();
		} 
		
	}
	
	//4. 회원조회 메소드
	public void select() {
	
		String sql = "SELECT * FROM NAVER";
		
		try {
			stmt = con.createStatement(); //?가 없으니까 stmt 사용
			rs = stmt.executeQuery(sql);	//sql 실행하면 나오는 정보를 넣겠다!
			
			while(rs.next()) {	// 레코드가 더이상 존재하지 않을 때까지 반복한다.(sql 조회하는 줄)
				
				System.out.println();	
				System.out.println("아이디 : " + rs.getString(1)); //1번째 컬럼
				System.out.println("비밀번호 : " + rs.getString(2));
				System.out.println("이름 : " + rs.getString(3));
				System.out.println("생년월일 : " + rs.getString(4));
				System.out.println("성별 : " + rs.getString(5));
				System.out.println("이메일 : " + rs.getString(6));
				System.out.println("연락처 : " + rs.getString(7));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	// 5-1, 6-1. 아이디, 비번 체크 메소드
	public boolean idCheck(String id, String pw) {
		
		// (1) 메소드 데이터타입과 같은 변수 만들고 초기값!
		boolean checkResult  = false;
		
		String sql = "SELECT N_ID FROM NAVER WHERE N_ID= ? AND N_PW =?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//결과 값이 존재할 때, 아이디가 하나밖에 없을 것이라 해서 if 사용
				checkResult = true;
				
			} else {		//결과 값 존재 안할 때
				checkResult = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
		// (2) 만들어준 변수 return
		return checkResult;
		
		
	}
	
	// 5-2. 회원정보 수정 메소드
	public void modify(NMember nmember) {
		String sql = "UPDATE NAVER SET N_PW=?, N_NAME=?, N_BIRTH=TO_DATE(?), N_GENDER=?, N_EMAIL=?, N_PHONE=? WHERE N_ID=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, nmember.getN_pw());
			pstmt.setString(2, nmember.getN_name());
			pstmt.setString(3, nmember.getN_birth());
			pstmt.setString(4, nmember.getN_gender());
			pstmt.setString(5, nmember.getN_email());
			pstmt.setString(6, nmember.getN_phone());
			pstmt.setString(7, nmember.getN_id());
			
			int result = pstmt.executeUpdate();
			
			if(result>0) {
				System.out.println("회원정보 수정 성공");
			} else {
				System.out.println("회원정보 수정 실패");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

		//6-2. 회원 삭제 메소드
	public void delete(String id1) {
		String sql = "DELETE NAVER WHERE N_ID=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id1);
			
			int result = pstmt.executeUpdate();
			
			if(result>0) {
				System.out.println("회원정보 삭제 성공!");
			} else {
				System.out.println("회원정보 삭제 실패!");
			}
		
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
