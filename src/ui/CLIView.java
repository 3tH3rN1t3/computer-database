package ui;

import java.util.ArrayList;

import model.Company;
import model.Computer;
import model.Page;

public class CLIView {
	void printMenu() {
		System.out.println("Selectionnez l'option d votre choix");
		System.out.println("1 - Afficher la liste des ordinateurs");
		System.out.println("2 - Afficher la liste des fabricants");
		System.out.println("3 - Montrer les informations d'un ordinateur");
		System.out.println("4 - Créer un ordinateur");
		System.out.println("5 - Mettre à jour les informations d'un ordinateur");
		System.out.println("6 - Supprimer un ordinateur");
		System.out.println("7 - quitter\n");
	}
	
	public void printCompanies(ArrayList<Company> coms) {
		for (Company com : coms) {
			System.out.println(com);
		}
	}
	
	public void printComputers(ArrayList<Computer> coms) {
		for (Computer com : coms) {
			System.out.println(com);
		}
	}
	
	public <T> void printPage(Page<T> p) {
		System.out.println(p);
	}
}
