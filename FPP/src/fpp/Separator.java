package fpp;

import ilog.cplex.*;
import ilog.concert.*;

import java.util.Map;
import java.util.HashMap;

public class Separator extends IloCplex.UserCutCallback
{
	private IloCplex _cplex;
	private Pool _pool;
	private static Map<String, Integer> _cuts;
	private Map<Codeword, IloNumVar> _x;
	
	private static double _threshold = 0.1;
	private static boolean _verbose = false;
	
	public Separator(Model model)
	{
		_cplex = model.getCplex();
		_pool = model.getPool();
		_x = model.getVariables();
		_cuts = new HashMap<String, Integer>();

		for(String family: _pool.families())
			_cuts.put(family, 0);
	}
	
	@Override
	protected void main() throws IloException
	{
		if( this.isAfterCutLoop() == false )
	        return;
		
		for(String family: _pool.families())
		for(Inequality dv: _pool.inequalities(family)) if( violated(dv) == true )
		{
			this.add(range(dv), IloCplex.CutManagement.UseCutForce);
			_cuts.put(family, _cuts.get(family)+1);
			
			if( _verbose == true )
				System.out.println("**** " + family + "! -  Total: " + _cuts.get(family));
		}
	}
	
	private boolean violated(Inequality inequality) throws IloException
	{
		double lhs = 0;
		for(Codeword c: inequality.support())
			lhs += inequality.get(c) * this.getValue(_x.get(c));
		
		return lhs + _threshold <= inequality.getRhs();
	}
	
	private IloRange range(Inequality inequality) throws IloException
	{
		IloNumExpr lhs = _cplex.linearIntExpr();

		for(Codeword c: inequality.support())
			lhs = _cplex.sum(lhs, _cplex.prod(inequality.get(c), _x.get(c)));
		
		return _cplex.ge(lhs, inequality.getRhs());
	}
	
	public static void showStatistics()
	{
		int sum = 0;
		for(String family: _cuts.keySet())
		{
			System.out.println(family + ": " + _cuts.get(family) + " cuts");
			sum += _cuts.get(family);
		}
		
		System.out.println(sum + " total cuts");
	}
}
