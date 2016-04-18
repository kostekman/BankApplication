package com.luxoft.bankapp.service;

import com.luxoft.bankapp.datagenarator.DataGenerator;
import com.luxoft.bankapp.model.Bank;

import java.util.Random;

public class BankApplication {
	
	private static final String fileName = "data.csv";
	private static final String csvSeparator = ";";
	
	private static Bank bank;
	private static BankServiceImpl bankService;
	private static Random r;
	
	
	/*private static void initialize(){
		r = new Random();
		bank = new Bank();
		bankService = new BankServiceImpl();
		String line = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName));){			
			Client client = new Client();
			while((line = br.readLine()) != null){
				String[] splittedLine = line.split(csvSeparator);
				if(splittedLine[0].equals("CLIENT")){
					Gender gender;
					if(splittedLine[2].equals("0"))
						gender = Gender.FEMALE;
					else
						gender = Gender.MALE;
					
					client = new Client(splittedLine[1], gender, Float.parseFloat(splittedLine[3]));
					bankService.addClient(bank, client);
				}
				else if(splittedLine[0].equals("ACCOUNT")){
					if(splittedLine[1].equals("0")){
						if(!client.getName().equals("")){
							bankService.addAccount(client, new SavingAccount(Float.parseFloat(splittedLine[2])));
						}
					}
					else if(splittedLine[1].equals("1")){
						if(!client.getName().equals("")){
							bankService.addAccount(client, new CheckingAccount(Float.parseFloat(splittedLine[2]), Float.parseFloat(splittedLine[3])));
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}*/
	
//	private static void modifyBank(){
//		List<Client> clients = bank.getClients();
//		for(Client client : clients){
//			List<Account> accounts = client.getAccounts();
//			for(Account account : accounts){
//				if(r.nextBoolean()){
//					if(r.nextBoolean()){
//						account.deposit(Math.abs(r.nextFloat()*(float)Math.abs(r.nextInt())%1000));
//					} else
//						try {
//							account.withdraw(Math.abs(r.nextFloat()*(float)Math.abs(r.nextInt())%1000));
//						} catch (BankException e) {
//							System.out.printf("You have exceeded possible withdraw by: %f", e.toString());
//							e.printStackTrace();
//						}
//				}
//			}
//		}
//	}

	public static void main(String[] args) {
		if(args.length == 3 && args[0].equals("-gD")) {
			DataGenerator.generateData(fileName, csvSeparator, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}
		else if(args.length == 1 && args[0].equals("-report")){
			BankReport bankReporter = new BankReport();
			bankReporter.getNumberOfClients(bank);
			bankReporter.getAccountsNumber(bank);
			bankReporter.getBankCreditSum(bank);
			bankReporter.getClientsSorted(bank);
			bankReporter.getClientsByCity(bank);
		}

		bank.printReport();
		System.out.println("\n------------------------------------------");
		bank.printReport();
	}




}
