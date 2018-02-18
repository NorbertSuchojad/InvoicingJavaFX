package invoicing.model;

import java.util.Date;

public class Invoices {
	private int id_invoice;
	private String service_name;
	private String sales_type;
	private double sales_value;
	private double sales_price;
	private Date date_of_issue;
	private Date execution_date;
	private int payment_time;
	
	public int getId_invoice() {
		return id_invoice;
	}
	public void setId_invoice(int id_invoice) {
		this.id_invoice = id_invoice;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getSales_type() {
		return sales_type;
	}
	public void setSales_type(String sales_type) {
		this.sales_type = sales_type;
	}
	public double getSales_value() {
		return sales_value;
	}
	public void setSales_value(double sales_value) {
		this.sales_value = sales_value;
	}
	public double getSales_price() {
		return sales_price;
	}
	public void setSales_price(double sales_price) {
		this.sales_price = sales_price;
	}
	public Date getDate_of_issue() {
		return date_of_issue;
	}
	public void setDate_of_issue(Date date_of_issue) {
		this.date_of_issue = date_of_issue;
	}
	public Date getExecution_date() {
		return execution_date;
	}
	public void setExecution_date(Date execution_date) {
		this.execution_date = execution_date;
	}
	public int getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(int payment_time) {
		this.payment_time = payment_time;
	}
	public Invoices(int id_invoice, String service_name, String sales_type, double sales_value, double sales_price,
			Date date_of_issue, Date execution_date, int payment_time) {
		super();
		this.id_invoice = id_invoice;
		this.service_name = service_name;
		this.sales_type = sales_type;
		this.sales_value = sales_value;
		this.sales_price = sales_price;
		this.date_of_issue = date_of_issue;
		this.execution_date = execution_date;
		this.payment_time = payment_time;
	}
	public Invoices() {
		super();
	}
	@Override
	public String toString() {
		return "Invoices [id_invoice=" + id_invoice + ", service_name=" + service_name + ", sales_type=" + sales_type
				+ ", sales_value=" + sales_value + ", sales_price=" + sales_price + ", date_of_issue=" + date_of_issue
				+ ", execution_date=" + execution_date + ", payment_time=" + payment_time + "]";
	}
	
	
}
