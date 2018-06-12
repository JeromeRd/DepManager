package com.dm.reader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 28/02/2018.
 */
public class PomReader {

    private String artifactId;
    private String version;
    private List<String> modules;
    private final String ARTIFACT_ID_ELEMENT = "artifactId";
    private final String VERSION_ELEMENT = "version";
    private final String MODULES_ELEMENT = "modules";
    private final String SUBMODULE_ELEMENT = "module";

    public PomReader(String path) {
        modules = new ArrayList<>();

        //Convert to a JDOM tree
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(path);

        try {
            Document document = (Document) builder.build(xmlFile);
            Element root = document.getRootElement();

            //Parse artifact id
            artifactId = processSimpleElement(root, ARTIFACT_ID_ELEMENT, path);

            //Parse version
            version = processSimpleElement(root, VERSION_ELEMENT, path);

            //Parse modules
            processModules(root.getChild(MODULES_ELEMENT, root.getNamespace()),path);
        } catch (JDOMException | IOException e) {
            System.out.println("Cannot parse file:" + path);
        }
    }

    private String processSimpleElement(Element element, String childName, String path) {
        Element child = element.getChild(childName, element.getNamespace());
        if (child == null) {
            System.out.println("No "+childName+" element found in file " + path);
            return null;
        } else {
            return element.getText();
        }
    }

    private void processModules(Element parentModule, String path) {
        if (parentModule == null) {
            System.out.println("No "+MODULES_ELEMENT+" element found in file " + path);
            return;
        }
        List<Element> moduleList = parentModule.getChildren(SUBMODULE_ELEMENT, parentModule.getNamespace());
        for (Element module : moduleList) {
            modules.add(module.getText());
        }
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getModules() {
        return modules;
    }
}

