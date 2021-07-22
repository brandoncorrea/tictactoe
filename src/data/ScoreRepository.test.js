import { StorageNames } from "../enums/StorageNames"
import ScoreRepository from './ScoreRepository';

test('Returns 0 when Wins not found', () => {
  localStorage.removeItem(StorageNames.Wins);
  var score = new ScoreRepository();
  expect(score.getWins()).toBe(0);
})

test('Returns 0 when Losses not found', () => {
  localStorage.removeItem(StorageNames.Losses);
  var score = new ScoreRepository();
  expect(score.getLosses()).toBe(0);
})

test('Returns 0 when Losses not found', () => {
  localStorage.removeItem(StorageNames.Draws);
  var score = new ScoreRepository();
  expect(score.getDraws()).toBe(0);
})

test('Set Wins to 1', () => {
  var score = new ScoreRepository();
  score.setWins(1);
  expect(score.getWins()).toBe(1);
})

test('Set Losses to 1', () => {
  var score = new ScoreRepository();
  score.setLosses(1);
  expect(score.getLosses()).toBe(1);
})

test('Set Draws to 1', () => {
  var score = new ScoreRepository();
  score.setDraws(1);
  expect(score.getDraws()).toBe(1);
})
