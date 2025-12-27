package interfaz;

import fpp.*;

public class EntryPoint
{
	public static void main(String[] args)
	{
		ArgMap argmap = new ArgMap(args);
		Instance instance = new Instance(argmap.intArg("-n", 5));

		Pool pool = new Pool();
		
		if( argmap.containsArg("-box") )
			BoxEnumerator.run(instance, pool);
		
		if( argmap.containsArg("-rbox") )
			ReinforcedBoxEnumerator.run(instance, pool);

		if( argmap.containsArg("-3d12") )
			Box3D12Enumerator.run(instance, pool);
		
		if( argmap.containsArg("-3d23") )
			Box3D23Enumerator.run(instance, pool);

		if( argmap.containsArg("-inner") )
			InnerDiamondEnumerator.run(instance, pool);
		
		if( argmap.containsArg("-outer") )
			OuterDiamondEnumerator.run(instance, pool);
		
		if( pool.families().size() == 0 )
			pool = null;

		Model model = new Model(instance, pool);
		
		if( argmap.containsArg("-root") )
			model.setMaxNodes(1);
		
		if( argmap.containsArg("-nodes") )
			model.setMaxNodes(argmap.intArg("-nodes", 100000000));

		if( argmap.containsArg("-nomagic") )
			model.useCplexMagic(false);

		model.solve();
	}
}
