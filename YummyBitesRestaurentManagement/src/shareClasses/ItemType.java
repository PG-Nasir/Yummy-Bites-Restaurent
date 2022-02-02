package shareClasses;

public enum ItemType {

	Finish_Goods(1),Raw_Materials(2);
	private int type;
	
	private ItemType(int i){
		this.type = i;
	}
	
	public int getType(){
		return type;
	}
	
}
