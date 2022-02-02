package shareClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ui.trading.SalesController.InvoiceList;
import ui.trading.SalesController.PaymentDetails;
import ui.trading.SalesController.ProductDetails;

public class SalesCounter {
	
	private String find;
	private String invoiceNo;
	private String customer;
	private String contactNo;
	private String date;
	private String product;
	private String unit;
	private String stock;
	private String quantity;
	private String price;
	private String discount;
	private String warrentyDate;
	private String totalAmount;
	private String vat;
	private String grossAmount;
	private String discountPercent;
	private String percentDiscount;
	private String manualDiscount;
	private String totalDiscount;
	private String netAmount;
	private String paidAmount;
	private String paymentMethod;
	private String paymentLedger;
	private String checkNo;
	private String checkPassDate;
	private String remarks;
	private String approvedBy;
	private String promissDate;
	private String duePaymentMethod;
	private String duePaymentLedger;
	private String dueCheckNo;
	private String duePassDate;
	private String dueAmount;
	
	private boolean isNew;

	
	ObservableList<PaymentDetails> listPaymentDetails = FXCollections.observableArrayList();
	
	ObservableList<ProductDetails> listProductDetails = FXCollections.observableArrayList();
	
	ObservableList<InvoiceList> listInvoiceList = FXCollections.observableArrayList();
	
	public SalesCounter() {
		isNew = true;
	}

	
	public String getFind() {
		return find;
	}

	public void setFind(String find) {
		this.find = find;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getWarrentyDate() {
		return warrentyDate;
	}

	public void setWarrentyDate(String warrentyDate) {
		this.warrentyDate = warrentyDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(String percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public String getManualDiscount() {
		return manualDiscount;
	}

	public void setManualDiscount(String manualDiscount) {
		this.manualDiscount = manualDiscount;
	}

	public String getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentLedger() {
		return paymentLedger;
	}

	public void setPaymentLedger(String paymentLedger) {
		this.paymentLedger = paymentLedger;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getCheckPassDate() {
		return checkPassDate;
	}

	public void setCheckPassDate(String checkPassDate) {
		this.checkPassDate = checkPassDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getPromissDate() {
		return promissDate;
	}

	public void setPromissDate(String promissDate) {
		this.promissDate = promissDate;
	}

	public String getDuePaymentMethod() {
		return duePaymentMethod;
	}

	public void setDuePaymentMethod(String duePaymentMethod) {
		this.duePaymentMethod = duePaymentMethod;
	}

	public String getDuePaymentLedger() {
		return duePaymentLedger;
	}

	public void setDuePaymentLedger(String duePaymentLedger) {
		this.duePaymentLedger = duePaymentLedger;
	}

	public String getDueCheckNo() {
		return dueCheckNo;
	}

	public void setDueCheckNo(String dueCheckNo) {
		this.dueCheckNo = dueCheckNo;
	}

	public String getDuePassDate() {
		return duePassDate;
	}

	public void setDuePassDate(String duePassDate) {
		this.duePassDate = duePassDate;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public ObservableList<PaymentDetails> getListPaymentDetails() {
		return listPaymentDetails;
	}

	public void setListPaymentDetails(ObservableList<PaymentDetails> listPaymentDetails) {
		this.listPaymentDetails.clear();
		this.listPaymentDetails.addAll(listPaymentDetails);
	}

	public ObservableList<ProductDetails> getListProductDetails() {
		return listProductDetails;
	}

	public void setListProductDetails(ObservableList<ProductDetails> listProductDetails) {
		this.listProductDetails.clear();
		this.listProductDetails.addAll(listProductDetails);
	}

	public ObservableList<InvoiceList> getListInvoiceList() {
		return listInvoiceList;
	}

	public void setListInvoiceList(ObservableList<InvoiceList> listInvoiceList) {
		this.listInvoiceList.clear();
		this.listInvoiceList.addAll(listInvoiceList);
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	
}
