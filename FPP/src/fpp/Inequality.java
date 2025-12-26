package fpp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Inequality
{
	private Map<Codeword, Integer> _coeff;
	private int _rhs;
	
	public Inequality()
	{
		_coeff = new HashMap<Codeword, Integer>();
	}
	
	public void set(Codeword c, int coefficient)
	{
		if( _coeff.containsKey(c) )
			throw new RuntimeException("Codeword " + c + " already present in the inequality");
		
		_coeff.put(c, coefficient);
	}
	
	public void rhs(int value)
	{
		_rhs = value;
	}
	
	public Set<Codeword> support()
	{
		return _coeff.keySet();
	}
	
	public int get(Codeword c)
	{
		return _coeff.get(c);
	}
	
	public int getRhs()
	{
		return _rhs;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(_coeff, _rhs);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inequality other = (Inequality) obj;
		return Objects.equals(_coeff, other._coeff) && _rhs == other._rhs;
	}
}
