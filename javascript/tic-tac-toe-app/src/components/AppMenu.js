import React, { Component } from 'react'
import { Menu, Container } from 'semantic-ui-react'
import { MenuOptions } from '../enums/MenuOptions'
import Settings from './Settings';
import Score from './Score';
import Game from './Game';
import GameBoard from '../game/GameBoard';
import SettingsRepository from '../data/SettingsRepository';
import Guid from '../data/Guid';

export default class AppMenu extends Component {
  settings = new SettingsRepository();
  menuOptions = [
    MenuOptions.Score,
    MenuOptions.Game,
    MenuOptions.Settings
  ]

  constructor(props) {
    super(props);
    this.state = {
      activeItem: this.menuOptions[0],
      game: new GameBoard(this.settings.getTableSize())
    }
  }

  handleItemClick = (e, target) => 
    this.setState({ activeItem: target.name })

  render() {
    const activeItem = this.state.activeItem;
    return (
      <div>
        <Menu pointing secondary>
          {
            this.menuOptions.map(option => 
              <Menu.Item
                key={Guid.newGuid()}
                name={option}
                active={activeItem === option}
                onClick={this.handleItemClick}
              />)
          }
        </Menu>

        <Container>
          {
            activeItem === MenuOptions.Game 
              ? <Game game={this.state.game} />
            : activeItem === MenuOptions.Settings
              ? <Settings />
            : activeItem === MenuOptions.Score
              ? <Score />
            : <></>
          }
        </Container>
      </div>
    )
  }
}
