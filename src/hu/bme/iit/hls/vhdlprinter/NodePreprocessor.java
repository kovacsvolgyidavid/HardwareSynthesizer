package hu.bme.iit.hls.vhdlprinter;

import java.util.ArrayList;
import java.util.List;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.hig.HigModel.Const;
import hu.bme.iit.hls.hig.HigModel.GraphNode;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class NodePreprocessor {
	public static PreprocessedNode presprocessNode(GraphNode node){
		PreprocessedNode preNode=new PreprocessedNode();
		preNode.setComponents(getComponents(node));
		preNode.setConstants(getConstants(node));
		preNode.setEntity(getEntity(node));
		preNode.setInstances(getInstances(node));
		preNode.setSignals(getSignals(node));
		return preNode;
	}

	private static List<Signal> getSignals(GraphNode node) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	private static List<Instance> getInstances(GraphNode node) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	private static VhdlEntity getEntity(GraphNode node) {
		VhdlManager manager = new VhdlManager();
		return manager.getEntity(node);
	}

	private static List<Const> getConstants(GraphNode node) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	private static List<VhdlEntity> getComponents(GraphNode node) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}
}
