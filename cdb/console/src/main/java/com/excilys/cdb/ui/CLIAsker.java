package com.excilys.cdb.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@Component("cliAsker")
@Scope("singleton")
public class CLIAsker {//TODO Cette classe pourrie fait trop de choses
	
	private Scanner scanner;
	
	private CompanyService companyService;
	
	public CLIAsker(CompanyService companyService) {
		this.companyService = companyService;
		this.scanner = new Scanner(System.in);
	}
	
	public int askChoice() {
		String input = scanner.nextLine();
		try {
			int choice = Integer.valueOf(input);
			if (!MenuOption.isValid(choice)) {
				System.out.println("Veuillez entrer une option valide (1-7)");
				return askChoice();
			}
			return choice;
		} catch (NumberFormatException e) {
			System.out.println("Veuillez entrer une option valide (1-7)");
			return askChoice();
		}
		
	}

	public int askComputerId() {
		System.out.println("Veuillez entrer l'id de l'ordinateur (obligatoire)");
		String input = scanner.nextLine();
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			System.out.println("Veuillez entrer un entier !");
			return askComputerId();
		}
	}

	public String askComputerName() {
		System.out.println("Entrez le nom de l'ordinateur (obligatoire)");
		String name = scanner.nextLine();
		if ("".equals(name)) {
			System.out.println("Le nom est obligatoire !");
			return askComputerName();
		}
		return name;
	}
	
	public LocalDate askAddDate() {
		System.out.println("Veuillez entrer la date d'ajout de l'ordinateur (AAAA-MM-JJ) (optionel)");
		String input = scanner.nextLine();
		if ("".equals(input)) {
			return null;
		}
		try {
			return LocalDate.parse(input);
		} catch (DateTimeParseException e) {
			System.out.println("Format de date invalide !");
			return askAddDate();
		}
	}

	public LocalDate askRemoveDate() {
		System.out.println("Veuillez entrer la date de retrait de l'ordinateur (AAAA-MM-JJ) (optionel)");
		String input = scanner.nextLine();
		if ("".equals(input)) {
			return null;
		}
		try {
			return LocalDate.parse(input);
		} catch (DateTimeParseException e) {
			System.out.println("Format de date invalide !");
			return askAddDate();
		}
	}

	public Company askCompany() throws SQLException {
		System.out.println("Veuillez entrer l'id du fabricant (optionel)");
		String input = scanner.nextLine();
		if (input.equals("")) {
			return null;
		}
		try {
			Optional<Company> com = companyService.getCompanyById(Integer.valueOf(input));
			if (com.isPresent()) {
				return com.get();
			} else {
				System.out.println("Aucun fabricant ne porte l'id demandé");
				return askCompany();
			}
		} catch (NumberFormatException e) {
			System.out.println("Veuillez entrer un entier !");
			return askCompany();
		}
	}

	public String askCompanyNameOrId() throws SQLException {
		System.out.println("Veuillez entrer l'id ou le nom du fabricant");
		return scanner.nextLine();
		
	}
	
	public String askPage(int pageNum, int PagesCount) {
		System.out.println("Taper a pour la page précédente et " + "z pour la suivante\nq pour revenir au menu principal");
		return scanner.nextLine();
	}
}
