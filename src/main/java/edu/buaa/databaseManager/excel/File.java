package edu.buaa.databaseManager.excel;

public class File extends java.io.File {

	public File(String pathname) {
		super(pathname);
	}
	
	public String getFileSuffix(){
		String name =getName();
		String s[] = name.split("\\.");
		String suffix = s[s.length-1];
		return suffix;
	}

}
