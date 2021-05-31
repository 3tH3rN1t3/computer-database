package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.mapper.DBCompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CLIAsker;
import com.excilys.cdb.ui.CLIView;
import com.excilys.cdb.ui.MenuOption;

public class CLIController {

	private CLIView view;
	private CLIAsker asker;
	private ComputerService computerService;

	public CLIController() throws IOException {
		this.view = new CLIView();
		this.asker = new CLIAsker();
		this.computerService = ComputerService.getInstance();
	}

	public CLIView getView() {
		return view;
	}

	public CLIAsker getAsker() {
		return asker;
	}

	public void executeChoice(int choiceId) throws SQLException, IOException {
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

		default:
			break;
		}
	}
	private void executeListComputers() throws SQLException, IOException {
		Pagination p = new Pagination(computerService.countComputers());
		ArrayList<Computer> coms = new ArrayList<Computer>();
		while (p.getNumPage() <= p.getMaxPage()) {
			coms = computerService.getComputers(p);
			view.printComputers(coms);
			String choice = asker.askPage(p.getNumPage(), p.getMaxPage());
			if ("q".equalsIgnoreCase(choice)) {
				break;
			} else if ("a".equalsIgnoreCase(choice)) {
				p.setNumPage(((p.getNumPage() + p.getMaxPage() - 2) % p.getMaxPage()) + 1);
			} else {
				p.setNumPage((p.getNumPage() % p.getMaxPage()) + 1);
			}
		}
	}

	private void executeListCompanies() throws SQLException, IOException {
		CompanyDAO dao = CompanyDAO.getInstance();
		Pagination p = new Pagination(dao.countCompanies());
		ArrayList<Company> coms = new ArrayList<Company>();
		while (p.getNumPage() <= p.getMaxPage()) {
			coms = DBCompanyMapper.getMapper().toCompanies(dao.getCompaniesPerPage(p));
			view.printCompanies(coms);
			String choice = asker.askPage(p.getNumPage(), p.getMaxPage());
			if ("q".equalsIgnoreCase(choice)) {
				break;
			} else if ("a".equalsIgnoreCase(choice)) {
				p.setNumPage(((p.getNumPage() + p.getMaxPage() - 2) % p.getMaxPage()) + 1);
			} else {
				p.setNumPage((p.getNumPage() % p.getMaxPage()) + 1);
			}
		}
		
	}

	private void executeShowDetails() throws IOException, SQLException {
		int id = asker.askComputerId();
		Computer com = computerService.getComputer(id);
		if (com != null) {
			System.out.println(com);
		} else {
			System.out.println("L'ordianteur n'existe pas");
		}
	}
	
	private void executeInsertComputer() throws SQLException, IOException {
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

	private int executeUpdateComputer() throws SQLException, IOException {
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
		int updateCount = computerService.updateComputer(new Computer.ComputerBuilder(id, name)
				.withIntroduced(addDate)
				.withDiscontinued(removeDate)
				.withCompany(company)
				.build());
		System.out.println(updateCount + " ligne(s) a/ont été mise(s) à jour");
		return updateCount;
	}

	private void executeDeleteComputer() throws SQLException, IOException {
		int id = asker.askComputerId();
		int deleteCount = ComputerDAO.getInstance().deleteComputer(id);
		System.out.println(deleteCount + " ligne(s) a/ont été supprimée(s)");
	}

}
