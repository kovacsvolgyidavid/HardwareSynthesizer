package hu.bme.iit.hls.higprocessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.Sets;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.higmodel.HIG;
import hu.bme.iit.hls.higmodel.HigModelRoot;
import hu.bme.iit.hls.higmodel.LoopComp;
import hu.bme.iit.hls.higmodel.Node;
import hu.bme.iit.hls.higmodel.util.HigModelSwitch;
import hu.bme.iit.hls.utility.TempFileHandler;
import hu.bme.iit.hls.vhdlbuilder.VhdlBuilder;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class HIGProcessor {
	
	VhdlBuilder builder = new VhdlBuilder();
	
	private void addToVhdlLibrary(EObject eobject) {//VHDL Library-hez hozzá ad minden EObject által tartalmazott complex és nem simple operationt
		HigModelSwitch<Object> visitor = new HigModelSwitch<Object>() { //metódusok felül definiálása
			VhdlBuilder builder = new VhdlBuilder();
			VhdlManager manager = new VhdlManager();

			@Override
			public Object caseHIG(HIG hig) {
				if("root".equals(hig.getName())){
					return super.caseHIG(hig);
				}				
				Set<Vhdl> components = hig.getNodes().stream().map(Node::getComponent).map(manager::getVhdl).collect(Collectors.toSet());
				Vhdl vhdl = manager.getVhdl(hig);
				vhdl.setComponentsList(components);				
				try(OutputStream outputStream=Files.newOutputStream(vhdl.getVhdlFile().toPath())) {
					builder.buildVhdl(hig, outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}				
				return super.caseHIG(hig);
			}

			@Override
			public Object caseLoopComp(LoopComp loop) {
				Vhdl loopVhdl = manager.getVhdl(loop);
				loopVhdl.setComponentsList(getLoopComponents(loop));
				try(OutputStream outputStream=Files.newOutputStream(loopVhdl.getVhdlFile().toPath())) {
					builder.buildVhdl(loop, outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}				
				return super.caseLoopComp(loop);
			}

			private Set<Vhdl> getLoopComponents(LoopComp loop) {
				Vhdl mainComponent = manager.getVhdl(loop.getComp());
				Vhdl phiComponent = manager.getVhdl("Phi");
				return Sets.newHashSet(mainComponent,phiComponent);
			}
			
			
		};
		for (Iterator iter = EcoreUtil.getAllContents(Collections.singleton(eobject)); iter.hasNext();) {
			EObject eObject = (EObject) iter.next();
			visitor.doSwitch(eObject);
		}
	}

	public Vhdl processHigModellRoot(HigModelRoot higModellRoot) {	
		addToVhdlLibrary(higModellRoot);
		return null;
	}
}
