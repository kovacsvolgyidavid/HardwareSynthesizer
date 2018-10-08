package hu.bme.iit.hls.entities

import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class VhdlEntityInstance {
    VhdlEntity entity;
    Map<VhdlPort, Signal> portMap;
}