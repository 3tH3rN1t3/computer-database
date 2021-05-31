package com.excilys.cdb;

import java.io.IOException;
import java.util.ArrayList;

import com.excilys.cdb.ui.CLI;

public class Main {

	public static void main(String[] args) throws IOException {
		//CLI cli = new CLI();
		//cli.runCLI();
		int j=0;
		for(int k=0; k<5; k++) {
			j++;
		}
		System.out.println("1-"+j);
		j=0;
		
		for(int k=1; k<=5; k++) {
			j++;
		}
		System.out.println("2-"+j);
		j=0;
		
		int k = 0;
		do {
			j++;
		}while(k++ < 5);
		System.out.println("3-"+j);
		j=0;
		
		k=0;
		while(k++ < 5) {
			j++;
		}
		System.out.println("4-"+j);
	}
	
}
