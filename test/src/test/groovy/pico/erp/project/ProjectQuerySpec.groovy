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
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProjectQuerySpec extends Specification {

  @Autowired
  ProjectQuery projectQuery


  def "조회 조건에 맞게 조회"() {
    expect:
    def page = projectQuery.retrieve(condition, pageable)
    page.totalElements == totalElements

    where:
    condition                                                   | pageable               || totalElements
    new ProjectView.Filter(name: "프로젝트1")                       | new PageRequest(0, 10) || 1
    new ProjectView.Filter(customerId: CompanyId.from("CUST1")) | new PageRequest(0, 10) || 2
    new ProjectView.Filter(managerId: UserId.from("ysh"))       | new PageRequest(0, 10) || 3
    new ProjectView.Filter(customerManagerName: "고객2 회사 담당자")   | new PageRequest(0, 10) || 1
  }

  def "라벨 생성 확인"() {
    when:
    def result = projectQuery.asLabels("", 10)
    then:
    result.size() == 3
  }

}
