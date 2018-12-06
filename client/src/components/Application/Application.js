import React, {Component} from 'react';
import classnames from 'classnames';
import {Container, Collapse, Button, TabContent, TabPane, Nav, NavItem, NavLink, CardTitle, CardText, Row} from 'reactstrap';
import {get_poem} from '../../api/api';



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
                poem: "",
                title: "",
                year: ""
            },
            activeTab: '1',
            collapse: false
        };
        this.toggle = this.toggle.bind(this);
        this.writePoem = this.writePoem.bind(this);
        // this.formatPoem = this.formatPoem.bind(this);
    }

    toggle() {
        this.setState({ collapse: !this.state.collapse });
    }
    toggleTab(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
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
                <React.Fragment key={i}>
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

                <Nav tabs>
                    <Row>
                    <NavItem>
                        <NavLink
                            style={{'cursor': 'pointer'}}
                            className={classnames({ active: this.state.activeTab === '1' })}
                            onClick={() => { this.toggleTab('1'); }}
                        >
                            PoBot
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink
                            style={{'cursor': 'pointer'}}
                            className={classnames({ active: this.state.activeTab === '2' })}
                            onClick={() => { this.toggleTab('2'); }}
                        >
                            Designer Statement
                        </NavLink>
                    </NavItem>
                        &nbsp;
                        <a href={"https://github.com/amrictor/PoetryBot"}><img src={"https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png"} height={'32'}/></a>
                </Row>
                </Nav>

                <TabContent activeTab={this.state.activeTab}>
                    <TabPane tabId="1">
                        <br/>
                        <Button color="primary" onClick={this.writePoem} style={{ marginBottom: '1rem' }}>Write me a poem!</Button>
                        <Collapse isOpen={this.state.collapse}>
                            {this.formatPoem()}
                        </Collapse>
                    </TabPane>
                        <TabPane tabId="2">
                            <br/>
                            <CardTitle>Special Title Treatment</CardTitle>
                            <CardText>With supporting text below as a natural lead-in to additional content.</CardText>

                        </TabPane>
                </TabContent>


            </Container>
        )
    }
}

export default Application;
