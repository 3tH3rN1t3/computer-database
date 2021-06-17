package com.excilys.cdb.ui;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component("cliView")
@Scope("singleton")
public class CLIView {
	void printMenu() {
		System.out.println("Selectionnez l'option d votre choix");
		System.out.println("1 - Afficher la liste des ordinateurs");
		System.out.println("2 - Afficher la liste des fabricants");
		System.out.println("3 - Montrer les informations d'un ordinateur");
		System.out.println("4 - Créer un ordinateur");
		System.out.println("5 - Mettre à jour les informations d'un ordinateur");
		System.out.println("6 - Supprimer un ordinateur");
		System.out.println("7 - Supprimer une companie");
		System.out.println("8 - quitter\n");
	}
	
	public void printCompanies(List<Company> list) {
		for (Company com : list) {
			System.out.println(com);
		}
	}
	
	public void printComputers(List<Computer> coms) {
		for (Computer com : coms) {
			System.out.println(com);
		}
	}
}
