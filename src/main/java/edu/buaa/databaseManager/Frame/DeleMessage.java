package edu.buaa.databaseManager.Frame;

public class DeleMessage {
	private boolean isdele = false;
	private MyPane panelname = null;
	public boolean isIsdele() {
		return isdele;
	}
	public void setIsdele(boolean isdele) {
		this.isdele = isdele;
	}
	public MyPane getPanelname() {
		return panelname;
	}
	public void setPanelname(MyPane panelname) {
		this.panelname = panelname;
	}
	
}
