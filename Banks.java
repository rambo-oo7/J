
import java.io.IOException;
import java.util.*;
 
 
 Class InsufficientFundsException extends Exception{//Custom Exception Class
	 Account account; 
	 double dAmount;
	 InsufficientFundsException(){} //no argument
	InsufficientFundsException(Account account, double dAmount){
		this.account = account;
		this.dAmount = dAmount;
	}
	
	 Public String getMessage(){ //Error message
		 String str = "Withdrawal failed! Account balance:" + account.getBalance()+ ", withdrawal amount: " + dAmount + ", insufficient balance";
		return str;
	}
}
 
class Account{
	 Boolean isUseful = true; // is the card available?
	 String cardNumber; //4 digit card number, assigned by bank according to regulations
	 String userName; //username
	 String password; //password
	 Double balance; //balance
	
	Account(){
		this.isUseful = true;
		this.balance = 0.0;
	}
	Account(String cardNumber, String userName, String password){
		this.isUseful = true;
		this.balance = 0.0;
		this.cardNumber = cardNumber;
		this.userName = userName;
		this.password = password;
	}
	//
	//public void setCardNumber() {
		//Random rnd = new Random();
		 //rnd.setSeed(9999);
	//}
	//
	public void setUserName() throws IOException{//cin exception
		Scanner sc = new Scanner(System.in);
		 System.out.println("Please enter your username:");
		this.userName = sc.nextLine();
	}
	//
	public void setPassword() throws IOException {//cin exception
		Scanner sc = new Scanner(System.in);
		 System.out.println("Please enter your password:");
		this.password = sc.nextLine();
	}
	 // Check the balance
	public double getBalance(){
		return this.balance;
	}
	 // take
	public void withdraw(double money) throws InsufficientFundsException{
		if (balance < money) {
			throw new InsufficientFundsException(this, money);
		}
		balance = balance - money;
		 System.out.println("Successful withdrawal! Account balance is + balance");
	}
	 // Check the record
	public void recordsInfo() {
		
	}	
         //Save
	public void saveMoney(double money) {
		this.balance += money;
		 System.out.println("The deposit was successful! The new balance is: "+this.balance);
	}
}
 //Bank system
class Bank{
	
	
	
	
	
	Account card[];
	static int current_account_index = 0;
	 static int accountNumber = 0; //number of accounts
	 static int illegalCardNumber = 0; //freeze number
	
	Bank(){
		card = new Account[1];
		card[0] = new Account("1000", "root", "password");
		accountNumber ++;
	}
	
	void addAccount() throws IOException, InsufficientFundsException {
		Account [] temp = card;
		card = new Account[accountNumber + 1];
		for(int i = 0; i<accountNumber; i++) {
			card[i] = new Account();
			card[i] = temp[i];
		}
		card[accountNumber] = new Account();
		card[accountNumber].cardNumber = String.valueOf(accountNumber + 1000);
		card[accountNumber].setUserName();
		card[accountNumber].setPassword();
		 System.out.println("Registered successfully, the card is: "+card[accountNumber].cardNumber);
		accountNumber++;
		load();
	}
	
	void cutAccount(String cardNumber) {
		 //The system finds a malicious account and should freeze it immediately.
		 //The card is lost, it freezes immediately.
		 // Freeze according to the card number
		for (Account ac: card) {
			if(ac.cardNumber.equals(cardNumber)) {
				ac.isUseful = false;
				illegalCardNumber++;
			}
		}
	}
	//
	void load() throws IOException, InsufficientFundsException{
		 System.out.println("********************* Welcome to the xx bank login interface ****************** *******");
		 System.out.println("1: login\t2.registration\t3.exit");
		Scanner sc = new Scanner(System.in);
		 System.out.println("Please select the function:");
		int i = sc.nextInt();
		if (i==1) {
			loadInfo();
			}
		if (i==2) {
			addAccount();
			}
		if (i==3) {
			System.exit(0);
			}
	}
	
	//
	void mainWnd() throws IOException, InsufficientFundsException{
		 System.out.println("Welcome to the banking system!");
		 System.out.println("*****************Banking System************************ ");
		while (true){
			 System.out.println("1: save money \t2. withdraw money \t3. check balance \t4. report loss \t5. exit");
			Scanner sc = new Scanner(System.in);
			 System.out.println("Please select the function:");
			int i = sc.nextInt();
			if(i==5) {break;}
			if(i==1) {
				if(card[current_account_index].isUseful == true) {
					 System.out.println("Please enter the deposit amount:");
					double money = sc.nextDouble();
					card[current_account_index].saveMoney(money);}
				 else {System.out.println("Account is frozen"); break;}
			}
			if(i==2) {
				if(card[current_account_index].isUseful == true) {
					 System.out.println("Please enter the withdrawal amount:");
					double money = sc.nextDouble();
					card[current_account_index].withdraw(money);
					}
				 else {System.out.println("Account is frozen"); break;}
				}
			if(i==3) {
				if(card[current_account_index].isUseful == true)
					{
					 System.out.println("The balance is: "+card[current_account_index].getBalance());
					 }
				 else {
					 System.out.println("Account is frozen"); 
					 break;
					 }
			}
			
			if(i==4) {
				if(card[current_account_index].isUseful == true)
					{
					cutAccount(card[current_account_index].cardNumber);
					}
			 else {
				 System.out.println("Account is frozen");
				 break;
				 }
			}
		}
		System.exit(0);
	}
	
	void loadInfo() throws IOException, InsufficientFundsException{
		Scanner sc = new Scanner(System.in);
		 System.out.println("Please enter the card number:");
		String cardNumber = sc.nextLine();
		 System.out.println("Please enter password:");
		String password = sc.nextLine();
		for (Account ac: card) {
			if(ac.isUseful == true && ac.cardNumber.equals(cardNumber) && ac.password.equals(password)) {
				 System.out.println("Login succeeded!");
				mainWnd();
			}
			current_account_index++;
		}
		 System.out.println("The card number or password is wrong! Please log in again");
		loadInfo();
	}
}
 
 
//test
public class Banks {
 
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Bank b = new Bank();
		//System.out.println(b.card[0].balance);
		
		try {
			b.load();
		} catch (InsufficientFundsException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}