import { Component } from "react";
import { Container, Divider, Segment } from "semantic-ui-react";
import FirstPlayerSetting from "./FirstPlayerSetting";
import IconSetting from "./IconSetting";
import GameModeSetting from './GameModeSetting'
import TableSizeSetting from "./TableSizeSetting";

export default class Settings extends Component {

  render = () =>
    <Segment>
      <GameModeSetting />
      <Divider />
      <FirstPlayerSetting />
      <Divider />
      <IconSetting />
      <TableSizeSetting />
    </Segment>

}