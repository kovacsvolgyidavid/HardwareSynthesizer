package hu.bme.iit.hls.vhdlprinter;

import java.util.Map;
import java.util.stream.Collectors;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlConstant;
import hu.bme.iit.hls.higmodel.Const;
import hu.bme.iit.hls.higmodel.Port;
import hu.bme.iit.hls.higmodel.SelComp;

public class SelectionPreprocessor {
	
	SourceBuilder sourceBuilder = new SourceBuilder();

	public Map<Const, VhdlConstant> createSelectorMap(SelComp sel) {
		return sel.getCases().stream().flatMap(k->k.getSelectors().stream().filter(n->!"def".equals(n.getValue()))).collect(Collectors.toMap(
				k->{return k;}, v->{return sourceBuilder.createConstant(v.getValue());}));
	}

	public Map<Port, Signal> createOutputMap(SelComp comp) {
		return comp.getCases().stream().map(k->k.getComp()).flatMap(k->k.getOutPorts().stream()).collect(Collectors.toMap(k->k,p->sourceBuilder.createSignal(p)));

	}

}
	