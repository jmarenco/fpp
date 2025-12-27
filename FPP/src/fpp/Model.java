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
	private Pool _pool;
	private IloCplex _cplex;
	private Map<Codeword,IloNumVar> _x;
	private int _maxNodes = 0;
	private boolean _cplexMagic = true;
	
	public Model(Instance instance)
	{
		_instance = instance;
	}
	
	public Model(Instance instance, Pool pool)
	{
		_instance = instance;
		_pool = pool;
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
		if( _pool != null )
			_cplex.use(new Separator(this));
		
		long start = System.currentTimeMillis();
		
		if( _maxNodes > 0 )
			_cplex.setParam(IloCplex.Param.MIP.Limits.Nodes, _maxNodes);

		if( _cplexMagic == false )
		{
			_cplex.setParam(IloCplex.Param.Preprocessing.Presolve, false);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.BQP, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Cliques, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Covers, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Disjunctive, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.FlowCovers, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Gomory, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.GUBCovers, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Implied, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.LiftProj, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.LocalImplied, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.MCFCut, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.MIRCut, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.Nodecuts, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.PathCut, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.RLT, -1);
			_cplex.setParam(IloCplex.Param.MIP.Cuts.ZeroHalfCut, -1);
			_cplex.setParam(IloCplex.Param.MIP.Strategy.LBHeur, false);
			_cplex.setParam(IloCplex.Param.MIP.Strategy.PresolveNode, -1);
		}
		
		_cplex.setParam(IloCplex.Param.MIP.Interval, 1);
		_cplex.solve();
		
		System.out.println();
		System.out.println("Status: " + _cplex.getStatus());
		System.out.println("Objective: " + _cplex.getObjValue());
		System.out.println("Dual: " + _cplex.getBestObjValue());
		System.out.println("Gap: " + _cplex.getMIPRelativeGap());
		System.out.println("Nodes: " + _cplex.getNnodes());
		System.out.println("Time: " + String.format("%.2f", (System.currentTimeMillis() - start) / 1000.0) + " sec.");
		System.out.println();
		
		if( _pool != null )
			Separator.showStatistics();

		_cplex.end();
	}
	
	public void setMaxNodes(int maxNodes)
	{
		_maxNodes = maxNodes;
	}
	
	public void useCplexMagic(boolean magic)
	{
		_cplexMagic = magic;
	}
	
	protected IloCplex getCplex()
	{
		return _cplex;
	}
	
	protected Map<Codeword,IloNumVar> getVariables()
	{
		return _x;
	}
	
	protected Pool getPool()
	{
		return _pool;
	}
}
