package hu.bme.iit.hls.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityMap {
	VhdlEntity entity;
	Map<VhdlPort, Signal> signalMap = new HashMap<>();

	public EntityMap() {

	}

	public EntityMap(VhdlEntity entity) {
		this.entity = entity;
	}

	public void mapPortToSignal(VhdlPort port, Signal signal) {
		if (entity.getPorts().contains(port)) {
			signalMap.put(port, signal);
		} else {
			throw new IllegalArgumentException(
					String.format("Nincs $1 nevû port a $2 nevû vhdl-ben", port.getName(), entity.getName()));
		}

	}

	public Optional<Signal> getMappedSignal(VhdlPort port) {
		return Optional.of(signalMap.get(port));

	}

}
