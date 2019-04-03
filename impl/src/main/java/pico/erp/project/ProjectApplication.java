package pico.erp.project;

import java.util.HashMap;
import kkojaeh.spring.boot.component.ComponentBean;
import kkojaeh.spring.boot.component.SpringBootComponent;
import kkojaeh.spring.boot.component.SpringBootComponentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.util.UriTemplate;
import pico.erp.ComponentDefinition;
import pico.erp.attachment.category.AttachmentCategory;
import pico.erp.attachment.category.AttachmentCategory.AttachmentCategoryImpl;
import pico.erp.attachment.category.AttachmentCategoryId;
import pico.erp.comment.subject.type.CommentSubjectType;
import pico.erp.comment.subject.type.CommentSubjectType.CommentSubjectTypeImpl;
import pico.erp.comment.subject.type.CommentSubjectTypeId;
import pico.erp.project.ProjectApi.Roles;
import pico.erp.shared.SharedConfiguration;
import pico.erp.shared.data.Role;

@Slf4j
@SpringBootComponent("project")
@EntityScan
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
@Import(value = {
  SharedConfiguration.class
})
public class ProjectApplication implements ComponentDefinition {

  public static void main(String[] args) {
    new SpringBootComponentBuilder()
      .component(ProjectApplication.class)
      .run(args);
  }

  @Bean
  @ComponentBean(host = false)
  public AttachmentCategory attachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("project"), "프로젝트");
  }

  @Bean
  @ComponentBean(host = false)
  public CommentSubjectType commentSubjectType(
    final @Value("${comment.uri.project}") String template) {
    return new CommentSubjectTypeImpl(
      CommentSubjectTypeId.from("project"),
      info -> new UriTemplate(template).expand(new HashMap<String, String>() {
        {
          put("subjectId", info.getSubjectId().getValue());
          put("commentId", info.getId().getValue().toString());
        }
      })
    );
  }

  @Override
  public Class<?> getComponentClass() {
    return ProjectApplication.class;
  }


  @Bean
  @ComponentBean(host = false)
  public Role projectAccessorRole() {
    return Roles.PROJECT_ACCESSOR;
  }

  @Bean
  @ComponentBean(host = false)
  public Role projectManagerRole() {
    return Roles.PROJECT_MANAGER;
  }

}
