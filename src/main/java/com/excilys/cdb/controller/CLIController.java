package com.excilys.cdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.ui.CLIAsker;
import com.excilys.cdb.ui.CLIView;
import com.excilys.cdb.ui.MenuOption;

public class CLIController {

	private CLIView view;
	private CLIAsker asker;

	public CLIController() {
		this.view = new CLIView();
		this.asker = new CLIAsker();
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
		ComputerDAO dao = ComputerDAO.getInstance();
		Pagination p = new Pagination(dao.CountComputers());
		ArrayList<Computer> coms = new ArrayList<Computer>();
		while (p.getNumPage() <= p.getMaxPage()) {
			coms = dao.getComputersPerPage(p);
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
			coms = dao.getCompaniesPerPage(p);
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
		ComputerDAO dao = ComputerDAO.getInstance();
		Optional<Computer> com = dao.getComputerById(id);
		if (com.isPresent()) {
			System.out.println(com.get());
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
		int id = ComputerDAO.getInstance().insertComputer(new Computer(0, name, addDate, removeDate, company));
		System.out.println("L'ordinateur a été créé avec l'ID " + id);
	}

	private void executeUpdateComputer() throws SQLException, IOException {
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
		int updateCount = ComputerDAO.getInstance().updateComputer(new Computer(id, name, addDate, removeDate, company));
		System.out.println(updateCount + " ligne(s) a/ont été mise(s) à jour");
		
	}

	private void executeDeleteComputer() throws SQLException, IOException {
		int id = asker.askComputerId();
		int deleteCount = ComputerDAO.getInstance().deleteComputer(id);
		System.out.println(deleteCount + " ligne(s) a/ont été supprimée(s)");
	}

}
