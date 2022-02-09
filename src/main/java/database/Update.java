package database;

import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
@Path("/Update")
public class Update {
	@POST
	@Path("/Update-book")
	//This function is used to update the name of the book if there is some error in the name using its Old name.
	public Response books(@NotNull
						  @QueryParam("bookname") String bookname,
						  @NotNull
						  @QueryParam("correct") String correct) throws SQLException {

		Connection conn = DBUtil.getConnection();
		System.out.print("Enter the Name of the Book:");
		Scanner sc = new Scanner(System.in);
		String i = bookname;

		String myQuery1 = "select * from books";
		PreparedStatement pstmt_books ;
		pstmt_books = conn.prepareStatement(myQuery1);
		ResultSet rst_books = null;
		rst_books = pstmt_books.executeQuery();
		int y_books=0;
		while(rst_books.next()) {

			String com_books = rst_books.getString(2);
			if(i.equals(com_books))
			{
				y_books=1;
				break;
			}
		}
		if(y_books==0)
		{
			System.out.println("Book Already exists");
			return Response.status(500).entity("Book Does not exists").build();
		}


		System.out.print("Enter the Correct name of the book:");
		String n = correct;

		if(i.equals(n))
		{
			return Response.status(500).entity("Same names Entered.").build();
		}
		 		 String myQuery = "UPDATE Books "
				 + "SET Title = ?"
				 + " WHERE Title = ?";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 pstmt.setString(1, n);
		 pstmt.setString(2, i);
		 
		 pstmt.executeUpdate();
		return Response.status(200).entity("Book Name Changed!!! ").build();
		 
	}

	@POST
	@Path("/Update-Author_name")
	//By this function we can change the incorrect author name to its correct one.
	public Response Authors_name(@NotNull
								 @QueryParam("incorrect") String incorrect,
								 @NotNull
								 @QueryParam("correct") String correct) throws SQLException {
		Connection conn = DBUtil.getConnection();

		//System.out.print("Enter the Incorrect Name of the Author:");
		String i = incorrect;

		String myQuery1 = "select * from authors";
		PreparedStatement pstmt_books ;
		pstmt_books = conn.prepareStatement(myQuery1);
		ResultSet rst_books = null;
		rst_books = pstmt_books.executeQuery();
		int y_books=0;
		while(rst_books.next()) {

			String com_books = rst_books.getString(2);
			if(i.equals(com_books))
			{
				y_books=1;
				break;
			}
		}
		if(y_books==0)
		{
			return Response.status(500).entity("Name Does not exists").build();
		}


		//System.out.print("Enter the Correct name of the Author:");
		String n = correct;
		if(i.equals(n))
		{
			return Response.status(500).entity("Same Name Entered").build();
		}
		 String myQuery = "UPDATE authors "
		 + "SET Name = ?"
		 + " WHERE Name = ?";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 pstmt.setString(1, n);
		 pstmt.setString(2, i);
		 pstmt.executeUpdate();
		return Response.status(200).entity("Author Name Changed!!! ").build();
	}
	@POST
	@Path("/Update-Author_city")
	//By this function we can change the country of the author using his name.
	public Response Authors_country(@NotNull
									@QueryParam("authorname") String authorname,
									@NotNull
									@QueryParam("cityname") String cityname) throws SQLException {
		Connection conn = DBUtil.getConnection();
		//System.out.print("Enter the Name of the Author:");
		//Scanner sc = new Scanner(System.in);
		String i = authorname;

		String myQuery1 = "select * from authors";
		PreparedStatement pstmt_books ;
		pstmt_books = conn.prepareStatement(myQuery1);
		ResultSet rst_books = null;
		rst_books = pstmt_books.executeQuery();
		int y_books=0;
		while(rst_books.next()) {

			String com_books = rst_books.getString(2);
			if(i.equals(com_books))
			{
				y_books=1;
				break;
			}
		}
		if(y_books==0)
		{
			return Response.status(500).entity("Author Name Does not exists").build();
		}


		//System.out.println(" ");
		//Scanner sc1 = new Scanner(System.in);
		//System.out.print("Enter the name of the Country:");
		String n = cityname;
		 String myQuery = "UPDATE authors "
		 + "SET Country = ?"
		 + " WHERE Name = ?";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 pstmt.setString(1, n);
		 pstmt.setString(2, i);
		 pstmt.executeUpdate();
		return Response.status(200).entity("City Name Changed!!! ").build();
	}
}
