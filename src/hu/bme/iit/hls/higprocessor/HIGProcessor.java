package hu.bme.iit.hls.higprocessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
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
import hu.bme.iit.hls.higmodel.InputPort;
import hu.bme.iit.hls.higmodel.LoopComp;
import hu.bme.iit.hls.higmodel.Node;
import hu.bme.iit.hls.higmodel.Port;
import hu.bme.iit.hls.higmodel.SelCase;
import hu.bme.iit.hls.higmodel.SelComp;
import hu.bme.iit.hls.higmodel.util.HigModelSwitch;
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
			
			

			@Override
			public Object caseSelComp(SelComp sel) {
				Set<Vhdl> components = sel.getCases().stream().map(SelCase::getComp).map(manager::getVhdl).collect(Collectors.toSet());
				Vhdl vhdl = manager.getVhdl(sel);
				vhdl.setComponentsList(components);				
				try(OutputStream outputStream=Files.newOutputStream(vhdl.getVhdlFile().toPath())) {
					builder.buildVhdl(sel, outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}	
				return super.caseSelComp(sel);
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
		prepareHig(higModellRoot);
		addToVhdlLibrary(higModellRoot);
		return null;
	}

	private void prepareHig(EObject eobject) {
		HigModelSwitch<Object> visitor = new HigModelSwitch<Object>() {
			int i = 0;
			@Override
			public Object casePort(Port object) {
				String name = object.getName().replaceAll("[^a-zA-Z\\d\\s:]*", "");
				String newName;
				Stream <String> exceptions = Stream.of("in");
				if(name.matches("^\\d.*") || exceptions.anyMatch(k->k.equals(name))){
					newName = createPortName(object)+i++;
				}else{
					newName=name;
				}
				object.setName(newName);
				
				return super.casePort(object);
			}
			private String createPortName(Port object) {
				// TODO Auto-generated method stub
				return object instanceof InputPort ? "input":"output";
			}
			
		};
		for (Iterator iter = EcoreUtil.getAllContents(Collections.singleton(eobject)); iter.hasNext();) {
			EObject eObject = (EObject) iter.next();
			visitor.doSwitch(eObject);
		}
	}
}
