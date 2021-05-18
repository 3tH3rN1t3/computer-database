package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import model.Company;

// id | name
//singleton
public class CompanyMapper {
	private static CompanyMapper mapper;
	
	public static CompanyMapper getMapper() {
		if (mapper == null)
			mapper = new CompanyMapper();
		return mapper;
	}
	
	public ArrayList<Company> map(ResultSet rs) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		while(true) {
			Optional<Company> com = mapOne(rs);
			if(com.isPresent()) {
				companies.add(com.get());
			} else {
				break;
			}
		}
		return companies;
	}
	
	public Optional<Company> mapOne(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return Optional.of(new Company(rs.getInt("id"), rs.getString("name")));
		} else {
			return Optional.empty();
		}
	}
}
