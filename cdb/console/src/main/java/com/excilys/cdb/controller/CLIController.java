package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.SearchBy;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CLIAsker;
import com.excilys.cdb.ui.CLIView;
import com.excilys.cdb.ui.MenuOption;

@Component
@Scope("singleton")
public class CLIController {
	
	private CLIView view;
	
	private CLIAsker asker;
	
	private ComputerService computerService;
	
	private CompanyService companyService;

	private CLIController(CLIView view, CLIAsker asker, ComputerService computerService, CompanyService companyService) {
		this.view = view;
		this.asker = asker;
		this.computerService = computerService;
		this.companyService = companyService;
	}

	public CLIView getView() {
		return view;
	}

	public CLIAsker getAsker() {
		return asker;
	}

	public void executeChoice(int choiceId) throws SQLException {
		MenuOption choice = MenuOption.values()[choiceId - 1];
		switch (choice) {
		case LIST_COMPUTERS:
			executeListComputers();
			break;

		case LIST_COMPANIES:
			executeListCompanies();
			break;

		case SHOW_DETAILS:
			executeShowDetails();
			break;

		case CREATE_COMPUTER:
			executeInsertComputer();
			break;

		case UPDATE_COMPUTER:
			executeUpdateComputer();
			break;

		case DELETE_COMPUTER:
			executeDeleteComputer();
			break;
			
		case DELETE_COMPANY:
			executeDeleteCompany();
			break;

		default:
			break;
		}
	}
	private void executeListComputers() throws SQLException {
		Pageable page = PageRequest.of(0, 10);
		Page<Computer> computers = computerService.search(page, SearchBy.NAME, "");
		while (!page.isUnpaged()) {
			view.printComputers(computers.getContent());
			String choice = asker.askPage(page.getPageNumber(), computers.getTotalPages());
			if ("q".equalsIgnoreCase(choice)) {
				break;
			} else if ("a".equalsIgnoreCase(choice)) {
				page = computers.previousOrFirstPageable();
			} else {
				page = computers.nextPageable();
			}
		}
	}

	private void executeListCompanies() throws SQLException {
		view.printCompanies(companyService.getAllCompanies());
	}

	private void executeShowDetails() throws SQLException {
		int id = asker.askComputerId();
		Optional<Computer> com = computerService.getComputer(id);
		if (com.isPresent()) {
			System.out.println(com);
		} else {
			System.out.println("L'ordianteur n'existe pas");
		}
	}
	
	private void executeInsertComputer() throws SQLException {
		String name = asker.askComputerName();
		LocalDate addDate = null;
		while (true) {
			addDate = asker.askAddDate();
			if (addDate != null && (addDate.isBefore(LocalDate.parse("1970-01-01")) || addDate.isAfter(LocalDate.parse("2038-01-19")))) {
				System.out.println("La date de retrait de l'ordianteur doit être comprise entre le 1er Janvier 1970 et le 19 janvier 2038");
			} else {
				break;
			}
		}
		LocalDate removeDate = null;
		while (true) {
			removeDate = asker.askRemoveDate();
			if (removeDate != null && (removeDate.isBefore(LocalDate.parse("1970-01-01")) || (removeDate.isAfter(LocalDate.parse("2038-01-19"))))) {
				System.out.println("La date de retrait de l'ordianteur doit être comprise entre le 1er Janvier 1970 et le 19 janvier 2038");
			} else if (removeDate != null && addDate != null && removeDate.isBefore(addDate)) {
				System.out.println("La date de retrait de l'ordinateur ne peut être antérieure à la date d'ajout de l'ordinateur");
			} else {
				break;
			}
		}
		Company company = asker.askCompany();
		computerService.addComputer(new Computer.ComputerBuilder(0, name)
				.withIntroduced(addDate)
				.withDiscontinued(removeDate)
				.withCompany(company)
				.build());
		System.out.println("L'ordinateur a été créé");
	}

	private void executeUpdateComputer() throws SQLException {
		int id = asker.askComputerId();
		String name = asker.askComputerName();
		LocalDate addDate = asker.askAddDate();
		LocalDate removeDate = null;
		while (true) {
			removeDate = asker.askRemoveDate();
			if (removeDate != null && addDate != null && removeDate.isBefore(addDate)) {
				System.out.println("La date de retrait ne peut être antérieure à la date d'ajout de l'ordinateur");
			} else {
				break;
			}
		}
		Company company = asker.askCompany();
		computerService.updateComputer(new Computer.ComputerBuilder(id, name)
				.withIntroduced(addDate)
				.withDiscontinued(removeDate)
				.withCompany(company)
				.build());
	}

	private void executeDeleteComputer() throws SQLException {
		int id = asker.askComputerId();
		computerService.deleteComputer(id);
	}
	
	private void executeDeleteCompany() throws SQLException {
		String nameOrId;
		Company company = null;
		do {
			nameOrId = asker.askCompanyNameOrId();
			//TODO remplacer par un scanner
			try {
				int id = Integer.parseInt(nameOrId);
				company = companyService.getCompanyById(id).orElse(null);
			} catch (NumberFormatException e){
			}
			if (company == null) {
				System.out.println("Aucune companie n'a été trouvée");
			}
		} while (company == null);
		companyService.deleteCompany(company.getId());
	}

}
