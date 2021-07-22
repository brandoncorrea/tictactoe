import { Component } from "react";
import { Form, Radio, Dropdown } from "semantic-ui-react";
import { FirstPlayer } from "../enums/FirstPlayer";
import { PlayerIcon } from "../enums/PlayerIcon";

export default class Settings extends Component {

  iconOptions = [
    { key: PlayerIcon.X, text: PlayerIcon.X, value: PlayerIcon.X },
    { key: PlayerIcon.O, text: PlayerIcon.O, value: PlayerIcon.O },
  ]

  constructor(props) {
    super(props);
    this.state = {
      firstPlayer: FirstPlayer.User,
      selectedIcon: this.iconOptions[0].value
    }

    this.handleFirstPlayerChange = this.handleFirstPlayerChange.bind(this);
    this.handleIconChange = this.handleIconChange.bind(this);
  }

  handleFirstPlayerChange = (e, target) =>
    this.setState({ firstPlayer: target.value });
  handleIconChange = (e, target) =>
    this.setState({ selectedIcon: target.value });

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