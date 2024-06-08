package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class BankModel {
	public Integer nextPK() throws Exception {
		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_bank");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;

	}

	public long add(BankBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_bank values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getAccount());
		pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
		pstmt.setString(5, bean.getAmmount());

		int i = pstmt.executeUpdate();
		System.out.println("Account holder Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(BankBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_bank where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Account delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(BankBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_bank set name = ?, account = ?, date = ? where id = ?");

		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getAccount());
		pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
		pstmt.setString(4, bean.getAmmount());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public BankBean findByPK(long pk) throws Exception {

		String sql = "select * from st_bank where id = ?";
		BankBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new BankBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAccount(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setAmmount(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(BankBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_bank where 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}

			if (bean.getAccount() != null && bean.getAccount().length() > 0) {
				sql.append(" AND Account like '" + bean.getAccount() + "%'");
			}

			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new Date(bean.getDate().getTime());
				sql.append(" AND Date = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new BankBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAccount(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setAmmount(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();
		
		StringBuffer sql = new StringBuffer("select * from st_bank");

		Connection conn = JDBCDataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			BankBean bean = new BankBean();

			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAccount(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setAmmount(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;
	}

}
