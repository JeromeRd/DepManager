package com.dm.writer;


import com.dm.common.ProfilesReferential;
import com.dm.converter.ProfileConverter;
import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.reader.ProfilesReader;
import com.dm.serializer.CommandSerializer;
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
public class ProfilesWriter {

    private static void write(List<Profile> profiles) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(ProfilesReferential.PROFILES_FILE_NAME);
        try {
            File file = new File(url.toURI());
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(file);
            Element root = document.getRootElement();
            root.removeContent();
            for (Profile profile : profiles) {
                Element element = new Element(ProfilesReferential.PROFILE_ELEMENT_NAME);
                element.setAttribute(ProfilesReferential.UUID_ATTRIBUTE_NAME, profile.getId().toString());
                if (profile.getCommand() != null) {
                    element.setAttribute(ProfilesReferential.COMMAND_ATTRIBUTE_NAME, CommandSerializer.serialize(profile.getCommand()));
                }
                if (profile.getSubProject() != null) {
                    element.setAttribute(ProfilesReferential.SUBPROJECT_ATTRIBUTE_NAME, profile.getSubProject());
                }
                if (profile.getStrategy() != null) {
                    element.setAttribute(ProfilesReferential.STRATEGY_ATTRIBUTE_NAME, profile.getStrategy().getLabel());
                }
                root.addContent(element);
            }
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            //output xml to console for debugging
            //xmlOutputter.output(doc, System.out);
            xmlOutputter.output(document, new FileWriter(file));
        } catch (URISyntaxException | JDOMException | IOException e) {
            System.out.println("An error occurred when reading "+ProfilesReferential.PROFILES_FILE_NAME +" file : " + e.getMessage());
        }
    }

    public static void overwrite(Profile profile) {
        List<Profile> profiles = new ArrayList<>();
        boolean hasBeenOverwritten = false;
        for (ProfileProxy profileProxy : ProfilesReader.read()) {
            if (profileProxy.getProfileId().equals(profile.getId())) {
                profiles.add(profile);
                hasBeenOverwritten = true;
            } else {
                profiles.add(ProfileConverter.convertToProfile(profile, profileProxy));
            }
        }
        if (!hasBeenOverwritten) {
            profiles.add(profile);
        }
        write(profiles);
    }
}
