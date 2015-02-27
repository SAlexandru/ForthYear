package com.salexandru.codeGeneration;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.type.TypeMirror;

import com.salexandru.corex.interfaces.XEntity;

public class XPropertyGenarator {
	private Map<String, XPropertyComputer> properties_;
	
	public XPropertyGenarator() {
		properties_ = new HashMap<>();
	}

	public XPropertyComputer createPropertyComputer(TypeMirror elem) {
		if (!properties_.containsKey(elem.toString())) {
			properties_.put(elem.toString(), new XPropertyComputer(elem));
		}
		return properties_.get(elem.toString());
	}

	public XPropertyComputer getPropertyComputer(TypeMirror typeMirror) {
		return properties_.get(typeMirror.toString());
	}
	
	public void generate(Filer filer) throws IOException {
		for (XPropertyComputer pc: properties_.values()) {
			Writer out = filer.createSourceFile(pc.getQualifiedName()).openWriter();
			out.write(pc.toString());
			out.flush();
			out.close();
		}
		generateImpl(filer);
		generateFactory(filer);
	}
	
	public Collection<XPropertyComputer> getPropertyComputers() {
		return properties_.values();
	}
	
	public void generateImpl(Filer filer) throws IOException {
		for (XPropertyComputer pc: properties_.values()) {
			Writer out = filer.createSourceFile(pc.getQualifiedNameImpl()).openWriter();
			out.write(pc.generateImpl());
			out.flush();
			out.close();
		}
	}
	
	public void generateFactory(Filer filer) throws IOException {
		generateLRUCache(filer);
		Writer out = filer.createSourceFile(XPropertyComputer.PACKAGE + ".factory.FactoryMethod").openWriter();
		out.write("package " + XPropertyComputer.PACKAGE + ".factory;\n\n");
		out.write("import " + XEntity.class.getCanonicalName() + ";\n");
		out.write("import " + XPropertyComputer.PACKAGE + ".*;\n");
		out.write("import " + XPropertyComputer.PACKAGE_IMPL + ".*;\n");
		out.write("\n");
		out.write("public class FactoryMethod {\n");
		out.write("   private LRUCache<XEntity, XEntity> lruCache_;\n");
		out.write("   public FactoryMethod() {\n");
		out.write("       lruCache_ = new LRUCache<XEntity, XEntity>(1000);\n");
		out.write("   }\n");
		out.write("   public FactoryMethod(int capacity) {\n");
		out.write("       lruCache_ = new LRUCache<XEntity, XEntity>(capacity);\n");
		out.write("   }\n");
		out.write("   public void clearCache() {lruCache_.clear();}\n");
		for (XPropertyComputer pc: properties_.values()) {
			out.write(String.format("   public %1$s create%1$s(%2$s obj) {\n", pc.getName(), pc.getUnderlyingType()));
			out.write("       XEntity instance = lruCache_.get(obj);\n");
			out.write("        if (null == instance) {\n");
			out.write("           instance = new " + pc.getNameImpl() + "(obj);\n");
			out.write("           lruCache_.put(instance, instance);\n");
			out.write("        }\n");
			out.write("        return (" + pc.getName() +")instance;\n");
			out.write("    }\n");
		}
		out.write("}\n");
		out.flush();
		out.close();
	}
	
	private void generateLRUCache(Filer filer) throws IOException {
		Writer out = filer.createSourceFile(XPropertyComputer.PACKAGE + ".factory.LRUCache").openWriter();
		out.write("package " + XPropertyComputer.PACKAGE + ".factory;\n\n");
		out.write("import java.util.LinkedHashMap;\n");
		out.write("import java.util.Map.Entry;\n\n");
		out.write("public class LRUCache < K, V > extends LinkedHashMap < K, V > {\n");
		out.write("    private int capacity;\n");
		out.write("   public LRUCache(int capacity) {\n");
		out.write("       super(capacity+1, 1.0f, true);\n");
		out.write("       this.capacity = capacity;\n");
		out.write("   }\n");
		out.write("   @Override");
		out.write("   protected boolean removeEldestEntry(Entry entry) {\n");
		out.write("       return (size() > this.capacity);\n");
		out.write("   }\n");
		out.write("}\n");
   
		out.flush();
		out.close();
	}
	
}
