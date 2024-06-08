package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;



public class OrderModel {
	public Integer nextPK() throws Exception {
	int pk = 0;

	Connection conn = JDBCDataSource.getConnection();

	PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_order");

	ResultSet rs = pstmt.executeQuery();

	while (rs.next()) {
		pk = rs.getInt(1);
	}

	rs.close();

	return pk + 1;

}
	public long add(OrderBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_order values(?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getOrderName());
		pstmt.setString(3, bean.getOrderPrice());
		pstmt.setDate(4, new java.sql.Date(bean.getOrderConfirm().getTime()));

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}
	
	public void delete( OrderBean  bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_order where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();

}
	public void update(OrderBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_order set productName = ?, productAmmount = ?, purchaseDate = ? where id = ?");

		pstmt.setString(1, bean.getOrderName());
		pstmt.setString(2, bean.getOrderPrice());
		pstmt.setDate(3, new java.sql.Date(bean.getOrderConfirm().getTime()));
		pstmt.setLong(4, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public OrderBean findByPK(long pk) throws Exception {

		String sql = "select * from st_order where id = ?";
		OrderBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setOrderName(rs.getString(2));
			bean.setOrderPrice(rs.getString(3));
			bean.setOrderConfirm(rs.getDate(4));

		}

		rs.close();

		return bean;
	}

	public List search(OrderBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_order where 1=1");
		if (bean != null) {

			if (bean.getOrderName() != null && bean.getOrderName().length() > 0) {
				sql.append(" AND orderName like '" + bean.getOrderName() + "%'");
			}

			if (bean.getOrderPrice() != null && bean.getOrderPrice().length() > 0) {
				sql.append(" AND orderPrice like '" + bean.getOrderPrice()+ "%'");
			}

			if (bean.getOrderConfirm() != null && bean.getOrderConfirm().getTime() > 0) {
				Date d = new Date(bean.getOrderConfirm().getTime());
				sql.append(" AND orderConfirm= '" + d + "'");
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

			bean = new OrderBean();
			bean.setId(rs.getLong(1));
			bean.setOrderName(rs.getString(2));
			bean.setOrderPrice(rs.getString(3));
			bean.setOrderConfirm(rs.getDate(4));

			list.add(bean);

		}
		rs.close();

		return list;

	}
	public List list() throws ApplicationException {
		
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_order");

	
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderBean bean = new OrderBean();
						
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderPrice(rs.getString(3));
				bean.setOrderConfirm(rs.getDate(4));
		

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


