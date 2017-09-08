package hu.bme.iit.hls.higprocessor;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import hu.bme.iit.hls.admittance.SignalMapper;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.DataLink;
import hu.bme.iit.hls.hig.HigModel.ElementaryOp;
import hu.bme.iit.hls.hig.HigModel.util.HigModelSwitch;
import hu.bme.iit.hls.vhdlbuilder.VhdlBuilder;

public class HIGProcessor {
	private void preprocessHig(EObject eobject) {
		HigModelSwitch<Object> visitor = new HigModelSwitch<Object>() {
			SignalMapper signalMapper = new SignalMapper();
			public Object caseDataLink(DataLink object) {
				signalMapper.mapSignal(object);
				return object;
			}
		};

		for (Iterator iter = EcoreUtil.getAllContents(Collections.singleton(eobject)); iter.hasNext();) {
			EObject eObject = (EObject) iter.next();
			visitor.doSwitch(eObject);
		}
	}

	private void addToVhdlLibrary(EObject eobject) {
		HigModelSwitch<Object> visitor = new HigModelSwitch<Object>() {
			VhdlBuilder builder = new VhdlBuilder();
			public Object caseComplexNode(ComplexNode object) {
				builder.buildVhdl(object);
				return object;
			}

			public Object caseElementaryOp(ElementaryOp object) {
				builder.buildVhdl(object);
				return object;
			}
		};
		for (Iterator iter = EcoreUtil.getAllContents(Collections.singleton(eobject)); iter.hasNext();) {
			EObject eObject = (EObject) iter.next();
			visitor.doSwitch(eObject);
		}
	}

	public void processHigtoVhdl(EObject eobject) {
		//preprocessHig(eobject);
		addToVhdlLibrary(eobject);
	}
}
