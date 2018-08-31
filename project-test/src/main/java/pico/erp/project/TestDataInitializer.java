package pico.erp.project;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.project.ProjectRequests.CreateRequest;
import pico.erp.shared.ApplicationInitializer;

@Transactional
@Configuration
@Profile({"!development", "!production"})
public class TestDataInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  private ProjectService projectService;


  @Autowired
  private DataProperties dataProperties;

  @Override
  public void initialize() {
    dataProperties.projects.forEach(projectService::create);
  }

  @Data
  @Configuration
  @ConfigurationProperties("data")
  public static class DataProperties {

    List<CreateRequest> projects = new LinkedList<>();

  }

}
