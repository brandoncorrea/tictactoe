import { PlayerIcon } from '../enums/PlayerIcon';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('Player 1 Icon can be set X and O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1('X');
  var icon = settings.getIconPlayer1();
  expect(icon).toBe('X');
})

test('Player 1 icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1('O');
  var icon = settings.getIconPlayer1();
  expect(icon).toBe('O');
})

test('Player 1 Icon changes to O when Player 1 is set to X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1(PlayerIcon.X);
  settings.setIconPlayer2(PlayerIcon.X);
  expect(settings.getIconPlayer1()).toBe(PlayerIcon.O);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.X);
})

test('Player 1 Icon changes to X when Player 2 is set to O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1(PlayerIcon.O);
  settings.setIconPlayer2(PlayerIcon.O);
  expect(settings.getIconPlayer1()).toBe(PlayerIcon.X);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.O);
})

test('Player 1 Icon defaults to X when no value exists and Player 2 is not X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.O);
  localStorage.removeItem(StorageNames.IconPlayer1);
  var icon = settings.getIconPlayer1();
  expect(icon).toBe(PlayerIcon.X);
})

test('Player 1 Icon defaults to O when no value exists and Player 2 is X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.X);
  localStorage.removeItem(StorageNames.IconPlayer1);
  expect(settings.getIconPlayer1()).toBe(PlayerIcon.O);
})

test('Player 1 Icon defaults to X when neither Player 1 or 2 icons exist', () => {
  localStorage.removeItem(StorageNames.IconPlayer2);
  localStorage.removeItem(StorageNames.IconPlayer1);
  var settings = new SettingsRepository();
  var player1 = settings.getIconPlayer1();
  expect(player1).toBe(PlayerIcon.X);
})

test('Player 1 Icon can be set to something other than X and O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1('123');
  settings.setIconPlayer2('321');
  var player1 = settings.getIconPlayer1();
  var player2 = settings.getIconPlayer2();
  expect(player1).toBe('123');
  expect(player2).toBe('321');
})