package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class PaymentModel {
	public Integer nextPK() throws Exception {
		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_payment");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;

	}

	public long add(PaymentBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_Payment values(?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getBank());
		pstmt.setString(4, bean.getAmmount());

		int i = pstmt.executeUpdate();
		System.out.println("Payment Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(PaymentBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_Payment where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(PaymentBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_payment set name = ?, bank = ?, ammount = ? where id = ?");

		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getBank());
		pstmt.setString(3, bean.getAmmount());
		pstmt.setLong(4, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("payment update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public PaymentBean findByPK(long pk) throws Exception {

		String sql = "select * from st_payment where id = ?";
		PaymentBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new PaymentBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setBank(rs.getString(3));
			bean.setAmmount(rs.getString(4));

		}

		rs.close();

		return bean;
	}

	public List search(PaymentBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_payment where 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}

			if (bean.getBank() != null && bean.getBank().length() > 0) {
				sql.append(" AND bank like '" + bean.getBank() + "%'");
			}

			if (bean.getBank() != null && bean.getBank().length() > 0) {

				sql.append("AND bank=" + "");
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
		
		System.out.println("after list obj");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		
		System.out.println("after pstmt");

		ResultSet rs = pstmt.executeQuery();
		
		System.out.println("ofter result set ");

		while (rs.next()) {

			bean = new PaymentBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setBank(rs.getString(3));
			bean.setAmmount(rs.getString(4));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_payment");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PaymentBean bean = new PaymentBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setBank(rs.getString(3));
				bean.setAmmount(rs.getString(4));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
