package interfaz;

import fpp.*;

public class EntryPoint
{
	public static void main(String[] args)
	{
		Instance instance = new Instance(5);

		Pool pool = new Pool();
		BoxEnumerator.run(instance, pool);
		ReinforcedBoxEnumerator.run(instance, pool);
		Box3D12Enumerator.run(instance, pool);
		Box3D23Enumerator.run(instance, pool);
		InnerDiamondEnumerator.run(instance, pool);
		OuterDiamondEnumerator.run(instance, pool);

//		Model model = new Model(instance);
		Model model = new Model(instance, pool);
		model.solve();
	}
}
