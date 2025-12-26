package fpp;

import java.util.HashMap;
import java.util.Map;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Model
{
	private Instance _instance;
	private IloCplex _cplex;
	private Map<Codeword,IloNumVar> _x;
	
	public Model(Instance instance)
	{
		_instance = instance;
	}
	
	public void solve()
	{
		try
		{
			createCplex();
			createVariables();
			createObjective();
			createConstraints();
			solveModel();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void createCplex() throws IloException
	{
		_cplex = new IloCplex();
	}
	
	private void createVariables() throws IloException
	{
		_x = new HashMap<Codeword, IloNumVar>();
		
		for(Codeword c: _instance.get())
			_x.put(c, _cplex.boolVar("x" + c.toString()));
	}

	private void createObjective() throws IloException
	{
		IloNumExpr obj = _cplex.linearIntExpr();
		
		for(Codeword c: _instance.get())
			obj = _cplex.sum(obj, _x.get(c));
		
		_cplex.addMinimize(obj);
	}

	private void createConstraints() throws IloException
	{
		for(Codeword c: _instance.get())
		{
			IloNumExpr lhs = _cplex.linearIntExpr();
			for(Codeword d: _instance.get()) if( c.distance(d) <= 1 )
				lhs = _cplex.sum(lhs, _x.get(d));
		
			_cplex.addGe(lhs, 1);
		}
	}

	private void solveModel() throws IloException
	{
		_cplex.solve();
		_cplex.end();
	}
}
