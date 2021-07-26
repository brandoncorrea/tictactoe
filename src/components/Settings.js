import { Component } from "react";
import { Container } from "semantic-ui-react";
import FirstPlayerSetting from "./FirstPlayerSetting";
import IconSetting from "./IconSetting";
import GameModeSetting from './GameModeSetting'

export default class Settings extends Component {

  render = () =>
    <Container>
      <GameModeSetting />
      <FirstPlayerSetting />
      <IconSetting />
    </Container>

}