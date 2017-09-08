package hu.bme.iit.hls.entities

import hu.bme.iit.hls.hig.HigModel.Const
import java.io.Serializable
import java.util.List
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Architecture implements Serializable{
    List<VhdlEntity> components;
    List<Const> constants;
    Set<Signal> signals;
    String body;
}