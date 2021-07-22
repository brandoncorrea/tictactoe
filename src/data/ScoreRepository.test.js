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

test('Add wins sets to 1 when not found', () => {
  var score = new ScoreRepository();
  localStorage.removeItem(StorageNames.Wins);
  score.addWin();
  expect(score.getWins()).toBe(1);
})

test('Can add wins to 2', () => {
  var score = new ScoreRepository();
  score.setWins(0);
  score.addWin();
  score.addWin();
  expect(score.getWins()).toBe(2);
})

test('Add draws sets to 1 when not found', () => {
  var score = new ScoreRepository();
  localStorage.removeItem(StorageNames.Draws);
  score.addDraw();
  expect(score.getDraws()).toBe(1);
})

test('Can add draws to 2', () => {
  var score = new ScoreRepository();
  score.setDraws(0);
  score.addDraw();
  score.addDraw();
  expect(score.getDraws()).toBe(2);
})

test('Add losses sets to 1 when not found', () => {
  var score = new ScoreRepository();
  localStorage.removeItem(StorageNames.Losses);
  score.addLoss();
  expect(score.getLosses()).toBe(1);
})

test('Can add losses to 2', () => {
  var score = new ScoreRepository();
  score.setLosses(0);
  score.addLoss();
  score.addLoss();
  expect(score.getLosses()).toBe(2);
})