package shareClasses;

public enum StageType {

	Purchase(1),Purchase_Return(2),Sales(3),Sales_Return(4),Kitchen_Issue(5),Kitchen_Return(6),Cash_Payment(11),Cash_Recived(12),Bank_Payment(13),Bank_Received(14);
	private int type;
	
	private StageType(int i){
		this.type = i;
	}
	
	public int getType(){
		return type;
	}
	
}
