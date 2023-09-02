import React, {Component} from 'react';

import {Path} from "./Path";
import {MapLineProps} from "./MapLine";


interface DisplayListProps {
    onChange(edges: MapLineProps[]): void;  // called when a new path between buildings is ready

}
interface displayState {
    currentBuildings: string [][],
    from: string,
    to: string,
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class DisplayPath extends Component<DisplayListProps, displayState> {
    constructor(props: any) {
        super(props);
        this.state = {
            currentBuildings: [],
            from: "",
            to: "",
        };
        this.requestBuilding();

    }

    handleInputChangeFrom = (e:any) => {
        this.setState({from: e.target.value});
    }
    handleInputChangeTo = (e:any) => {
        this.setState({to: e.target.value});
    }

    requestBuilding = async () => {
        try {
            let response = await fetch("http://localhost:4567/get-building");
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            const building = (await response.json()) as string[];
            //const parsed: Path = await response.json() as Path;
            console.log(building);
            const set = [];
            for (let i = 0; i < building.length; i+= 2) {
                const b = [building[i], building[i+1]]
                set.push(b);
            }
            let newState = {
                from: set[0][0],
                to: set[0][0],
                currentBuildings: set,
            }
            this.setState(newState);
            console.log(this.state.currentBuildings);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }
    onDrawClick = async () => {
        // take the current value present in the text box

        try {

            const st: string = this.state.from;
            const en:  string = this.state.to;
            if (st === en) {
                alert("Pick different destination");
                return;
            }

            const input: string = "http://localhost:4567/your-route?start=" + st+ "&end="+en;
            let response = await fetch(input);

            console.log(response);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            const building = (await response.json()) as number[];

            if (building.length === 0) {
                alert("No path found was between the 2 buildings");
                return;
            }
            const set: MapLineProps[] = [];
            for (let i = 0; i < building.length; i+= 4) {
                set.push({
                    color:"red",
                    x1: building[i],
                    y1: building[i+1],
                    x2: building[i+2],
                    y2: building[i+3]
                });
            }
            this.props.onChange(set);

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }

    }

    onClearClick = () => {
        const b = this.state.currentBuildings;
        let newState = {
            from: b[0][0],
            to: b[0][0],
        }

        this.props.onChange([]);
        this.setState(newState);
    }



    render() {
        let allLines: any[] = [];
        for (let i = 0; i < this.state.currentBuildings.length; i++) {
            allLines.push(
                //<div>
                    <option value={this.state.currentBuildings[i][0]}>{this.state.currentBuildings[i][0] + " ( " + this.state.currentBuildings[i][1] + " )"}</option>
                //</div>

            )
        }
        return (

            <div id="edge-list">

                <select id = "select-option" value={this.state.from} onChange={this.handleInputChangeFrom}>
                    {allLines};
                </select>

                <select id= "select-option2" value={this.state.to} onChange={this.handleInputChangeTo}>
                    {allLines};
                </select>
                <br></br>
                <button onClick={this.onDrawClick}>Draw</button>
                <button onClick={this.onClearClick}>Clear</button>
                <br></br>
            </div>
        );
    }
}
export default DisplayPath;
