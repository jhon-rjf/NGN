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
			System.out.println(name+"님의 메시지 쿼리 성공");
		} catch (SQLException e) {
			System.out.println("쿼리 실패");
			e.printStackTrace();
		}
		
	}

	String driver = "org.mariadb.jdbc.Driver";
    Connection con;
    PreparedStatement pstmt;
    
    public DBcon() {
         try {
            Class.forName(driver); //드라이버 객체화
            con = DriverManager.getConnection( //db와 연결
                    "jdbc:mariadb://127.0.0.1:3309/test",
                    "root",
                    "dygks0917");
            
            if( con != null ) {
                System.out.println("DB 접속 성공");           
            }
        
           
            
        } catch (ClassNotFoundException e) { 
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }

         
    }
    
   
   
    
    public static void main(String[] args){
        DBcon DBcon    = new DBcon();
    }
}
