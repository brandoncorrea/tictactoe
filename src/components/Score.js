import { Component } from "react";
import { Header, Statistic } from "semantic-ui-react";
import ScoreRepositoryPvE from "../data/ScoreRepositoryPvE";
import ScoreRepositoryPvP from "../data/ScoreRepositoryPvP";

export default class Score extends Component {

  getScoreItems = repo => [
    { key: 'wins', label: 'Wins', value: repo.getWins().toLocaleString() },
    { key: 'draws', label: 'Draws', value: repo.getDraws().toLocaleString() },
    { key: 'losses', label: 'Losses', value: repo.getLosses().toLocaleString() },
  ]

  render = () => 
  <>
    <Header 
      as='h2' 
      content='Player vs Computer' 
      textAlign='left' />
    <Statistic.Group 
      horizontal
      size='huge' 
      items={this.getScoreItems(new ScoreRepositoryPvE())}/>
    <Header 
      as='h2' 
      content='Player vs Player' 
      textAlign='left' />
    <Statistic.Group 
      horizontal
      size='huge' 
      items={this.getScoreItems(new ScoreRepositoryPvP())}/>
  </>
}