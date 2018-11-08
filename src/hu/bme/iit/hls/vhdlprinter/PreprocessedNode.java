package hu.bme.iit.hls.vhdlprinter;

import java.util.List;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlEntity;
public class PreprocessedNode {
	List<VhdlEntity> components;
	VhdlEntity entity;
	List<Const> constants;
	List<Signal> signals;
	List<Instance> instances;
	
	public List<VhdlEntity> getComponents() {
		return components;
	}
	public void setComponents(List<VhdlEntity> components) {
		this.components = components;
	}
	public VhdlEntity getEntity() {
		return entity;
	}
	public void setEntity(VhdlEntity entity) {
		this.entity = entity;
	}
	public List<Const> getConstants() {
		return constants;
	}
	public void setConstants(List<Const> constants) {
		this.constants = constants;
	}
	public List<Signal> getSignals() {
		return signals;
	}
	public void setSignals(List<Signal> signals) {
		this.signals = signals;
	}
	public List<Instance> getInstances() {
		return instances;
	}
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
	
}
