package edu.buaa.databaseManager.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TestConnection {
	
	public static void main(String[] args) {
		
		
		
		
		Runtime run = Runtime.getRuntime();
		String cmd = "C:\\soft\\xampp1\\mysql\\bin\\mysqld.exe";
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			char ss[] = new char[1000];
			inBr.read(ss);
			//System.out.println(new String(ss));
			
			while(true){
				boolean r = NetUtil.isLoclePortUsing(3306);
				System.out.println(r);
				if(r)break;
			}
			
			//p.destroy();
			
			p = run.exec("C:\\soft\\xampp1\\mysql\\bin\\mysqladmin.exe -uroot -p shutdown");
//			System.out.println("weak");
			in = new BufferedInputStream(p.getInputStream());
			inBr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			OutputStream out = p.getOutputStream();
			out.write("123456\n".getBytes());
			out.flush();
			while ((lineStr = inBr.readLine()) != null)
				//获得命令执行后在控制台的输出信息
				System.out.println(lineStr);// 打印输出信息
			//检查命令是否执行失败。
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	static {
//		System.out.println("a");
//		b();
//	}
//	private static void b(){
//		System.out.println("b");
//	}
}
