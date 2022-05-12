import { StorageNames } from "../enums/StorageNames"
import ScoreRepository from './ScoreRepository';

const score = new ScoreRepository(
  StorageNames.WinsPvE,
  StorageNames.LossesPvE,
  StorageNames.DrawsPvE);

test('Returns 0 when Wins not found', () => {
  localStorage.removeItem(StorageNames.WinsPvE);
  expect(score.getWins()).toBe(0);
})

test('Returns 0 when Losses not found', () => {
  localStorage.removeItem(StorageNames.LossesPvE);
  expect(score.getLosses()).toBe(0);
})

test('Returns 0 when Losses not found', () => {
  localStorage.removeItem(StorageNames.DrawsPvE);
  expect(score.getDraws()).toBe(0);
})

test('Set Wins to 1', () => {
  score.setWins(1);
  expect(score.getWins()).toBe(1);
})

test('Set Losses to 1', () => {
  score.setLosses(1);
  expect(score.getLosses()).toBe(1);
})

test('Set Draws to 1', () => {
  score.setDraws(1);
  expect(score.getDraws()).toBe(1);
})

test('Add wins sets to 1 when not found', () => {
  localStorage.removeItem(StorageNames.WinsPvE);
  score.addWin();
  expect(score.getWins()).toBe(1);
})

test('Can add wins to 2', () => {
  score.setWins(0);
  score.addWin();
  score.addWin();
  expect(score.getWins()).toBe(2);
})

test('Add draws sets to 1 when not found', () => {
  localStorage.removeItem(StorageNames.DrawsPvE);
  score.addDraw();
  expect(score.getDraws()).toBe(1);
})

test('Can add draws to 2', () => {
  score.setDraws(0);
  score.addDraw();
  score.addDraw();
  expect(score.getDraws()).toBe(2);
})

test('Add losses sets to 1 when not found', () => {
  localStorage.removeItem(StorageNames.LossesPvE);
  score.addLoss();
  expect(score.getLosses()).toBe(1);
})

test('Can add losses to 2', () => {
  score.setLosses(0);
  score.addLoss();
  score.addLoss();
  expect(score.getLosses()).toBe(2);
})