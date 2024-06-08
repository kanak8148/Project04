package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name = "OrderCtl ", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl  extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("orderName"))) {
			request.setAttribute("orderName", PropertyReader.getValue("error.require", "orderName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("orderPrice"))) {
			request.setAttribute("orderPrice", PropertyReader.getValue("error.require", "orderPrice"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("orderConfirm"))) {
			request.setAttribute("orderConfirm", PropertyReader.getValue("error.require", "orderConfirm"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		OrderBean  bean = new OrderBean ();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setOrderName(DataUtility.getString(request.getParameter("orderName")));

		bean.setOrderPrice(DataUtility.getString(request.getParameter("orderPrice")));

		bean.setOrderConfirm(DataUtility.getDate(request.getParameter("orderConfirm")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in do get order ctl");

		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModel model = new OrderModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			OrderBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		OrderModel model = new OrderModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			OrderBean  bean = (OrderBean ) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Order is successfully Updated", request);
				} catch (Exception e) {
					System.out.println("product not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Order is successfully Added", request);
					bean.setId(pk);
				} catch (Exception e) {
					System.out.println("product not added");
					e.printStackTrace();
				}

			}

		}
		ServletUtility.forward(getView(), request, response);

	}

	
	
	
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ORDER_VIEW;
	}

}
