package sentiment;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SentimentServiceInitializer extends HttpServlet {
	private static final long serialVersionUID = 1l;
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Sentiment Initialization goes here.");
	}

}
