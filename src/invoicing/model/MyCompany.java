/**
 * 
 */
package invoicing.model;

/**
 * @author Norbert
 *
 */
public class MyCompany {
	
	private int my_nip;
	private int my_regon;
	private String my_company_name;
	private String my_address;
	private String my_postcode;
	private String my_post;
	private String my_company_account;
	

	public MyCompany() {
		super();
	}

	public MyCompany(int my_nip, int my_regon, String my_company_name, String my_address, String my_postcode,
			String my_post, String my_company_account) {
		super();
		this.my_nip = my_nip;
		this.my_regon = my_regon;
		this.my_company_name = my_company_name;
		this.my_address = my_address;
		this.my_postcode = my_postcode;
		this.my_post = my_post;
		this.my_company_account = my_company_account;
	}

	@Override
	public String toString() {
		return "MyCompany [my_nip=" + my_nip + ", my_regon=" + my_regon + ", my_company_name=" + my_company_name
				+ ", my_address=" + my_address + ", my_postcode=" + my_postcode + ", my_post=" + my_post
				+ ", my_company_account=" + my_company_account + "]";
	}

	public int getMy_nip() {
		return my_nip;
	}

	public void setMy_nip(int my_nip) {
		this.my_nip = my_nip;
	}

	public int getMy_regon() {
		return my_regon;
	}

	public void setMy_regon(int my_regon) {
		this.my_regon = my_regon;
	}

	public String getMy_company_name() {
		return my_company_name;
	}

	public void setMy_company_name(String my_company_name) {
		this.my_company_name = my_company_name;
	}

	public String getMy_address() {
		return my_address;
	}

	public void setMy_address(String my_address) {
		this.my_address = my_address;
	}

	public String getMy_postcode() {
		return my_postcode;
	}

	public void setMy_postcode(String my_postcode) {
		this.my_postcode = my_postcode;
	}

	public String getMy_post() {
		return my_post;
	}

	public void setMy_post(String my_post) {
		this.my_post = my_post;
	}

	public String getMy_company_account() {
		return my_company_account;
	}

	public void setMy_company_account(String my_company_account) {
		this.my_company_account = my_company_account;
	}

}
