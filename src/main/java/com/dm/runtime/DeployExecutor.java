package com.dm.runtime;

import com.dm.converter.ProfileConverter;
import com.dm.datamodel.Profile;
import com.dm.datamodel.ProfileProxy;
import com.dm.datamodel.Project;
import com.dm.exception.ProfileNotFoundException;
import com.dm.reader.ProfilesReader;
import com.dm.visitor.BuildExecutorVisitor;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jrichard on 12/07/2017.
 */
public class DeployExecutor {

    public void execute(Project project) {
        if (project != null) {
            System.out.println("Deploy project ["+project+"]");

            DefaultDockerClientConfig config
                    = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withRegistryEmail("info@baeldung.com")
                    .withRegistryPassword("baeldung")
                    .withRegistryUsername("baeldung")
                    .withDockerCertPath("/home/baeldung/.docker/certs")
                    .withDockerConfig("/home/baeldung/.docker/")
                    .withDockerTlsVerify("1")
                    .withDockerHost("tcp://docker.baeldung.com:2376").build();

            DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

            final com.spotify.docker.client.DockerClient docker;
            try {
                docker = DefaultDockerClient.fromEnv().build();
                List<com.spotify.docker.client.messages.Container> containerInfoList = docker.listContainers();
            }
            catch (DockerCertificateException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (DockerException e) {
                e.printStackTrace();
            }


            /*DockerClient dockerClient = DockerClientBuilder.getInstance().build();

            String containerName = "dockercomposeelk_pfm_1";
            Container container = null;

            //Get the docker id
            List<Container> containers = dockerClient.listContainersCmd().exec();
            for (Container c : containers) {
                if (Arrays.asList(c.getNames()).contains(containerName)) {
                    container = c;
                }
            }

            //Stop docker
            dockerClient.stopContainerCmd(container.getId());*/

            //Build project
            try {
                ProfileProxy profileProxy = ProfilesReader.find(project.getProfileId());
                Profile profile = project.createProfile();
                ProfileConverter.convertToProfile(profile, profileProxy);
                profile.execute(new BuildExecutorVisitor());
            } catch (ProfileNotFoundException ex) {
                System.out.println("Profile not found with id : " + project.getProfileId());
            }

            //Copy secret key
            CommandExecutor commandExecutor = new CommandExecutor();
            commandExecutor.executeCommand(new String[]{"docker cp C:/Users/jerome-r/git/repositories/app/docker/pfm/resources/linxo.key dockercomposeelk_pfm_1:/opt/linxo/pfm/conf"});

            //Start docker
            /*dockerClient.startContainerCmd(container.getId());*/
        }
    }
}
