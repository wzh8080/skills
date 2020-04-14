package wzh.java.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Demo {
	
	/**
	 * 原始方法
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void original_Statement() throws SQLException, ClassNotFoundException{
		//1.注册驱动(静态方法)(包名+类名）
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象(导包都导sql里面的，不导jdbc里的；多态！报异常是因为用户输入的串可能写错）后面设置下数据格式
//        String url="jdbc:mysql://localhost:3306/java0603?useUnicode=true&characterEncoding=UTF-8";
        String url="jdbc:oracle:thin:@192.168.10.106:1521:orcl";
        String user="zzrwms";
        String password="zzrwms";
        Connection conn=DriverManager.getConnection(url,user,password);
        //System.out.println(conn);报地址为正确sql服务关了可能报错
        
        //3.获取语句执行平台：
        Statement sta=conn.createStatement();
        //4.执行SQL语句：
        	//增-----一套语句执行一句sql语句 返回的是一个Int值，是指执行了几行
//	        String sql="insert into sort(sname) values('口红')";
//	        int row=sta.executeUpdate(sql);
//	        System.out.println(row);
        	// 查
        	String name = "无锡信电局";
        	String id = "2012080300000005' or '1'='1";
        	String sql = "select * from customer_info O where customerName ='"+name+"' "
        			+ "and customerid='"+id+"'";
        	ResultSet rs = sta.executeQuery(sql);
        	while(rs.next()){
        		String n = rs.getString("CustomerName");
        		System.out.println(n);
        	}
        //6.释放资源(先开后关)
        sta.close();
        conn.close();
	}
	/**
	 * 预编译对象(预处理对象)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void original_PreparedStatement() throws SQLException, ClassNotFoundException{
		//1.注册驱动(静态方法)(包名+类名）
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象(导包都导sql里面的，不导jdbc里的；多态！报异常是因为用户输入的串可能写错）后面设置下数据格式
//        String url="jdbc:mysql://localhost:3306/java0603?useUnicode=true&characterEncoding=UTF-8";
        String url="jdbc:oracle:thin:@192.168.10.106:1521:orcl";
        String user="zzrwms";
        String password="zzrwms";
        Connection conn=DriverManager.getConnection(url,user,password);
        //System.out.println(conn);报地址为正确sql服务关了可能报错
        
        //3.获取语句执行平台对象：
        String sql="select * from customer_info O where customerName =? and customerid=?";
        PreparedStatement pst=conn.prepareStatement(sql);
        
        //4.执行SQL语句：
//        Scanner sc=new Scanner(System.in);
//        System.out.println("请出入客户名称：");
//        String name=sc.next();
//        System.out.println("请输入客户编号：");
//        String id=sc.next();
        
        //给sql语句的？赋值
        pst.setString(1,"无锡信电局");
        pst.setString(2,"2012080300000005' or '1'='1");
        System.out.println("1.  "+pst.toString());
        ResultSet rs= pst.executeQuery();//()里不能写sql
        System.out.println("1.  "+pst.toString());
        System.out.println(" ------------------ " );
        
        //5.处理结果集
        while(rs.next()){
            String n =rs.getString("CustomerName");//因为就一列
            System.out.println(n); 
            int c = rs.getInt("CustomerType");
            System.out.println(c);
//            int c1 = rs.getInt("CustomerID");  // 【报错】因为， 超过了int容量
//            System.out.println(c1);
        }
        
        //6.释放资源(先开后关)
        pst.close();
        conn.close();
	}
	public static void main(String[] args) {
		try {
			System.out.println("Start");
//			original_Statement();
//			original_PreparedStatement();
			
			
			String a1 = "123456 from";
			int index = a1.lastIndexOf(" from");
			System.out.println(index);
			String a2 = a1.substring(0,index+4);
			System.out.println(a2);
			
			
			
			
			System.out.println("End");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
