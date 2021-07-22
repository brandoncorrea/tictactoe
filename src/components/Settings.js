import { Component } from "react";
import { Form, Radio, Dropdown } from "semantic-ui-react";
import SettingsRepository from "../data/SettingsRepository";
import { FirstPlayer } from "../enums/FirstPlayer";
import { PlayerIcon } from "../enums/PlayerIcon";

export default class Settings extends Component {

  settings = new SettingsRepository();
  iconOptions = [
    { key: PlayerIcon.X, text: PlayerIcon.X, value: PlayerIcon.X },
    { key: PlayerIcon.O, text: PlayerIcon.O, value: PlayerIcon.O },
  ]

  constructor(props) {
    super(props);
    this.state = {
      firstPlayer: this.settings.getFirstPlayer(),
      selectedIcon: this.settings.getUserIcon(),
    }

    this.handleFirstPlayerChange = this.handleFirstPlayerChange.bind(this);
    this.handleIconChange = this.handleIconChange.bind(this);
  }

  handleFirstPlayerChange = (e, target) => {
    this.settings.setFirstPlayer(target.value);
    this.setState({ firstPlayer: this.settings.getFirstPlayer() });
  }
  
  handleIconChange = (e, target) => {
    this.settings.setUserIcon(target.value);
    this.setState({ selectedIcon: this.settings.getUserIcon() });
  }

  render = () =>
    <Form>
      <Form.Group inline>
        <label>Who goes first?</label>
        <Form.Field
          control={Radio}
          label='I will go first'
          value={FirstPlayer.User}
          checked={this.state.firstPlayer === FirstPlayer.User}
          onChange={this.handleFirstPlayerChange}
          />
        <Form.Field
          control={Radio}
          label='The Computer goes first'
          value={FirstPlayer.Computer}
          checked={this.state.firstPlayer === FirstPlayer.Computer}
          onChange={this.handleFirstPlayerChange}
          />
      </Form.Group>
      <Form.Group inline>
        <Form.Field>
          <label>Choose an Icon</label>
          <Dropdown selection
            options={this.iconOptions}
            value={this.state.selectedIcon} 
            onChange={this.handleIconChange}/>
        </Form.Field>
      </Form.Group>
    </Form>

}