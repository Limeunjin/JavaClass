package BANK;

public class Client {

	private String accountNo; 	//계좌번호
	private int clientNo;	//회원번호
	private String cName;	//회원이름
		
	private int balance;	//잔액
	
	public int getClientNo() {
		return clientNo;
	}
	public void setClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Client [clientNo=" + clientNo + ", cName=" + cName + ", accountNo=" + accountNo + ", balance=" + balance
				+ "]";
	}

	
	public Client(String accountNo,int clientNo, String cName, int balance) {
		super();
		this.accountNo = accountNo;
		this.clientNo = clientNo;
		this.cName = cName;
	
		this.balance = balance;
	}
	
	public Client() {
		// TODO Auto-generated constructor stub
	}

	
	
	




}
