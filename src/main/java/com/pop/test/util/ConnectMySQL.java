package com.pop.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectMySQL {

        public static void main(String[] args){

            // 驱动程序名
			String driver = "com.mysql.jdbc.Driver";

            // URL指向要访问的数据库名scutcs
            String url = "jdbc:mysql://bj-cdb-kfxut7i1.sql.tencentcdb.com:63640/qa_payplus";

            // MySQL配置时的用户名
            String user = "root";

            // MySQL配置时的密码
            String password = "20180313@PayPlus";

            try {
                // 加载驱动程序
                Class.forName(driver);

                // 连续数据库
                Connection conn = DriverManager.getConnection(url, user, password);

                if(!conn.isClosed())
                    System.out.println("Succeeded connecting to the Database!");

                // statement用来执行SQL语句
                Statement statement = conn.createStatement();

                // 要执行的SQL语句
                String sql = "select * from acc_settle_record";

                // 结果集
                ResultSet rs = statement.executeQuery(sql);

                System.out.println("-----------------");
                System.out.println("执行结果如下所示:");
                System.out.println("-----------------");
                System.out.println(" 商户" + "\t" + " 金额");
                System.out.println("-----------------");

                while(rs.next()) {

                    System.out.println(rs.getString("merchant_No") + "\t" + rs.getString("settle_amount"));

                }

                rs.close();
                conn.close();

            } catch(ClassNotFoundException e) {


                System.out.println("Sorry,can`t find the Driver!");
                e.printStackTrace();


            } catch(SQLException e) {


                e.printStackTrace();


            } catch(Exception e) {


                e.printStackTrace();


            }
        }
}