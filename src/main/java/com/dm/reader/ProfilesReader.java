package com.dm.reader;

import com.dm.common.ProfilesReferential;
import com.dm.datamodel.ProfileProxy;
import com.dm.exception.ProfileNotFoundException;
import com.dm.serializer.CommandSerializer;
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
public class ProfilesReader {

    public static List<ProfileProxy> read() {
        List<ProfileProxy> result = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(ProfilesReferential.PROFILES_FILE_NAME);
        try {
            File file = new File(url.toURI());
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(file);
            Element root = document.getRootElement();
            for (Element profileElement : root.getChildren()) {
                Attribute uuidAttribute = profileElement.getAttribute(ProfilesReferential.UUID_ATTRIBUTE_NAME);
                if (uuidAttribute != null) {


                    ProfileProxy profileProxy = new ProfileProxy(UUID.fromString(uuidAttribute.getValue()));
                    Attribute commandAttribute = profileElement.getAttribute(ProfilesReferential.COMMAND_ATTRIBUTE_NAME);
                    if (commandAttribute != null) {
                        profileProxy.addProperty(ProfilesReferential.COMMAND_ATTRIBUTE_NAME, CommandSerializer.deserializer(commandAttribute.getValue()));
                    }
                    Attribute subProjectAttribute = profileElement.getAttribute(ProfilesReferential.SUBPROJECT_ATTRIBUTE_NAME);
                    if (subProjectAttribute != null) {
                        profileProxy.addProperty(ProfilesReferential.SUBPROJECT_ATTRIBUTE_NAME, subProjectAttribute.getValue());
                    }
                    Attribute strategyAttribute = profileElement.getAttribute(ProfilesReferential.STRATEGY_ATTRIBUTE_NAME);
                    if (strategyAttribute != null) {
                        profileProxy.addProperty(ProfilesReferential.STRATEGY_ATTRIBUTE_NAME, strategyAttribute.getValue());
                        //(new StrategyDispatcher()).dispatch(profileProxy, ProjectStrategy.findByLabel(strategyAttribute.getValue()));
                    }
                    result.add(profileProxy);
                }
            }
        } catch (URISyntaxException | JDOMException | IOException e) {
            System.out.println("An error occurred when reading "+ ProfilesReferential.PROFILES_FILE_NAME +" file : " + e.getMessage());
        }
        return result;
    }

    public static ProfileProxy find(UUID profileId) throws ProfileNotFoundException {
        for (ProfileProxy profileProxy : read()) {
            if (profileProxy.getProfileId().equals(profileId)) {
                return profileProxy;
            }
        }
        throw new ProfileNotFoundException();
    }
}
