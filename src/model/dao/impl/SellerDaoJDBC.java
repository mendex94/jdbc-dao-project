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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
								Department department = instantiateDepartment(resultSet);

								return instantiateSeller(resultSet, department);
						}

						return null;
				} catch (SQLException e) {
						throw new DbException(e.getMessage());
				} finally {
						DB.closeStatement(statement);
						DB.closeResultSet(resultSet);
				}
		}

		private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
				return new Department(resultSet.getInt("DepartmentId"), resultSet.getString("DepName"));
		}

		private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
				return new Seller(resultSet.getInt("Id"),
								resultSet.getString("Name"),
								resultSet.getString("Email"),
								resultSet.getDate("BirthDate"),
								resultSet.getDouble("BaseSalary"),
								department
				);
		}

		@Override
		public List<Seller> findAll() {
				return List.of();
		}

		@Override
		public List<Seller> findByDepartment(Department department) {
				PreparedStatement statement = null;
				ResultSet resultSet = null;

				try {
						statement = connection.prepareStatement(
										"SELECT seller.*,department.Name as DepName "
										+ "FROM seller INNER JOIN department "
										+ "ON seller.DepartmentId = department.Id "
										+ "WHERE DepartmentId = ? "
										+ "ORDER BY Name"
						);

						statement.setInt(1, department.getId());

						resultSet = statement.executeQuery();

						List<Seller> sellers = new ArrayList<>();
						Map<Integer, Department> departmentMap = new HashMap<>();

						while (resultSet.next()) {
								Department departmentFromDb = departmentMap.get(resultSet.getInt("DepartmentId"));

								if (departmentFromDb == null) {
										departmentFromDb = instantiateDepartment(resultSet);
										departmentMap.put(resultSet.getInt("DepartmentId"), departmentFromDb);
								}

								Seller seller = instantiateSeller(resultSet, departmentFromDb);

								sellers.add(seller);
						}

						return sellers;
				} catch (SQLException e) {
						throw new DbException(e.getMessage());
				} finally {
						DB.closeStatement(statement);
						DB.closeResultSet(resultSet);
				}
		}
}
