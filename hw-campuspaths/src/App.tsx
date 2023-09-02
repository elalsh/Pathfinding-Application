/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import DisplayPath from "./DisplayPath";
import Map from "./Map";
// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import {MapLineProps} from "./MapLine";
import {xToLon} from "./MapLine"
import {yToLat} from "./MapLine"
import {LatLngExpression} from "leaflet";
interface AppState {
    lines: MapLineProps[];
}

class App extends Component<{}, AppState> {
    constructor(props: any) {
        super(props);
        this.state = {
            // TODO: store edges in this state
            lines: [],
        };
    }
    componentDidMount() {
        document.body.style.backgroundColor = "bisque"
    }
    handleChange = (edges: MapLineProps[]) => {
        if (edges.length === 0) {
            this.setState({
                lines:edges,
            })
        } else {
            const num = edges[0].x1 + edges[edges.length-1].x2;
            const n = edges[0].y1 + edges[edges.length-1].y2;
            console.log(num/2 + "  " + n/2);
            const pos: LatLngExpression = [xToLon((num/2)), yToLat((n/2))];
            console.log(pos);
            this.setState({
                lines: edges,
            })
        }
    }
    render() {
        return (

            <div>
                <div>
                    <DisplayPath onChange={this.handleChange}/>
                    <Map edges={this.state.lines} ></Map>
                </div>
            </div>
        );
    }

}

export default App;
