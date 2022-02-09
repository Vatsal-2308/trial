package database;


import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//Here I have wrote a single function to insert a book inside the library.
@Path("/Insert")
public class Insert {
	@POST
	@Path("/Insert-book")
	public Response books(@NotNull
						  @QueryParam("Name") String Name,
						  @NotNull
						  @QueryParam("author_name") String author_name,
						  @NotNull
						  @QueryParam("city_name") String city_name) throws SQLException{
		Connection conn = DBUtil.getConnection();
		//Calculating the new Id which is to be given to the new book.
		String s =" SELECT max(Id) FROM books ";
		PreparedStatement pst = conn.prepareStatement(s);
		ResultSet rst = pst.executeQuery();
		int m=0;
		while(rst.next())
		{
			m = rst.getInt(1);
		}
		m=m+1;
		
		
		//Calculating the new ID for author.
		String s1 =" SELECT max(Id) FROM authors ";
		PreparedStatement pst1 = conn.prepareStatement(s1);
		ResultSet rst1 = pst1.executeQuery();
		int m1=0;
		while(rst1.next())
		{
			m1 = rst1.getInt(1);
		}
		m1=m1+1;
		
		
		//First we have to give the name of book as input.
		System.out.print("Enter the name of the book:");

		String n = Name;
		
		
		//Now I am checking if the book exits or not in the library.
		 String myQuery1 = "select * from books";
		 PreparedStatement pstmt_books ;
		 pstmt_books = conn.prepareStatement(myQuery1);
		 ResultSet rst_books = null;
		 rst_books = pstmt_books.executeQuery();
		 int y_books=0;
		 while(rst_books.next()) {
			
			 String com_books = rst_books.getString(2);
			 if(n.equals(com_books))
			 {
				 y_books=rst_books.getInt(1);
				 break;
			 }
	      }
		if(y_books!=0)
		{
			System.out.println("Book Already exists");
			return Response.status(500).entity("Book already exists").build();
			                                   //If the book exists then we will not store it again.
		}
		
		
		//As book does not exists so storing it in database.
		String str = "INSERT INTO books VALUES(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(str);
		pstmt.setInt(1, m);
		pstmt.setString(2, n);
		pstmt.executeUpdate();
		pstmt.close();
		
		
		//Taking the input name of author.
		System.out.print("Enter the Name of Author:");

		String n1 = author_name;
		
		
		//Checking if the name of the author already exists or not as if it exists then I will not give it another Id again as it can create confusion.
		 String myQuery = "select * from authors";
		 PreparedStatement pstmt1 ;
		 pstmt1 = conn.prepareStatement(myQuery);
		 ResultSet rst2 = null;
		 rst2 = pstmt1.executeQuery();
		 int y=0;
		 while(rst2.next()) {
			
			 String com = rst2.getString(2);
			 if(n1.equals(com))
			 {
				 y=rst2.getInt(1);
				 break;
			 }
	      }
		 if(y!=0)
		 {
			 m1=y;
			 System.out.println("Name of the Author Already Exists");
		 }
		 else
		 {
			 
			 //It the name is new then will take country input for the database and then storing it in the author table.
			 System.out.print("Enter the Name of the Country:");

			 String city = city_name;
			 String str_author = "INSERT INTO authors VALUES(?,?,?)";
			 PreparedStatement pstmt_author = conn.prepareStatement(str_author);
			 pstmt_author.setInt(1, m1);
		   	 pstmt_author.setString(2, n1);
		   	 pstmt_author.setString(3, city);
			 pstmt_author.executeUpdate();
			 pstmt_author.close();
			 
		 }
		 
		 
		 //Now here i am connecting the book with the author by using book id and author id in the booksauthors table.
		 String str_booksauthors = "INSERT INTO booksauthors VALUES(?,?)";
		 PreparedStatement pstmt_booksauthors = conn.prepareStatement(str_booksauthors);
		 pstmt_booksauthors.setInt(1,m1);
		 pstmt_booksauthors.setInt(2,m);
		 pstmt_booksauthors.executeUpdate();
		 pstmt_booksauthors.close();
		return Response.status(200).entity("Book Added sucessfully!!! ").build();
	}
}
