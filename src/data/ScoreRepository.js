import { StorageNames } from '../enums/StorageNames';
import { setItem, getNumber } from '../helpers/LocalStorageHelper';

export default class ScoreRepository {
  // Setters
  setWins = wins => setItem(StorageNames.Wins, wins);
  setLosses = losses => setItem(StorageNames.Losses, losses);
  setDraws = draws => setItem(StorageNames.Draws, draws);

  // Getters
  getWins = () =>  getNumber(StorageNames.Wins);
  getLosses = () => getNumber(StorageNames.Losses);
  getDraws = () => getNumber(StorageNames.Draws);
}