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

import { MapLineProps} from "./MapLine";
import React, {Component} from 'react';

import { errorMessage} from "./Constants";

interface EdgeListProps {
    onChange(edges: string[]): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}
interface edgeState {
    value: string
    errorIndex: number
    errorRow: number
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, edgeState> {
    constructor(props: any) {
        super(props);
        this.state = {
            value: "",
            errorIndex: 0,
            errorRow: 0.
        };
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange = (event: any) => {
        this.setState({
            value: event.target.value
        });
    }
    onDrawClick = () => {
        // take the current value present in the text box
        const tempVal = this.state.value.trimEnd();

        // take each row
        const split = tempVal.split(/\r?\n/);

        let flag: boolean = false;
        let errorInd: number = 0;
        let errorR: number = 0;

        // store all the valid strings
        const validStrings: string[] = [];

        for (let i = 0; i < split.length; i++) {
            // for each edge:
            const splitLine = split[i].trim().split(' ');
            if (splitLine.length == 5) {
                const condTwo: boolean = !isNaN(Number(splitLine[0])) && !isNaN(Number(splitLine[1])) && !isNaN(Number(splitLine[2]))
                         && !isNaN(Number(splitLine[3])) && this.isLetter(splitLine[4])
                if (!condTwo) {
                    if  (!flag) {
                        flag = true;
                        errorInd = 3;
                        errorR = i;
                    }
                }
                const values: number[] = [parseInt(splitLine[0]), parseInt(splitLine[1]), parseInt(splitLine[2]), parseInt(splitLine[0])];
                const condThree: boolean = this.isAllValid(values);
                if (!condThree) {
                    if  (!flag) {
                        flag = true;
                        errorInd = 1;
                        errorR = i;
                    }
                }
                if (condTwo && condThree) {
                    validStrings.push(split[i].trim());
                }
            } else {
                if  (!flag) {
                    flag = true;
                    errorInd = 2;
                    errorR = i;
                }
            }
        }

        this.props.onChange(validStrings);
        const val: string = this.state.value;
             if (!flag) {
                let newState = {
                    value: val,
                    errorIndex: 0,
                    errorRow: 0,
                }
                this.setState(newState);
             } else {
                 let newState = {
                     value: val,
                     errorIndex: errorInd,
                     errorRow: errorR,
                 }
                 this.setState(newState);
             }
    }
    isLetter(char:string) {
        return /^[a-zA-Z]+$/.test(char);
    }
    isAllValid(values: number[]) {
        return values[0] <= 4000 && values[0] >= 0 && values[1] <= 4000 && values[1] >= 0 && values[2] <= 4000 && values[2] >= 0
        && values[3] <= 4000 && values[3] >= 0
    }
     onClearClick = () => {
        let newState = {
            value: '',
            errorIndex: 0,
            errorRow: 0,
        }
         const tempLines: string[] = [];
         this.props.onChange(tempLines);
         this.setState(newState);
     }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.handleInputChange}
                    value={this.state.value}
                /> <br/>
                <button onClick={this.onDrawClick}>Draw</button>
                <button onClick={this.onClearClick}>Clear</button>
                <br></br>
                {this.state.errorIndex != 0 ? errorMessage[this.state.errorIndex-1] + ' @ Row ' + (this.state.errorRow +1): ''}
            </div>
        );
    }
}

export default EdgeList;
