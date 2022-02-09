package database;



import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.HashMap;


@Path("/Info")
public class Info {

	
	// This function is used to show all the books in the library.

	@GET
	@Path("/books")

	@Produces(MediaType.APPLICATION_JSON)
	public Response books() throws SQLException {
		Connection conn = DBUtil.getConnection();
		 String myQuery = "select * from books";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 ResultSet rst = null;
		 rst = pstmt.executeQuery();
		 System.out.println("BookTitle\n");
		HashMap<Integer,String> b = new HashMap<>();
		int k=1;
		while(rst.next()) {

	           b.put(k,rst.getString(2));
			   k++;
	           // System.out.print(rst.getString(2));
	            //System.out.println();
	         }
		 System.out.println();
		return Response.ok(b,MediaType.APPLICATION_JSON).build();

	}
	@GET
	@Path("/authorfrombook")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authorfrombook(@NotNull
									@QueryParam("bookname") String bookname) throws SQLException{
		Connection conn = DBUtil.getConnection();
		//pojo p1 = new pojo();
		String myQuery = "select * from books";
		String i = bookname;
		PreparedStatement pstmt = conn.prepareStatement(myQuery);
		ResultSet rst = pstmt.executeQuery();
		int y1=0;
		while(rst.next())
		{
			String str = rst.getString(2);
			if(i.equals(str))
			{
				y1=rst.getInt(1);
				break;
			}
		}
		if(y1==0)
		{
			//System.out.println("No book found");
			String result = "No book found";
			return Response.ok(result,MediaType.APPLICATION_JSON).build();
			//System.exit(0);
		}
		myQuery = "select * from booksauthors";
		pstmt = conn.prepareStatement(myQuery);
		rst = pstmt.executeQuery();
		int y2=0;
		while(rst.next())
		{
			if(y1==rst.getInt(2))
			{
				y2=rst.getInt(1);
				break;
			}
		}

		myQuery = "select * from authors";
		pstmt = conn.prepareStatement(myQuery);
		rst = pstmt.executeQuery();
		HashMap<String,String> b = new HashMap<>();
		while(rst.next())
		{
			if(y2==rst.getInt(1))
			{
				System.out.println(rst.getString(2));
				b.put(i,rst.getString(2));
				break;
			}
		}
		return Response.ok(b,MediaType.APPLICATION_JSON).build();


	}

	@GET
	@Path("/bookfromauthor")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bookfromauthor(@NotNull
								   @QueryParam("authorname") String authorname) throws SQLException{
		Connection conn = DBUtil.getConnection();
		String myQuery = "select * from authors";
		String i = authorname;
		PreparedStatement pstmt = conn.prepareStatement(myQuery);
		ResultSet rst = pstmt.executeQuery();
		int y1=0;
		while(rst.next())
		{
			String str = rst.getString(2);
			if(i.equals(str))
			{
				y1=rst.getInt(1);
				break;
			}
		}
		if(y1==0)
		{
			String result = "Author name Does not exists";
			return Response.ok(result,MediaType.APPLICATION_JSON).build();
			//System.out.println("No author found");
			//System.exit(0);
		}
		myQuery = "select * from booksauthors";
		pstmt = conn.prepareStatement(myQuery);
		rst = pstmt.executeQuery();
		HashMap<Integer,String> b = new HashMap<>();
		int num=1;
		while(rst.next())
		{
			if(y1==rst.getInt(1))
			{
				int y=rst.getInt(2);
				String q = "select * from books";
				PreparedStatement pstmt1 = conn.prepareStatement(q);
				ResultSet rst1 = pstmt1.executeQuery();
				while(rst1.next())
				{
					if(y==rst1.getInt(1))
					{
						b.put(num,rst1.getString(2));
						num++;
						//System.out.println(rst1.getString(2));
						break;
					}
				}
			}
		}
		return Response.ok(b,MediaType.APPLICATION_JSON).build();

	}




	@GET
	@Path("/authors")

	@Produces(MediaType.APPLICATION_JSON)
	// This function is used to show all the authors name and their country of origin.
	public Response authors() throws SQLException {
		Connection conn = DBUtil.getConnection();
		 String myQuery = "select * from authors";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 ResultSet rst = null;
		 rst = pstmt.executeQuery();
		HashMap<String,String> b = new HashMap<>();
		 System.out.println("Name\t\tCountry\t\n");
		 while(rst.next()) {

			 b.put(rst.getString(2),rst.getString(3));
	            System.out.print(rst.getString(2));
	            System.out.print("\t\t"+rst.getString(3));
	            System.out.println();
	         }
		 System.out.println();
		return Response.ok(b,MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/bookauthors")

	@Produces(MediaType.APPLICATION_JSON)
	// This function is used to show books along with their author name.
	public Response bookauthors() throws SQLException {
		Connection conn = DBUtil.getConnection();
		 String myQuery = "SELECT a.Name AuthorName,b.Title BookTitle FROM BooksAuthors ba INNER JOIN Authors a ON a.id = ba.authorid INNER JOIN Books b ON b.id = ba.bookid";
		 PreparedStatement pstmt ;
		 pstmt = conn.prepareStatement(myQuery);
		 ResultSet rst = null;
		 rst = pstmt.executeQuery();
		 System.out.println("AuthorName\t\t\tBookTitle\t\n");
		HashMap<String,String> b = new HashMap<>();
		 while(rst.next()) {
			 b.put(rst.getString(2),rst.getString(1));
	            System.out.print(rst.getString(1));	            
	            System.out.print("\t\t\t"+rst.getString(2));	      
	            System.out.println();
	         }
		 System.out.println();
		return Response.ok(b,MediaType.APPLICATION_JSON).build();
	}



	@GET
	@Path("/country")
	@Produces(MediaType.APPLICATION_JSON)
	public Response country(@NotNull
							@QueryParam("name") String name) throws SQLException{
		return Response.ok("result",MediaType.APPLICATION_JSON).build();
		Connection conn = DBUtil.getConnection();
		String myQuery = "select * from authors";
		String i= name;
		PreparedStatement pstmt = conn.prepareStatement(myQuery);
		ResultSet rst = pstmt.executeQuery();
		int y=1;
		HashMap<Integer,String> b = new HashMap<>();
		while(rst.next())
		{
			if(i.equals(rst.getString(3)))
			{
				b.put(y,rst.getString(2));
				y++;
				//System.out.println(rst.getString(2));
			}
		}
		if(y==1)
		{
			String result = "No authors from this country";
			return Response.ok(result,MediaType.APPLICATION_JSON).build();
		}
		else
		{
			return Response.ok(b,MediaType.APPLICATION_JSON).build();
		}
	}

	@GET
	@Path("/author_exists")
	@Produces(MediaType.APPLICATION_JSON)
	public Response author_exists(@NotNull
								  @QueryParam("name") String name) throws SQLException{
		Connection conn = DBUtil.getConnection();
		String i = name;
		String myQuery = "select * from authors";
		PreparedStatement pstmt = conn.prepareStatement(myQuery);
		ResultSet rst = pstmt.executeQuery();
		while(rst.next())
		{
			if(i.equals(rst.getString(2)))
			{
				String result = "Author Exists";
				return Response.ok(result,MediaType.APPLICATION_JSON).build();
				//System.out.println("Author Exists");
				//System.exit(0);
			}
		}
		return Response.ok("No such Author Exists",MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Path("/book_exists")
	@Produces(MediaType.APPLICATION_JSON)
	public Response book_exists(@NotNull
								  @QueryParam("name") String name) throws SQLException{
		Connection conn = DBUtil.getConnection();
		String i = name;
		String myQuery = "select * from books";
		PreparedStatement pstmt = conn.prepareStatement(myQuery);
		ResultSet rst = pstmt.executeQuery();
		while(rst.next())
		{
			if(i.equals(rst.getString(2)))
			{
				String result = "book Exists";
				return Response.ok(result,MediaType.APPLICATION_JSON).build();
				//System.out.println("Author Exists");
				//System.exit(0);
			}
		}
		return Response.ok("No such Book Exists",MediaType.APPLICATION_JSON).build();

	}
}
