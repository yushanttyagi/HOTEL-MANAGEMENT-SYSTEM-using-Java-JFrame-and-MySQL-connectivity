/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minor.pkg2019;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.*;
import static jdk.nashorn.internal.objects.NativeJava.type;
/**
 *
 * @author Yushant Tyagi
 */
public class Minor2019 {
static Connection con;static Statement stat;static PreparedStatement stdm;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       createcon();
        Scanner in=new Scanner(System.in);
        System.out.println("enter 1 to retrieve\neneter 2 to insert in a table\nenter 3 to run a dml command\nenter 4 to run a query");
        int choice;choice=in.nextInt();
       if(choice==1){
           retrieve();
       }
       if(choice==4){
          runquery();
       }
       if(choice==3){
           rundml();
       }
       if(choice==2){
           insert();
       }
       
    }
    static void insert(){
        System.out.println("enter the table name");
        String ch,query;
        Scanner scan=new Scanner(System.in);
        ch=scan.nextLine();
        System.out.println("enter the following details ");
        if(ch.equals("customer")){
            try{
                
            System.out.println("enter first name:");
            String first=scan.nextLine();
            System.out.println("enter last name:");
            String last=scan.nextLine();
            System.out.println("enter phone:");
            String ph=scan.nextLine();
            System.out.println("enter addr:");
            String addr=scan.nextLine();
            System.out.println("enter email:");
            String email=scan.nextLine();
            System.out.println("enter ssn:");
            int ss=scan.nextInt();
            stdm=con.prepareStatement("insert into customer values(?,?,?,?,?,?)");
            stdm.setInt(1, ss);
            stdm.setString(2, first);stdm.setString(3,last);stdm.setString(4,ph);
            stdm.setString(5,addr);stdm.setString(6,email);
            stdm.execute();
            } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if(ch.equals("rooms")){
            try{System.out.println("enter type:");
                String type=scan.nextLine();System.out.println("enter facilities:");
                String facil=scan.nextLine();System.out.println("enter room number:");
                int room=scan.nextInt();
                System.out.println("enter booking id:");
                int book=scan.nextInt();
                 stdm=con.prepareStatement("insert into rooms values(?,?,?,?)");
            stdm.setInt(1, book);
            stdm.setString(2,type);stdm.setString(3,facil);stdm.setInt(4,room);stdm.execute();
            } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if(ch.equals("checkin_out")){
            try{
                String chi=scan.nextLine();
                String cho=scan.nextLine();
                
SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

java.util.Date date = sdf1.parse(chi);
java.sql.Date start = new java.sql.Date(date.getTime()); 

java.util.Date date2 = sdf1.parse(cho);
java.sql.Date end= new java.sql.Date(date2.getTime()); 
LocalDate d1 = LocalDate.parse(chi, DateTimeFormatter.ISO_LOCAL_DATE);
LocalDate d2 = LocalDate.parse(cho, DateTimeFormatter.ISO_LOCAL_DATE);
Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
int diffDays =  (int) diff.toDays();
                int ssn=scan.nextInt();
                 stdm=con.prepareStatement("insert into checkin_out values(?,?,?)");
            stdm.setDate(1,start);
            stdm.setDate(2,end);stdm.setInt(3,ssn);
            stdm.execute();
            System.out.println("check out successful");
            int money=diffDays*3000;
            stdm=con.prepareStatement("insert into invoice values(?,?,?)");
            stdm.setInt(1,ssn);
            stdm.setInt(2,diffDays);
            stdm.setInt(3,money);
            stdm.execute();
            System.out.println("invoice created");
            } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }   catch (ParseException ex) {
                Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    static void rundml(){
        String query;
        Scanner scan=new Scanner(System.in);
        System.out.println("enter the query");query=scan.nextLine();
            try{
            stdm=con.prepareStatement(query);
                stdm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void runquery(){
        System.out.println("enter the table name");
        String ch,query;
        Scanner scan=new Scanner(System.in);
        ch=scan.nextLine();System.out.println("enter the query on "+ch);query=scan.nextLine();
                if(ch.equals("invoice")){
                  try{
             
                Statement stat=con.createStatement();
                ResultSet rs=stat.executeQuery(query);
                
                while(rs.next()){
                    int ssn=rs.getInt("ssn");
                    int days=rs.getInt("days");
                    int total=rs.getInt("total");
                    System.out.println(ssn+" "+days+" "+total);
                     }//con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
               }
                else if(ch.equals("customer")){
try{
            
                stat=con.createStatement();
                ResultSet rs=stat.executeQuery(query);
                while(rs.next()){
                    int ssn=rs.getInt("ssn");
                    String first=rs.getString("first_name");
                    String last=rs.getString("last_name");
                    String phone=rs.getString("ph_no");
                    String addr=rs.getString("address");
                    String email=rs.getString("email");
                    System.out.println(String.valueOf(ssn)+" "+first+" "+last+" "+phone+" "+addr+" "+email);
                     }//con.close();
        }catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }                }
                else if(ch.equals("rooms")){
                  try{
         
                stat=con.createStatement();
                    ResultSet rs=stat.executeQuery(query);
                    while(rs.next()){
                        int book=rs.getInt("booking_no");
                        String type=rs.getString("type");
                        String face=rs.getString("facilities");
                        int room=rs.getInt("room_no");
                        System.out.println(String.valueOf(book)+" "+type+" "+face+" "+String.valueOf(room));
                    }
                    //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        } 
                }
                else{
                   try{
             
                Statement stat=con.createStatement();
                    ResultSet rs=stat.executeQuery(query);
                    while(rs.next()){
                        java.sql.Date chin=rs.getDate("chin");
                        java.sql.Date chout=rs.getDate("chout");
                        int ssn=rs.getInt("ssn");
                        System.out.println(chin+" "+chout+" "+ssn);
                    }
                    //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
    }
    static void createcon(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","root");
                System.out.println("it is connected");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void retrieve(){
        System.out.println("enter the table name");
        String ch;
        Scanner scan=new Scanner(System.in);
        ch=scan.nextLine();
                if(ch.equals("invoice")){
                  table3();
               }
                else if(ch.equals("customer")){
                  table1();  
                }
                else if(ch.equals("rooms")){
                  table2();  
                }
                else if(ch.equals("checkin_out")){
                  table4(); 
                }
     }
    
    static void table1(){
        try{
            
                stat=con.createStatement();
                ResultSet rs=stat.executeQuery("select * from customer");
                while(rs.next()){
                    int ssn=rs.getInt("ssn");
                    String first=rs.getString("first_name");
                    String last=rs.getString("last_name");
                    String phone=rs.getString("ph_no");
                    String addr=rs.getString("address");
                    String email=rs.getString("email");
                    System.out.println(String.valueOf(ssn)+" "+first+" "+last+" "+phone+" "+addr+" "+email);
                     }//con.close();
        }catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void table2(){
        try{
         
                stat=con.createStatement();
                    ResultSet rs=stat.executeQuery("select * from rooms");
                    while(rs.next()){
                        int book=rs.getInt("booking_no");
                        String type=rs.getString("type");
                        String face=rs.getString("facilities");
                        int room=rs.getInt("room_no");
                        System.out.println(String.valueOf(book)+" "+type+" "+face+" "+String.valueOf(room));
                    }
                    //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void table3(){
        try{
             
                stat=con.createStatement();
                ResultSet rs=stat.executeQuery("select * from invoice");
                
                while(rs.next()){
                    int ssn=rs.getInt("ssn");
                    int days=rs.getInt("days");
                    int total=rs.getInt("total");
                    System.out.println(ssn+" "+days+" "+total);
                     }//con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void table4(){
        try{
             
                stat=con.createStatement();
                    ResultSet rs=stat.executeQuery("select * from checkin_out");
                    while(rs.next()){
                        java.sql.Date chin=rs.getDate("chin");
                        java.sql.Date chout=rs.getDate("chout");
                        int ssn=rs.getInt("ssn");
                        System.out.println(chin+" "+chout+" "+ssn);
                    }
                    //con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Minor2019.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
