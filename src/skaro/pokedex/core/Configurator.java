package skaro.pokedex.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;				//in Discord4J's dependencies
import com.fasterxml.jackson.databind.ObjectMapper;			//in Discord4J's dependencies

/**
 * A singleton class for parsing the configuration file "config.json".
 * The data member "isDebugSession" should be set to TRUE if Pokedex
 * is being tested; FALSE otherwise.
 *
 */
public class Configurator 
{
	private static String dataKey = "production".intern();
	private static Configurator instance;
	private static JsonNode rootNode;
	
	private Configurator(boolean debug)
	{
		if(debug)
			dataKey = "debug".intern();
		
		try 
        {
			ObjectMapper objectMapper = new ObjectMapper();
	        InputStream config = ResourceManager.getConfigurationResource("config.json");
			rootNode = objectMapper.readTree(config);
		} 
        catch (IOException e) 
        {
			System.out.println("[Congifurator] Unable to parse configuration file (config.json). Exiting..");
			System.exit(1);
		}
	}
	
	public static Configurator initializeConfigurator(boolean debug)
	{
		if(instance != null)
			return instance;
			
		instance = new Configurator(debug);
		return instance;
	}
	
	public static Optional<Configurator> getInstance()
	{
		return Optional.ofNullable(instance);
	}
	
	public String getVersion()
	{
		return rootNode.get("version".intern()).asText();
	}
	
	public Optional<String>getAuthToken(String application)
	{
		return getConfigData("token".intern(), application);
	}
	
	public Optional<String> getUsername(String application)
	{
		return getConfigData("username".intern(), application);
	}
	
	public Optional<String> getConfigData(String data, String application)
	{
		JsonNode dataNode = rootNode.get(application);
		if(dataNode == null)
			return Optional.empty();	//No configuration data for application
		
		String result = dataNode.get(data).get(dataKey).asText();
		
		if(result.equals(""))
			return Optional.empty();
		return Optional.of(result);
	}
	
	public List<String> getPublishRecipients()
	{
		List<String> result = new ArrayList<String>();
		JsonNode dataNode = rootNode.get("publish_recipients");
		
		for(Iterator<String> itr = dataNode.fieldNames(); itr.hasNext(); )
			result.add(itr.next());
		
		return result;
	}
	
	public Optional<String> getPublishAuthToken(String recipient)
	{
		JsonNode dataNode = rootNode.get("publish_recipients");
		if(dataNode == null)
			return Optional.empty();	//No configuration data for application
		
		String result = dataNode.get(recipient).get("token").get(dataKey).asText();
		
		if(result.equals(""))
			return Optional.empty();
		return Optional.of(result);
	}
	
	public int getPublishDesignatedShard(String recipient)
	{
		JsonNode dataNode = rootNode.get("publish_recipients");
		
		return dataNode.get(recipient).get("designated_shard").asInt();
	}
	
	public String getPublishClass(String recipient)
	{
		JsonNode dataNode = rootNode.get("publish_recipients");
		
		String result = dataNode.get(recipient).get("class_name").asText();
		return result;
	}
	
	public String[] getDBCredentials()
	{
		JsonNode dataNode = rootNode.get("sql");
		if(dataNode == null)
			throw new IllegalStateException("No database congifuration data could be found.");
		
		String[] credentials = new String[4];
		credentials[0] = dataNode.get("username").asText();
		credentials[1] = dataNode.get("database").asText();
		credentials[2] = dataNode.get("password").asText();
		credentials[3] = dataNode.get("uri").asText();
		
		return credentials;
	}
	
	public String getModelBasePath()
	{
		JsonNode dataNode = rootNode.get("model_path");
		if(dataNode == null)
			throw new IllegalStateException("No database congifuration data could be found.");
		
		return dataNode.get(dataKey).asText();
	}
	
	public String getDebugLevel()
	{
		JsonNode dataNode = rootNode.get("debug_level");
		if(dataNode == null)
			throw new IllegalStateException("No database congifuration data could be found.");
		
		return dataNode.get(dataKey).asText();
	}
	
	public String getPokeFlexURL()
	{
		JsonNode dataNode = rootNode.get("pokeflex_url");
		if(dataNode == null)
			throw new IllegalStateException("No database congifuration data could be found.");
		
		return dataNode.get(dataKey).asText();
	}
}
