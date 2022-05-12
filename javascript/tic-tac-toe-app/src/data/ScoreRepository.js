import { setItem, getNumber } from '../helpers/LocalStorageHelper';

// Generic implementation of ScoreRepository
export default class ScoreRepository {
  // Setters
  setWins = wins => setItem(this.storageNames.wins, wins);
  setLosses = losses => setItem(this.storageNames.losses, losses);
  setDraws = draws => setItem(this.storageNames.draws, draws);

  // Getters
  getWins = () =>  getNumber(this.storageNames.wins);
  getLosses = () => getNumber(this.storageNames.losses);
  getDraws = () => getNumber(this.storageNames.draws);

  // Adders
  addWin = () => this.setWins(this.getWins() + 1);
  addDraw = () => this.setDraws(this.getDraws() + 1);
  addLoss = () => this.setLosses(this.getLosses() + 1);

  constructor(
    winsStorageName,
    lossesStorageName,
    drawsStorageName
  ) {
    this.storageNames = {
      wins: winsStorageName,
      losses: lossesStorageName,
      draws: drawsStorageName
    };
  }
}