package database;

import com.sun.istack.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
@Path("/Delete")
public class Delete {

	@DELETE
	@Path("/delete-book")

	@Produces(MediaType.APPLICATION_JSON)
	public Response delete_book(@NotNull
								@QueryParam("Name") String Name) throws SQLException{

		Connection conn = DBUtil.getConnection();

		String result ="Book deleted Successfully.";
		// Enter the Name of the book to delete

		String book_delete = Name;
		
		//Checking if the book exists in the library or not
		String query = "SELECT * FROM books";
		PreparedStatement check = conn.prepareStatement(query);
		ResultSet rst = check.executeQuery();
		int p=0;
		while(rst.next()) {
			if(book_delete.equals(rst.getString(2)))
			{
				p=rst.getInt(1);
				break;
			}
		}
		if(p==0) {
			System.out.println("The entered Book does not exists.");
			return Response.status(500).entity("Book does not exists").build();                                             //Exit as the entered book does not exists in the library.
		}
		
		
		//Checking if the author of the book has more books under his name.
		//Getting the authorid from bookid.
		
		String query1 = "SELECT * FROM booksauthors";
		PreparedStatement check1 = conn.prepareStatement(query1);
		ResultSet rst1 = check1.executeQuery();
		int p1=0;                                                           //Used to store the authorid
		while(rst1.next()) {
			if(p==rst1.getInt(2))
			{
				p1=rst1.getInt(1);
				break;
			}
		}
	
		
		//Counting the number of books written by author.
		int count=0;
		String query2 = "SELECT * FROM booksauthors";
		PreparedStatement check2 = conn.prepareStatement(query2);
		ResultSet rst2 = check2.executeQuery();
		while(rst2.next()) {
			if(p1-rst2.getInt(1)==0)
			{
				count++;
			}
		}
		
		
		//Deleteing entry from booksauthors table.
		String query3 = "DELETE from booksauthors where BookId = ?";
		PreparedStatement check3 = conn.prepareStatement(query3);
		check3.setInt(1,p);
		check3.executeUpdate();
		
		//Delete from books table.
		String query4 = "DELETE from books where Id = ?";
		PreparedStatement check4 = conn.prepareStatement(query4);
		check4.setInt(1,p);
		check4.executeUpdate();
		
		//Delete from author table if only one book written.
		if(count==1)
		{
			String query5 = "DELETE from authors where Id = ?";
			PreparedStatement check5 = conn.prepareStatement(query5);
			check5.setInt(1,p1);
			check5.executeUpdate();
		}

		return Response.status(200).entity("Book Removed sucessfully!!! ").build();
	}



}
