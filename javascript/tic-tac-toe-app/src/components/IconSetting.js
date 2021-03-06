import { Component } from "react";
import { Form, Header, Input } from 'semantic-ui-react';
import SettingsRepository from "../data/SettingsRepository";

export default class IconSetting extends Component {
  settings = new SettingsRepository();

  constructor(props) {
    super(props);
    this.state = {
      player1: this.settings.getIconPlayer1(),
      player2: this.settings.getIconPlayer2()
    }

    this.handleChangePlayer1 = this.handleChangePlayer1.bind(this);
    this.handleChangePlayer2 = this.handleChangePlayer2.bind(this);
  }

  handleChangePlayer1(e, target) {
    if (target.value.length > 2)
      return

    // Only save items with a value
    if (target.value.length > 0)
      this.settings.setIconPlayer1(target.value);
    this.setState({ 
      player1: target.value,
      player2: this.settings.getIconPlayer2() 
    });
  }

  handleChangePlayer2(e, target) {
    if (target.value.length > 2)
      return

    // Only save items with a value
    if (target.value.length > 0)
      this.settings.setIconPlayer2(target.value);
    this.setState({ 
      player1: this.settings.getIconPlayer1(), 
      player2: target.value 
    });
  }

  render = () =>
    <Form>
      <Header as='h3' content='Icons' />
      <Form.Group widths='equal'>
        <Form.Field 
          control={Input}
          label='Your Icon'
          value={this.state.player1}
          onChange={this.handleChangePlayer1} 
          size='massive'
          />
        <Form.Field 
          control={Input}
          label='Opponent Icon'
          value={this.state.player2}
          onChange={this.handleChangePlayer2}  
          size='massive'
          />
      </Form.Group>
    </Form>
}
