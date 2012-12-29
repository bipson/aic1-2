package sentiment;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class SentimentServiceInitializer extends HttpServlet {
	private static final long serialVersionUID = 1l;

	Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public void init(ServletConfig config) throws ServletException {
		logger.warn("UNFINISHED BUSINESS: Sentiment Initialization goes here.");
	}

}
