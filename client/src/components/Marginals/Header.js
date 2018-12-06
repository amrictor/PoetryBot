import React, {Component} from 'react';
import './css/marginals.css';
import Navigation from "./Navigation";

/* Renders a text heading above the application with useful information.
 */
class Header extends Component{
  constructor(props) {
    super(props);
  }


  /**
   *
   * @returns {XML}
   */
  render() {
    return(
      <div>
        <Navigation/>
        <div className="add-title"/> {/* Background box to header */}
      </div>
    );
  }

}

export default Header;