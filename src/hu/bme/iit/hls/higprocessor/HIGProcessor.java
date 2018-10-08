package hu.bme.iit.hls.higprocessor;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.LoopNode;
import hu.bme.iit.hls.hig.HigModel.util.HigModelSwitch;
import hu.bme.iit.hls.vhdlbuilder.VhdlBuilder;

public class HIGProcessor {
	private void addToVhdlLibrary(EObject eobject) {//VHDL Library-hez hozzá ad minden EObject által tartalmazott complex és nem simple operationt
		HigModelSwitch<Object> visitor = new HigModelSwitch<Object>() { //metódusok felül definiálása
			VhdlBuilder builder = new VhdlBuilder();
			public Object caseComplexNode(ComplexNode object) { 
				builder.buildVhdl(object);
				return object;
			}

			public Object caseLoopNode(LoopNode object) {
				builder.buildVhdl(object);
				return object;
			}
			
		};
		for (Iterator iter = EcoreUtil.getAllContents(Collections.singleton(eobject)); iter.hasNext();) {
			EObject eObject = (EObject) iter.next();
			visitor.doSwitch(eObject);
		}
	}

	public void processHig(EObject eobject) {
		addToVhdlLibrary(eobject);
	}
}
