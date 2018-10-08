package pico.erp.project

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.CompanyId
import pico.erp.shared.IntegrationConfiguration
import pico.erp.shared.data.Contact
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProjectQuerySpec extends Specification {

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

  @Autowired
  ProjectQuery projectQuery

  @Autowired
  ProjectService projectService

  def "조회 조건에 맞게 조회"() {
    expect:
    def page = projectQuery.retrieve(condition, pageable)
    page.totalElements == totalElements

    where:
    condition                                                | pageable               || totalElements
    new ProjectView.Filter(name: "프로젝트1")                    | new PageRequest(0, 10) || 2
    // new ProjectView.Filter(customerId: "company") | new PageRequest(0, 10) || 2
    // new ProjectView.Filter(managerId: "user")   | new PageRequest(0, 10) || 2
    new ProjectView.Filter(customerManagerName: "고객 회사 담당자") | new PageRequest(0, 10) || 2
  }

}
