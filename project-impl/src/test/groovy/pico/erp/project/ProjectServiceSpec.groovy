package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.data.CompanyId
import pico.erp.project.data.ProjectId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Contact
import pico.erp.user.data.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProjectServiceSpec extends Specification {

  @Autowired
  ProjectService projectService

  def setup() {
    projectService.create(new ProjectRequests.CreateRequest(id: ProjectId.from("P1"), name: "프로젝트1",
      customerId: CompanyId.from("CUST2"),
      managerId: UserId.from("ysh"),
      customerManagerContact: new Contact(name: "고객 회사 담당자", email: "manager@company.com", telephoneNumber: "+821011111111", mobilePhoneNumber: "+821011111111", faxNumber: "+821011111111")))
    projectService.create(new ProjectRequests.CreateRequest(id: ProjectId.from("P2"), name: "프로젝트2",
      customerId: CompanyId.from("CUST2"),
      managerId: UserId.from("ysh"),
      customerManagerContact: new Contact(name: "고객 회사 담당자", email: "manager@company.com", telephoneNumber: "+821011111111", mobilePhoneNumber: "+821011111111", faxNumber: "+821011111111")))
  }

  def "아이디로 존재하는 회사 확인"() {
    when:
    def exists = projectService.exists(ProjectId.from("P1"))

    then:
    exists == true
  }

  def "아이디로 존재하지 않는 회사 확인"() {
    when:
    def exists = projectService.exists(ProjectId.from("!P1"))

    then:
    exists == false
  }

  def "아이디로 존재하는 회사를 조회"() {
    when:
    def project = projectService.get(ProjectId.from("P1"))

    then:
    project.id.value == "P1"
    project.name == "프로젝트1"
  }

  def "아이디로 존재하지 않는 회사를 조회"() {
    when:
    projectService.get(ProjectId.from("!P1"))

    then:
    thrown(ProjectExceptions.NotFoundException)
  }

}
