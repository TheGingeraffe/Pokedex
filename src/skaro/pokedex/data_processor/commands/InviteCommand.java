package skaro.pokedex.data_processor.commands;

import java.awt.Color;

import skaro.pokedex.data_processor.AbstractCommand;
import skaro.pokedex.data_processor.Response;
import skaro.pokedex.input_processor.Input;
import skaro.pokedex.input_processor.arguments.ArgumentCategory;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

public class InviteCommand extends AbstractCommand 
{
	private Response staticDiscordReply;
	
	public InviteCommand()
	{
		super(null);
		commandName = "invite".intern();
		argCats.add(ArgumentCategory.NONE);
		expectedArgRange = new ArgumentRange(0,0);
		staticDiscordReply = new Response();
		
		EmbedBuilder builder = new EmbedBuilder();	
		builder.setLenient(true);
		builder.withColor(new Color(0xD60B01));
		
		builder.appendField("Invite Pokdex to your server!", "[Click to invite Pokedex](https://discordapp.com/oauth2/authorize?client_id=206147222746824704&scope=bot&permissions=2165760)", false);
		builder.appendField("Join Pokedex's home server!", "[Click to join Pokedex's server](https://discord.gg/D5CfFkN)", false);
		
		staticDiscordReply.setEmbededReply(builder.build());
	}
	
	public boolean makesWebRequest() { return false; }
	public String getArguments() { return "none"; }
	public Response discordReply(Input input, IUser requester) {  return staticDiscordReply; }
}
