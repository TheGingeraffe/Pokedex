package skaro.pokedex.input_processor.arguments;

import java.util.ArrayList;
import java.util.List;

import skaro.pokedex.data_processor.formatters.TextFormatter;
import skaro.pokedex.input_processor.AbstractArgument;
import skaro.pokedex.input_processor.Language;

public class MetaArgument extends AbstractArgument 
{
	private static List<String> metas;
	
	static
	{
		metas = new ArrayList<String>();
		metas.add("lc"); metas.add("nu"); metas.add("uber");
		metas.add("ou"); metas.add("pu"); metas.add("ru"); metas.add("uu");
	}
	
	public MetaArgument()
	{
		
	}

	public void setUp(String argument, Language lang) 
	{
		//Set up argument
		this.dbForm = TextFormatter.dbFormat(argument, lang);
		this.cat = ArgumentCategory.META;
		this.rawInput = argument;
		
		//Check if resource is recognized. If it is not recognized, attempt to spell check it.
		//If it is still not recognized, then return the argument as invalid (default)
		if(!isMeta(this.dbForm))
		{
			this.valid = false;
			return;
		}
		
		this.valid = true;
		this.flexForm = this.dbForm;
	}
	
	private boolean isMeta(String s)
	{
		return metas.contains(s);
	}
}
