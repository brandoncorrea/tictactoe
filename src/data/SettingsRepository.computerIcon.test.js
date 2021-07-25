import { PlayerIcon } from '../enums/PlayerIcon';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('Computer icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon('O');
  var icon = settings.getComputerIcon();
  expect(icon).toBe('O');
})

test('Computer Icon can be set to X', () => {
  var settings = new SettingsRepository();
  settings.setComputerIcon('X');
  var icon = settings.getComputerIcon();
  expect(icon).toBe('X');
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

test('GetComputerIcon defaults to O when neither User or Computer icons exist', () => {
  localStorage.removeItem(StorageNames.ComputerIcon);
  localStorage.removeItem(StorageNames.UserIcon);
  var settings = new SettingsRepository();
  var computerIcon = settings.getComputerIcon();
  expect(computerIcon).toBe(PlayerIcon.O);
})
