package fpp;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Pool
{
	private Map<String, Set<Inequality>> _dvs;
	
	public Pool()
	{
		_dvs = new HashMap<String, Set<Inequality>>();
	}
	
	public void createFamily(String name)
	{
		_dvs.put(name, new HashSet<Inequality>());
	}
	
	public void add(String family, Inequality dv)
	{
		_dvs.get(family).add(dv);
	}
	
	public Set<String> families()
	{
		return _dvs.keySet();
	}
	
	public Set<Inequality> inequalities(String family)
	{
		return _dvs.get(family);
	}
}
