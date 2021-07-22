import React, { Component } from 'react'
import { Menu, Segment } from 'semantic-ui-react'
import TicTacToeTable from './TicTacToeTable'
import { MenuOptions } from '../enums/MenuOptions'
import GameBoard from '../game/GameBoard';
import Settings from './Settings';

export default class AppMenu extends Component {
  constructor(props) {
    super(props);
    this.state = { 
      activeItem: MenuOptions.NewGame,
      game: new GameBoard()
    }
  }

  handleItemClick = (e, target) => 
    this.setState({ activeItem: target.name })

  render() {
    const activeItem = this.state.activeItem;
    return (
      <div>
        <Menu pointing secondary>
          <Menu.Item
            name={MenuOptions.NewGame}
            active={activeItem === MenuOptions.NewGame}
            onClick={this.handleItemClick}
          />
          <Menu.Item
            name={MenuOptions.Settings}
            active={activeItem === MenuOptions.Settings}
            onClick={this.handleItemClick}
          />
        </Menu>

        <Segment>
          {
            activeItem === MenuOptions.NewGame 
              ? <TicTacToeTable table={this.state.game.getTable()}/>
            : activeItem == MenuOptions.Settings
              ? <Settings />
            : <></>
          }
        </Segment>
      </div>
    )
  }
}
