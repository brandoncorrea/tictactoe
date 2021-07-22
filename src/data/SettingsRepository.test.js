import { FirstPlayer } from '../enums/FirstPlayer';
import { PlayerIcon } from '../enums/PlayerIcon';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('User Icon can be set X and O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon('X');
  var icon = settings.getUserIcon();
  expect(icon).toBe('X');
})

test('Computer Icon can be set to X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon('X');
  var icon = settings.getComputerIcon();
  expect(icon).toBe('X');
})

test('User icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon('O');
  var icon = settings.getUserIcon();
  expect(icon).toBe('O');
})

test('Computer icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon('O');
  var icon = settings.getComputerIcon();
  expect(icon).toBe('O');
})

test('Computer Icon changes to O when User is set to X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon(PlayerIcon.X);
  settings.setUserIcon(PlayerIcon.X);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.O);
  expect(settings.getUserIcon()).toBe(PlayerIcon.X);
})

test('Computer Icon changes to X when User is set to O', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon(PlayerIcon.O);
  settings.setUserIcon(PlayerIcon.O);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.X);
  expect(settings.getUserIcon()).toBe(PlayerIcon.O);
})


test('User Icon changes to O when User is set to X', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon(PlayerIcon.X);
  settings.setComputerIcon(PlayerIcon.X);
  expect(settings.getUserIcon()).toBe(PlayerIcon.O);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.X);
})

test('User Icon changes to X when User is set to O', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon(PlayerIcon.O);
  settings.setComputerIcon(PlayerIcon.O);
  expect(settings.getUserIcon()).toBe(PlayerIcon.X);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.O);
})

test('User Icon defaults to X when no value exists Computer is not X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon('O');
  localStorage.removeItem(StorageNames.UserIcon);
  var icon = settings.getUserIcon();
  expect(icon).toBe(PlayerIcon.X);
})

test('User Icon defaults to O when no value exists and Computer is X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon(PlayerIcon.X);
  localStorage.removeItem(StorageNames.UserIcon);
  expect(settings.getUserIcon()).toBe(PlayerIcon.O);
})

test('Computer Icon defaults to X when no value exists User is not X', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon('O');
  localStorage.removeItem(StorageNames.ComputerIcon);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.X);
})

test('Computer Icon defaults to O when no value exists and User is X', () => {
  var settings = new SettingsRepository();
  settings.setUserIcon(PlayerIcon.X);
  localStorage.removeItem(StorageNames.ComputerIcon);
  expect(settings.getComputerIcon()).toBe(PlayerIcon.O);
})

test('GetFirstPlayer defaults to Computer when no value exists', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.FirstPlayer);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.Computer);
})

test('SetFirstPlayer can be assigned to Computer', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(FirstPlayer.Computer);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.Computer);
})

test('SetFirstPlayer can be assigned to User', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(FirstPlayer.User);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.User);
})