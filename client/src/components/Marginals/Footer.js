import React, {Component} from 'react';
import './css/marginals.css';

/* Renders a text footer below the application with copyright
 * and other useful information.
 */
class Footer extends Component {
  constructor(props) {
    super(props);
  }

  /**
   * Error check that the links are defined.
   * @param column within the footer.
   * @param name
   * @returns {*}
   */
  departmentColumn(column, name) {
    let content = (column === 1) ? this.departmentWidget() : this.additionalWidget();
    return (
      <div className="col-xs-12 col-sm-6 col-md-4">
        <h4 className="company-widget">
          {name}
        </h4>
        {content}
      </div>
    );
  }

  companyInformation() {
    return (
        <div className="add-company-footer">
          <div className="application-width">
            <div className="row">
              {this.departmentColumn(1, "Computer Science Department")}
              {this.departmentColumn(2, "Department")}
            </div>
          </div>
        </div>
    );
  }

  multiLineAddress(address) {
    const key = Number.MAX_SAFE_INTEGER / 2;
    let lines = [];
    if (address !== undefined) {
      lines = address.split("\n").map((line) => {
        return (
          <span key={key + Math.trunc(Math.random() * key)}>
          {line}<br/>
        </span>
        );
      });
      return (
        <li className="widget-list">
          {lines}
        </li>
      );
    }
  }

  /**
   * Render each element within the Links array
   * @param element
   * @returns {*}
   */
  parseLinks(element, index) {
    if (element !== undefined && element.url !== undefined && element.name !== undefined)
      return (
        <li className="widget-list" key={index}>
          <a className="widget-link" target="_blank" href={element.url}>{element.name}</a>
        </li>
      );
  }

  /**
   * Render the Department links.
   * @returns {*}
   */
  additionalWidget() {
    const links = [
      {"name":"Courses","url":"http://www.cs.colostate.edu/cstop/csacademics/cscourses.php"},
      {"name":"Degrees","url":"http://www.cs.colostate.edu/cstop/csacademics/csdegrees.php"},
      {"name":"Faculty & Staff","url":"http://www.cs.colostate.edu/cstop/cspeople/csfacultyandstaff.php"},
      {"name":"Prospective Students","url":"http://www.cs.colostate.edu/cstop/csprostudents.php"}
    ].map((element, index) => this.parseLinks(element, index));
    return (
      <div className="company-sub-widget">
        <ul className="widget-list">
          {links}
        </ul>
      </div>
      );
    ;
  }


  nameValue(name, value) {
    if (value !== undefined)
      return(
        <li className="widget-list"><b>{name}:</b>{' '}{value}</li>
      );
  }

  nameLink(name, value) {
    if (value !== undefined )
      return (
        <li className="widget-list"><b>{name}:</b>{' '}
          <a className="widget-link" href={"mailto:"+value}>
            {value}
          </a>
        </li>
      );
  }


  departmentWidget() {
    return (
        <div className="company-sub-widget">
          <ul className="widget-list">
            {this.multiLineAddress("279 Computer Science Building\n1100 Centre Avenue\nFort Collins, CO 80523")}
            {this.nameValue("Phone", "(970) 491-5792")}
            {this.nameValue("Fax", "(970) 491-2466")}
            {this.nameLink("Email", "info@cs.colostate.edu")}
          </ul>
        </div>
    );
  }

  departmentName() {
    return ("Computer Science");
  }

  footerCopyright() {
    let year = new Date().getFullYear();
    return (
        <div>
            <ul>
              <li><a href="http://admissions.colostate.edu" className="company-sub-widget">Apply to CSU</a></li>
              <li><a href="http://www.colostate.edu/info-contact.aspx" className="company-sub-widget">Contact CSU</a></li>
              <li><a href="http://www.colostate.edu/info-disclaimer.aspx" className="company-sub-widget">Disclaimer</a></li>
              <li><a href="http://www.colostate.edu/info-equalop.aspx" className="company-sub-widget">Equal Opportunity</a></li>
              <li><a href="http://www.colostate.edu/info-privacy.aspx" className="company-sub-widget">Privacy Statement</a></li>
              <br/>
              <li><p>© {year} Colorado State University - Computer Science Department, Fort Collins, CO 80523</p></li>
            </ul>
        </div>
    );
  }

  render() {
    return (
        <div>

        </div>
    )
  }
}

export default Footer;