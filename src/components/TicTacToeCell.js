import { Component } from "react";
import { Table } from "semantic-ui-react";

export default class TicTacToeCell extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: props.content,
      style: {
        cursor: 'pointer', 
        height: props.height
      }
    }
  }

  render = () =>
    <Table.Cell
      selectable
      style={this.state.style}
      content={this.state.content} />
}