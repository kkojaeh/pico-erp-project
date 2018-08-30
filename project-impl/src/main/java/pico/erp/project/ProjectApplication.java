package pico.erp.project;

import java.util.HashMap;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.util.UriTemplate;
import pico.erp.attachment.data.AttachmentCategory;
import pico.erp.attachment.data.AttachmentCategoryId;
import pico.erp.attachment.impl.AttachmentCategoryImpl;
import pico.erp.audit.data.AuditConfiguration;
import pico.erp.comment.data.CommentSubjectType;
import pico.erp.comment.data.CommentSubjectType.CommentSubjectTypeImpl;
import pico.erp.comment.data.CommentSubjectTypeId;
import pico.erp.shared.ApplicationStarter;
import pico.erp.shared.Public;
import pico.erp.shared.SpringBootConfigs;
import pico.erp.shared.data.Contact;
import pico.erp.shared.data.Role;
import pico.erp.shared.impl.ApplicationImpl;

@Slf4j
@SpringBootConfigs
public class ProjectApplication implements ApplicationStarter {

  public static final String CONFIG_NAME = "project/application";

  public static final String CONFIG_NAME_PROPERTY = "spring.config.name=project/application";

  public static final Properties DEFAULT_PROPERTIES = new Properties();

  static {
    DEFAULT_PROPERTIES.put("spring.config.name", CONFIG_NAME);
  }

  public static SpringApplication application() {
    return new SpringApplicationBuilder(ProjectApplication.class)
      .properties(DEFAULT_PROPERTIES)
      .web(false)
      .build();
  }

  public static void main(String[] args) {
    application().run(args);
  }

  @Override
  public int getOrder() {
    return 3;
  }

  @Public
  @Bean
  public AttachmentCategory attachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("project"), "프로젝트");
  }

  @Bean
  @Public
  public CommentSubjectType commentSubjectType(
    final @Value("${comment.uri.project}") String template) {
    return new CommentSubjectTypeImpl(
      CommentSubjectTypeId.from("project"),
      info -> new UriTemplate(template).expand(new HashMap<String, String>() {
        {
          put("subjectId", info.getSubjectId().getValue());
          put("commentId", info.getId().getValue());
        }
      })
    );
  }

  @Override
  public boolean isWeb() {
    return false;
  }

  @Bean
  @Public
  public Role projectManagerRole() {
    return ROLE.PROJECT_MANAGER;
  }

  @Bean
  @Public
  public AuditConfiguration auditConfiguration() {
    return AuditConfiguration.builder()
      .packageToScan("pico.erp.project")
      .entity(ROLE.class)
      .valueObject(Contact.class)
      .build();
  }

  @Override
  public pico.erp.shared.Application start(String... args) {
    return new ApplicationImpl(application().run(args));
  }
}
