package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
		private final Connection connection;

		public SellerDaoJDBC(Connection connection) {
				this.connection = connection;
		}

		@Override
		public void insert(Seller seller) {

		}

		@Override
		public void update(Seller seller) {

		}

		@Override
		public void deleteById(Integer id) {

		}

		@Override
		public Seller findById(Integer id) {
				PreparedStatement statement = null;
				ResultSet resultSet = null;

				try {
						statement = connection.prepareStatement(
										"SELECT seller.*,department.Name as DepName "
										+ "FROM seller INNER JOIN department "
										+ "ON seller.DepartmentId = department.Id "
										+ "WHERE seller.Id = ?"
						);

						statement.setInt(1, id);

						resultSet = statement.executeQuery();

						if (resultSet.next()) {
								Department department = new Department(resultSet.getInt("DepartmentId"), resultSet.getString("DepName"));

								return new Seller(resultSet.getInt("Id"),
												resultSet.getString("Name"),
												resultSet.getString("Email"),
												resultSet.getDate("BirthDate"),
												resultSet.getDouble("BaseSalary"),
												department
								);
						}

						return null;
				} catch (SQLException e) {
						throw new DbException(e.getMessage());
				} finally {
						DB.closeStatement(statement);
						DB.closeResultSet(resultSet);
				}
		}

		@Override
		public List<Seller> findAll() {
				return List.of();
		}
}
