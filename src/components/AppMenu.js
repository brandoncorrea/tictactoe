import React, { Component } from 'react'
import { Menu, Segment } from 'semantic-ui-react'
import { MenuOptions } from '../enums/MenuOptions'
import Settings from './Settings';
import Score from './Score';
import Game from './Game';

export default class AppMenu extends Component {
  constructor(props) {
    super(props);
    this.state = { 
      activeItem: MenuOptions.Score,
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
              ? <Game />
            : activeItem === MenuOptions.Settings
              ? <Settings />
            : activeItem === MenuOptions.Score
              ? <Score />
            : <></>
          }
        </Segment>
      </div>
    )
  }
}
