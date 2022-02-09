package database;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class App extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e) throws Exception 
	{
		LOGGER.info("Registering REST resources");
		

		e.jersey().register(new Info());
		e.jersey().register(new Delete());
		e.jersey().register(new Insert());
		e.jersey().register(new Update());
		final Client client = new JerseyClientBuilder(e)
				.build("DemoRESTClient");



		
		//Setup Basic Security


        e.jersey().register(RolesAllowedDynamicFeature.class);
	}

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
}