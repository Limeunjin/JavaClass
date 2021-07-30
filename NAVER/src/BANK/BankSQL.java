package BANK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class BankSQL {

		//접속
		Connection con=null;
	
		//DB로 쿼리문 전송 위한 변수 선언
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		
		//조회(sellect)의 결과 저장하기 위한 변수 선언
		ResultSet rs = null;
	
	
		//접속
		public void connect() {
		con = DBC.DBConnect();
		
		}
		
		//접속 해제
		public void conClose() {
		try {
			con.close();
			System.out.println("db 접속 해제");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
		// 3-1. 계좌 갯수 구하기
		public int acCount() {
			
			int count = 0;
			
			String sql = "SELECT COUNT(*) FROM BANK";
			try {
				stmt =con.createStatement();
				rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
					count = rs.getInt(1);	//Number로 db두었으니까 int, 
											//1번째 값을 count에 넣겠다 -> 메인으로
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
					
			return count;
		}
		
		// 3
		public void insert(Client ct) {
			String sql = "INSERT INTO BANK VALUES(?,?,?,?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, ct.getAccountNo());
				pstmt.setInt(2, ct.getClientNo());
				pstmt.setString(3, ct.getcName());
				pstmt.setInt(4, ct.getBalance());
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					System.out.println(ct.getcName()+" 님의 계좌가 생성되었습니다");
				} else {
					System.out.println("계좌생성에 실패하였습니다");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		// 4. 입금
		public void deposit(String accountNo, int balance) {
			String sql = "UPDATE BANK SET BALANCE = BALANCE+? WHERE ACCOUNTNO=?";
			//입력한 계좌에 원래 입금액+입력한 값을 넣을 것이다
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, balance);
				pstmt.setString(2, accountNo);
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					System.out.println(balance+ " 원 입금 성공!");
				} else {
					System.out.println("입금 실패!");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 5. 출금
		public void withdraw(String accountNo, int balance) {
			String sql = "UPDATE BANK SET BALANCE = BALANCE-? WHERE ACCOUNTNO=?";
			
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, balance);
				pstmt.setString(2, accountNo);
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					System.out.println(balance+ " 원 출금 성공!");
				} else {
					System.out.println("출금 실패!");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// 6. 잔액
		public int checkBalance(String accountNo) {

			int result = 0;
			
			String sql = "SELECT BALANCE FROM BANK WHERE ACCOUNTNO=?";
			
			try {
				pstmt= con.prepareStatement(sql);
				pstmt.setString(1, accountNo);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				} 
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		
		public boolean sendCheck(String sAccount) {
			
			boolean result = false;
			String sql = "SELECT * FROM BANK WHERE ACCOUNTNO=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, sAccount);
				
				rs = pstmt.executeQuery(); //select 짝꿍 rs
				
				if(rs.next()) { //정보가 하나만 나오니 if 사용
					result = true;
				} else {
					result = false;
					System.out.println("보내시는 분의 계좌가 존재하지 않습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		//받는 사람
		public boolean receive(String rAccount) {
			
			boolean result = false;
			String sql = "SELECT * FROM BANK WHERE ACCOUNTNO=?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, rAccount);
				
				rs = pstmt.executeQuery(); //select 짝꿍 rs
				
				if(rs.next()) { //정보가 하나만 나오니 if 사용
					result = true;
				} else {
					result = false;
					System.out.println("받는 분의 계좌가 존재하지 않습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		//보내는 사람, 받는 사람 
		public void transfer(String sAccount, String rAccount, int sbalance) {

			String sql1 = "UPDATE BANK SET BALANCE = BALANCE-? WHERE ACCOUNTNO=?";
			try {
				pstmt = con.prepareStatement(sql1);
				pstmt.setInt(1,sbalance);
				pstmt.setString(2, sAccount);
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
			//받는 사람
			String sql2 = "UPDATE BANK SET BALANCE = BALANCE+? WHERE ACCOUNTNO=?";
			try {
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1,sbalance);
				pstmt.setString(2, rAccount);
				
				int result = pstmt.executeUpdate();
				
				if(result >0) {
					System.out.println("송금 완료!!");
				} else {
					System.out.println("송금 실패!!");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

			
			
			
			
			
		
}

