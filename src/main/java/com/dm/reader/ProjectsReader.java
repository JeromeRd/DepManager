package com.dm.reader;

import com.dm.common.ProjectsReferential;
import com.dm.datamodel.Project;
import com.dm.exception.ProjectNotFoundException;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jrichard on 30/06/2017.
 */
public class ProjectsReader {

    public static List<Project> read() {
        List<Project> result = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(ProjectsReferential.PROJECTS_FILE_NAME);
        try {
            File file = new File(url.toURI());
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(file);
            Element root = document.getRootElement();
            for (Element projectElement : root.getChildren()) {
                Attribute uuidAttribute = projectElement.getAttribute(ProjectsReferential.UUID_ATTRIBUTE_NAME);
                Attribute nameAttribute = projectElement.getAttribute(ProjectsReferential.NAME_ATTRIBUTE_NAME);
                if (nameAttribute != null) {
                    Project project = new Project(UUID.fromString(uuidAttribute.getValue()), nameAttribute.getValue());
                    result.add(project);
                    Attribute profileAttribute = projectElement.getAttribute(ProjectsReferential.PROFILE_ATTRIBUTE_NAME);
                    if (profileAttribute != null) {
                        project.setProfileId(UUID.fromString(profileAttribute.getValue()));
                    }
                    Attribute stateAttribute = projectElement.getAttribute(ProjectsReferential.STATE_ATTRIBUTE_NAME);
                    if (stateAttribute != null) {
                        project.setState(Project.ProjectState.valueOf(stateAttribute.getValue()));
                    }
                    Attribute pathAttribute = projectElement.getAttribute(ProjectsReferential.PATH_ATTRIBUTE_NAME);
                    if (pathAttribute != null) {
                        project.setPath(pathAttribute.getValue());
                    }
                }
            }
        } catch (URISyntaxException | JDOMException | IOException e) {
            System.out.println("An error occured when reading "+ProjectsReferential.PROJECTS_FILE_NAME +" file : " + e.getMessage());
        }
        return result;
    }

    public static Project find(UUID projectId) throws ProjectNotFoundException {
        for (Project project : read()) {
            if (project.getUuid().equals(projectId)) {
                return project;
            }
        }
        throw new ProjectNotFoundException();
    }
}
