package BANK;

import java.util.Scanner;

public class BankMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		Client ct = new Client();
		BankSQL bsql = new BankSQL();
		
		
		boolean run = true;
		int menu = 0;
		
		do {
		
			System.out.println("============================");
            System.out.println("1. DB접속     2. DB접속 해제   3. 계좌생성");
            System.out.println("4. 입금  5. 출금  6. 잔액조회");
            System.out.println("7. 송금	8. 종료");
        	System.out.println("============================");
        	System.out.println("메뉴 선택>>");
	
        	menu = sc.nextInt();

        	switch(menu) {
        	case 1:
        		bsql.connect();
        		break;

        	case 2:
        		bsql.conClose();
        		break;
	
        	case 3:
        		//계좌번호 생성
        		// 현재 계좌 갯수 + 1
        		int clientNo = bsql.acCount() + 1; 
        		String accountNo = null;
        		
        		if(clientNo<10) {
        			accountNo ="110-352-33440"+Integer.toString(clientNo);
        		} else if(clientNo<100) {
        			accountNo ="110-352-3344"+Integer.toString(clientNo);
        		} ////////////
        		
        		System.out.println("고객이름 >> ");
        		String cName = sc.next();
        		
        		System.out.println("첫 입금액>>");
        		int balance = sc.nextInt();
        		
        		/*ct.setAccountNo(AccountNo);
        		ct.setClientNo(clientNo);
        		ct.setcName(cName);
        		ct.setBalance(balance);*/
        		
        		ct  = new Client(accountNo, clientNo, cName, balance);
        		
        		bsql.insert(ct);
        		
        	
        		break;
	
        	case 4:
        		System.out.print("계좌번호 >> ");
        		accountNo= sc.next();
        		
        		System.out.println("입금액>> ");
        		balance= sc.nextInt();
        		
        		bsql.deposit(accountNo,balance);
        		
        		break;
	
        	case 5:
        		System.out.println("계좌번호>>");
        		accountNo= sc.next();
        		
        		System.out.println("출금액>> ");
        		int wbalance= sc.nextInt();
        		
        		balance = bsql.checkBalance(accountNo);
        		
        		//잔액이 출금액보다 큰지 확인
        
        		if(balance>=wbalance) {
        			bsql.withdraw(accountNo,wbalance);
        		} else {
        			System.out.println("출금액이 " +(wbalance- balance) + "원 부족함");
        		}
        		
        		break;
        		
        	case 6:
        		// 현재 잔액 조회
        		System.out.println("조회할 계좌번호>>");
        		accountNo= sc.next();
        		
        		//잔액 balance
        		balance = bsql.checkBalance(accountNo);
        		System.out.println("잔액 : " + balance);
        	
        		
        		break;
	
        		
        	case 7:
        		//송금시스템
        		// (1) 보내는 사람 계좌
        		System.out.println("보내는 분 계좌>> ");
        		String sAccount = sc.next();
        		
        		boolean check1 = bsql.sendCheck(sAccount);
        		
        		// (2) 보내는 사람 잔액
        		boolean check2;
        		System.out.println("송금액>> ");
        		int sbalance= sc.nextInt();
        		
        		balance = bsql.checkBalance(sAccount);
        		
        		if(balance >=sbalance) {
        			check2 = true;
        		} else {
        			check2 = false;
        			System.out.println("송금액이 " +(sbalance- balance) + "원 부족함");
        		}
        		
        	
        		// (3) 받는 사람 계좌
        		System.out.println("받으시는 분 계좌>> ");
        		String rAccount = sc.next();
        		
        		boolean check3 = bsql.receive(rAccount); 
        		
        		if(check1 && check2 && check3) {
        			bsql.transfer(sAccount,rAccount, sbalance);
        		}
        		
        		
        		break;

        	case 8:
        		run = false;
        		System.out.println("종료합니다");
        		break;
	
        	default:
        		System.out.println("잘못 입력.");
        		break;
}
		
		
		
		} while(run);
		

		
		
		
	}

}
