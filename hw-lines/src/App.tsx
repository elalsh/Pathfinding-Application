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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import MapLine from "./MapLine";
import mapLine from "./MapLine";
import { MapLineProps } from "./MapLine";

interface AppState {
    lines: MapLineProps[],
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
      // TODO: store edges in this state
        lines: [],
    };
    this.update_edges = this.update_edges.bind(this);
  }
    update_edges(edges: string[]) {
      if (edges.length === 0) {
          let newState = {
              lines: [],
          }
          this.setState(newState);
      } else {

          const tempLines: MapLineProps[] = [];
          for (let i = 0; i < edges.length; i++) {
              const splitLine: string[] = edges[i].split(' ');
              tempLines.push({
                  color: splitLine[4],
                  x1: parseInt(splitLine[0]),
                  y1: parseInt(splitLine[1]),
                  x2: parseInt(splitLine[2]),
                  y2: parseInt(splitLine[3])
              });
          }
          let newState = {
              lines: tempLines
          };
          this.setState(newState);
      }


    }
  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          {/* TODO: define props in the Map component and pass them in here */}
          <Map edges={this.state.lines}/>
        </div>
        <EdgeList
          onChange={this.update_edges}
        />
      </div>
    );
  }
}

export default App;
