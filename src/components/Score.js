import { Component } from "react";
import { Divider, Header, Segment, Statistic } from "semantic-ui-react";
import ScoreRepositoryPvE from "../data/ScoreRepositoryPvE";
import ScoreRepositoryPvP from "../data/ScoreRepositoryPvP";

export default class Score extends Component {

  getScoreItems = repo => [
    { key: 'wins', label: 'Wins', value: repo.getWins().toLocaleString() },
    { key: 'draws', label: 'Draws', value: repo.getDraws().toLocaleString() },
    { key: 'losses', label: 'Losses', value: repo.getLosses().toLocaleString() },
  ]

  render = () => 
  <Segment>
    <Header 
      as='h2' 
      content='Player vs Computer' 
      textAlign='left' />
    <Statistic.Group 
      widths='3'
      size='big' 
      items={this.getScoreItems(new ScoreRepositoryPvE())}/>
    <Divider />
    <Header 
      as='h2' 
      content='Player vs Player' 
      textAlign='left' />
    <Statistic.Group 
      widths='3'
      size='big' 
      items={this.getScoreItems(new ScoreRepositoryPvP())}/>
  </Segment>
}