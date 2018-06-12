package com.dm.writer;

import com.dm.common.ProjectsReferential;
import com.dm.datamodel.Project;
import com.dm.reader.ProjectsReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ProjectsWriter {

    /**
     * Write projects
     * @param projects
     * @param overwrite (existing projects are removed)
     */
    public static void write(List<Project> projects, boolean overwrite) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(ProjectsReferential.PROJECTS_FILE_NAME);
        try {
            File file = new File(url.toURI());
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(file);
            Element root = document.getRootElement();
            if (overwrite) {
                root.removeContent();
            }
            for (Project project : projects) {
                Element element = new Element(ProjectsReferential.PROJECT_ELEMENT_NAME);
                element.setAttribute(ProjectsReferential.UUID_ATTRIBUTE_NAME, project.getUuid().toString());
                element.setAttribute(ProjectsReferential.NAME_ATTRIBUTE_NAME, project.getName());
                if (project.getProfileId() != null) {
                    element.setAttribute(ProjectsReferential.PROFILE_ATTRIBUTE_NAME, project.getProfileId().toString());
                }
                if (project.getState() != null) {
                    element.setAttribute(ProjectsReferential.STATE_ATTRIBUTE_NAME, project.getState().name());
                }
                if (project.getPath() != null) {
                    element.setAttribute(ProjectsReferential.PATH_ATTRIBUTE_NAME, project.getPath());
                }
                root.addContent(element);
            }
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            //output xml to console for debugging
            //xmlOutputter.output(doc, System.out);
            xmlOutputter.output(document, new FileWriter(file));
        } catch (URISyntaxException | JDOMException | IOException e) {
            System.out.println("An error occurred when reading "+ProjectsReferential.PROJECTS_FILE_NAME +" file : " + e.getMessage());
        }
    }

    public static void overwrite(Project project) {
        List<Project> projects = new ArrayList<>();
        boolean hasBeenOverwritten = false;
        for (Project p : ProjectsReader.read()) {
            if (p.equals(project)) {
                projects.add(project);
                hasBeenOverwritten = true;
            } else {
                projects.add(p);
            }
        }
        if (!hasBeenOverwritten) {
            projects.add(project);
        }
        write(projects, true);
    }
}
