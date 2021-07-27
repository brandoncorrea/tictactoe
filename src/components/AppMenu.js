import React, { Component } from 'react'
import { Menu, Container } from 'semantic-ui-react'
import { MenuOptions } from '../enums/MenuOptions'
import Settings from './Settings';
import Score from './Score';
import Game from './Game';
import GameBoard from '../game/GameBoard';
import SettingsRepository from '../data/SettingsRepository';

export default class AppMenu extends Component {
  settings = new SettingsRepository();

  constructor(props) {
    super(props);
    this.state = {
      activeItem: MenuOptions.Score,
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
          <Menu.Item
            name={MenuOptions.Score}
            active={activeItem === MenuOptions.Score}
            onClick={this.handleItemClick}
          />
          <Menu.Item
            name={MenuOptions.Game}
            active={activeItem === MenuOptions.Game}
            onClick={this.handleItemClick}
          />
          <Menu.Item
            name={MenuOptions.Settings}
            active={activeItem === MenuOptions.Settings}
            onClick={this.handleItemClick}
          />
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
