package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ShopingBean;
import com.rays.pro4.Util.JDBCDataSource;

public class ShopingModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_shoping");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(ShopingBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_shoping values(?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getShopingPrice());
		pstmt.setString(3, bean.getShopingAddress());
		pstmt.setString(4, bean.getQuantity());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(ShopingBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_shoping where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(ShopingBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_shoping set SHOPINGPRICE = ?,SHOPINGADDRESS = ?, QUANTITY = ? where id = ?");

		pstmt.setString(1, bean.getShopingPrice());
		pstmt.setString(2, bean.getShopingAddress());
		pstmt.setString(3, bean.getQuantity());
		pstmt.setLong(4, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public ShopingBean findByPK(long pk) throws Exception {

		String sql = "select * from st_Shoping  where id = ?";
		ShopingBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ShopingBean();
			bean.setId(rs.getLong(1));
			bean.setShopingPrice(rs.getString(2));
			bean.setShopingAddress(rs.getString(3));
			bean.setQuantity(rs.getString(4));

		}

		rs.close();

		return bean;
	}

	public List search(ShopingBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_Shoping where 1=1");
		if (bean != null) {

			if (bean.getShopingPrice() != null && bean.getShopingPrice().length() > 0) {
				sql.append(" AND shopingPrice like '" + bean.getShopingPrice() + "%'");
			}

			if (bean.getShopingAddress() != null && bean.getShopingAddress().length() > 0) {
				sql.append(" AND shopingAddress like '" + bean.getShopingAddress() + "%'");
			}

			if (bean.getQuantity() != null && bean.getQuantity().length() > 0) {

				sql.append(" AND Quantity like '" + bean.getQuantity() + "%'");

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

			bean = new ShopingBean();
			bean.setId(rs.getLong(1));
			bean.setShopingPrice(rs.getString(2));
			bean.setShopingAddress(rs.getString(3));
			bean.setQuantity(rs.getString(4));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_shoping");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			ShopingBean bean = new ShopingBean();
			bean.setId(rs.getLong(1));
			bean.setShopingPrice(rs.getString(2));
			bean.setShopingAddress(rs.getString(3));
			bean.setQuantity(rs.getString(4));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
