import { Component } from "react";
import { Container, Table } from "semantic-ui-react";
import Guid from "../data/Guid";
import GameBoard from "../game/GameBoard";
import TicTacToeRow from "./TicTacToeRow";

export default class TicTacToeTable extends Component {
  row = 0;
  constructor(props) {
    super(props);
    this.state = {
      table: props.table,
      rowHeight: '100px',
      game: new GameBoard(),
    }
    
    this.state.style = {
      maxWidth: `${this.state.table.length * 100}px`, 
      fontSize: '32px',
      margin: '0 auto',
      textAlign: 'center'
    }
  }

  render = () =>
    <Container>
      <Table fixed celled style={this.state.style}>
        <Table.Body>
          {
            this.state.table.map(row =>
              <TicTacToeRow
                key={Guid.newGuid()}
                game={this.state.game}
                row={this.row++}
                data={row}
                height={this.state.rowHeight} />)
          }
        </Table.Body>
      </Table>
    </Container>

}