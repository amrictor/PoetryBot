import React, {Component} from 'react';
import {Container, Collapse, Button} from 'reactstrap';
import {get_poem} from '../../api/api';

import { TabContent, TabPane, Nav, NavItem, NavLink } from 'reactstrap';
import classnames from 'classnames';
// constructor(props) {
//     super(props);
//     this.state = {
//         trip: {
//             type: "trip",
//             version: 4,
//             title: "My Trip",
//             options: {
//                 units: "miles",
//                 unitName: "",
//                 unitRadius: 0,
//                 optimization: "none",
//                 map: "svg"
//             },
//             places: [],
//             distances: [],
//             map: null
//         },
//         poem: {
//             author: "",
//             classification: "",
//             keywords: "",
//             period: "",
//             reference: "",
//             region: "",
//             text: "",
//             title: "",
//             year: ""
//         },
//         collapse: false
//     };
//     this.toggle = this.toggle.bind(this);
//     this.writePoem = this.writePoem.bind(this);
// }



/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
    constructor(props) {
        super(props);
        this.state = {
            poem: {
                author: "",
                classification: "",
                keywords: "",
                period: "",
                reference: "",
                region: "",
                text: "",
                title: "",
                year: ""
            },
            
            collapse: false
        };
        this.toggle = this.toggle.bind(this);
        this.writePoem = this.writePoem.bind(this);
        this.formatPoem = this.formatPoem.bind(this);
    }

    toggle() {
        this.setState({ collapse: !this.state.collapse });
    }

    writePoem() {
        get_poem().then(
            poem => {
                this.setState({
                    poem: poem
                })
            }
        );
        this.setState({ collapse: true });
    }

    formatPoem() {
        let data = [];
        for(let i = 0; i<this.state.poem.text.length; i++) {
            data.push(
                <React.Fragment>
                    {this.state.poem.text[i]}
                    <br/>
                </React.Fragment>
            );
        }
        return data;
    }


    render() {
        return (
            <Container>
                <Button color="primary" onClick={this.writePoem} style={{ marginBottom: '1rem' }}>Write me a poem!</Button>
                <Collapse isOpen={this.state.collapse}>
                    {this.formatPoem()}


                </Collapse>
            </Container>
        )
    }
}

export default Application;
