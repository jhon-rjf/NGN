import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBcon {
	
	public void user (String code, String line) {
		String name = code;
		String Msg = line;
		try {
			pstmt=con.prepareStatement("INSERT INTO CHAT VALUES(?,DEFAULT,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, Msg);
			pstmt.executeQuery();
			System.out.println(name+"���� �޽��� ���� ����");
		} catch (SQLException e) {
			System.out.println("���� ����");
			e.printStackTrace();
		}
		
	}

	String driver = "org.mariadb.jdbc.Driver";
    Connection con;
    PreparedStatement pstmt;
    
    public DBcon() {
         try {
            Class.forName(driver); //����̹� ��üȭ
            con = DriverManager.getConnection( //db�� ����
                    "jdbc:mariadb://127.0.0.1:3309/test",
                    "root",
                    "dygks0917");
            
            if( con != null ) {
                System.out.println("DB ���� ����");           
            }
        
           
            
        } catch (ClassNotFoundException e) { 
            System.out.println("����̹� �ε� ����");
        } catch (SQLException e) {
            System.out.println("DB ���� ����");
            e.printStackTrace();
        }

         
    }
    
   
   
    
    public static void main(String[] args){
        DBcon DBcon    = new DBcon();
    }
}