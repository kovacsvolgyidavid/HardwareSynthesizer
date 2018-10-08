package hu.bme.iit.hls.HIGadmittance;

import java.nio.file.Path;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class FileHandler {
	public EList<EObject> loadAllData(Path path) {

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("*", new XMIResourceFactoryImpl());

		ResourceSet resSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(path.toString());

		Resource resource = resSet.getResource(uri, true);
		return resource.getContents();
	}

	public EObject loadData(Path path) {

		return (EObject) loadAllData(path).get(0);
	}

}
