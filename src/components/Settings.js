import { Component } from "react";
import { Form } from "semantic-ui-react";
import FirstPlayerSetting from "./FirstPlayerSetting";
import IconSetting from "./IconSetting";
import GameModeSetting from './GameModeSetting'

export default class Settings extends Component {

  render = () =>
    <Form>
      <GameModeSetting />
      <FirstPlayerSetting />
      <IconSetting />
    </Form>

}